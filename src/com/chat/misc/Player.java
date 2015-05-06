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
 * ��Ƶ����
 * @author �ٴ�����
 *
 */



public class Player implements OnCompletionListener {
	/**
	 * �ļ���ַ
	 */
	private String path = "";
	
	/**
	 * ���Ŵ���Ƶ����
	 */
	private MediaPlayer player = null;
	
	/**
	 *�����
	 */
	private Context activity = null;
	
	/**
	 * �ļ�
	 */
	private File file = null;
	
	/**
	 * ��Ƶ��Id
	 */
	private int ResourceId = 0;
	
	/**
	 * ����һ���е���Ƶ
	 */
	private int ResorceIdArrays[] = null;
	/**
	 * С��Ƶ����
	 */
	private SoundPool soundPool = null;
	
	/**
	 * ��Ŷ���
	 */
	private HashMap<Integer, Integer> soundmap =
			                                             new HashMap<Integer, Integer>();
	
	/**
	 * С��Ƶ�ĸ���
	 */
	private int SmallNum = 0;
	
	/**
	 * ���ŷ�uri
	 */
	private Uri uri = null;
	
	/**
	 * �Ƿ��ڲ���
	 */
	private boolean isBoFang = false;
	
	/**
	 * ���캯��
	 * @param activity
	 */
	public Player(Context activity){
		this.activity = activity;
	}
	
	/**
	 * ���췽��
	 * @param ��Ƶ��ַ
	 */
	public Player(String path,Context activity){
		this(activity);
		this.path = path;
	}
	
	/**
	 * ���ű���������ڵ��ļ�
	 * @param ResorceId  ��Ƶ��Id
	 * @param activity  
	 */
	public Player(int ResorceId ,Context activity){
		this(activity);
		this.ResourceId = ResorceId;
	}
	
	/**
	 * ���ű���������ڵ������
	 * @param ResorceIdArrays  ��Ƶ����
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
	 * ����
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
	 * �رղ���
	 */
	public void close(){
		isBoFang = false;
		if(player != null)
			player.stop();
	}
		
	/**
	 * ����Ƶ�Ĳ���
	 */
	public void Largerplay(){
		//Ԥ������Ƶ
		install();
		isBoFang = true;
		if(player != null){
			player.start();
			isBoFang = false;
		}
	}
	
	/**
	 * С��Ƶ�Ĳ���
	 */
	public void SmallPlayer(){
		this.SmallPlayer(0);
	}
	
	/**
	 * ����С��Ƶ������Ǹ��ݴ����Id�������жϵ�
	 * @param index  ���ŵĵ�ĳһ��
	 */
	public void SmallPlayer(int index){
		LoadYingp();
		soundPool.play(soundmap.get(index), 1 , 1 , 0 , 0, 1);
	}
	
	/**
	 * ��ȡ���ŵ�ʱ��
	 * @return
	 */
	public int getPlayTime(){
		if(player == null) return 0;
		return player.getDuration();
	}
	
	/**
	 * ��ȡ���ŵ�ʱ��
	 * @param context
	 * @param pathName �ļ���ַ
	 * @return ���ز��ŵ�ʱ��
	 */
	public static int getPlayTime(Context context , String pathName){
		
		MediaPlayer player = MediaPlayer.create(context, Uri.parse(pathName));
		if(player == null) return 0;
		int time = player.getDuration();
		player = null;
		return time;
	}
	
	
	/**
	 * ����С��Ƶ
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
	 * ��ʼ��������
	 */
	private void install(){
		if(player == null){
			player = MediaPlayer.create(activity, Uri.parse(path));
			Log.i("Player",path+"   �ļ���ַ");
		}
		if(player != null){
		//ע�����
			player.setOnCompletionListener(this);
		}else Log.i("Player","player��ȡ�ļ�����");
		
		
	}

	/**
	 * �Ƿ��ڲ���
	 * @return
	 */
	public boolean isPlay(){
		return isBoFang;
	}
	
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		//�������
		isBoFang = false;
	}
	

	
}
