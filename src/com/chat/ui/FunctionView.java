package com.chat.ui;

import com.chat.misc.PhotoAndCamera;
import com.example.chat.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class FunctionView extends LinearLayout{

	private PhotoAndCamera mPhotoAndCamera = null;
	
	public FunctionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView(){
		
		LayoutInflater.from(getContext()).inflate(R.layout.view_menu_funtion, this);
		
		
		
		findViewById(R.id.choosephoto).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mPhotoAndCamera.choosePhoto();
			}
		});
		
		findViewById(R.id.takephoto).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mPhotoAndCamera.takePhoto();
			}
		});
		
	}
	
	public PhotoAndCamera getPhotoAndCamera(){
		return mPhotoAndCamera;
	}
	
	public void initPhotoCamera(Activity activity){
		mPhotoAndCamera = new PhotoAndCamera(activity);
	}
	
	
	
}
