package com.chat.ImageLoader;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

public class ImageViewNetwork extends ImageView implements OnCecheNewBitmapAddListener{

	private Asy mAsy = null;
	private NetworkPhotoTask mParams;
	private OnPublishProgressListener mOnPublishProgressListener;
	private Bitmap mBitmap = null;

//	private int mHeight = -1;
//	private int mWidth = -1;


	public ImageViewNetwork(Context context){
		super(context);
	}
	public ImageViewNetwork(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}



	public void setImageUrl(String url){
		mParams = NetworkPhotoTask.build();
		mParams.url = url;
		setImageTask(mParams);
	}

	//add Task
	public void setImageTask(NetworkPhotoTask params){

		if(params == null){
			throw new NullPointerException("NetworkPhotoTask is not null");
		}
		init(params);

		if(mParams.viewMaxHeight != Integer.MAX_VALUE && mParams.viewMaxWidth != Integer.MAX_VALUE){
			setMaxHeight(mParams.viewMaxHeight);
			setMaxWidth(mParams.viewMaxWidth);
		}


		if(mAsy != null){
			mAsy.onCancelled();
			mAsy.cancel(true);
			mAsy = null;
		}

		// do bitmap get from cache
		if(mParams.isGetImageFromCache){
			Bitmap bitmap = getBitmapFromCache();
			if(bitmap != null){
				setLayoutParams(calc(bitmap));
				setImageBitmap(bitmap);
				Log.i("ImageViewNetwork","get image from cache");
				return;
			}
		}
		//add cache listener
		setCacheListener();
		mAsy = new Asy();
		mAsy.execute();
	}

	private void init(NetworkPhotoTask params){
		mBitmap = null;
		mParams = params;
		mParams.v = this;
	}

	public synchronized void removeCecheListener(){
		CacheBitmapManager.cacheManager.getcacheManager().removeOnCecheNewBitmapListener(this);
	}

	public synchronized void setCacheListener(){
		CacheBitmapManager.cacheManager.getcacheManager().setOnCecheNewBitmapListener(this);
	}

	//加载图片的情�??
	public void setOnPublishProgressListener(OnPublishProgressListener l){
		mOnPublishProgressListener = l;
	}

	private Bitmap getBitmapFromCache(){
		ImageCacheManager imageCache= CacheBitmapManager.cacheManager.getcacheManager();
		return imageCache.getCacheItemt(mParams.getPhotoName());
	}

	private void addBitmapToCache(String key , Bitmap bitmap){
		CacheBitmapManager.cacheManager.getcacheManager().addCacheItem(key, bitmap);
	}

	private Bitmap getImageObjectParams(Object obj){
		Bitmap bm = null;

		if(obj instanceof Integer){
			int resource = (Integer) obj;
			if(resource > 0) bm = BitmapFactory.decodeResource(getResources(), resource);

		}else if(obj instanceof Bitmap){
			bm = (Bitmap)obj;
		}
		return bm;
	}

	private void saveImageToApp(){
		//do get image from sdCard
		if(mParams != null && mParams.isGetImageFromSdCard){
			FileInputStream fis = null;
			FileOutputStream fos = null;
			try {
				fis = new FileInputStream(mParams.url);
				fos = getContext().openFileOutput(mParams.getPhotoName(), Context.MODE_PRIVATE);
				System.out.println("size:"+fis.available());
				byte b[] = new byte[fis.available()];
				int count = 0;
				if((count = fis.read(b)) != -1){
					fos.write( b, 0 , count);
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					if(fos != null){
						fos.close();
					}
					if(fis != null)

						fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


	class Asy extends AsyncTask<NetworkPhotoTask , Integer, Bitmap> implements AsyPublish{
		String name = mParams.getPhotoName();
	
		LayoutParams lp;
		boolean isStop = false;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		
			setImageBitmap(getImageObjectParams(mParams.startDrawId));
		}


		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

			if(mOnPublishProgressListener != null && 
					values.length == 1 && values[0] >= 0 && values [0] <= 100){
				mOnPublishProgressListener.OnPublisListener(values[0]);
			}
		}

		@Override
		protected Bitmap doInBackground(NetworkPhotoTask... params) {
			// TODO Auto-generated method stub
			
			Bitmap bitmap = null;
			
			if(mParams.url == null || mParams.url.trim().length() == 0){
				return getImageObjectParams(mParams.erroDrawId);
			}
			
			if(!isCancelleds()){

				bitmap = AccessNetwork.getBitmapFromLocalOrNetWork(name, mParams, this);
				if(bitmap != null){
					removeCecheListener();
					if(mParams.isAddToCache) {
						//add bitmap to ceche
						addBitmapToCache(name, bitmap);
						Log.i("ImageViewNetwork"," image add cache");
					}
				}

				if(!isCancelleds() && bitmap == null ){
					bitmap = getImageObjectParams(mParams.erroDrawId);
				}
			}

			if(isCancelleds()){
				Log.i("ImageVIewNetwork", "bitmap is have");
				bitmap = mBitmap;
			}

			if(mParams.isSaveImageToApp)
				//image file from SdCard save to app
				saveImageToApp();
			
			if(bitmap != null)
				lp = calc(bitmap);

			return bitmap;
		}



		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(lp != null)
				setLayoutParams(lp);
			setImageBitmap(result);
		}



		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			isStop = true;
		}



		@Override
		public void publishProgressDevelopment(Integer... progress) {
			// TODO Auto-generated method stub
			publishProgress(progress);
		}


		@Override
		public boolean isCancelleds() {
			// TODO Auto-generated method stub
			return isStop;
		}

	}



	@SuppressLint("NewApi")
	private LayoutParams calc(Bitmap bitmap){


		int width = getMaxWidth();
		int height = getMaxHeight();

		if(height == Integer.MAX_VALUE || width == Integer.MAX_VALUE){
			return getLayoutParams();
		}

		int bWidth = bitmap.getWidth();
		int bHeight = bitmap.getHeight();

		Log.i("ImageViewNetwork", "view.width = "+width+" view.height = "+height);
		Log.i("ImageViewNetwork", "bitmap.width = "+bWidth+" bitmap.height = "+bHeight);

		final int size = width < height ? height : width;
		final int bsize = bWidth < bHeight ? bHeight :bWidth;
		float scaling = (float) (bsize / (size * 1.0)) ;
		Log.i("ImageViewNetwork", "scaling"+scaling);
		scaling = scaling < 1 ? 1 : scaling;
		Log.i("ImageViewNetwork", "alter scaling"+scaling);
		bWidth /=scaling;
		bHeight /=scaling;

		Log.i("ImageViewNetwork", " bitmap scaling  bitmap.width = "+bWidth+" bitmap.height = "+bHeight);

		boolean iswidth = width > bWidth;
		boolean isheight = height > bHeight;

		LayoutParams params = getLayoutParams();


		int w = width;
		int h = height;

		if(iswidth && !isheight){
			w = bWidth;
		}else if(!iswidth && isheight){
			h = bHeight;
		}else if(iswidth && isheight){
			w = bWidth;
			h = bHeight;
		}
		Log.i("ImageViewNetwork", " set View   width = "+w+" bWidth = "+h);
		params.width = w;
		params.height = h;
		return params;
	}


	@Override
	public void onCecheListener(String name, final Bitmap bitmap) {
		// TODO Auto-generated method stub

		if(mParams.isGetImageFromCache && mParams.getPhotoName().equals(name)){
			if(mAsy != null){
				mAsy.onCancelled();
			}
			mBitmap = bitmap;
			removeCecheListener();
		}

	}



	public interface OnPublishProgressListener{

		public void OnPublisListener(int process);

	}

}
