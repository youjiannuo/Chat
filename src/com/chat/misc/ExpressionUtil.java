package com.chat.misc;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.chat.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;


public class ExpressionUtil {
	/**
	 * 瀵箂panableString杩涜姝ｅ垯鍒ゆ柇锛屽鏋滅鍚堣姹傦紝鍒欎互琛ㄦ儏鍥剧墖浠ｆ浛
	 * @param context
	 * @param spannableString
	 * @param patten
	 * @param start
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
    public static void dealExpression(Context context,SpannableString spannableString, 
    			Pattern patten, int start) throws SecurityException, NoSuchFieldException, NumberFormatException, IllegalArgumentException, IllegalAccessException {
    	Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            key = key.length() == 0  ? key : key.substring(1, key.length());
            if (matcher.start() < start) {
                continue;
            }
            Field field = R.drawable.class.getDeclaredField(key);
			int resId = Integer.parseInt(field.get(null).toString());		//閫氳繃涓婇潰鍖归厤寰楀埌鐨勫瓧绗︿覆鏉ョ敓鎴愬浘鐗囪祫婧恑d
            if (resId != 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);	
                @SuppressWarnings("deprecation")
				ImageSpan imageSpan = new ImageSpan(bitmap);				//閫氳繃鍥剧墖璧勬簮id鏉ュ緱鍒癰itmap锛岀敤涓�涓狪mageSpan鏉ュ寘瑁�
                int end = matcher.start() + key.length() + 1;					//璁＄畻璇ュ浘鐗囧悕瀛楃殑闀垮害锛屼篃灏辨槸瑕佹浛鎹㈢殑瀛楃涓茬殑闀垮害
                spannableString.setSpan(imageSpan, matcher.start(), end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);	//灏嗚鍥剧墖鏇挎崲瀛楃涓蹭腑瑙勫畾鐨勪綅缃腑
                if (end < spannableString.length()) {						//濡傛灉鏁翠釜瀛楃涓茶繕鏈獙璇佸畬锛屽垯缁х画銆傘��
                    dealExpression(context,spannableString,  patten, end);
                }
                break;
            }
        }
    }
    
    public static SpannableString getExpressionString(Context context,SpannableString spannableString,String zhengze){
    	 Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);		//閫氳繃浼犲叆鐨勬鍒欒〃杈惧紡鏉ョ敓鎴愪竴涓猵attern
         try {
             dealExpression(context,spannableString, sinaPatten, 0);
         } catch (Exception e) {
             Log.e("dealExpression", e.getMessage());
         }
         return spannableString;
    }
    
    /**
     * 寰楀埌涓�涓猄panableString瀵硅薄锛岄�氳繃浼犲叆鐨勫瓧绗︿覆,骞惰繘琛屾鍒欏垽鏂�
     * @param context
     * @param str
     * @return
     */
    public static SpannableString getExpressionString(Context context,String str,String zhengze){
    	SpannableString spannableString = new SpannableString(str);
       getExpressionString(context, spannableString, zhengze);
        return spannableString;
    }

    /**
     * 灏嗕竴涓〃鎯呰浆鎹㈡垚瀵瑰簲鐨勫瓧绗�
     * @param context
     * @param imageResource 鍥剧墖鐨勮祫婧恑d
     * @param name 鍥剧墖瀵瑰簲鐨勫悕绉�
     * @return
     */
    public static SpannableString getExpression(Context context , int imageResource , String name){
    	Bitmap bitmap = null;
		bitmap = BitmapFactory.decodeResource(context.getResources(), imageResource );
		ImageSpan imageSpan = new ImageSpan(context, bitmap);
		SpannableString spannableString = new SpannableString(name);
		spannableString.setSpan(imageSpan, 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannableString;
    }
    
}