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

	//�������ֵ���ǣ����ڰ�ť�ǳ���ʲô���ܣ�
	//���������VOICE��ʾ�������ڰ�ť��������ʾ��
	//������
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
					setText("����");
				}else if(s.length() == 0){
					mStatus = VOICE;
					setText("����");
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
		
		String s = "����";
		int voice = View.GONE;
		int input = View.VISIBLE;
		
		
		if(mStatus == VOICE){
			//����� ,,Record view show
			mStatus = INPUT;
			voice = View.VISIBLE;
			input = View.GONE;
		}else if(mStatus == INPUT || mStatus == SEND){
			
			//������Ϣ
			if(mStatus == SEND && mActivity.sendTextMessage()){
				return;
			}
			s = "����";
			mStatus = VOICE;
		    
		}
		
		setMessage(s , input , voice);
		
	}
	
	
	
}
