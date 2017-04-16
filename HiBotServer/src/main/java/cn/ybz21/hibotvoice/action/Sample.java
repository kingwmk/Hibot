package cn.ybz21.hibotvoice.action;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import cn.ybz21.hibotvoice.speech.json.JSONObject;

public class Sample {

	private static final String serverURL = "http://vop.baidu.com/server_api";
	private static String token = "";
	// private static final String testFileName =
	// "C:\\graduate design\\Baidu_Voice_RestApi_SampleCode\\sample\\aa.amr";
	// put your own params here
	private static final String apiKey = "ocwCP0AYlCkNy9f8KTS2xEUgT3z8ErNG";
	private static final String secretKey = "kmfaabVnT4rOsU4AgXqFg4OVFdUoNrB5";
	private static final String cuid = "E0-06-E6-F3-7D-6D";

	private static String filePath = "";

	static VoiceNav spe = new VoiceNav();
	private static void getToken() throws Exception {
		String getTokenURL = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials"
				+ "&client_id=" + apiKey + "&client_secret=" + secretKey;
		HttpURLConnection conn = (HttpURLConnection) new URL(getTokenURL)
				.openConnection();
		token = new JSONObject(printResponse(conn)).getString("access_token");
		System.out.println(token);
	}

	public static void uploadFile(String path) throws Exception {
		filePath = path;
		getToken();
		// upload();
		String resultString = upload();
		speechToMove(resultString);

	}

	public static void speechToMove(String resultString) throws Exception {
		// json转换java map对象
		Map<String, String> map = new JsonToJava()
				.getJsonToHashMap(resultString.toString());

		// 获取result对应的value值，字符串输出
        final String cmd = map.get("result");
	    //	final String cmd="向右转，";
		System.out.println("打印结果：Json to Map： \t" + cmd);
		//VoiceNav spe = new VoiceNav();
		spe.move(cmd);
		/*new Thread() {//星期五19：23注释
			public void run() {
				VoiceNav spe = new VoiceNav();
				spe.move(cmd);
			}
		}.start();*/
	}

	private static String upload() throws Exception {
		File pcmFile = new File(filePath);
		HttpURLConnection conn = (HttpURLConnection) new URL(serverURL)
				.openConnection();
		// 音频参数
		JSONObject params = new JSONObject();
		params.put("format", "amr");
		params.put("rate", 8000);
		params.put("channel", "1");
		params.put("token", token);
		params.put("cuid", cuid);
		params.put("len", pcmFile.length());
		params.put("speech",
				DatatypeConverter.printBase64Binary(loadFile(pcmFile)));
		// 识别英文,默认英文
		// params.put("lan", "en");

		// add request header
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/json; charset=utf-8");

		conn.setDoInput(true);
		conn.setDoOutput(true);

		// send request
		// 创建一个将数据写入指定输出流out的数据输出流。
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		wr.writeBytes(params.toString());// 将字符串按字节顺序写入到基本输出流中。
		wr.flush();
		wr.close();

		return printResponse(conn);

	}

	/**
	 * private static void method1() throws Exception { File pcmFile = new
	 * File(testFileName); HttpURLConnection conn = (HttpURLConnection) new
	 * URL(serverURL) .openConnection();
	 * 
	 * // construct params JSONObject params = new JSONObject();
	 * params.put("format", "amr"); params.put("rate", 8000);
	 * params.put("channel", "1"); params.put("token", token);
	 * params.put("cuid", cuid); params.put("len", pcmFile.length());
	 * params.put("speech",
	 * DatatypeConverter.printBase64Binary(loadFile(pcmFile)));
	 * 
	 * // add request header conn.setRequestMethod("POST");
	 * conn.setRequestProperty("Content-Type",
	 * "application/json; charset=utf-8");
	 * 
	 * conn.setDoInput(true); conn.setDoOutput(true);
	 * 
	 * // send request DataOutputStream wr = new
	 * DataOutputStream(conn.getOutputStream());
	 * wr.writeBytes(params.toString()); wr.flush(); wr.close();
	 * 
	 * printResponse(conn); }
	 ***/

	/**
	 * private static void method2() throws Exception { File pcmFile = new
	 * File(testFileName); HttpURLConnection conn = (HttpURLConnection) new
	 * URL(serverURL + "?cuid=" + cuid + "&token=" + token).openConnection();
	 * 
	 * // add request header conn.setRequestMethod("POST");
	 * conn.setRequestProperty("Content-Type", "audio/amr; rate=8000");
	 * 
	 * conn.setDoInput(true); conn.setDoOutput(true);
	 * 
	 * // send request DataOutputStream wr = new
	 * DataOutputStream(conn.getOutputStream()); wr.write(loadFile(pcmFile));
	 * wr.flush(); wr.close();
	 * 
	 * System.out.println(printResponse(conn)); }
	 ***/

	private static String printResponse(HttpURLConnection conn)
			throws Exception {
		if (conn.getResponseCode() != 200) {
			// request error
			return "";
		}
		InputStream is = conn.getInputStream();
		// //将字节流向字符流的转换。
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		// StringBuffer类中的方法主要偏重于对于字符串的变化，例如追加、插入和删除等，这个也是StringBuffer和String类的主要区别。
		StringBuffer response = new StringBuffer();
		// 一次读一行，读入null时文件结束//将字节流向字符流的转换。
		while ((line = rd.readLine()) != null) {
			// append()追加内容到当前StringBuffer对象的末尾，类似于字符串的连接。
			response.append(line);
			response.append('\r');// \r,回到当前行的行首
		}
		rd.close();
		// 下两句原本为一句,json对象转换为字符串，打印json格式的对象
		// JSONObject jsonObj = new JSONObject(response.toString());
		// System.out.println(jsonObj.toString(4));
		// System.out.println(response.toString());
		return response.toString();// 生成json字符串
	}

	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			is.close();
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		is.close();
		return bytes;
	}

}
