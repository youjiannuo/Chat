package com.chat.ImageLoader;


public enum CacheBitmapManager {
	
	cacheManager;
	
	private ImageCacheManager imageCacheManager = new ImageCacheManager();
	
	public ImageCacheManager getcacheManager(){
		return imageCacheManager;
	}
	
	
}
