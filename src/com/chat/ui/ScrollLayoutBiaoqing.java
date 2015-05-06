package com.chat.ui;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;







import com.chat.misc.ExpressionUtil;
import com.example.chat.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ScrollLayoutBiaoqing extends ScrollLayout {

	private int mSize = 20;
	
	public ScrollLayoutBiaoqing(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	
	/**
	 * 绑定输入文本框
	 * @param editText
	 */
	public ScrollLayoutBiaoqing setbindEditText(final EditText editText){
		
		setOnItemSelecteListener(new ScrollLayout.OnItemSelecteListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent, int groupPosition,
					int chilldPosition) {
				// TODO Auto-generated method stub
				Object object[] = getDrawableResourceAndName(groupPosition, chilldPosition);
				if((Integer)object[1] == 0) return;
				editText.append(ExpressionUtil.getExpression(getContext(), (Integer)object[1], "\\"+object[0].toString()));
			}
		});
		return this;
	}
	
	/**
	 * 初始化控件
	 */
	private void initView(){
		int total = 107;
		int size = 20;
		int N = 20;
		int n = total % size == 0 ? total / size : total / size + 1 ;
		
		List<List<Map<String,Object>>>datas = new ArrayList<List<Map<String,Object>>>();
		for(int i = 0 ; i < n ; i ++){
			List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
			if(i == n - 1) N = total - size * i;
			for(int j = 0 ; j < N ; j ++){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("0", getDrawableResourceAndName(i , j )[1]);
				data.add(map);
			}
			datas.add(data);
		}
		setDataToView(datas, R.layout.jxsd_chat_biaoqingmenu, new String[]{"0"}, new int[]{R.id.imageView1}, 7);
	}
	
	
	
	/**
	 * 获取对应的照片
	 * @param groupPosition 组数
	 * @param childPosition 组数里面的第某一个
	 * @return 返回的是一个字符串数组，长度为2,</br>
	 * 					0表示的是对应的名称</br>
	 * 					1表示的是对应的对应的资源
	 */
	public Object[] getDrawableResourceAndName(int groupPosition,int childPosition){
		Object obj[] = new Object[2];
		String s = ""+((groupPosition * mSize) + childPosition);
		obj[0] = "f"+(s.length() == 1 ? "00"+s : s.length() == 2 ? "0"+s : s);
		obj[1] =  getDrawableResource(obj[0].toString());
		return obj;
	}
	
	/**
	 * 获取对应的图片的资源
	 * @param key 对应的图片的名称
	 */
	protected int getDrawableResource(String key){
		Field field = null;
		int resId = 0;
		try {
			field = R.drawable.class.getDeclaredField(key);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			resId = Integer.parseInt(field.get(null).toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resId;
		
	}
}
