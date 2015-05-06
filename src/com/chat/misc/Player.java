package com.chat.misc;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;







import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
import android.net.Uri;
import android.util.Log;

/**
 * 音频播放
 * @author 临川柴子
 *
 */



public class Player implements OnCompletionListener {
	/**
	 * 文件地址
	 */
	private String path = "";
	
	/**
	 * 播放大音频对象
	 */
	private MediaPlayer player = null;
	
	/**
	 *活动对象
	 */
	private Context activity = null;
	
	/**
	 * 文件
	 */
	private File file = null;
	
	/**
	 * 音频的Id
	 */
	private int ResourceId = 0;
	
	/**
	 * 播放一序列的音频
	 */
	private int ResorceIdArrays[] = null;
	/**
	 * 小音频播放
	 */
	private SoundPool soundPool = null;
	
	/**
	 * 存放对象
	 */
	private HashMap<Integer, Integer> soundmap =
			                                             new HashMap<Integer, Integer>();
	
	/**
	 * 小音频的个数
	 */
	private int SmallNum = 0;
	
	/**
	 * 播放发uri
	 */
	private Uri uri = null;
	
	/**
	 * 是否还在播放
	 */
	private boolean isBoFang = false;
	
	/**
	 * 构造函数
	 * @param activity
	 */
	public Player(Context activity){
		this.activity = activity;
	}
	
	/**
	 * 构造方法
	 * @param 音频地址
	 */
	public Player(String path,Context activity){
		this(activity);
		this.path = path;
	}
	
	/**
	 * 播放本身软件存在的文件
	 * @param ResorceId  音频的Id
	 * @param activity  
	 */
	public Player(int ResorceId ,Context activity){
		this(activity);
		this.ResourceId = ResorceId;
	}
	
	/**
	 * 播放本身软件存在的软件，
	 * @param ResorceIdArrays  音频序列
	 * @param activity
	 */
	public Player(int ResorceIdArrays[] ,Context activity){
		this(activity);
		this.ResorceIdArrays = ResorceIdArrays;
	}
	
	public Player( Uri uri, Context activity){
		this(activity);
		this.uri = uri;
	}
	
	/**
	 * 播放
	 */
	public void play(){
		if(player == null){
			if(uri == null) return;
			player = MediaPlayer.create(activity, uri);
		}
		player = MediaPlayer.create(activity, Uri.parse(path));
		try {
			isBoFang = true;
			player.start();
		} catch (Exception e) {
			// TODO: handle exception
			isBoFang = false;
			e.printStackTrace();
		}
	}

	/**
	 * 关闭播放
	 */
	public void close(){
		isBoFang = false;
		if(player != null)
			player.stop();
	}
		
	/**
	 * 大音频的播放
	 */
	public void Largerplay(){
		//预加载音频
		install();
		isBoFang = true;
		if(player != null){
			player.start();
			isBoFang = false;
		}
	}
	
	/**
	 * 小音频的播放
	 */
	public void SmallPlayer(){
		this.SmallPlayer(0);
	}
	
	/**
	 * 播放小音频，这个是根据传入的Id数组来判断的
	 * @param index  播放的第某一个
	 */
	public void SmallPlayer(int index){
		LoadYingp();
		soundPool.play(soundmap.get(index), 1 , 1 , 0 , 0, 1);
	}
	
	/**
	 * 获取播放的时间
	 * @return
	 */
	public int getPlayTime(){
		if(player == null) return 0;
		return player.getDuration();
	}
	
	/**
	 * 获取播放的时间
	 * @param context
	 * @param pathName 文件地址
	 * @return 返回播放的时间
	 */
	public static int getPlayTime(Context context , String pathName){
		
		MediaPlayer player = MediaPlayer.create(context, Uri.parse(pathName));
		if(player == null) return 0;
		int time = player.getDuration();
		player = null;
		return time;
	}
	
	
	/**
	 * 加载小音频
	 */
	private void LoadYingp(){
		if(soundPool == null){
			if(ResorceIdArrays == null){
				SmallNum = 1;
			}else SmallNum = ResorceIdArrays.length;
			
			soundPool = new SoundPool(SmallNum,
					                                        AudioManager.STREAM_SYSTEM, 0);
			if(ResorceIdArrays == null){
				soundmap.put( 0, soundPool.load(activity, ResourceId, 1));
			}else 
				for(int i = 0 ; i < SmallNum ;  i ++){
					soundmap.put( i , soundPool.load(activity, ResorceIdArrays[i], 1));
				}
		}
	}
	
	
	/**
	 * 初始化播放器
	 */
	private void install(){
		if(player == null){
			player = MediaPlayer.create(activity, Uri.parse(path));
			Log.i("Player",path+"   文件地址");
		}
		if(player != null){
		//注册监听
			player.setOnCompletionListener(this);
		}else Log.i("Player","player获取文件有误");
		
		
	}

	/**
	 * 是否在播放
	 * @return
	 */
	public boolean isPlay(){
		return isBoFang;
	}
	
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		//播放完成
		isBoFang = false;
	}
	

	
}
