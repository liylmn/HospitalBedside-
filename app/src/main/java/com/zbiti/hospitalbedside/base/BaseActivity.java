package com.zbiti.hospitalbedside.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


/**
 * 一般页面基类
 * Created by Song on 2016/8/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public ProgressDialog mProgressDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(setContentView());
        getSupportActionBar().hide();
        findViews();
        setListeners();
        initData();
    }

    @Override
    protected void onResume() {
        findViewById(setRootLayoutId()).setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        dismissWaiting();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
    }

    /**
     * 设置页面布局
     *
     * @return int
     */
    protected abstract int setContentView();

    /**
     * 获取控件
     */
    protected abstract void findViews();

    /**
     * 设置监听
     */
    protected abstract void setListeners();

    /**
     * 初始化页面数据
     */
    protected abstract void initData();

    /**
     * 设置页面根布局ID
     *
     * @return
     */
    protected abstract int setRootLayoutId();

    public void showWaiting(String message, boolean cancelable) {
        if (null == mProgressDialog) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public void showWaiting(String message) {
        showWaiting(message, false);
    }

    public void showWaiting() {
        showWaiting("数据处理中,请稍候...");
    }

    public void dismissWaiting() {
        if (null != mProgressDialog && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 关闭软键盘
     */
    public void closeInputMethod(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

}
