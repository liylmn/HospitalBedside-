package com.zbiti.hospitalbedside.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zbiti.android.baseframe.http.HttpHelper;
import com.zbiti.android.baseframe.httpbase.DownloadCallback;
import com.zbiti.hospitalbedside.R;
import com.zbiti.hospitalbedside.common.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 版本更新对话框
 * Created by Digsol on 2016/5/6 11:16.
 */
public class UpdateFragment extends DialogFragment {
    private TextView mTextViewTitle;
    private TextView mTextViewContent;
    private ProgressBar mProgressBar;

    private FragmentCallback mFragmentCallback;

    private String versionCode = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_update, container);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        setListeners();
        mFragmentCallback = (FragmentCallback) getActivity();
        mTextViewContent.setText(getArguments().getString("content"));
        versionCode = getArguments().getString("code");
        mTextViewTitle.setText("新版本v" + versionCode + "   更新");
        download(getArguments().getString("path"), "bedVersion" + versionCode + ".apk");
    }

    private void findViews() {
        mTextViewTitle = (TextView) getView().findViewById(R.id.tv_title);
        mTextViewContent = (TextView) getView().findViewById(R.id.tv_content);
        mProgressBar = (ProgressBar) getView().findViewById(R.id.pb_update);
    }

    private void setListeners() {
    }

    @Override
    public void onDestroyView() {
        HttpHelper.getInstance(getActivity()).cancel();
        super.onDestroyView();
    }

    private void download(final String path, final String name) {
        HttpHelper.getInstance(getActivity()).download(Constant.Download, path, name, new
                DownloadCallback() {
                    @Override
                    public void onFinish() {
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("path", path);
                        map.put("name", name);
                        mFragmentCallback.onDataResult(FragmentCallbackResult.SUCCESS, map);
                        dismiss();
                    }

                    @Override
                    public void onError() {
                        mFragmentCallback.onDataResult(FragmentCallbackResult.FAILED, null);
                        dismiss();
                    }

                    @Override
                    public void onProgress(int progress) {
                        mTextViewTitle.setText("新版本v" + versionCode + "   更新中(" + progress + "%)");
                        mProgressBar.setProgress(progress);
                    }
                });
    }
}
