package com.chat.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;






import java.util.ArrayList;
import java.util.List;

import com.chat.activity.FeedChatActivity;
import com.chat.activity.FeedChatActivity.Info;

/**
 * Created by youjiannuo on 2015/4/9.
 */
public class ChatAdapter extends BaseAdapter {

    private List<FeedChatActivity.Info> mInfos = new ArrayList<Info>();

    private Context mContext;
    
    public ChatAdapter(Context context){
    	mContext = context;
    }
    
    /**
     * called this method , you needs  called method notifyDataSetChanged()
     * @param info
     */
    public void remove(FeedChatActivity.Info info){
    	if(mInfos == null) return ;
    	mInfos.remove(info);
    }
    
    /**
     * called this method , you needs  called method notifyDataSetChanged()
     * @param info
     */
    public void remove(int index){
    	if(mInfos == null || mInfos.size() <= index) return;
    	mInfos.remove(index);
    }
    
    /**
     * user this method , you needs  called method notifyDataSetChanged()
     * @param info
     */
    public void addInfo(FeedChatActivity.Info info){
    	if(mInfos == null) mInfos = new ArrayList<FeedChatActivity.Info>();
    	mInfos.add(info);
    }
    
    public void setInfo(List<FeedChatActivity.Info> infos){
        mInfos = infos;
    }

    @Override
    public int getCount() {

        return mInfos == null ? 0 : mInfos.size();
    }

    @Override
    public Object getItem(int position) {

        return mInfos == null ? null : position >= mInfos.size() ? null :mInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatListItem chatListItem = null;

        if(convertView == null){
            chatListItem = new ChatListItem(mContext);
        }else chatListItem = (ChatListItem) convertView;

        FeedChatActivity.Info info = (FeedChatActivity.Info) getItem(position);
        if(info != null){
            chatListItem.setInfo(info);
        }

        return chatListItem;

    }
}
