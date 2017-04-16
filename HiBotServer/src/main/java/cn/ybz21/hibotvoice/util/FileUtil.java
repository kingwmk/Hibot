package cn.ybz21.hibotvoice.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 
 * @author wj
 * @email
 * @time 2016年11月15日
 */
public class FileUtil {

	public static void main(String[] args) {
		copyFile("C://Users/WeiJing/Desktop/风险分析.docx", "F://unzip/风险分析.docx");
		copyFolder("F://unzip", "C://Users/WeiJing/Desktop/unzip");
		getFileExtention("C://Users/WeiJing/Desktop/风险分析.docx");
	}

	/**
	 * 
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错!");
			e.printStackTrace();

		}
	}

	/**
	 * 
	 * @param oldPath
	 *            源文件路径（含名称）
	 * @param newPath
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错!");
			e.printStackTrace();
		}
	}


	/**
	 * 获取文件的扩展名
	 * 
	 * @param name
	 *            文件名（文件地址）
	 * @return
	 */
	public static String getFileExtention(String name) {
		File f = new File(name);
		String fileName = f.getName();
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		System.out.println(prefix);
		return prefix;
	}

	/**
	 * 写入文件
	 * 
	 * @param strs
	 * @param path
	 * @param name
	 */
	public static void writeFile(String strs[], String path, String name) {
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(new File(path + "/" + name)));
			for (String str : strs) {
				// 写文件
				bw.write(str, 0, str.length());
				// 刷新流
				bw.flush();
				// 换行
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭文件流
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static ArrayList<File> allFiles = new ArrayList<File>();

	/**
	 * 
	 * @param path 文件路径
	 * @return 获取文件名（不含后缀）
	 */
	public static String getFileName(String path) {

		String temp = getFileNameWithDot(path);
		return temp.substring(0, temp.lastIndexOf("."));
	}

	/**
	 * 
	 * @param path 文件路径
	 * @return 获取文件名带后缀
	 */
	public static String getFileNameWithDot(String path) {
		String temp[] = path.replaceAll("\\\\", "/").split("/");
		if (temp.length > 1) {
			path = temp[temp.length - 1];
		}
		return path;
	}
	
	/**
	 * 
	 * @param size 文件的大小 单位：B
	 * @return KB or MB
	 */
	public static String getFileSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		if (size / (1024 * 1024) >= 1) {//
			float m = (float) (size / (1024.0 * 1024.0));
			return "容量 ： " + df.format(m) + "MB";
		} else {
			float k = (float) (size / (1024.0));
			return "容量 ： " + df.format(k) + "KB";
		}
	}
/**
 * 删除文件（含文件夹）
 * @param path 文件路径
 */
	public static void deleteFolder(String path) {
		File fi = new File(path);
		getAllFiles(fi);
		while (allFiles.size() != 0) {
			for (int i = 0; i < allFiles.size(); i++) {
				File file = allFiles.get(i);
				if (file.isFile()) {
					boolean deleted = file.delete();
					if (deleted)
						allFiles.remove(i);
				} else if (file.isDirectory()) {
					try {
						boolean deleted = file.delete();
						if (deleted)
							allFiles.remove(i);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		}
	}
/**
 * 获取文件大小
 * @param path 文件路径
 * @return
 * @throws Exception
 */
	@SuppressWarnings("resource")
	public static long getFileSizes(String path) throws Exception {
		File f = new File(path);
		long s = 0;
		if (f.exists())
			s = new FileInputStream(f).available();
		return s;
	}

	private static void getAllFiles(File path) {
 ArrayList<File> allFiles = new ArrayList<File>();
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			allFiles.add(file);
			if (file.isDirectory()) {
				getAllFiles(file);
			}
		}

	}
	public static String getExtensionName(String filename) {   
        if ((filename != null) && (filename.length() > 0)) {   
            int dot = filename.lastIndexOf('.');   
            if ((dot >-1) && (dot < (filename.length() - 1))) {   
                return filename.substring(dot + 1);   
            }   
        }   
        return filename;   
    }   
}
