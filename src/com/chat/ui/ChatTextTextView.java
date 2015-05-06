package com.chat.ui;

import com.chat.activity.FeedChatActivity.Info;
import com.chat.misc.ExpressionUtil;

import android.content.Context;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by youjiannuo on 2015/4/8.
 */
public class ChatTextTextView extends TextView implements Chat {

    public ChatTextTextView(Context context) {
        super(context);
    }

    public ChatTextTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }




    @Override
    public void sendInfoToServer(Info obj) {
    	
    }

    @Override
    public void getInfoFromNetwork(Info obj) {

    }

    @Override
    public void showInfoToView(Info obj) {
        if(obj == null) return;
        setText(obj.obj.toString());
    }
    
    /**
	 * �����б����
	 * @param text ����
	 * @param zhengwen ������ʽ
	 */
	public void setText(String text,String zhengwen){
		
		super.setText(ExpressionUtil.getExpressionString(getContext(), text, zhengwen));
		
	}
	
	/**
	 * ����Ĭ�ϱ���
	 * @param text
	 */
	public void setText(String text){
		setText(text , "\\\\f0[0-9]{2}|\\\\f10[0-7]");
	}
	
	
    
}
