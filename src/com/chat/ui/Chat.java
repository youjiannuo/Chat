package com.chat.ui;

import com.chat.activity.FeedChatActivity.Info;

/**
 * Created by youjiannuo on 2015/4/8.
 */
public interface Chat {

    public void sendInfoToServer(Info obj);

    public void getInfoFromNetwork(Info obj);

    public void showInfoToView(Info obj);
}
