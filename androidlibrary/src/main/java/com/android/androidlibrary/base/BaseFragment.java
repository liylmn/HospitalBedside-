package com.android.androidlibrary.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.androidlibrary.event.BusFactory;
import com.android.androidlibrary.util.KnifeUtil;


import butterknife.Unbinder;




public abstract class BaseFragment extends Fragment implements UiCallback {
    protected View rootView;
    protected LayoutInflater layoutInflater;
    protected Activity context;
    protected UiDelegate uiDelegate;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layoutInflater = inflater;
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), null);
            unbinder = KnifeUtil.bind(this, rootView);
        } else {
            ViewGroup viewGroup = (ViewGroup) rootView.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(rootView);
            }
        }

        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (useEventBus()) {
            BusFactory.getBus().register(this);
        }
        setListener();
        initData(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.context = (Activity) context;
        }
    }

    /**
     *加载数据进度条 
     */
    public void showProgressDialog() {

    }
    /**
     *关闭进度条 
     */
    public void closeProgressDialog() {

    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        context = null;

    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BusFactory.getBus().unregister(this);
        getUiDelegate().destory();
    }

    protected UiDelegate getUiDelegate() {
        if (uiDelegate == null) {
            uiDelegate = UiDelegateBase.create(getContext());
        }
        return uiDelegate;
    }

}
