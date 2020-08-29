package com.lazywg.assembly.sql.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author: gaowang
 *
 * createTime:2018年1月29日 下午4:02:13
 *
 */
public final class FileUtil {

	private  FileUtil(){}

	public static boolean checkExist(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static boolean createDir(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			if (!file.exists()) {
				file.mkdirs();
			}
		} else {
			File parentDir = file.getParentFile();
			if (!parentDir.exists()) {
				parentDir.mkdirs();
			}
		}
		return true;
	}

	public static boolean createFile(String path) {
		if (!checkExist(path)) {
			try {
				createDir(path);
				File file = new File(path);
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 删除文件
	 *
	 * @param path
	 *            文件全路径
	 * @return
	 */
	public static boolean remove(String path) {
		if (!checkExist(path)) {
			return true;
		}
		File file = new File(path);
		return file.delete();
	}

	public static boolean writeFile(String txt, String path) {
		if (createFile(path)) {
			FileOutputStream outputStream = null;
			try {
				outputStream = new FileOutputStream(path);
				outputStream.write(txt.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				FileUtil.remove(path);
				return false;
			}
		}
		return false;
	}

	public static boolean isWindows() {
		String os = System.getProperty("os.name").toLowerCase();
		return os.indexOf("windows") != -1;
	}

	public static String combinPath(String path, String... args) {
		StringBuilder pathBuilder = new StringBuilder();
		String separator = "/";
		if (FileUtil.isWindows()) {
			separator = "\\";
			path = path.replace("/", separator);
		}
		if (path.endsWith(separator)) {
			path = path.substring(0, path.length() - separator.length());
		}
		pathBuilder.append(path);
		for (String item : args) {
			pathBuilder.append(separator);
			if (FileUtil.isWindows()) {
				item = item.replace("/", separator);
			}
			if (item.startsWith(separator)) {
				item = item.substring(1,item.length());
			}
			if (item.endsWith(separator)) {
				item = item.substring(0, item.length() - separator.length());
			}
			pathBuilder.append(item);
		}
		return pathBuilder.toString();
	}
}
