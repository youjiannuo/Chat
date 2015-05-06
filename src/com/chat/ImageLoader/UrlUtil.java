package com.chat.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class UrlUtil {


    
    public static void createFile(String url){

    	File f = new File(url.substring(0, getFromUrlToFileNamePostion(url)));
    	if(!f.exists()){
    		f.mkdirs();
    	}
    	f = new File(url);
    	if(!f.isFile()){
    		try {

    			f.createNewFile();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}

    }
    //����վ�����������Ӧ��?
    @SuppressWarnings("deprecation")
	public static String getFromURLToFileName(String url) {

    	int a = getFromUrlToFileNamePostion(url);
    	if(a == -1)  return new Date().getSeconds() + "";

    	return url.substring(a + 1, url.length());

    }

    private static int getFromUrlToFileNamePostion(String url){
    	if (url == null || url.trim().length() == 0) return -1;

    	int a = url.lastIndexOf('\\');
    	int b = url.lastIndexOf('/');

    	return a > b ? a : b;
    }

    //��ȡû�к�׺���?
    @SuppressWarnings("deprecation")
	private static String getFromURLFileNameNotSuffixs(String name){
    	if(name == null || name.trim().length() == 0) return ""+new Date().getSeconds();
    	int i = name.lastIndexOf('.');
    	return i < 0 ? name : name.substring(0 , i);
    }

    public static String getFromURLFileNameNotSuffix(String url){
    	return getFromURLFileNameNotSuffixs(getFromURLToFileName(url));
    }



}
