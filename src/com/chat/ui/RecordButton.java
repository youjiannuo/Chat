package com.chat.ui;

import java.util.Calendar;
import java.util.Date;

import com.chat.misc.Record;
import com.chat.misc.SystemUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.Toast;

/**
 * 
 * @author yjn 
 *
 */
public class RecordButton extends Button{

	private Record mRecord = null;
	private String mPath = "";
	private String mName = "";
	private long mStartTime = 0;
	
	private OnRecordListener mOnRecordListener = null;
	

	public RecordButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public RecordButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView(){
		mPath = SystemUtil.getSDCardPath()+"/"+SystemUtil.getAppName(getContext()) +"/";
	}
	
	
	public void setOnRecordListener(OnRecordListener l) {
		this.mOnRecordListener = l;
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			startRecord();
		}else if(event.getAction() == MotionEvent.ACTION_UP){
			stopRecord();
		}
		
		return super.onTouchEvent(event);
	}
	
	private void startRecord(){
//		mName = SystemUtil.getDeviceId(getContext())+new Date().getTime();
//		mName = "you";
		toastStartRecord();
		mRecord = new Record(getContext());
		mRecord.startRecord(mPath, SystemUtil.getDeviceId(getContext()));
		mStartTime = new Date().getTime();
	}
	
	private void stopRecord(){
		if(mRecord == null) return;
			mRecord.stopRecord();
		long time = new Date().getTime() - mStartTime;
		if(time < 1000){
			toastTooShortRecord();
		}else{
			if(mOnRecordListener != null)
				mOnRecordListener.onRecord(mPath, mRecord.getName() , time);
			toastStopRecord();
		}
	}
	
	// toast start record
	private void toastStartRecord(){
		Toast.makeText(getContext(), "开始录音", Toast.LENGTH_SHORT).show();
	}
	
	// toast record too short
	private void toastTooShortRecord(){
		Toast.makeText(getContext(), "录音时间太短了", Toast.LENGTH_SHORT).show();
	}
	
	// toast stop record
	private void toastStopRecord(){
		Toast.makeText(getContext(), "结束录音", Toast.LENGTH_SHORT).show();
	}
	
	
	public interface OnRecordListener{
		public void onRecord(String path , String name , long recordTime);
	}
	
	
}
