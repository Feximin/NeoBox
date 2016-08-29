package com.feximin.box.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.WindowManager;

import com.feximin.library.R;

/**
 * Created by Neo on 16/5/12.
 */
public abstract class BaseFullScreenDialogFragment extends BaseDialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.Translucent);
        dialog.getWindow().setWindowAnimations(R.style.style_dialog_anim_fade);
        return dialog;
    }

    @Override
    protected void setDialogSize(Dialog dialog){
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }
}
