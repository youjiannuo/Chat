package com.chat.misc;

import java.io.ByteArrayOutputStream;
import java.io.File;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;



/**
 * 从手机里面获取照片
 * @author youjiannuo
 *	@time 2014.12.12
 */


public class PhotoAndCamera {

	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果

	public static final String IMAGE_UNSPECIFIED = "image/*";
	private Activity activity = null;
	public String mAppName = "";

	public static  String nameFile;

	public PhotoAndCamera( Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

	public void takePhoto(){
		Intent intent = null;
		intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		nameFile = new Date().getTime()+".png";

		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(), nameFile)));
		activity.startActivityForResult(intent, PHOTOHRAPH);
	}

	public void choosePhoto(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				IMAGE_UNSPECIFIED);
		activity.startActivityForResult(intent, PHOTOZOOM);
	}


	/*
	 * 获取照片
	 */
	public Message  getPicture(int requestCode, int resultCode, Intent data){
		if (resultCode == NONE)
			return null ;

		// 拍照
		if (requestCode == PHOTOHRAPH) {
			Message msg = new Message();
			msg.path = Environment.getExternalStorageDirectory()
					+"/"+nameFile;
			// 设置文件保存路径这里放在跟目录下
			//			File picture = new File(msg.path);
			return msg;
			//			startPhotoZoom(Uri.fromFile(picture));
		}

		if (data == null)
			return null ;

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			startPhotoZoom(data.getData()); 
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			Bundle extras = data.getExtras();
			//			Uri uri = data.getData();
			//			if(uri != null){
			//				Bitmap photo = null;
			//				try {
			//					photo = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
			//				} catch (FileNotFoundException e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				} catch (IOException e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				}
			//				ByteArrayOutputStream stream = new ByteArrayOutputStream();
			//				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
			////																		 100)压缩文件
			//				Date date = new Date();
			//				String url = SDCardTools.getSDCardPath()+"/JXSD"+"/"+ date.getTime()+".png";
			//				ImageTools.savePhotoToSDCard(photo, SDCardTools.getSDCardPath()+"/JXSD", date.getTime()+"");
			//				msg.path = url;
			//				msg.bitmap = photo;
			//				return msg;
			//			}
			if (extras != null) {
				Message msg = new Message();
				Bitmap photo = extras.getParcelable("data");
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);// (0 -
				// 100)压缩文件
				Date date = new Date();
				String url = SDCardTools.getSDCardPath()+"/"+getAppName()+"/"+ date.getTime()+".png";
				ImageTools.savePhotoToSDCard(photo, SDCardTools.getSDCardPath()+"/"+getAppName()+"/", date.getTime()+"");
				msg.path = url;
				msg.bitmap = photo;
				return msg;
			}
		}
		return null;
	}

	private String getAppName(){
		if(mAppName.length() != 0) return mAppName;

		PackageManager pm = activity.getPackageManager();
		return mAppName = activity.getApplicationInfo().loadLabel(pm).toString();
	}


	public class Message{

		public Bitmap bitmap;

		public String path;

	}

	//截取图片
	public void cropImage(Uri uri, int outputX, int outputY, int requestCode){
		Intent intent = new Intent("com.android.camera.action.CROP");  
		intent.setDataAndType(uri, "image/*");  
		intent.putExtra("crop", "true");  
		intent.putExtra("aspectX", 1);  
		intent.putExtra("aspectY", 1);  
		intent.putExtra("outputX", outputX);   
		intent.putExtra("outputY", outputY); 
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);  
		activity.startActivityForResult(intent, requestCode);
	}
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");


		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		activity.startActivityForResult(intent, PHOTORESOULT);
	}

}
