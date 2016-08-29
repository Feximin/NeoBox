package com.feximin.box.util;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.feximin.box.Box;
import com.feximin.box.Constant;
import com.feximin.box.activity.BaseActivity;
import com.feximin.box.api.ApiResult;
import com.feximin.box.exceptions.BaseException;
import com.feximin.box.fragment.BaseFragment;
import com.feximin.box.interfaces.AlphaIndexAble;
import com.feximin.box.interfaces.JSONExtractor;
import com.feximin.box.util.rx.RxHelper;
import com.feximin.library.R;
import com.github.promeg.pinyinhelper.Pinyin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by Neo on 15/11/18.
 */
public class Tool {

    private static Toast sToast;
    public static void showToast(String content, int duration){
        if (isMainThread()){
            makeToast(content, duration);
        }else{
            RxHelper.emptyMainThreadOb().subscribe(o -> makeToast(content, duration));
        }
    }

    private static void makeToast(String content, int duration){
        if (isEmpty(content)) return;
        if(sToast == null){
            
            sToast = Toast.makeText(Box.sApp, content, duration);
        }else{
            sToast.setText(content);
        }
        sToast.show();
    }
    public static void showToast(String content){
        showToast(content, Toast.LENGTH_SHORT);
    }

    public static void showToast(int strResId){
        showToast(getString(strResId));
    }

    public static void showApiError(ApiResult result, String hint){
        if (result == null){
            showToast(hint);
        }else if (!result.isOk()){
            showToast(extractError(result, hint));
        }
    }

    public static boolean checkNullAndFinishAndShowToast(Activity activity, Object obj, String toast){
        boolean isNull = obj == null;
        if (isNull){
            activity.finish();
            showToast(toast);
        }
        return isNull;
    }

    public static boolean checkEmptyAndFinishAndShowToast(Activity activity, String str, String toast){
        boolean isEmpty = Tool.isEmpty(str);
        if (isEmpty){
            activity.finish();
            showToast(toast);
        }
        return isEmpty;
    }

    public static boolean checkEmptyAndFinishAndShowToast(Activity activity, String str, int toastResId){
        return checkEmptyAndFinishAndShowToast(activity, str, getString(toastResId));
    }
    public static boolean checkEmptyAndFinishAndShowToast(Activity activity, String str){
        return checkEmptyAndFinishAndShowToast(activity, str, R.string.wrong_data);
    }

    public static boolean checkNullAndFinishAndShowToast(Activity activity, Object obj, int toastResId){
        return checkNullAndFinishAndShowToast(activity, obj, getString(toastResId));
    }

    public static boolean checkNullAndFinishAndShowToast(Activity activity, Object object){
        return checkNullAndFinishAndShowToast(activity, object, R.string.wrong_data);
    }

    public static boolean isDoingAndToast(boolean b, String toast){
        if (b) showToast(toast);
        return b;
    }

    public static boolean isDoingAndToast(boolean b, int toastResId){
        return isDoingAndToast(b, getString(toastResId));
    }

    public static void showApiError(ApiResult result, int hintResId){
        showApiError(result, getString(hintResId));
    }

    public static void showToastWrongData(){
        showToast("数据出错");
    }

    public static boolean isEmpty(List<?> list){
        return list == null || list.size() ==0;
    }

    public static <T> boolean  isEmpty(T...array){
        return array == null || array.length == 0;
    }

    public static <T> boolean isNotEmpty(T...array){
        return !isEmpty(array);
    }

    public static boolean isEmpty(JSONArray array){
        return array == null || array.length() == 0;
    }

    public static boolean isNotEmpty(JSONArray array){
        return  !isEmpty(array);
    }

    public static boolean isNotEmpty(List<?> list){
        return !isEmpty(list);
    }
    public static boolean isAllNotEmpty(List<?>...list){
        for(List<?> l : list){
            if(isEmpty(l)) return false;
        }
        return true;
    }

    public static <T> T getFirstObject(T[] array){
        return getFirstObject(array, null);
    }

    public static <T> T getFirstObject(T[] array, T defaultValue){

        if (Tool.isEmpty(array)) return defaultValue;
        return array[0];
    }

    public static boolean isEmpty(String s){
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }

    public static boolean isEmpty(String s, String emptyHint){
        boolean b = isEmpty(s);
        if(b) showToast(emptyHint);
        return b;
    }

    public static boolean isEmpty(String s, int emptyHintResId){
        return isEmpty(s, getString(emptyHintResId));
    }

    public static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }

    public static boolean isAllNotEmpty(String...list){
        for(String s : list){
            if(isEmpty(s)) return false;
        }
        return true;
    }

    public static boolean isAllEmpty(String...list){
        for (String s : list){
            if (isNotEmpty(s)) return false;
        }
        return true;
    }

    //判断是否所有的都不为空并且相等
    public static boolean isAllNotEmptyAndEquals(String...strings){
        if(isAllNotEmpty(strings)){
            int length = strings.length;
            if(length == 1) return true;
            for(int i = 0; i<length-1; i++){
                String first = strings[i];
                for(int j = i+1; j<length; j++){
                    if(!strings[j].equals(first)){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    public static String getString(int resId){
        return Box.sApp.getResources().getString(resId);
    }



    public static int getInt(Object obj, int defaultValue){
        if(obj == null) return defaultValue;
        if(obj instanceof String){
            try {
                return Integer.parseInt((String)obj);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        if(obj instanceof Number){
            return ((Number)obj).intValue();
        }
        return defaultValue;
    }

    public static int getInt(Object obj){
        return getInt(obj, 0);
    }

    public static long getLong(Object obj, long defaultValue){
        if(obj == null) return defaultValue;
        if(obj instanceof String){
            try {
                return Long.parseLong((String)obj);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        if(obj instanceof Number){
            return ((Number)obj).longValue();
        }
        return defaultValue;

    }

    public static long getLong(Object obj){
        return getLong(obj, 0);
    }



    public static void sleep(long duration){
        if(duration < 0) return;
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从start开始到当前时间，如果不够milli则休眠至
     * @param milli
     */
    public static void sleepUntil(long milli, long start){
        long duration = System.currentTimeMillis() - start;
        if(duration >= milli) return;
        sleep(milli - duration);
    }
    /**
     * 1000毫秒
     * @param start
     */
    public static void sleepUntil(long start){
        sleepUntil(1000, start);
    }
    private static String sPackageName;
    private static String sVersionName;
    private static int sVersionCode;

    public static String getPackageName(){
        if(TextUtils.isEmpty(sPackageName)){
            sPackageName = Box.sApp.getPackageName();
        }
        return sPackageName;
    }

    public static String getCurrentProcessName(Context context){
        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public static boolean isAppProcess(Context context){
        return isAllNotEmptyAndEquals(getCurrentProcessName(context), Constant.PROCESS_NAME);
    }

    public static String getCurrentVersionName(){
        if(TextUtils.isEmpty(sVersionName)){
            try {
                PackageInfo info = Box.sApp.getPackageManager().getPackageInfo(getPackageName(), 0);
                sVersionName = info.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                sVersionName = "";
            }
        }
        return sVersionName;
    }
    public static int getCurrentVersionCode(){
        if(sVersionCode == 0) {
            try {
                PackageInfo info = Box.sApp.getPackageManager().getPackageInfo(getPackageName(), 0);
                sVersionCode = info.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return sVersionCode;
    }
    public static SharedPreferences getSP(){
        return Box.sApp.getSharedPreferences("miliang_sp", 0);
    }
    public static int getIntValue(String key, int defaultValue){
        return getSP().getInt(key, defaultValue);
    }

    public static void setIntValue(String key, int value){
        getSP().edit().putInt(key, value).commit();
    }

    public static int getIntValueMultiUser(String key, int defaultValue){
        return getIntValue(decorSpKey(key), defaultValue);
    }

    public static void setIntValueMultiUser(String key, int value){
        setIntValue(decorSpKey(key), value);
    }

    public static String getStringValue(String key, String defaultValue){
        return getSP().getString(key, defaultValue);
    }

    public static String getStringValue(String key){
        return getStringValue(key, null);
    }

    public static void setStringValue(String key, String value){
        getSP().edit().putString(key, value).commit();
    }


    public static String getStringValueMultiUser(String key, String defaultValue){
        return getStringValue(decorSpKey(key), defaultValue);
    }

    public static void setStringValueMultiUser(String key, String value){
        setStringValue(decorSpKey(key), value);
    }

    public static boolean getBooleanValue(String key, boolean defaultValue){
        return getSP().getBoolean(key, defaultValue);
    }

    public static void setBooleanValue(String key, boolean value){
        getSP().edit().putBoolean(key, value).commit();
    }

    public static boolean getBooleanValueMultiUser(String key, boolean defaultValue){
        return getBooleanValue(decorSpKey(key), defaultValue);
    }

    public static void setBooleanValueMultiUser(String key, boolean value){
        setBooleanValue(decorSpKey(key), value);
    }

    private static String decorSpKey(String key){
        key = String.format("%s_user_%s", key, "");
        return key;
    }

    /**
     * 每次版本更新的时候都需要检查一下这个是否需要显示引导页面
     * @return
     */
    public static boolean isFirstLaunched(){						//每次更新后都需要显示引导页面
        int localVersionCode = getIntValue(Constant.LOCAL_VERSION_CODE, 0);
        int curVersionCode = getCurrentVersionCode();

        String localVersionName = getStringValue(Constant.LOCAL_VERSION_NAME, "");
        String curVersionName = getCurrentVersionName();

        setStringValue(Constant.LOCAL_VERSION_NAME, curVersionName);
        setIntValue(Constant.LOCAL_VERSION_CODE, curVersionCode);
        if(TextUtils.isEmpty(localVersionName)){
            return true;
        }else{
            //首先比较本地存储的版本号与当前版本号是否一样，如果不一样，就显示引导页面
            if(localVersionCode > 0 && curVersionCode > 0 && localVersionCode == curVersionCode) return false;
            //同一个大版本之间的小版本迭代是不需要显示引导页面的，例如，老版本为2.1.0，新版本为2.1.2， 则都属于2.1版本，不用显示引导页面
            String prefix = localVersionName.substring(0, 3);
            return !curVersionName.startsWith(prefix);
        }
    }

    public static BaseFragment newFragment(String mFragmentName){
        try {
            return  (BaseFragment) Class.forName(mFragmentName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T newInstance(Class<T> clazz){

        T instance = null;
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw new BaseException("an empty constructor was required" + clazz.getName());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static <T> T newInstance(Class<T> clazz, Object param){

        T instance = null;
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor(param.getClass());
            constructor.setAccessible(true);
            instance = constructor.newInstance(param);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw new BaseException("constructor with params "+ param.getClass().getName() +" was required");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static void loadImage(Context context, ImageView img, String url){
        Glide.with(context).load(url).crossFade().into(img);
    }
    public static void loadImage(Context context, ImageView img, int resId){
        Glide.with(context).load(resId).crossFade().into(img);
    }
    public static String md5(String val) {
        byte[] hash = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(val.getBytes("UTF-8"));
            hash = md5.digest();//加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        if(hash != null){
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10)
                    hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }
            String newStr = hex.toString();
            return newStr;
        }
        return null;
    }


    public static String base64(String str){
        byte[] data = str.getBytes();
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return base64;
    }

    public static String randomName(String suffix){
        String name = UUID.randomUUID().toString();
        name = md5(name);
        name = name+"."+suffix;
        return name;
    }

    public static String extractSuffix(String path){
        if (Tool.isEmpty(path)) return null;
        int index = path.lastIndexOf(".");
        if (index > 0){
            return path.substring(index+1);
        }
        return  null;

    }

    public static void toMarket(Activity activity){
        String url = "market://details?id="+getPackageName();
        Uri uri = Uri.parse(url);
        Intent intent = new Intent("android.intent.action.VIEW", uri);
        try {
            activity.startActivity(intent);
        }catch (ActivityNotFoundException e){
            Tool.showToast(R.string.no_market_app);
        }catch (Exception e){

        }
    }
    public static void shakeView(View view){
        ObjectAnimator.ofFloat(view, "translationX", 0, -10, 10, -10, 10, 0).setDuration(280).start();
    }

    /**
     *
     * @param edit
     * @return 如果为空返回true
     */
    public static boolean checkEditEmptyAndShake(TextView edit, String emptyHint){
        boolean b = isEmpty(edit);
        if(b){
            shakeView(edit);
            if(isNotEmpty(emptyHint)) showToast(emptyHint);
        }
        return b;
    }

    public static boolean isEmpty(TextView edit){
        String content = edit.getText().toString().trim();
        boolean b = isEmpty(content);
        return b;
    }

    public static boolean checkEmptyAndShowToast(String content, String toast){
        boolean empty = isEmpty(content);
        if (empty) showToast(toast);
        return empty;
    }

    public static boolean checkEmptyAndShowToast(String content, int toastResId){
        return checkEmptyAndShowToast(content, getString(toastResId));
    }

    public static boolean isNotEmpty(TextView edit){
        return !isEmpty(edit);
    }

    public static boolean checkEditEmptyAndShake(TextView edit){
        return checkEditEmptyAndShake(edit, null);
    }
    public static boolean checkEditEmptyAndShake(TextView edit, int resId){
        return checkEditEmptyAndShake(edit, getString(resId));
    }


    private static SimpleDateFormat mSimpleDateFormat1 = new SimpleDateFormat("MM-dd HH:mm");



    //获取给定毫秒数的yyyy-MM-dd类型的日期
    public static String getTimeDate(long milliseconds){
        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH)+1;
        int curDay = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder();
        sb.append(curMonth < 10 ?"0":"").append(curMonth).append("-").append(curDay < 10 ?"0":"").append(curDay);
        String today = sb.toString();
        if(milliseconds <= 0) return today;

        sb.delete(0, sb.length());
        Date date = new Date(milliseconds);
        c.setTime(date);
        int year =  c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        if(year == curYear){
            if(month == curMonth){
                if(day == curDay){
                    return today;
                }else{
                    sb.append(month < 10 ?"0":"").append(month).append("-").append(day < 10 ?"0":"").append(day);
                    return sb.toString();
                }
            }else{
                sb.append(month < 10 ?"0":"").append(month).append("-").append(day < 10 ?"0":"").append(day);
                return sb.toString();
            }
        }else{
            sb.append(year).append("-").append(month < 10 ?"0":"").append(month).append("-").append(day < 10 ?"0":"").append(day);
            return sb.toString();
        }
    }

    //根据传进来的毫秒数返回00:00:00格式的字符串
    public static String getHourMinuteSecond(long mili){
        if (mili <= 0) return "00:00";
        long hour = mili / (60 * 60 * 1000);
        long minute = mili % ( 60 * 60 * 1000) / (60 * 1000);
        long second = mili % (60 * 1000) / 1000;
        String hourStr = "" + hour;
        String minuteStr = "" + minute;
        String secondStr = "" + second;
        if (hour < 10) hourStr = "0" + hourStr;
        if (minute < 10) minuteStr = "0" + minuteStr;
        if (second < 10) secondStr = "0" + secondStr;
        if (hour <= 0){
            return String.format("%s:%s", minuteStr, secondStr);
        }else{
            return String.format("%s:%s:%s", hourStr, minuteStr, secondStr);
        }
    }

    public static void inflateStub(Activity activity, int stubId){
        ViewStub stub = ViewSpanner.getViewById(activity, stubId);
        stub.setVisibility(View.VISIBLE);
    }

    private static String imei;
    public static String getImei(){
        if(TextUtils.isEmpty(imei)){
            TelephonyManager manager = (TelephonyManager) Box.sApp.getSystemService(Context.TELEPHONY_SERVICE);
            imei = manager.getDeviceId();
        }
        return imei;
    }

    public static JSONObject getJSONObject(String json){

        if(TextUtils.isEmpty(json)) return new JSONObject();
        JSONObject obj;
        try {
            obj = new JSONObject(json);
        } catch (Exception e) {
            obj = new JSONObject();
        }
        return obj;
    }

    public static JSONObject put(JSONObject obj, String key, Object value){
        if (obj != null){
            try {
                obj.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static JSONArray getJSONArray(String json){
        if(TextUtils.isEmpty(json)) return new JSONArray();
        JSONArray array = null;
        try {
            array = new JSONArray(json);
        } catch (JSONException e) {
            array = new JSONArray();
        }
        return array;
    }

    public static <T extends JSONExtractor> List<T> extractListFromJSONArray(JSONArray array, Class<T> clazz){
        return extractListFromJSONArray(array, SingletonFactory.getInstance(clazz));
    }

    public static <T> List<T> extractListFromJSONArray(JSONArray arr, JSONExtractor<T> extractor){
        int length;
        List<T> list = null;
        if(arr != null && (length = arr.length()) >= 0){            //等于0的时候也是可以的，表示从服务器拿到了数据，只不过只有0条数据
            list = new ArrayList<>(length);
            for(int i = 0; i<length; i++){
                JSONObject obj = arr.optJSONObject(i);
                if(obj != null){
                    T t = extractor.create(obj);
                    list.add(t);
                }
            }
        }
        return list;
    }
    public static <T> List<T> extractListFromJsonStr(String json, JSONExtractor<T> extractor){
        return extractListFromJSONArray(Tool.getJSONArray(json), extractor);
    }
    public static <T extends JSONExtractor> List<T> extractListFromJSONObject(JSONObject obj, String filed, JSONExtractor<T> extractor){
        if(obj != null && Tool.isNotEmpty(filed)){
            return extractListFromJSONArray(obj.optJSONArray(filed), extractor);
        }
        return null;
    }

    public static <T extends JSONExtractor> List<T> extractListFromJSONObject(JSONObject obj, String filed, Class<T> extractor){
        return extractListFromJSONObject(obj, filed, SingletonFactory.getInstance(extractor));
    }


    public static <T extends JSONExtractor> List<T> extractListFromApiResult(ApiResult result, String filed, Class<T> clazz){
        return  extractListFromJSONObject(result.data, filed, SingletonFactory.getInstance(clazz));
    }

    public static List<String> extractStringListFromJSONArray(JSONArray array){
        int length;
        List<String> list = null;
        if (array != null && (length = array.length()) >= 0){
            list = new ArrayList<>(length);
            for (int i = 0; i < length; i++){
                list.add(array.optString(i));
            }
        }
        return list;
    }

    public static List<String> extractStringListFromJSONObject(JSONObject obj, String field){
        return extractStringListFromJSONArray(obj.optJSONArray(field));
    }



    public static List<View> getAllViews(ViewGroup parent){
        int count = 0;
        if(parent == null || (count = parent.getChildCount()) == 0) return null;
        List<View> total = new ArrayList<>(count);
        for(int i = 0; i< count; i++){
            View v = parent.getChildAt(i);
            total.add(v);
            if(v instanceof ViewGroup){
                List<View> list = getAllViews((ViewGroup) v);
                if(list != null) total.addAll(list);
             }
        }
        return total;
    }

    //判断应用是否在运行
    public static boolean isRunningApp(Context context, String packageName) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1024);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName().equals(packageName)) {
                isAppRunning = true;
                break;
            }
        }
        return isAppRunning;
    }


    /***
     * 判断应用是否是前台
     * @return
     */
    public static boolean isRunningForeground () {
        ActivityManager am = (ActivityManager) Box.sApp.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        boolean b = false;
        if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(getPackageName())){
            b = true;
        }
        return b ;
    }



    public static boolean isValidPhoneNum(String str){
        if(!TextUtils.isEmpty(str)){
            /**
             * 验证手机格式
             */
				/*
				移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
				联通：130、131、132、152、155、156、185、186
				电信：133、153、180、189、（1349卫通）
				总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
				*/
            String telRegex = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、7、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
            return str.matches(telRegex);
        }
        return false;
    }

    public static boolean isValidPhoneNum(EditText edit){
        String content = edit.getText().toString().trim();
        if(isEmpty(content)){
            showToast(R.string.pls_input_phone_num);
            return false;
        }else{
            if(!isValidPhoneNum(content)){
                showToast(R.string.pls_input_valid_phone_num);
                return false;
            }
            return true;
        }
    }


    public static boolean isValidPhoneNumAndShake(EditText edit){
        boolean b = isValidPhoneNum(edit);
        if(!b) shakeView(edit);
        return b;
    }

    public static boolean isValidPhoneNumAndShake(EditText edit, String hint){
        boolean b = isValidPhoneNumAndShake(edit);
        if(!b && isNotEmpty(hint)) showToast(hint);
        return b;
    }

    public static boolean isValidPhoneNumAndShake(EditText edit, int hintResId){
        return isValidPhoneNumAndShake(edit, getString(hintResId));
    }

    public static boolean isValidPassword(EditText edit){
        String content = edit.getText().toString().trim();
        return isValidPassword(content);
    }
    public static boolean isValidPasswordAndShake(EditText edit, String hint){
        boolean b = isValidPassword(edit);
        if(!b){
            shakeView(edit);
            if (isNotEmpty(hint)) showToast(hint);
        }
        return b;
    }

    public static boolean isValidPasswordAndShake(EditText edit, int hintResId){
        return isValidPasswordAndShake(edit, getString(hintResId));
    }
    public static boolean isValidPassword(String content){
        if(isEmpty(content)){
            showToast(R.string.pls_input_phone_num);
            return false;
        }else{
            int length = content.length();
            if(length < 6 || length > 18){
                showToast(R.string.password_less_than_18_more_than_6);
                return false;
            }
            return true;
        }
    }

    public static String getContent(TextView textView){
        return textView.getText().toString().trim();
    }

    public static ColorStateList createColorStateList0(int normal, int pressed){
        int[] colors = new int[]{normal, normal, pressed};
        int[][] states = new int[3][];
        states[0] = new int[]{};
        states[1] = new int[]{android.R.attr.state_enabled};
        states[2] = new int[]{android.R.attr.state_pressed};
        ColorStateList stateList = new ColorStateList(states, colors);
        return stateList;
    }
    public static ColorStateList createColorStateList1(int normal, int selected){
        int[] colors = new int[]{normal, selected};
        int[][] states = new int[2][];
        states[0] = new int[]{};
        states[1] = new int[]{android.R.attr.state_selected};
        ColorStateList stateList = new ColorStateList(states, colors);
        return stateList;
    }

    public static ColorStateList getColoStateList(int resId){
        ColorStateList colorStateList = Box.sApp.getResources().getColorStateList(resId);
        return colorStateList;
    }

    public static int getColor(int colorResId){
        return Box.sApp.getResources().getColor(colorResId);
    }

//    private static final SparseArray<ImageSpan> sImgSpanCache = new SparseArray<>(2);
//
//    public static void append(TextView txt, String content, int color){
//        if(isEmpty(content)) return;
//        CharSequence cs = txt.getText();
//        SpannableStringBuilder ssb;
//        if (cs instanceof SpannableString){
//            ssb = new SpannableStringBuilder(cs);
//        }else{
//            ssb = new SpannableStringBuilder();
//        }
//        appendSpan(ssb, color, content);
//        txt.setText(ssb);
//    }
//
//    public static void appendSpan(TextView tv, int imgResId){
//        if(tv == null) return;
//        if (imgResId == 0) return;
//        CharSequence cs = tv.getText();
//        SpannableStringBuilder ssb;
//        if (cs instanceof SpannableStringBuilder){
//            ssb = (SpannableStringBuilder) cs;
//        }else{
//            ssb = new SpannableStringBuilder();
//        }
//        appendSpan(ssb, imgResId);
//        tv.setText(ssb);
//    }

//    public static void append(TextView txt, String content, int color){
//        if(isEmpty(content)) return;
//        txt.getText();
//        SpannableStringBuilder ssb = new SpannableStringBuilder();
//        appendSpan(ssb, color, content);
//        txt.append(ssb);
//    }
//    public static void appendSpan(SpannableStringBuilder ssb,int imgResId){
//        int dp5 = ScreenUtil.dpToPx(5);
//        appendSpan(ssb, imgResId, dp5, dp5);
//    }
//
//    public static void appendSpan(SpannableStringBuilder ssb,int imgResId, int mL, int mR){
//        ImageSpan span = sImgSpanCache.get(imgResId);
//        if(span == null){
//            synchronized (sImgSpanCache) {
//                span = sImgSpanCache.get(imgResId);
//                if (span == null) {
//                    span = new CenterVerticalImageSpan(Box.sApp, imgResId, mL, mR);
//                    sImgSpanCache.put(imgResId, span);
//                }
//            }
//        }
//        appendSpan(ssb, span);
//    }
//    public static void appendSpan(SpannableStringBuilder ssb, ImageSpan span){
//        ssb.append("s");
//        int length = ssb.length();
//        ssb.setSpan(span, length -1, length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//    }
//
//    private static final SparseArray<ForegroundColorSpan> sForegroundColorSpanCache = new SparseArray<>(2);
//
//    public static void appendSpan(SpannableStringBuilder ssb, int color, CharSequence text) {
//        ForegroundColorSpan span = sForegroundColorSpanCache.get(color);
//        if (span == null) {
//            synchronized (sForegroundColorSpanCache) {
//                span = sForegroundColorSpanCache.get(color);
//                if (span == null) {
//                    span = new ForegroundColorSpan(color);
//                    sForegroundColorSpanCache.put(color, span);
//                }
//            }
//        }
//        appendSpan(ssb, span, text);
//    }
//    public static void appendSpan(SpannableStringBuilder ssb, ForegroundColorSpan span, CharSequence text){
//        int start = ssb.length();
//        ssb.append(text);
//        ssb.setSpan(span,start, ssb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//    }


//    public static ArrayList<ActivityImageShower.ImageInfo> getImageInfoListFromStringList(List<String> data){
//        if(isEmpty(data)) return null;
//        ArrayList<ActivityImageShower.ImageInfo> infos = new ArrayList<>(data.size());
//        for(String s : data){
//            infos.add(new ActivityImageShower.ImageInfo(s));
//        }
//        return infos;
//    }


    public static String getTimeString1(String milliseconds){
        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH)+1;
        int curDay = c.get(Calendar.DAY_OF_MONTH);
        if(TextUtils.isEmpty(milliseconds)) return curMonth + "-" + curDay;

        Date date = new Date(Long.parseLong(milliseconds));
        c.setTime(date);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String hourStr = hour>9?hour+"":"0"+hour;
        String minuteStr = minute>9?minute+"":"0"+minute;
        String monthStr = month>9?month+"":"0"+month;
        String dayStr = day>9?day+"":"0"+day;

        String time;

        if(curYear == year){
            if(curMonth == month){
                if(curDay == day){
                    time = hourStr+":"+minuteStr;
                }else{
                    if(curDay - day == 1){
                        time = "昨天" + hourStr+":"+minuteStr;
                    }else if(curDay - day == 2){
                        time = "前天" + hourStr+":"+minuteStr;
                    }else{
                        time = monthStr+"-"+dayStr +" " + hourStr+":"+minuteStr;
                    }
                }
            }else{
                time = monthStr+"-"+dayStr;
            }
        }else{
            time = year+"-"+monthStr+"-"+dayStr;
        }
        return time;

    }
    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static long getTimeMilliseconds(String from){
        if(isEmpty(from)) return 0;
        if(from.matches(sTimePattern)){
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sSimpleDateFormat.parse(from));
                return c.getTimeInMillis();
            } catch (Exception e) {
                e.printStackTrace();
                return System.currentTimeMillis();
            }
        }else if(from.matches("\\d+")){
            return Long.parseLong(from);
        }
        return 0;
    }

    /**
     * 返回的是真正的月份，不需要减一
     * @param from
     * @return
     */
    public static int[] getYearMonthDay(String from){
        if(isEmpty(from)) return null;
        int[] time = null;
        if(from.matches(sTimePattern)){
            String[] timeArray = from.split("-");
            time = new int[3];
            time[0] = getInt(timeArray[0]);
            time[1] = getInt(timeArray[1]);
            time[2] = getInt(timeArray[2]);

        }else if(from.matches(sTimePattern_1)){

            String[] timeArray = from.split("-");
            time = new int[2];
            time[0] = getInt(timeArray[0]);
            time[1] = getInt(timeArray[1]);
        }
        return time;
    }

    //将当前时间转化为yyyy-MM-dd HH:mm:ss格式
    public static String getCurDateTime(){
        String time = sSimpleDateFormat.format(new Date());
        return time;
    }

    private static String sTimePattern = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
    private static String sTimePattern_1 = "^\\d{4}-\\d{2}-\\d{2}$";
    public static String getTimeString(String from){
        return getTimeString(getTimeMilliseconds(from));
    }


    public static String getTimeString(long milliseconds){
        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH)+1;
        int curDay = c.get(Calendar.DAY_OF_MONTH);
        if(milliseconds <= 0) return curMonth + "-" + curDay;

        Date date = new Date(milliseconds);
        c.setTime(date);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String hourStr = hour>9?hour+"":"0"+hour;
        String minuteStr = minute>9?minute+"":"0"+minute;
        String monthStr = month>9?month+"":"0"+month;
        String dayStr = day>9?day+"":"0"+day;

        String time;

        if(curYear == year){
            if(curMonth == month){
                if(curDay == day){
                    time = hourStr+":"+minuteStr;
                }else{
                    if(curDay - day == 1){
                        time = "昨天" + hourStr+":"+minuteStr;
//                    }else if(curDay - day == 2){
//                        time = "前天" + hourStr+":"+minuteStr;
                    }else{
                        time = monthStr+"-"+dayStr +" " + hourStr+":"+minuteStr;
                    }
                }
            }else{
                time = monthStr+"-"+dayStr;
            }
        }else{
            time = year+"-"+monthStr+"-"+dayStr;
        }
        return time;
    }




    public static String getDateTime(long milliseconds){
        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH)+1;
        int curDay = c.get(Calendar.DAY_OF_MONTH);
        if(milliseconds <= 0) return curMonth + "-" + curDay;

        Date date = new Date(milliseconds);
        c.setTime(date);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String hourStr = hour>9?hour+"":"0"+hour;
        String minuteStr = minute>9?minute+"":"0"+minute;
        String monthStr = month>9?month+"":"0"+month;
        String dayStr = day>9?day+"":"0"+day;

        String time;

        if(curYear == year){
            if(curMonth == month && curDay == day){
                time = hourStr+":"+minuteStr;
            }else{
                time = monthStr+"-"+dayStr + " " + hourStr+":"+minuteStr;
            }
        }else{
            time = year+"-"+monthStr+"-"+dayStr + " " + hourStr+":"+minuteStr;
        }
        return time;
    }




    public static Drawable getDrawable(int resId){
        return getDrawable(Box.sApp, resId);
    }

    public static Uri getResourceUri(int resId){

        Uri uri = null;
        if(resId > 0){
            Resources r = Box.sApp.getResources();
            uri =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + getPackageName() + "/"
                    + r.getResourceTypeName(resId) + "/"
                    + r.getResourceEntryName(resId));
        }
        return uri;
    }

    public static String getDrawableUriString(int resId){
        Uri uri = getResourceUri(resId);
        if(uri == null) return null;
        return uri.toString();
    }

    public static String convertFirstAlpha(String s){
        String r = getStringFirstAlpha(s);
        if(!r.matches("[A-Z]")) r = "#";
        return r;
    }

    public static String getStringFirstAlpha(String s){
        if(TextUtils.isEmpty(s) || s.trim().length() == 0) return "";
        return getCharFirstAlpha(s.trim().toCharArray()[0]);
    }

    public static String getCharFirstAlpha(char ch){
        if(Pinyin.isChinese(ch)){
            String spell = Pinyin.toPinyin(ch);
            return spell.substring(0, 1);
        }
        return String.valueOf(ch).toUpperCase();
    }

    public static String getAbbr(String str){
        char[] chars = str.trim().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (Character ch : chars){
            sb.append(getCharFirstAlpha(ch));
        }
        return sb.toString().toUpperCase();
    }

    //根据map的特性去排序
    public static <T extends AlphaIndexAble> List<T> sort(List<T> ori){
        long start = System.currentTimeMillis();
        Map<String, T> sortMap = new TreeMap<>();
        for(T t : ori){
            String name = t.getAbbr();
            name = getSort1Key(name, sortMap);
            sortMap.put(name, t);
        }
        List<T> afterList = new ArrayList<>(sortMap.values());

        long end = System.currentTimeMillis();
        Log.e("sort", "耗时---》"+(end - start));
        return afterList;
    }

    //可能会出现名称一样的情况，用这个方法产生唯一的key
    private static  <T extends AlphaIndexAble> String getSort1Key(String name, Map<String, T> sortMap){
        if(sortMap.get(name) != null){
            name += " ";   //空格  ascii 32  是可显示字符中最前面的
            name = getSort1Key(name, sortMap);
        }
        return name;
    }

    public static int getDistance(PointF p1, PointF p2){
        float x = p1.x - p2.x;
        x *= x;
        float y = p1.y - p2.y;
        y *= y;
        return (int) Math.sqrt(x + y);
    }


    public static String getPathFromUri(Uri uri){
        if(uri == null) return null;
        String scheme = uri.getScheme();
        if(isEmpty(scheme)){
            return uri.getPath();
        }else if(ContentResolver.SCHEME_FILE.equals(scheme)){
            return uri.getPath();
        }else if(ContentResolver.SCHEME_CONTENT.equals(scheme)){
            Cursor cursor = Box.sApp.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        String path = cursor.getString(index);
                        return path;
                    }
                }
                cursor.close();
            }
        }
        return uri.toString();
    }


    public static File getFileFromBitmap(File file, Bitmap bitmap){
        if(file != null && bitmap != null && bitmap.getWidth() > 0 && bitmap.getHeight() > 0){
            try{
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                bitmap.recycle();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return file;
    }
    //根据图片获取文件
    /**
     * @param maxSize	文件的最大容量
     * @param maxWidth	文件的最大宽度
     * @return 根据图片路径获取对应的合适的文件
     */
    public static File getPictureFileLessThan(Uri uri, int maxSize, int maxWidth){
        String path = getPathFromUri(uri);
        return getImageFileLessThan(path, maxSize, maxWidth);
    }

    public static File getImageFileLessThan(String path, int maxSize, int maxWidth){
        if(path == null) return null;
        File f = new File(path);
        if(!f.exists() || f.length() ==0) return null;
        long length = f.length();
        if(length < maxSize) return f;

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        int outH = opts.outHeight;
        int outW = opts.outWidth;
        opts.inSampleSize = 1;
        while(outH/opts.inSampleSize > maxWidth || outW/opts.inSampleSize > maxWidth){
            opts.inSampleSize <<= 1;
        }
        if(opts.inSampleSize == 1) opts.inSampleSize = 0;

        opts.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        File newF = new File(Constant.f_image, randomName("jpg"));
        newF = getFileFromBitmap(newF, bitmap);
        length = newF.length();
        return newF;
    }

    public static Uri getImageMessageThumb(String path){
        File f = getImageFileLessThan(path, 128 * 1024, 256);
        if (f == null) return null;
        return Uri.fromFile(f);
    }

    //获取聊天时候的图片信息，包含大图和缩略图
    public static Uri[] getImageMessageUris(String path){
        //大图大小不超过1M, 最大边长不大于2048
        //缩略图大小不超过128k, 最大边长不大于256
        Uri[] uris = new Uri[2];
        File f = getImageFileLessThan(path, 1024 * 1024, 2048);
        if (f == null) return uris;
        uris[0] = Uri.fromFile(f);
        File thumb = getImageFileLessThan(f.getAbsolutePath(), 128 * 1024, 256);
        if (thumb != null) uris[1] = Uri.fromFile(thumb);
        return uris;
    }


    public static int[] getImageWH(Uri uri){
        if (uri == null) return null;
        String path = getPathFromUri(uri);
        return getImageWH(path);
    }

    public static int[] getImageWH(String path){
        if (path == null || !new File(path).exists()) return null;
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, op);
        int[] wh = new int[2];
        wh[0] = op.outWidth;
        wh[1] = op.outHeight;
        return wh;
    }


    //根据毫秒数获取时间，如果不足1小时，返回形式如 00:00 如果大于一小时返回形如 00:00:00
    public static String getSecond(long milli){
        long second = milli/1000;
        long hour = second / 3600;
        long minute = second % 3600 / 60;
        second = second % 3600 % 60;
        StringBuilder sb = new StringBuilder();
        if (hour > 0){
            if (hour < 10){
                sb.append(0);
            }
            sb.append(hour).append(":");
        }
        if (minute < 10){
            sb.append("0");
        }
        sb.append(minute).append(":");
        if (second < 10){
            sb.append(0);
        }
        sb.append(second);
        return sb.toString();
    }

    public static String getStringFromBundleIfExist(Bundle bundle, String key){
        if (bundle != null && bundle.containsKey(key)) return bundle.getString(key);
        return null;
    }

    public static String getRandomCacheImage(){
        return Constant.f_image + Tool.randomName("jpg");
    }

    public static String getRandomCacheVideo(){
        return Constant.f_video + Tool.randomName("mp4");
    }

    public static boolean isValidFile(String path){
        return Tool.isNotEmpty(path) && isValidFile(new File(path));
    }
    public static boolean isNotValidFile(String path){
        return !isValidFile(path);
    }

    public static boolean isValidFile(File file){
        boolean b = file != null;
        if (b){
            b = file.exists();
            if (b){
                long size = file.length();
                b = size > 0;
                return b;
            }
        }
        return false;
    }

    public static void startActivityToTakePhoto(Activity activity, String destUrl, int requestCode){
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(destUrl)));
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Tool.showToast("没有拍照应用");
        } catch (Exception e) {
            Tool.showToast("拍照失败");
        }
    }

    public static void startActivityToSelectPicture(Activity activity, int requestCode) {
        Intent intent3 = new Intent();
        intent3.setType("image/*");
        intent3.setAction(Intent.ACTION_PICK);
        try {
            activity.startActivityForResult(intent3, requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Tool.showToast(R.string.no_picture_library_app);
        } catch (Exception e) {
            Tool.showToast(R.string.take_photo_error);
        }
    }

    public static void startActivityToMakeVideo(Activity activity, String destUrl, int requestCode){

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(destUrl)));
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Tool.showToast("没有录像应用");
        } catch (Exception e) {
            Tool.showToast("录制失败");
        }
    }

    public static void startActivity(BaseActivity activity, Class<? extends BaseActivity> clazz, Object data){
        Intent intent = new Intent(activity, clazz);
        if (data instanceof Integer){
            intent.putExtra(Constant.DATA, (Integer) data);
        }else if (data instanceof String){
            intent.putExtra(Constant.DATA, (String) data);
        } else if (data instanceof Parcelable){
            intent.putExtra(Constant.DATA, (Parcelable) data);
        }else if (data instanceof Bundle){
            intent.putExtras((Bundle) data);
        }else{
            throw new RuntimeException("no this kind of data current !!");
        }
        activity.startActivity(intent);
    }


    public static int[] getWidthAndHeightOfLocalVideo(String path){
        int[] wh = null;
        try {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//            if (Build.VERSION.SDK_INT >= 14) {
//                HashMap<String, String> headers = new HashMap<>();
//                headers.put("AbsUser-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
//                retriever.setDataSource(path, headers);
//            } else {
                retriever.setDataSource(path);
//            }
            String w = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            String h = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            int intW = Integer.valueOf(w);
            int intH = Integer.valueOf(h);
            wh = new int[]{intW, intH};

        }catch (Exception e){
            e.printStackTrace();
        }

        return wh;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) return null;
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Drawable getDrawable(Context context, @DrawableRes int resId){
        final int version = Build.VERSION.SDK_INT;
        if (version >= 21) {
            return context.getDrawable(resId);
        } else {
            return context.getResources().getDrawable(resId);
        }
    }

    public static int getHttpContentLength(String httpUrl){
        HttpURLConnection connection = null;
        try{
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();
            int latestFileSize = connection.getContentLength();         //获取到最新的文件的长度
            return latestFileSize;
        }catch (Exception e){
            return 0;
        }finally {
            if (connection != null) connection.disconnect();
        }
    }

    public static <T> T forceCast(Object obj) {
        return (T) obj;
    }

    public static boolean copyFile(String fromFilePath, String toFilePath) {
        if(isNotEmpty(fromFilePath) && isNotEmpty(toFilePath)) {
            if(!(new File(fromFilePath)).exists()) {
                return false;
            } else {
                try {
                    FileInputStream e = new FileInputStream(fromFilePath);
                    FileOutputStream fosto = new FileOutputStream(toFilePath);
                    copyFile(e, fosto);
                    return true;
                } catch (Throwable var4) {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static void copyFile(FileInputStream src, FileOutputStream dst) throws Throwable {
        byte[] buf = new byte[65536];

        for(int len = src.read(buf); len > 0; len = src.read(buf)) {
            dst.write(buf, 0, len);
        }

        src.close();
        dst.close();
    }

    //检查应用是否有某个权限
    public static boolean hasPermission(String permission){
        PackageManager packageManager = Box.sApp.getPackageManager();
        return packageManager.checkPermission(permission, getPackageName()) == PackageManager.PERMISSION_GRANTED;
    }

    //强制隐藏键盘
    public static void hideInputManager(EditText edit){
        try {
            InputMethodManager imm = (InputMethodManager)Box.sApp.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        hideInputManager(edit);
    }
    public static void hideInputManager(View edit){
        try {
            InputMethodManager imm = (InputMethodManager)Box.sApp.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showInputManager(EditText editText){
        showInputManager(editText, 0);
    }

    public static void showInputManager(final EditText edit, int delayMillis){
        edit.postDelayed(() -> {
            try {
                InputMethodManager imm = (InputMethodManager)Box.sApp.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edit, InputMethodManager.SHOW_FORCED);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, delayMillis);
    }

    //能被100整除，且能被400整除
    //不能被100整除，但是能被4整除
    public static boolean isLeap(int year){
        boolean leap = false;
        if (year % 100 == 0){
            if (year % 400 == 0) leap = true;
        }else if (year % 4 == 0){
            leap = true;
        }
        return leap;
    }

    public static String extractError(ApiResult apiResult, String defaultErr){
        if (apiResult != null){
            String err = apiResult.msg;
            if (Tool.isEmpty(err)) err = defaultErr;
            return err;
        }
        return defaultErr;
    }

    public static String extractError(ApiResult apiResult, int textResId){
        return extractError(apiResult, getString(textResId));
    }

    public static String getStringFromIntent(Intent intent, String key){
        return getStringFromIntent(intent, key, null);
    }

    public static String getStringFromIntent(Intent intent, String key, String defaultValue){
        if (intent != null && intent.hasExtra(key)) return intent.getStringExtra(key);
        return defaultValue;
    }

    public static int getIntFromIntent(Intent intent, String key, int defaultValue){
        if (intent != null && intent.hasExtra(key)) return intent.getIntExtra(key, defaultValue);
        return defaultValue;
    }

    public static int getIntFromIntent(Intent intent, String key){
        return getIntFromIntent(intent, key, 0);
    }

    public static  <T extends Parcelable> T getParcelableFromIntent(Intent intent, String name) {
        if (intent == null) return null;
        return  intent.getParcelableExtra(name);
    }

    public static <T extends Parcelable> T getParcelableFromBundle(Bundle bundle, String name){
        if (bundle == null) return null;
        return bundle.getParcelable(name);

    }

    /**
     *
     * @param result
     * @param hint
     * @return
     */
    public static boolean checkResultAndShowToastIfBad(ApiResult result, String hint){
        if (checkResult(result)) return true;
        showToast(extractError(result, hint));
        return false;
    }

    public static boolean checkResultAndShowToast(ApiResult result, String right, String error){
        boolean r = checkResult(result);
        if (r){
            Tool.showToast(right);
        }else{
            showApiError(result, error);
        }
        return r;
    }

    public static boolean checkResultAndShowToast(ApiResult result, int right, int error){
        return checkResultAndShowToast(result, getString(right), getString(error));
    }

    public static boolean checkResult(ApiResult result){
        return result != null && result.isOk();
    }


    public static boolean checkResultAndShowToastIfBad(ApiResult result, int hintResId){
        return checkResultAndShowToastIfBad(result, getString(hintResId));
    }

    /**
     * 超过10万就返回多少万
     * @param num
     * @return
     */
    public static String formatBigNum(int num){
        if (num < 100000) return String.valueOf(num);
        return String.format("%s万", num / 10000);
    }

    /**
     * 小于1M,返回Kb
     * 如果小于1G，返回M，大于1G返回G
     * @param b
     * @return
     */
    public static String formatFileSize(long b){
        if (b < 1024 * 1024){
            return String.format("%.1fK", b / (float)1024);
        }else if (b < 1024 * 1024 * 1024){
            return String.format("%.2fM", b / ((float)1024 * 1024));
        }else{
            return String.format("%.2fG", b / ((float)1024 * 1024 * 1024));
        }
    }

    /**
     * 剔除null
     * 这是一个非常操蛋的问题
     *
     * @param obj
     */
    public static void eliminateNull(JSONObject obj){
        if (obj == null) return;
        Iterator<String> keys = obj.keys();
        while (keys.hasNext()){
            String key = keys.next();
            Object content = obj.opt(key);
            if (content instanceof JSONArray){
                JSONArray array = (JSONArray) content;
                eliminateNull(array);
            }else if (content instanceof JSONObject){
                eliminateNull((JSONObject) content);
            }else{
                if (obj.isNull(key)){
                    try {
                        obj.put(key, "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void eliminateNull(JSONArray array){
        if (array == null) return;
        int N = array.length();
        for(int i = 0 ; i<N; i++){
            Object content = array.opt(i);
            if (content instanceof JSONArray){
                eliminateNull((JSONArray) content);
            }else if (content instanceof  JSONObject){
                eliminateNull((JSONObject) content);
            }else{
//                String dtt = array.optString(i);
//                int iii = array.optInt(i);
                if (array.isNull(i)){
                    try {
                        array.put(i, "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
//                dtt = array.optString(i);
//                iii = array.optInt(i);
//                dtt += "";
            }
        }
    }

    public static String formatNum(int num){
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(num);
    }

    private static final int[] DAY_COUNT = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static int getDayCount(int month){
        return DAY_COUNT[month];
    }

    /**
     *
     * @param num
     * @return
     */
    public static String floatToPercent(float num){
        num *= 100;
        DecimalFormat decimalFormat=new DecimalFormat(".0");
        String p = decimalFormat.format(num);
        if (p.endsWith(".0")) p = p.substring(0, p.indexOf(".0"));
        return p;
    }
    //获取对应日期的星期
    public static int getWeekIndex(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        int weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;         //为什么要减1
        return weekIndex;
    }


    /**
     * 获取view的默认大小
     * @param size
     * @param measureSpec
     * @return
     */
    public static int getDefaultSize(int size, int measureSpec) {
        return View.resolveSize(size, measureSpec);
    }


    public static boolean isMainThread(Boolean...throwIfNot){
        boolean b = Looper.myLooper() == Looper.getMainLooper();
        Boolean needThrow = getFirstObject(throwIfNot, false);
        if (!b && needThrow){
            throw new IllegalStateException("current thread is not main thread !!");
        }
        return b;
    }

    public static boolean isIntentExisting(Context context, String action, String toastIfNotExist) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean exist = resolveInfo.size() > 0;
        if (!exist){
            showToast(toastIfNotExist);
        }
        return exist;
    }

    public static boolean isIntentExisting(Context context, String action){
        return isIntentExisting(context, action, null);
    }

    public static boolean isIntentExisting(Context context, String action, int toastIfNotExist){
        return isIntentExisting(context, action, getString(toastIfNotExist));
    }

}
