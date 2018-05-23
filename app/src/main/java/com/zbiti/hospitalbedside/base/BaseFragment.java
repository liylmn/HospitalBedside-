package com.zbiti.hospitalbedside.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * * 一般Fragment基类
 * Created by Song on 2016/8/15.
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        return inflater.inflate(setContentView(), container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        setListeners();
        initData();
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

    public void toast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_SHORT).show();
    }
}
