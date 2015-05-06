package com.chat.ui;

import com.chat.activity.FeedChatActivity.Info;

/**
 * Created by youjiannuo on 2015/4/9.
 */
public interface ChatItem {

    public static final int CHAT_VOICE = 0;

    public static final int CHAT_IMAGE = 1;

    public static final int CHAT_TEXT = 2;


    public void setInfo(Info info);



}
