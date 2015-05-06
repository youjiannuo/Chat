package com.chat.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chat.misc.PhotoAndCamera;
import com.chat.misc.SystemUtil;
import com.example.chat.R;

/**
 * Created by youjiannuo on 2015/4/17.
 */
public class MenuLayout extends LinearLayout {

    private int mMaxHeight = 0;

    //menu some function
    private FunctionView mFunctionView;
    
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
    	
    	
    }

    public void initData(Activity activity){
    	//init camera
    	mFunctionView.initPhotoCamera(activity);
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
