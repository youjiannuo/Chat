package com.chat.ui;

import com.chat.activity.FeedChatActivity.Info;
import com.example.chat.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;



/**
 * Created by youjiannuo on 2015/4/9.
 */
public class ChatListItem extends LinearLayout {

    public static final int CHAT_LEFT = 0;

    public static final int CHAT_RIGHT = 1;

    private ChatItemLeft mChatItemLeft;
    private ChatItemRight mChatItemRight;



    public ChatListItem(Context context){
        super(context);
        initView();
    }

    public ChatListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){

    	LayoutInflater.from(getContext()).inflate(R.layout.view_chat_item , this);

        mChatItemLeft = (ChatItemLeft) findViewById(R.id.chatItemLeft);
        mChatItemRight = (ChatItemRight) findViewById(R.id.chatItemRight);

    }

    public void setInfo(Info info){
        ChatItemLinearLayout chatItemLinearLayout = getChattView(info.status);
        chatItemLinearLayout.setIconImage(info.headUrl);
        chatItemLinearLayout.setInfo(info);
    }


    private ChatItemLinearLayout getChattView(int statue ){

        ChatItemLinearLayout chatItemLinearLayout = null;
        if(statue == CHAT_LEFT){
            chatItemLinearLayout = mChatItemLeft;
            mChatItemRight.setVisibility(GONE);
            mChatItemLeft.setVisibility(VISIBLE);
        }else if(statue == CHAT_RIGHT){
            chatItemLinearLayout = mChatItemRight;
            mChatItemRight.setVisibility(VISIBLE);
            mChatItemLeft.setVisibility(GONE);
        }
        return chatItemLinearLayout;
    }



}
