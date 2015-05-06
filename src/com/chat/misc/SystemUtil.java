package com.chat.misc;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;



import java.util.Timer;
import java.util.TimerTask;


public class SystemUtil {
    private static String deviceId = null;

    public static String getDeviceId(Context context) {
        if (deviceId != null) {
            return deviceId;
        }

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static boolean isPhone(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * �?查SDCard是否存在
     *
     * @return
     */
    public static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取SDCard的地�?
     *
     * @return
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static int[] getPhoneScreenWH(Context context) {
        int wh[] = new int[2];
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        wh[0] = dm.widthPixels;
        wh[1] = dm.heightPixels;
        return wh;
    }

    
    public static int dipTOpx(Context context , float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int pxTodip(Context context ,float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    


    /**
     * 延迟显示
     * @param v
     */
    public static void showInputMethodManagerDelay(View v) {
        showInputMethodManager(v , 200 , null);
    }

    /**
     * 马上显示
     * @param v
     */
    public static void showInputMethodManagerNow(View v , onInputMethodListener l){
        showInputMethodManager(v , 0 , l);
    }



    public static void showInputMethodManager(final View v , long time , final onInputMethodListener l) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();

        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {

                    public void run() {
                        getInputMethodManager(v.getContext()).showSoftInput(v, 0);
                        if(l != null) l.onInputMethodShow();
                    }
                },
                time);
    }

    public static String getAppName(Context context){
    	return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
    }
    
   
    
    //判断软键盘是都出�?
    public static boolean isInoputMethodShow(View v){

//        return  context.getWindow().peekDecorView() != null;
          InputMethodManager imm = getInputMethodManager(v.getContext());
          return imm != null && imm.isActive(v);
//        return context.getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
    }

    /**
     * 关闭输入软键�?
     *
     * @param v
     */
    public static boolean closeInputMethodManager(View v) {
        boolean is = getInputMethodManager(v.getContext()).hideSoftInputFromWindow(v.getWindowToken(), 0);
//        getInputMethodManager(v.getContext()).get
        return is;
    }

    public  static InputMethodManager getInputMethodManager(Context context){
        return (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    public interface onInputMethodListener{

        public void onInputMethodShow();

    }



}
