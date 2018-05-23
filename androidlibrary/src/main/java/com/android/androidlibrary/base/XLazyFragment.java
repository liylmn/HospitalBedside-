package com.android.androidlibrary.base;

import android.os.Bundle;

import com.android.androidlibrary.event.BusFactory;
import com.android.androidlibrary.util.KnifeUtil;

import butterknife.Unbinder;


public abstract class XLazyFragment extends LazyFragment implements UiCallback {

    private UiDelegate uiDelegate;

    private Unbinder unbinder;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            unbinder = KnifeUtil.bind(this, getRealRootView());
        }
        if (useEventBus()) {
            BusFactory.getBus().register(this);
        }
        setListener();
        initData(savedInstanceState);
    }


    @Override
    protected void onDestoryLazy() {
        super.onDestoryLazy();
        if (useEventBus()) {
            BusFactory.getBus().unregister(this);
        }
        getUiDelegate().destory();
    }


    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public void setListener() {

    }

    protected UiDelegate getUiDelegate() {
        if (uiDelegate == null) {
            uiDelegate = UiDelegateBase.create(getContext());
        }
        return uiDelegate;
    }


}
