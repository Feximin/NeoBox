package com.feximin.library.activity;

import android.os.Bundle;

import com.mianmian.guild.R;
import com.mianmian.guild.view.NWebView;

/**
 * Created by Neo on 15/11/24.
 */
public abstract class BaseWebViewActivity extends BaseActivity {
    protected NWebView mWebView;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        mWebView = getViewById(R.id.webview);
    }

}
