package com.chat.ui;

import com.chat.activity.FeedChatActivity;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;

public class MethodButton extends Button implements OnClickListener{

	//下面参数值的是，现在按钮是出在什么功能，
	//比如参数是VOICE表示的是现在按钮的文字显示的
	//是语音
	private final int VOICE = 1;
	
	private final int SEND = 2;
	
	private final int INPUT = 3;
	
	private int mStatus = VOICE;
	
	EditText mEditText;
	RecordButton mRecordButton;
	FeedChatActivity mActivity;
	
	public MethodButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView(){
		setOnClickListener(this);
	}

	public void setView(FeedChatActivity activity , EditText mEditText ,RecordButton mRecordButton){
		this.mEditText = mEditText;
		this.mRecordButton = mRecordButton;
		this.mActivity = activity;
		mEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(s.length() != 0) {
					mStatus = SEND;
					setText("发送");
				}else if(s.length() == 0){
					mStatus = VOICE;
					setText("语音");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	
	
	private void setMessage(String s , int input , int voice){
		setText(s);
		mEditText.setVisibility(input);
		mRecordButton.setVisibility(voice);
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		String s = "键盘";
		int voice = View.GONE;
		int input = View.VISIBLE;
		
		
		if(mStatus == VOICE){
			//点击了 ,,Record view show
			mStatus = INPUT;
			voice = View.VISIBLE;
			input = View.GONE;
		}else if(mStatus == INPUT || mStatus == SEND){
			
			//发送消息
			if(mStatus == SEND && mActivity.sendTextMessage()){
				return;
			}
			s = "语音";
			mStatus = VOICE;
		    
		}
		
		setMessage(s , input , voice);
		
	}
	
	
	
}
