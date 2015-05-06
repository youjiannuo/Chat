package com.chat.ImageLoader;


import android.widget.ImageView;


/**
 * get image task
 * @author youjiannuo
 * set encoding = "UTF-8"
 */
public class NetworkPhotoTask {
	
	//文件名称
	public String fileName;
	
	public ImageView v = null;
	/**
	 * 加载照片的网地址获取本地图片地址
	 */
	public String url = null;
	
	/**
	 * 读取照片是从SDCard获取
	 */
	public boolean isGetImageFromSdCard = false;
	
	/**
	 * 从SDCard读取的照片是否需要保存到应用程序的内存里面
	 * <i>照片不被丢失，而且还可以保护照片</i>
	 * <i>如果是设置为true，表示可以保存到应用程序的内存里面，默认为false</i>
	 */
	public boolean isSaveImageToApp = false;
	
	
	/**
	 * 加载照片错误的时候，�?要显示的照片 
	 * <i>可以为资源文件， 也可以是bitmap</i>
	 */
	public Object erroDrawId = -1;
	/**
	 * 加载照片�?始的时�?�，�?要显示文件的照片
	 * <i>可以为资源文件， 也可以是bitmap</i>
	 */
	public Object startDrawId = -1;
	
	/**
	 * �??要加载照片的高，自动对照片进行缩�??
	 */
	public int height = -1;
	/**
	 * �??要加载照片的高，自动对照片进行缩�??
	 */
	public int width = -1;
	
	/**
	 * 控件设置的最大�?�宽�??
	 */
	public int viewMaxWidth = Integer.MAX_VALUE;
	
	/**
	 * 控件设置的最大高�??
	 */
	public int viewMaxHeight = Integer.MAX_VALUE;
	
	/**
	 * 照片的缩放比例，该�?�设置为5 ，照片就会缩小为原来�??5�??
	 */
	public int scalingSize = 0;
	
	/**
	 * 如果把这个参数设置为false ,那么就是不把当前的照片加入到缓存里面，那么当我们�??出当前的Activity的时候，
	 * 调用recycleBitmap方法来释放Bitmap对象
	 */
	public boolean isAddToCache = true;
	
	/**
	 * 是否�??要从缓存里面获取照片
	 */
	public boolean isGetImageFromCache = true;
	
	/**
	 * 回调方法，该类是�??个接口，可以回调从网络上或�?�本地获取照片成功，
	 */
	public OnLoaderImageCallback onLoaderImageCallback = null;
	
	
	/**
	 * 如果在设置圆形和圆角的时候，设置圆形的优先级更好
	 */
	
	/**
	 * 是否将图片设置为圆形
	 * 如果设置为true就是�??要把图片设置为圆�??
	 */
	public boolean isSetRounded = false;
	
	/**
	 * 设置图片的圆角的大小
	 */
	public int roundedCornersSize = -1;
	
	
	NetworkPhotoTask(){}
	
	NetworkPhotoTask(NetworkPhotoTask p) {
		// TODO Auto-generated constructor stub
		if(p == null) return;
		v = p.v;
		fileName = p.fileName;
		url = p.url;
		erroDrawId = p.erroDrawId;
		startDrawId = p.startDrawId;
		height = p.height;
		width = p.width;
		scalingSize = p.scalingSize;
		onLoaderImageCallback = p.onLoaderImageCallback;
		isAddToCache = p.isAddToCache;
		isGetImageFromCache = p.isGetImageFromCache;
		fileName = p.fileName;
	}
	
	/**
	 * 实例�?? NetworkPhotoTask
	 * @param params
	 * @return
	 */
	public static NetworkPhotoTask build(NetworkPhotoTask params){
		return new NetworkPhotoTask(params);
	}
	
	/**
	 * 获取照片的名字，不是照片的URL地址
	 * @return
	 */
	public String getPhotoName(){
		fileName = fileName != null && fileName.trim().length() != 0 ? 
				fileName : UrlUtil.getFromURLFileNameNotSuffix(url) ;
		return fileName;
	}
	
	/**
	 * 实例�?? NetworkPhotoTask
	 * @param params
	 * @return
	 */
	public static NetworkPhotoTask build(){
		return new NetworkPhotoTask(); 
	}
}

