package com.feximin.library.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.feximin.library.util.LeakHelper;
import com.feximin.library.util.LifeCycleHelper;
import com.feximin.library.util.ViewSpanner;
import com.feximin.library.util.rx.RxBus;
import com.feximin.library.util.rx.RxBusHelper;


/**
 * Created by Neo on 15/11/17.
 */
public abstract class BaseActivity extends AppCompatActivity implements RxBus.RxBusManager {

    protected boolean mTransparentStatusBar;
    protected BaseActivity mActivity;

    protected int FRAGMENT_CONTAINER_ID;

    protected FragmentManager mFragmentManager;
    protected RxBusHelper mRxBusHelper;
    protected LifeCycleHelper mLifeCycleHelper;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = this;
        this.mFragmentManager = getSupportFragmentManager();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        Window window = getWindow();
//        int sdk = Build.VERSION.SDK_INT;
//        if(sdk >= Build.VERSION_CODES.LOLLIPOP){   //透明状态栏
//            window.getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  |
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }else if(sdk >= Build.VERSION_CODES.KITKAT){
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }else{
//            mTransparentStatusBar = false;
//        }
        mRxBusHelper = RxBusHelper.obtain(this);
        mLifeCycleHelper = LifeCycleHelper.obtain();
        setSoftInputMode();
        setContentView(getLayoutResId());

        beforeInitViews();
        initViews(savedInstanceState);
        afterInitViews();
        LeakHelper.watch(this);
    }


    @Override
    public void onEvent(Object ev){

    }

    protected void beforeInitViews(){}
    protected abstract void initViews(Bundle savedInstanceState);
    protected void afterInitViews(){}


    protected <T extends View> T getViewById(int id) {
        return ViewSpanner.getViewById(mActivity, id);
    }
    protected <T extends View> T getViewById(int id, String methodName , Object...params) {
        return ViewSpanner.getViewById(mActivity, id, methodName, params);
    }

    public <T extends View> T getViewById(int id, View.OnClickListener listener){
        T t = ViewSpanner.getViewById(this, id);
        t.setOnClickListener(listener);
        return t;
    }

    public TextView getTextViewById(int id, String text){
        TextView tv = getViewById(id);
        tv.setText(text);
        return tv;
    }

    public TextView getTextViewById(int id, int textResId){
        return getTextViewById(id, getString(textResId));
    }

    protected void bindClick(View v, String methodName, Object...params) {
        ViewSpanner.bindClick(v, methodName, mActivity, params);
    }

    public void setSoftInputMode() {
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
    }
    public void setSoftInputMode(int mode) {
        getWindow().setSoftInputMode(mode);
    }

    public int getFragmentContainerId(){
        return FRAGMENT_CONTAINER_ID;
    }
    protected abstract int getLayoutResId();


    public void startActivity(Class<? extends Activity> activity){
        startActivity(new Intent(this, activity));
    }

    @Override
    public void addSubscription(Subscription sub){
        mRxBusHelper.addSubscription(sub);
    }

    public void addLifeCycleComponent(ILifeCycle lc){
        mLifeCycleHelper.addLifeCycleComponent(lc);
    }
    public void startActivityForResult(Class<? extends Activity> activity, int requestCode){
        startActivityForResult(new Intent(this, activity), requestCode);
    }

    public void startActivityAndFinish(Class<? extends Activity> activity){
        startActivity(activity);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isFinishing()){
            mRxBusHelper.unRegister();
            mLifeCycleHelper.destroy();
            onFinishing();
        }
    }

    protected void onFinishing(){

    }
}
