package com.chat.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.chat.activity.FeedChatActivity;
import com.chat.misc.PhotoAndCamera;
import com.chat.misc.SystemUtil;
import com.example.chat.R;

/**
 * Created by youjiannuo on 2015/4/17.
 */
public class MenuLayout extends LinearLayout {

	public static final int FUNTION = 0;

	public static final int EXPRESSION = 1;

	private int mStatus = Integer.MAX_VALUE; // 

	

	private int mMaxHeight = 0;


	//menu some function
	private FunctionView mFunctionView;
	//expression 
	private ExpressionView mExpressionView;

	public MenuLayout(Context context) {
		super(context);
		initView();
	}

	public MenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView(){
		//get screen 1/3 height
		mMaxHeight = SystemUtil.getPhoneScreenWH(getContext())[1] / 3;

		LayoutInflater.from(getContext()).inflate(R.layout.view_menu_all_function, this);

		mFunctionView = (FunctionView) findViewById(R.id.functionView);

		mExpressionView = (ExpressionView)findViewById(R.id.express);

	}

	public void bindEdiText(EditText editText){
		mExpressionView.setEditext(editText);
	}

	public void showExpressionView(){
		mFunctionView.setVisibility(GONE);
		mExpressionView.setVisibility(VISIBLE);
		mStatus = EXPRESSION;
	}

	public void showFuntion(){
		mFunctionView.setVisibility(VISIBLE);
		mExpressionView.setVisibility(GONE);
		mStatus = FUNTION;
	}
	
	public int getStatus() {
		return mStatus;
	}
	
	public void initData(FeedChatActivity activity){
		//init camera
		mFunctionView.initPhotoCamera(activity);
		bindEdiText(activity.getEdiText());
	}


	public PhotoAndCamera getPhotoAndCamera(){
		return mFunctionView.getPhotoAndCamera();
	}


	public void showMenu(){
		setHeight(mMaxHeight);
	}

	public void closeMenu(){
		setHeight(0);
	}

	private void setHeight(int height){
		ViewGroup.LayoutParams params = getLayoutParams();
		params.height = height;
		setLayoutParams(params);
	}

}
