package com.chat.misc;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

/**
 * 手机内存的SD Card工具
 * @author YJN
 *
 */
public class SDCardTools {
	
	/**
	 * 判断是否插入的SD Card
	 * @return 返回true 表示存在
	 */
	public static boolean isHave(){
		return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	/**
	 * 获取SD Card的地�?
	 * @return
	 */
	public static String getSDCardPath(){
		if(!isHave()) return null;
		return Environment.getExternalStorageDirectory().toString();  
	}
	
	
	/**
	 * 获得SD卡�?�大�?
	 * 
	 * @return
	 */
	public static String  getSDTotalSize(Context context) {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return Formatter.formatFileSize(context, blockSize * totalBlocks);
	}

	
	/**
	 * 获得sd卡剩余容量，即可用大�?
	 * 
	 * @return
	 */
	public static String getSDAvailableSize(Context context) {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return Formatter.formatFileSize(context, blockSize * availableBlocks);
	}

	/**
	 * 获得机身内存总大�?
	 * 
	 * @return
	 */
	public static String getRomTotalSize(Context context) {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return Formatter.formatFileSize(context, blockSize * totalBlocks);
	}

	/**
	 * 获得机身可用内存
	 * 
	 * @return
	 */
	public static String getRomAvailableSize(Context context) {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return Formatter.formatFileSize(context, blockSize * availableBlocks);
	}

	
}
