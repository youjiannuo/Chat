package com.chat.ui;

import android.content.Context;
import android.util.AttributeSet;

import com.chat.ImageLoader.ImageViewNetwork;
import com.chat.ImageLoader.NetworkPhotoTask;
import com.chat.activity.FeedChatActivity.Info;


/**
 * Created by youjiannuo on 2015/4/8.
 */
public class ChatImageView extends ImageViewNetwork implements Chat {

    public ChatImageView(Context context) {
        super(context);
    }

    public ChatImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public void sendInfoToServer(Info obj) {

    }

    @Override
    public void getInfoFromNetwork(Info obj) {

    }

    @Override
    public void showInfoToView(Info obj) {

    	String s = obj.obj.toString();
    	NetworkPhotoTask task = NetworkPhotoTask.build();
    	task.url = s;
    	
    	if(obj.isGetFromSdcard){
    		task.isGetImageFromSdCard = true;
    		task.isSaveImageToApp = true;
    		//不是从本地获取
    		obj.isGetFromSdcard = false;
    	}
    	
        setImageTask(task);

    }
}
