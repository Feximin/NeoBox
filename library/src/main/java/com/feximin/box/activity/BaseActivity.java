package com.feximin.box.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.feximin.box.fragment.BaseFragment;
import com.feximin.box.fragment.FragmentHelper;
import com.feximin.box.fragment.LoadingFragment;
import com.feximin.box.interfaces.ILifeCycle;
import com.feximin.box.util.LeakHelper;
import com.feximin.box.util.LifeCycleHelper;
import com.feximin.box.util.Tool;
import com.feximin.box.util.ViewSpanner;
import com.feximin.box.util.rx.RxBus;
import com.feximin.box.util.rx.RxBusHelper;
import com.feximin.box.view.TitleBar;
import com.feximin.library.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import rx.Subscription;

/**
 * Created by Neo on 15/11/17.
 */
public abstract class BaseActivity extends AppCompatActivity implements RxBus.RxBusManager {

    protected BaseActivity mActivity;

    protected int mFragmentContainerId;
    protected FragmentManager mFragmentManager;
    protected RxBusHelper mRxBusHelper;
    protected LifeCycleHelper mLifeCycleHelper;

    private ActivityStatus mCurActivityStatus;
    private LayoutInflater mInflater;
    protected TitleBar mTitleBar;               //initViews的时候去决定是否有TitleBar

    private static enum ActivityStatus {
        Create, Start, Resume, Pause, Stop, Destroy, SaveInstance
    }

    protected LoadingFragment mLoadingDialog;

    @Override
    protected void onStart() {
        super.onStart();
        this.mCurActivityStatus = ActivityStatus.Start;
    }


    public void setLeftButDefaultListener(){
        if (mTitleBar != null){
            mTitleBar.setLeftBut(R.mipmap.img_back, v -> finish());
        }
    }

    public void showTitle(boolean b){
        if (mTitleBar == null) return;
        mTitleBar.setVisibility(b?View.VISIBLE:View.GONE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCurActivityStatus = ActivityStatus.Create;
        this.mActivity = this;
        this.mFragmentContainerId = initFragmentContainerId();
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
        mRxBusHelper = RxBusHelper.obtain(this).register();
        mLifeCycleHelper = LifeCycleHelper.obtain();
        setSoftInputMode();
        setContentView();
        beforeInitViews();
        initViews(savedInstanceState);
        afterInitViews();
        LeakHelper.watch(this);
    }

    protected void setContentView() {
        setContentView(getLayoutResId());
    }

    public boolean isLegalToShowDialogFragment(){
        return mCurActivityStatus != ActivityStatus.Stop
                && mCurActivityStatus != ActivityStatus.SaveInstance
                && mCurActivityStatus != ActivityStatus.Destroy;
    }


    public void showLoadingDialog(boolean cancelable){
        if (mLoadingDialog != null && mLoadingDialog.isResumed()) return;
        dismissLoadingDialog();
        mLoadingDialog = LoadingFragment.obtain(cancelable);
        mLoadingDialog.show(mActivity);
    }

    public void dismissLoadingDialog(){
        if (mLoadingDialog != null){
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.mCurActivityStatus = ActivityStatus.Start;
    }


    private void ensureInflater(){
        if (mInflater == null){
            mInflater = LayoutInflater.from(mActivity);
        }
    }

    public View inflate(int layoutResId){
        ensureInflater();
        View view = mInflater.inflate(layoutResId, null);
        return view;
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
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

    public static final int RESIZE = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
    public static final int PAN = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
    public static final int UNSPECIFIED = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED;

    public static final int STATE_ALWAYS_HIDDEN = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;

    @IntDef({RESIZE, PAN, UNSPECIFIED, STATE_ALWAYS_HIDDEN})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface SoftInputMode{}

    public void setSoftInputMode() {
        setSoftInputMode(UNSPECIFIED);
    }
    public void setSoftInputMode(@SoftInputMode int mode) {
        getWindow().setSoftInputMode(mode);
    }

    public int initFragmentContainerId(){
        return 0;
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


    public ActivityStatus getCurActivityStatus(){
        return mCurActivityStatus;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.mCurActivityStatus = ActivityStatus.SaveInstance;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mCurActivityStatus = ActivityStatus.Pause;
        if(isFinishing()){
            mRxBusHelper.unRegister();
            mLifeCycleHelper.destroy();
            onFinishing();
        }
    }

    protected void onFinishing(){

    }
    public void replace(BaseFragment fragment, String...tag){
        FragmentHelper.replace(getSupportFragmentManager(), fragment, mFragmentContainerId, Tool.getFirstObject(tag));
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mCurActivityStatus = ActivityStatus.Stop;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mCurActivityStatus = ActivityStatus.Destroy;
    }
}
