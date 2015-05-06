package com.chat.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;










import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.chat.activity.FeedChatActivity.Info;
import com.chat.misc.SystemUtil;
import com.example.chat.R;


/**
 * Created by youjiannuo on 2015/4/9.
 */
public class ChatItemLinearLayout extends LinearLayout implements ChatItem {

    public static final String TAG = "ChatItemLinearLayout";

    protected ChatImageView mChatImageView;
    protected ChatTextTextView mChatTextTextView;
    protected ChatVoiceTextView mChatVoiceTextView;
    protected ProgressBar mProgressImageView;
    protected ProgressBar mProgressTextTextView;
    protected ProgressBar mProgressVoiceTextView;
    protected UserAvatarView mUserAvatarView;

    private RelativeLayout mRelativeLayout;

    private List<ChatView> views = new ArrayList<ChatView>();

    private int mType = -1;

    private int mMaxWidth = -1;

    public ChatItemLinearLayout(Context context) {
        super(context);
        initView(-1);
    }

    public ChatItemLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(-1);
    }


    protected void initView(int layoutId) {
        if (layoutId == -1) return;

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        View v = LayoutInflater.from(getContext()).inflate(layoutId, null);

        addView(v, params);

        mChatImageView = (ChatImageView) findViewById(R.id.chatImageView);
        mChatTextTextView = (ChatTextTextView) findViewById(R.id.chatTextView);
        mChatVoiceTextView = (ChatVoiceTextView) findViewById(R.id.chatVoiceTextView);

        mProgressImageView = (ProgressBar) findViewById(R.id.progressBarImageView);
        mProgressTextTextView = (ProgressBar) findViewById(R.id.progressBarTextView);
        mProgressVoiceTextView = (ProgressBar) findViewById(R.id.progressBarVoiceTextView);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.mainRelativewLayout);
        mUserAvatarView = (UserAvatarView) findViewById(R.id.userAvatarView);

        views.add(new ChatView(mChatVoiceTextView, mProgressVoiceTextView));
        views.add(new ChatView(mChatImageView, mProgressImageView));
        views.add(new ChatView(mChatTextTextView, mProgressTextTextView));

    }

    
    

    @Override
    public void setInfo(Info info) {
    	//set message type for exmplea message, image or text
        setType(info.messageType);
        
        ChatView chatView = getChatView();
        //update view
        chatView.v.showInfoToView(info);
    }

    protected ChatView getChatView(){
    	if(mType < 0 || mType >= views.size()) mType = 0;
    	return views.get(mType);
    }
    
    //设置头像
    public void setIconImage(String url) {
        mUserAvatarView.setAvartUrl(url);
    }


    private void setType(int type) {

    	// set history called hidden
        if (mType != -1 && mType < views.size()) {
            views.get(mType).hiddenView();
        }

        //show use , this is Voice , Image or Text View
        if (type < views.size()) {
            views.get(type).showView();
        }
        //save message type
        mType = type >= 0 && type < views.size() ? type : 0;
    }


    class ChatView {
        protected Chat v;
        protected ProgressBar progressBar;

        public ChatView(final Chat v, ProgressBar progressBar) {
            this.v = v;
            this.progressBar = progressBar;

            setMaxWidthAndHeight((View) v);

        }

        //设置�??大高度和�??小宽�??
        private void setMaxWidthAndHeight(View v) {

            if(mMaxWidth == -1){
                mMaxWidth = SystemUtil.getPhoneScreenWH(getContext())[0]
                        -
                        SystemUtil.dipTOpx(getContext() ,
                                70 +
                                        getContext().getResources().getDimensionPixelOffset(R.dimen.b_icon_size_44));
            }

            if(mMaxWidth <= 0) return;
            if (v instanceof ImageView) {
                ((ImageView) v).setAdjustViewBounds(true);
                ((ImageView) v).setMaxWidth(mMaxWidth);
            }
            else if(v instanceof TextView){
                ((TextView) v).setMaxWidth(mMaxWidth);
            }

        }

        public void showView() {
            setVisible(View.VISIBLE);
        }

        public void hiddenView() {
            setVisible(View.GONE);
        }

        private void setVisible(int visible) {
            setViewVisible(visible, (View) v);
            setViewVisible(visible, progressBar);
        }

        private void setViewVisible(int visible, View v) {
            if (v != null)
                v.setVisibility(visible);
        }
    }


}
