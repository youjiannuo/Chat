package com.chat.ui;

import android.content.Context;
import android.util.AttributeSet;






import com.chat.ImageLoader.ImageViewNetwork;
import com.chat.ImageLoader.NetworkPhotoTask;
import com.example.chat.R;




/**
 * Created by pipi on 15/3/5.
 */
public class UserAvatarView extends ImageViewNetwork {

//    UserProtos.PBUser user;

    public UserAvatarView(Context context){
        super(context);
    }

    public UserAvatarView(Context context , AttributeSet attrs) {
        super(context , attrs);
    }

    public void setAvartUrl(String url){
    	NetworkPhotoTask task = NetworkPhotoTask.build();
    	task.startDrawId = R.drawable.y_morentouxiang;
    	task.url = url;
    	task.isSetRounded = true;
    	setImageTask(task);
    }

}
