package com.chat.ui;

import com.chat.ui.ScrollLayout.PageListener;
import com.example.chat.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ExpressionView extends LinearLayout{

	public ExpressionView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}

	private void initView(){
		LayoutInflater.from(getContext()).inflate(R.layout.chat_main_biaoqing_other, this);
	}
	
	public void setEditext(EditText editText){
		final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.LinearLayoutposition);
		linearLayout.getChildAt(0).setEnabled(false);
		
		((ScrollLayoutBiaoqing) findViewById(R.id.scrolllayout))
		.setbindEditText(editText).
		setPageListener(new PageListener() {
			
			@Override
			public void page(int page) {
				// TODO Auto-generated method stub
				if(page + 1 < linearLayout.getChildCount())
					linearLayout.getChildAt(page +1).setEnabled(true);
				if(page - 1 >= 0)
					linearLayout.getChildAt(page - 1).setEnabled(true);
				linearLayout.getChildAt(page).setEnabled(false);
			}
		});
	}
	
}
