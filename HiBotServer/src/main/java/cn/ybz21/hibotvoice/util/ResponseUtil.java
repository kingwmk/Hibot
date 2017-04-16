package cn.ybz21.hibotvoice.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

	public void Response(HttpServletResponse response, String data) {
		response.setCharacterEncoding("UTF-8");

		//打印 格式化对象的表示 到文本输出流。
		PrintWriter writer = null;
		try {
			writer = response.getWriter();//getWriter返回PrintWriter对象，拿到流
		} catch (IOException e) {
			e.printStackTrace();
		}
		//写入一个字符串
		writer.write(data);
		writer.flush();
		writer.close();
	}
}
