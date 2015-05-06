package com.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.chat.misc.PhotoAndCamera.Message;
import com.chat.misc.SystemUtil;
import com.chat.ui.ChatAdapter;
import com.chat.ui.ChatItem;
import com.chat.ui.ChatItemLinearLayout;
import com.chat.ui.ChatListItem;
import com.chat.ui.MenuLayout;
import com.chat.ui.MethodButton;
import com.chat.ui.RecordButton;
import com.chat.ui.RecordButton.OnRecordListener;
import com.example.chat.R;

















import java.util.ArrayList;
import java.util.List;


/**
 * Created by youjiannuo on 2015/4/8.
 */
public class FeedChatActivity extends BarrageCommonActivity implements OnRecordListener {

	ListView mChatListView;
	EditText mEditText;
	RecordButton mRecordButton;
	MethodButton mMentodButton;
	MenuLayout mMenuLayout;

	private State mState = new State();

	private ChatAdapter mChatAdapter;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		initView();
	}


	protected void initView() {

		mChatListView = (ListView) findViewById(R.id.chatListView);
		mEditText = (EditText) findViewById(R.id.editText);
		mMenuLayout = (MenuLayout) findViewById(R.id.menu);
		mRecordButton = (RecordButton) findViewById(R.id.recodingButton);
		mMentodButton = (MethodButton) findViewById(R.id.someMethod);

		mMentodButton.setView(this, mEditText, mRecordButton);
		mMenuLayout.initData(this);


		mChatAdapter = new ChatAdapter(this);
		List<Info> infos = initData();
		mChatAdapter.setInfo(infos);
		mChatListView.setAdapter(mChatAdapter);


		//		mChatListView.setSelection(mChatListView.getBottom());

		mRecordButton.setOnRecordListener(this);
		mEditText.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if(!mState.isInputShow){
					mState.setInputStateShow();
					// show input
					SystemUtil.showInputMethodManagerNow(mEditText , null);
					moveListViewScroollToBottom();
					onMenu(null , -1);
				}
				return true;
			}
		});

	}

	public EditText getEdiText(){
		return mEditText;
	}

	private List<Info> initData(){

		List<Info> list = new ArrayList<Info>();

		int left = ChatListItem.CHAT_LEFT;
		int right = ChatListItem.CHAT_RIGHT;

		//        urls.add("http://h.hiphotos.baidu.com/image/h%3D200/sign=2d08cd1289d4b31cef3c93bbb7d7276f/0d338744ebf81a4c36ea3a98d52a6059242da683.jpg");
		//        urls.add("http://b.hiphotos.baidu.com/image/pic/item/35a85edf8db1cb13f045ed42df54564e92584bb7.jpg");
		//        urls.add("http://b.hiphotos.baidu.com/image/pic/item/472309f7905298222bebef92d5ca7bcb0b46d483.jpg");
		//        urls.add("http://d.hiphotos.baidu.com/image/pic/item/91529822720e0cf38c2f98aa0846f21fbf09aa83.jpg");
		//        urls.add("http://g.hiphotos.baidu.com/image/pic/item/8ad4b31c8701a18ba9d5c31e9c2f07082838fea1.jpg");
		//        urls.add("http://g.hiphotos.baidu.com/image/pic/item/0df3d7ca7bcb0a46acfa5d686963f6246a60af83.jpg");
		//        urls.add("http://f.hiphotos.baidu.com/image/pic/item/730e0cf3d7ca7bcb57fd117ebc096b63f724a8d9.jpg");
		//        urls.add("http://h.hiphotos.baidu.com/image/pic/item/0d338744ebf81a4c3e1b0298d52a6059252da6b2.jpg");

		list.add(new Info( "你好吃\n\n\n\n饭了二\t\t\t\t\t\t\t\t\t\t\t\t\t\t米啊", right, ChatItem.CHAT_TEXT));
		list.add(new Info( "http://b.hiphotos.baidu.com/image/pic/item/35a85edf8db1cb13f045ed42df54564e92584bb7.jpg", right, ChatItem.CHAT_IMAGE));
		list.add(new Info("http://b.hiphotos.baidu.com/image/pic/item/472309f7905298222bebef92d5ca7bcb0b46d483.jpg", left, ChatItem.CHAT_IMAGE));
		list.add(new Info( "http://d.hiphotos.baidu.com/image/pic/item/91529822720e0cf38c2f98aa0846f21fbf09aa83.jpg", right, ChatItem.CHAT_IMAGE));
		list.add(new Info( "http://g.hiphotos.baidu.com/image/pic/item/8ad4b31c8701a18ba9d5c31e9c2f07082838fea1.jpg", left, ChatItem.CHAT_IMAGE));
		list.add(new Info( "http://g.hiphotos.baidu.com/image/pic/item/0df3d7ca7bcb0a46acfa5d686963f6246a60af83.jpg", right, ChatItem.CHAT_IMAGE));
		list.add(new Info( "http://f.hiphotos.baidu.com/image/pic/item/730e0cf3d7ca7bcb57fd117ebc096b63f724a8d9.jpg", left, ChatItem.CHAT_IMAGE));
		list.add(new Info( "http://h.hiphotos.baidu.com/image/pic/item/0d338744ebf81a4c3e1b0298d52a6059252da6b2.jpg", right, ChatItem.CHAT_IMAGE));
		list.add(new Info( "你好吃饭\n\n\n了二米啊", left, ChatItem.CHAT_TEXT));
		list.add(new Info( "你好吃饭\n\n了二米啊", right, ChatItem.CHAT_TEXT));
		list.add(new Info( "你好吃饭\n\n了二米啊", left, ChatItem.CHAT_TEXT));
		list.add(new Info( "你好吃饭\n\n\n了二米啊", right, ChatItem.CHAT_TEXT));



		return list;



	}



	public void onMenuFuntion(View v){
		onMenu(v , MenuLayout.FUNTION);
		mMenuLayout.showFuntion();
		
	}

	public void onMenuExpression(View v){
		onMenu(v , MenuLayout.EXPRESSION);
		mMenuLayout.showExpressionView();
	}


	// show or close menu
	// return  if false , Menu is show
	public  boolean onMenu(View v , int status){
		if(!mState.isMenuShow){
			mState.setMenuStateShow();
			if(v != null){
				mMenuLayout.showMenu();
				moveListViewScroollToBottom();
			}
			return false;
		}
		
		if(status == -1 ||mMenuLayout.getStatus() == status){
			mMenuLayout.closeMenu();
			mState.setMenuStateClose();	
		}
		
		
		
		return true;
	}

	//listView Scroll move to bottom
	private void moveListViewScroollToBottom(){
		if(mChatListView == null) return;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mChatListView.setSelection(mChatListView.getBottom());
			}
		} , 40);

	}

	//send record message
	private void sendRecordMessage(String path , long recordTime){
		setMessage(path , ChatListItem.CHAT_RIGHT , ChatItem.CHAT_VOICE , false);
	}

	//send img message
	private void sendImageMessage(String path){
		setMessage(path, ChatListItem.CHAT_RIGHT, ChatItem.CHAT_IMAGE , true);
	}

	//send text message
	public boolean sendTextMessage(){
		//这个消息不是在这里发送的，而是在对应控件发送的，这里主要是设置好需要的对象

		//get mesaage from editText
		final String message = mEditText.getText().toString();
		mEditText.setText("");
		setMessage(message , ChatListItem.CHAT_RIGHT , ChatItem.CHAT_TEXT ,false);
		return false;
	}

	public void setMessage(Object obj , int status , int typeMessage , boolean isFromSdCard){
		Info info = new Info(obj , status , typeMessage);
		info.isGetFromSdcard = isFromSdCard;
		mChatAdapter.addInfo(info);
		mChatAdapter.notifyDataSetChanged();
		moveListViewScroollToBottom();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event){

		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(onMenu(null , -1)){
				return true;
			}
		}

		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onRecord(String path, String name, long recordTime) {
		// TODO Auto-generated method stub
		// stop record , and get record file info
		sendRecordMessage(path+name , recordTime);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		//get choose photo or take picture
		Message msg = mMenuLayout.getPhotoAndCamera().getPicture(requestCode, resultCode, data);

		if(msg != null){
			//get image from photo or camera
			sendImageMessage(msg.path);
		}

	}

	public static class Info{

		/**
		 * message
		 */
		public Object obj;
		/**
		 * record time
		 */
		public long recordTime = 0;

		/**
		 * message status ,  
		 *  <i>ChatListItem</i>
		 *  <i>CHAT_LEFT :Other message</i>
		 *  <i>CHAT_RIGHT :own message</i>
		 */
		public int status;

		/*
		 * message type
		 * <i>in Class ChatItem CHAT_TEXT,CHAT_IMAGE,CHAT_VOICE</i> 
		 */
		public int messageType;

		/**
		 * Is photo from sdcard
		 * <i>if true , photo from sdcard</i>
		 * <i>This parameter is only valid for the param of url and headUrl </i>
		 */
		public boolean isGetFromSdcard = false;

		/*
		 * set head icon 
		 * 
		 */
		public String headUrl = "http://c.hiphotos.baidu.com/image/pic/item/730e0cf3d7ca7bcb503a147ebc096b63f624a81a.jpg";

		public Info(Object obj , int status , int messageType){
			this.obj = obj;
			this.status = status;
			this.messageType = messageType;
		}

	}

	class State {
		//键盘是否弹出来了
		protected boolean isInputShow = false;
		//菜单是否弹出
		protected boolean isMenuShow = false;

		protected void setInputStateClose(){
			isInputShow = false;
		}

		protected void setInputStateShow(){
			isInputShow = true;
		}

		protected void setMenuStateClose(){
			isMenuShow = false;
		}

		protected void setMenuStateShow(){
			isMenuShow = true;
		}

	}



}
