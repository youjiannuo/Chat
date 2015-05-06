package com.chat.ui;

import com.chat.activity.FeedChatActivity.Info;
import com.chat.misc.Player;
import com.chat.misc.SystemUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

/**
 * Created by youjiannuo on 2015/4/8.
 */
public class ChatVoiceTextView extends TextView implements Chat , OnClickListener {


	private int mParectWidth = 0;
	
	private Info mInfo;
	
	private Player mPlayer;
	
    public ChatVoiceTextView(Context context) {
        super(context);
        initView();
    }

    public ChatVoiceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
    	setOnClickListener(this);
    	
    	getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

    		@SuppressLint("NewApi")
			@Override
    		public void onGlobalLayout() {
    			// TODO Auto-generated method stub
    			getViewTreeObserver().removeOnGlobalLayoutListener(this);
    			aa();
    		}
    	});
    	getViewTreeObserver().addOnScrollChangedListener(new OnScrollChangedListener() {

    		@Override
    		public void onScrollChanged() {
    			// TODO Auto-generated method stub
    			getViewTreeObserver().removeOnScrollChangedListener(this);
    			aa();
    		}
    	});
    }
    
    private void aa(){
    	if(mInfo != null)
			setHeights((int) (mInfo.recordTime / 1000));
    }


    @Override
    public void sendInfoToServer(Info obj) {
    	showInfoToView(obj);

    }

    @Override
    public void getInfoFromNetwork(Info obj) {
    	mInfo = obj;
    }

    @Override
    public void showInfoToView(Info obj) {
    	mInfo = obj;
    	int time = Player.getPlayTime(getContext(), obj.obj.toString()) / 1000;
    	setHeights(time);
    	setText(time +" ''");
    }
    
    
    private void setHeights(int time){
    	
    	if(mParectWidth == 0){
    		mParectWidth = ((ViewGroup)getParent()).getWidth();
    		Log.i("ChatVoiceTextView", "Parcet :"+mParectWidth);
    	}
    	if(mParectWidth == 0) return;
    	
    	float width = SystemUtil.dipTOpx(getContext(), 50);
    	width += (float) ((time * 1.0) / 60.0) * mParectWidth;
    	Log.i("ChatVoiceTextView", "width :"+width);
    	
    	LayoutParams params = getLayoutParams();
    	params.width = (int)width;
    	
    	setLayoutParams(params);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final Info info = mInfo;
		if(info == null) return;
		
		if(mPlayer != null){
			//close history play voices
			mPlayer.close();
		}
		
		mPlayer = new Player(info.obj.toString(), getContext());
		mPlayer.Largerplay();
		
	}
    
}
