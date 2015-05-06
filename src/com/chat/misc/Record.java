package com.chat.misc;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.media.MediaRecorder;
import android.widget.Toast;

/**
 * 录音
 * @author YJN
 *
 */
public class Record extends MediaRecorder {
	
	private Context context = null;
	
	/**
	 * 地址
	 */
	private String path = "";
	

	/**
	 * 录音的名�? 
	 */
	private String name = "";
	
	
	public Record(Context context){
		this.context = context;
	}
	
	public String getPath() {
		return path;
	}


	public String getName() {
		return name;
	}


	
	
	/**
	 * �?始录�?
	 * @param path 地址
	 * @param name 名称
	 * record file system add string to name
	 */
	public void startRecord(String path,String name){
		if(path.length() == 0 || path == null 
				|| name.length() == 0 || name == null){
			Toast.makeText(context, "异常错误", Toast.LENGTH_SHORT).show();
			return;
		}
		if(!SDCardTools.isHave()){
			Toast.makeText(context, "请插入SD卡", Toast.LENGTH_SHORT).show();
			return;
		}
		this.path = path;
		
		File f = new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		
		File myRecAudioFile  = null;
		try {
			myRecAudioFile = File.createTempFile(name, ".amr", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* 设定录音来源为麦克风 */  
		setAudioSource(MediaRecorder.AudioSource.MIC);  
		setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);  
		setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);  
		this.name = myRecAudioFile.getName();
	
		//有错�?
		setOutputFile(myRecAudioFile  
				.getAbsolutePath());  
		
		try {
			prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		start(); 
	}
	
	
	public void stopRecord(){
		
		 //设置后不会崩
        setOnErrorListener(null);
        setPreviewDisplay(null);
		try{
			stop();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		release();
	}
	
	
}
