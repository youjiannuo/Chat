package com.chat.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageCacheManager extends LruCache<String, Bitmap> implements Cache<Bitmap>{
	
	public List<OnCecheNewBitmapAddListener> mOnceche = new ArrayList<OnCecheNewBitmapAddListener>();
	
	public ImageCacheManager() {
		super((int) (Runtime.getRuntime().maxMemory() / 8));
		// TODO Auto-generated constructor stub
	}

	public void removeOnCecheNewBitmapListener(OnCecheNewBitmapAddListener l){
		mOnceche.remove(l);
	}
	
	public void setOnCecheNewBitmapListener(OnCecheNewBitmapAddListener l){
		mOnceche.add(l);
	}
	
	
	@SuppressLint("NewApi")
	@Override
	protected int sizeOf(String key, Bitmap value) {
		// TODO Auto-generated method stub
		return value.getByteCount();
	}

	@Override
	public  void addCacheItem(String key, Bitmap item) {
		// TODO Auto-generated method stub
		put(key, item);
		
		List<OnCecheNewBitmapAddListener> ls = new ArrayList<OnCecheNewBitmapAddListener>(mOnceche);
		
		for(int i = 0 ; i < ls.size() ; i ++){
			ls.get(i).onCecheListener(key, item);
		}
	}

	@Override
	public  Bitmap getCacheItemt(String key) {
		// TODO Auto-generated method stub
		return get(key);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		if (size() > 0) {
			evictAll();
		}
	}

	

	@Override
	public void Recycling() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removes(String key) {
		// TODO Auto-generated method stub
		Bitmap bm = super.remove(key);
		if (bm != null)
			bm.recycle();
	}
	
	public static ImageCacheManager build(){
		return new ImageCacheManager();
	}
	
	
}
