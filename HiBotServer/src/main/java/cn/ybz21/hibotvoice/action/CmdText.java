package cn.ybz21.hibotvoice.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class CmdText {
	//private static Map<String,String> CmdMap = new HashMap<String,String>();
	private static String filePath = "./file/speechCmd.txt";//语音命令对应存储文件
	public static Map<String,String> readTextFile() {
		Map<String,String> CmdMap = new HashMap<String,String>();
		try{
			String encoding = "UTF-8";
			File file = new File(filePath);
			
			if(file.isFile() && file.exists()){ // 判断文件是否存在

				InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);// 考虑到编码格式
				//将字节流向字符流的转换。
				BufferedReader rd = new BufferedReader(read);
				String lineTxt = null;
				while((lineTxt = rd.readLine()) != null){
					if (!" ".equals(lineTxt)) {
						String[] reads = lineTxt.split(":");
					  System.out.println(reads.length);
						for(int i =0;i<reads.length;i++ ){
							try{
							System.out.println(reads[i]+i);
							}catch(Exception e){
								e.printStackTrace();
								System.out.println("又出错了！！！");
							}
						}
						CmdMap.put(reads[0],reads[1]);
					}
				}
				System.out.println(CmdMap.size());
				read.close();
				rd.close();

			}
			else{
				System.out.println( "找不到指定文件");
			}
		}catch(Exception e){
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return CmdMap;
	}
	
}
