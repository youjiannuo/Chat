package com.chat.ui;

import com.chat.activity.FeedChatActivity.Info;
import com.example.chat.R;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by youjiannuo on 2015/4/9.
 */
public class ChatItemRight  extends ChatItemLinearLayout {
    public ChatItemRight(Context context) {
        super(context);
    }

    public ChatItemRight(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void initView(int layoutId){
        super.initView(R.layout.view_chat_item_right);
    }

    @Override
    public void setInfo(Info info) {
    	// TODO Auto-generated method stub
    	super.setInfo(info);
    	
    	ChatView chatView = getChatView();
    	//send message to network
    	chatView.v.sendInfoToServer(info);
    	
    	System.out.println("子类被调用");
    	
    }

}
