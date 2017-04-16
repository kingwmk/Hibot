package cn.ybz21.hibotvoice.action;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

import cn.ybz21.hibotvoice.bean.ResultCode;
import cn.ybz21.hibotvoice.util.DateUtil;
import cn.ybz21.hibotvoice.util.FileUtil;
import cn.ybz21.hibotvoice.util.ResponseUtil;
import cn.ybz21.hibotvoice.util.StaticValues;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class UPloadAction extends ActionSupport implements 	ServletResponseAware {


	private static final long serialVersionUID = 1409037081277613450L;
	private List<File> files;
	private List<String> uploadPaths;

	private HttpServletResponse response;

	public void doAction() {
		System.out.println("上传的文件=" + files.size());//客户端只储存一条语音命令。
		//2.打印文件名
		for (File f : files)
			System.out.println(f.getName());
		//3.打印文件上传路径
		for (String p : uploadPaths)
			System.out.println(p);

		saveFiles();//4.打印
		ResultCode resultCode = new ResultCode();
		resultCode.result=1;
		//用Gson将java对象转成json对象,序列化
		Gson gson = new Gson();
		String jsonData = gson.toJson(resultCode);
		new ResponseUtil().Response(response, jsonData);
	}

	String saveFiles() {
		String pathsReal[] = new String[files.size()];
		for (int i = 0; i < files.size(); i++) {
			String oldPath = files.get(i).getPath();
			UUID uuid = UUID.randomUUID();
			String name = new DateUtil().getNowFormatDate("yyyy-MM-dd-HHmmss")
					+ "_" + uuid.toString() + "."
					+ FileUtil.getExtensionName(uploadPaths.get(i));
			pathsReal[i] = StaticValues.PATH_ATTACH + "/" + name;
			
			System.out.println(pathsReal[i] +"\t"+i);//4.打印文件实际存储路径 i始终都是0，只有一个文件上传
			FileUtil.copyFile(oldPath, pathsReal[i]);
			
			//调用百度云，语音识别
			try {
			  Sample.uploadFile(pathsReal[i]);
			  //VoiceNav spe = new VoiceNav();
			  //spe.move("向右转，");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;

	}

	public List<String> getUploadPaths() {
		return uploadPaths;
	}

	public void setUploadPaths(List<String> uploadPaths) {
		this.uploadPaths = uploadPaths;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	
	public HttpServletResponse getServletResponse() {
		return response;
	}

	//@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

}