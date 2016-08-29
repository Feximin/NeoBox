package com.feximin.box.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.feximin.box.activity.BaseActivity;
import com.feximin.box.util.ScreenUtil;
import com.feximin.box.util.ViewSpanner;
import com.feximin.library.R;

/**
 * Created by Neo on 16/2/23.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    protected View mRootView;
    protected LayoutInflater mInflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        mRootView = inflater.inflate(getLayoutResId(), null);
        initViews();
        return mRootView;
    }

    protected abstract void initViews();

    protected abstract int getLayoutResId();

    public <T extends View> T getViewById(int id){
        return ViewSpanner.getViewById(mRootView, id);
    }


    public boolean show(BaseActivity activity) {
        if (activity.isLegalToShowDialogFragment()) {
            super.show(activity.getSupportFragmentManager(), getClass().getName());
            return true;
        }else{
            return false;
        }
    }

    public void show(Fragment fragment){
        if (fragment.isResumed()){
            super.show(fragment.getFragmentManager(), getClass().getName());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.style_dialog);
        dialog.getWindow().setWindowAnimations(R.style.style_dialog_anim);
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dialog dialog = getDialog();
        if(dialog == null) return;
        setDialogSize(dialog);
    }

    protected void setDialogSize(Dialog dialog){
        int width = ScreenUtil.getScreenWidthScaleValue(0.85f);
        dialog.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
