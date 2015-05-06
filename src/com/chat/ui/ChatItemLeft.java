package com.chat.ui;

import com.chat.activity.FeedChatActivity.Info;
import com.chat.ui.ChatItemLinearLayout.ChatView;
import com.example.chat.R;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by youjiannuo on 2015/4/9.
 */
public class ChatItemLeft extends ChatItemLinearLayout{

    public ChatItemLeft(Context context) {
        super(context);
    }

    public ChatItemLeft(Context context, AttributeSet attrs) {
        super(context, attrs);

    }



    @Override
    protected void initView(int layoutId){
        super.initView(R.layout.view_chat_item_left);
    }

    @Override
    public void setInfo(Info info) {
    	// TODO Auto-generated method stub
    	super.setInfo(info);
    	ChatView chatView = getChatView();
    	
    	if(info.messageType == CHAT_VOICE)
    		//get message from network
    		chatView.v.getInfoFromNetwork(info);
    	
    }

}
