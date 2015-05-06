package com.chat.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;







import com.chat.misc.SDCardTools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class AccessNetwork {


	
	public static Bitmap getBitmapFromLocalOrNetWork(String name, NetworkPhotoTask params, AsyPublish asy) {
		
		if(asy == null){
			throw new NullPointerException("AsyPublish is not null");
		}
		
		return get(name, params, asy , 0);
	}
	
	private static boolean isGetNetWork( Context context , String name){
		
		if(!context.getFileStreamPath(name).isFile()){
			Log.i("AccessWork", "file '"+name+"' is not exist");
			return true;
		}
		
		// file is exist
		boolean b = false;
		InputStream is = null;
		try {
			is = context.openFileInput(name);
			if(is.available() == 0){
				b = true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		}
		if(b){
			//delete empty file 
			context.deleteFile(name);
			System.out.println("文件删除");
		}
		
		System.out.println(b);
		return b;
	}
	
	@SuppressLint("NewApi") private static Bitmap get(String name, NetworkPhotoTask params, AsyPublish asy , int installCount){


		Context context  = params.v.getContext();
		final int height = params.height;
	   	final int with = params.width;
		final String url = params.url;
		int scalingSize = params.scalingSize;
        final ImageView v = params.v;
        final int viewHeight = v.getHeight();
        final int viewWidth = v.getWidth();
        final int viewMaxHeight = v.getMaxHeight();
        final int viewMaxWidth = v.getMaxWidth();
        final boolean isNetwork = !params.isGetImageFromSdCard;
        
        OnLoaderImageCallback l = params.onLoaderImageCallback;
        
        
        
        if(isNetwork && isGetNetWork(context, name)){
        	String s = getBitmapFromNetwork(name , url ,asy , context);
        	Log.i("AccessNetwork", "get image from network");
        	if(s == null)  return null;
        }

		Bitmap bitmap = null;
		System.out.println(name);
		if (scalingSize > 1)
			bitmap = getBitmapOpt(context ,name, scalingSize , asy , params , installCount , isNetwork);
		else if(with > 0 || height > 0)  bitmap = getBitmapOpt(context ,name, with , height , asy, params, installCount, isNetwork);
		else if(viewMaxHeight != Integer.MAX_VALUE && viewMaxHeight != Integer.MAX_VALUE ){
            bitmap = getBitmapOpt(context ,name , viewMaxWidth , viewMaxHeight , asy, params, installCount, isNetwork);
        }else bitmap = getBitmapOpt(context ,name , viewWidth ,viewHeight , asy, params, installCount, isNetwork);
		
		//deal bitmap , the image set round or round corner
		bitmap = dealBitmap(bitmap, params);
		
		if(l != null){
			Bitmap bm = l.onCallback(bitmap , name);
			if(bm != null && bm != bitmap) bitmap = bm; 
		}
		
		asy.publishProgressDevelopment(100);
		
		return bitmap;
	}
	
	private static Bitmap dealBitmap(Bitmap bitmap , NetworkPhotoTask task){
		if(bitmap == null) return null;
		Bitmap bm = null;
		
		//The image is set to the circle
		if(task.isSetRounded){
			bm = ImageUtil.getRoundedBitmap(bitmap);
		}else if(task.roundedCornersSize > 0){
			// The image is set to the rounded Corner
			bm = ImageUtil.toRoundCorner(bitmap, task.roundedCornersSize);
		}
		
		if(bm != null){
			//recycle bitmap
			ImageUtil.recycle(bitmap);
		}else bm = bitmap;
		
		
		return bm;
	}
	
	

	/*
	 * get bitmap from network
	 */
	 private static String getBitmapFromNetwork(String filename , String url ,AsyPublish asy , Context context) {
		 URL u; 
		 HttpURLConnection conn = null; 
		 InputStream in = null; 
		 OutputStream out = null; 
		 //is delete name
		 int progress = 0;
		 try { 
			 u = new URL(url); 
			 System.out.println(url);
			 conn = (HttpURLConnection) u.openConnection(); 
			 conn.setDoInput(true); 
			 conn.setDoOutput(false); 
			 conn.setConnectTimeout(20000); 
			 in = conn.getInputStream(); 
			 out = context.openFileOutput(filename, Context.MODE_PRIVATE); 
			 
//			 getStartHeightWidth(in);
			 
			 byte[] buf = new byte[8192]; 
			 int seg = 0; 
			 final long total = conn.getContentLength(); 
			 long current = 0; 
			 
			 while ( (seg = in.read(buf)) != -1 && isNext(asy) ) { 
				 out.write(buf, 0, seg); 
				 current += seg; 
				 progress = (int) ((float) current / (float) total * 100f); 
				 asy.publishProgressDevelopment(progress);
				 SystemClock.sleep(10);
				 
			 } 
			 
		 } catch(Exception e){
			 e.printStackTrace();
			 context.deleteFile(filename);
			 filename = null;
		 }finally { 
			 
			 try {
				 if (in != null) { 
					 in.close();
					 in = null;
				 } 
				 if (out != null){
					 out.close();
					 out = null;
				 }
			 } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 } 
			 if (conn != null) { 
				 conn.disconnect(); 
				 conn = null;
			 } 
			 if(filename == null) return null;
			 if(progress != 100 && !isNext(asy)){
				 //delete file
				 context.deleteFile(filename);
			 }else{
				 Log.i("AccessNetwork", "get image from network");
				 return filename;
			 }
		 }
		 return null;
	 }


	 
	 private static boolean isNext(AsyPublish asy){
		 return asy != null && ! asy.isCancelleds();
	 }


	 private static Bitmap getBitmapOpt(Context context ,String fileName, int width, 
			 						int height , AsyPublish asy , NetworkPhotoTask params , int postion , boolean isNetwork) {
		 if(fileName == null || fileName.trim().length() == 0) return null;
		 Options opts = new Options();
		
		 int scalingSize = 1;
		 InputStream inputStream = null;
		 try {
			 if(isNetwork){
				 inputStream = context.openFileInput(fileName);
			 }else{
				 inputStream = new FileInputStream(params.url);
			 }
			 if (width > 0 && height > 0) {
				 opts.inJustDecodeBounds = true;
				 BitmapFactory.decodeStream(inputStream, null, opts);
				 
				 int sampleSize = Math.max(opts.outHeight, opts.outWidth) 
						 / Math.max(width, height);
				 scalingSize = sampleSize < 1 ? 1 : sampleSize;
				 
			 }
		 } catch (FileNotFoundException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }finally{
			 if(inputStream != null){
				 try {
					 inputStream.close();
                     inputStream = null;
				 } catch (IOException e) {
					 // TODO Auto-generated catch block
					 e.printStackTrace();
				 }
			 }
		 }
          Log.i("AccessNetWork","scaling = "+scalingSize);
		 return getBitmapOpt(context , fileName , scalingSize , asy , params , postion , isNetwork);
	 }

	 public static Bitmap getBitmapOpt(Context context ,String fileName, int sampleSize , 
			 AsyPublish asy , NetworkPhotoTask params , int postion , boolean isNetwork) {
		 if(fileName == null || fileName.trim().length() == 0) return null;
		
		 Options opts = new Options();
		 InputStream inputStream = null;
		 Bitmap bitmap = null;
		 boolean is = false;
		 try {
			 if(isNetwork){
				 inputStream = context.openFileInput(fileName);
			 }else{
				 inputStream = new FileInputStream(params.url);
			 }
			 if(inputStream.available() != 0){
				 opts.inJustDecodeBounds = false;
				 opts.inSampleSize = sampleSize;
				 bitmap = BitmapFactory.decodeStream(inputStream, null, opts);
			 }else is = true;
		 } catch (FileNotFoundException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 } catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			 is = true;
		 }finally{
			 try {
				 if(inputStream != null){
					 inputStream.close();
					 inputStream = null;
				 }

			 } catch (Exception e2) {
				 // TODO: handle exception
				 e2.printStackTrace();
			 }
		 }
		 
		 if(is || bitmap == null){
			 is = false;
			 if(isNetwork)
				 context.deleteFile(fileName);
			 if(postion <= 2)
				 return get(fileName, params, asy , postion + 1);
		 }
		 
		 return bitmap;
	 }
}

