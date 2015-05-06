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
	 * �������ı���
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
	 * ��ʼ���ؼ�
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
	 * ��ȡ��Ӧ����Ƭ
	 * @param groupPosition ����
	 * @param childPosition ��������ĵ�ĳһ��
	 * @return ���ص���һ���ַ������飬����Ϊ2,</br>
	 * 					0��ʾ���Ƕ�Ӧ������</br>
	 * 					1��ʾ���Ƕ�Ӧ�Ķ�Ӧ����Դ
	 */
	public Object[] getDrawableResourceAndName(int groupPosition,int childPosition){
		Object obj[] = new Object[2];
		String s = ""+((groupPosition * mSize) + childPosition);
		obj[0] = "f"+(s.length() == 1 ? "00"+s : s.length() == 2 ? "0"+s : s);
		obj[1] =  getDrawableResource(obj[0].toString());
		return obj;
	}
	
	/**
	 * ��ȡ��Ӧ��ͼƬ����Դ
	 * @param key ��Ӧ��ͼƬ������
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
