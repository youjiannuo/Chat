package com.chat.ImageLoader;


public interface  Cache<T> {
	
	public void addCacheItem(String key, T item);
	
	public T getCacheItemt(String key);
	
	public void clear();
	
	public void removes(String key);
		
	public void Recycling();
}
