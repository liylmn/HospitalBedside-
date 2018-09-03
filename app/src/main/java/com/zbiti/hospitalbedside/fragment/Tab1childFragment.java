package com.zbiti.hospitalbedside.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zbiti.hospitalbedside.R;
import com.zbiti.hospitalbedside.base.BaseFragment;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 基础数据
 *
 * @author hp
 */
public class Tab1childFragment extends BaseFragment {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.ll1)
    AutoLinearLayout mLl1;
    @BindView(R.id.tv2)
    TextView mTv2;
    @BindView(R.id.ll2)
    AutoLinearLayout mLl2;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.ll3)
    AutoLinearLayout mLl3;
    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.ll4)
    AutoLinearLayout mLl4;
    @BindView(R.id.tv5)
    TextView mTv5;
    @BindView(R.id.ll5)
    AutoLinearLayout mLl5;
    @BindView(R.id.tv6)
    TextView mTv6;
    @BindView(R.id.ll6)
    AutoLinearLayout mLl6;
    @BindView(R.id.tv7)
    TextView mTv7;
    @BindView(R.id.ll7)
    AutoLinearLayout mLl7;
    @BindView(R.id.tv8)
    TextView mTv8;
    @BindView(R.id.ll8)
    AutoLinearLayout mLl8;
    @BindView(R.id.tv9)
    TextView mTv9;
    @BindView(R.id.ll9)
    AutoLinearLayout mLl9;
    @BindView(R.id.tv10)
    TextView mTv10;
    @BindView(R.id.ll10)
    AutoLinearLayout mLl10;
    @BindView(R.id.al_tab1child)
    AutoLinearLayout mAlTab1child;
    @BindView(R.id.tv1_num)
    TextView tv1Num;
    @BindView(R.id.tv2_num)
    TextView tv2Num;
    @BindView(R.id.tv3_num)
    TextView tv3Num;
    @BindView(R.id.tv4_num)
    TextView tv4Num;
    @BindView(R.id.tv5_num)
    TextView tv5Num;
    @BindView(R.id.tv6_num)
    TextView tv6Num;
    @BindView(R.id.tv7_num)
    TextView tv7Num;
    @BindView(R.id.tv8_num)
    TextView tv8Num;
    @BindView(R.id.tv9_num)
    TextView tv9Num;
    @BindView(R.id.tv10_num)
    TextView tv10Num;
    private TextView mtvs[] = new TextView[10];
    private TextView mtvs_num[] = new TextView[10];
    private AutoLinearLayout mAutoLinearLayouts[] = new AutoLinearLayout[10];
    private List<String> mStringList;
    private List<String> mStringList_num;

    @Override
    protected void findViews() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_tab1child;
    }

    @Override
    protected void initData() {
        mtvs[0] = mTv1;
        mtvs[1] = mTv2;
        mtvs[2] = mTv3;
        mtvs[3] = mTv4;
        mtvs[4] = mTv5;
        mtvs[5] = mTv6;
        mtvs[6] = mTv7;
        mtvs[7] = mTv8;
        mtvs[8] = mTv9;
        mtvs[9] = mTv10;
        mtvs_num[0] = tv1Num;
        mtvs_num[1] = tv2Num;
        mtvs_num[2] = tv3Num;
        mtvs_num[3] = tv4Num;
        mtvs_num[4] = tv5Num;
        mtvs_num[5] = tv6Num;
        mtvs_num[6] = tv7Num;
        mtvs_num[7] = tv8Num;
        mtvs_num[8] = tv9Num;
        mtvs_num[9] = tv10Num;
        mAutoLinearLayouts[0] = mLl1;
        mAutoLinearLayouts[1] = mLl2;
        mAutoLinearLayouts[2] = mLl3;
        mAutoLinearLayouts[3] = mLl4;
        mAutoLinearLayouts[4] = mLl5;
        mAutoLinearLayouts[5] = mLl6;
        mAutoLinearLayouts[6] = mLl7;
        mAutoLinearLayouts[7] = mLl8;
        mAutoLinearLayouts[8] = mLl9;
        mAutoLinearLayouts[9] = mLl10;
        if (mStringList != null) {
            setdata(mStringList);
            setdata_num(mStringList_num);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void setdata(List<String> list) {

        for (int i = 0; i < list.size(); i++) {
            mtvs[i].setText(list.get(i));
        }


    }

    public void setdata_num(List<String> list) {

        for (int i = 0; i < list.size(); i++) {
            mtvs_num[i].setText(list.get(i));
        }


    }

    public static final Tab1childFragment newInstance(Bundle bundle) {
        Tab1childFragment fragment = new Tab1childFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle bundle = getArguments();
        if (bundle != null) {
            mStringList = bundle.getStringArrayList("list");
            mStringList_num = bundle.getStringArrayList("list_num");

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
