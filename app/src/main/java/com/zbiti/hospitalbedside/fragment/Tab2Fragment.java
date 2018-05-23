package com.zbiti.hospitalbedside.fragment;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zbiti.hospitalbedside.R;
import com.zbiti.hospitalbedside.adapter.CatheterAdapter;
import com.zbiti.hospitalbedside.adapter.ListArrayAdapter;
import com.zbiti.hospitalbedside.adapter.ProtectAdapter;
import com.zbiti.hospitalbedside.base.BaseFragment;
import com.zbiti.hospitalbedside.entity.MainDataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 病患详情
 *
 * @author hp
 */
public class Tab2Fragment extends BaseFragment implements ListArrayAdapter.OnRecyclerViewItemClickListener {

    private final int SHOWN_ITEM_TOTAL = 6;//列表项显示个数

    private TextView mTextViewBedNum;//床位号
    private TextView mTextViewName;//姓名
    private TextView mTextViewOperationTime;//手术日期
    private TextView mTextViewBill;//费用类别
    private RecyclerView mrecycleview;
    private RecyclerView mrecycleview2;
    private ListArrayAdapter adapter;

//    private ListView mListViewProtects;
//    private ProtectAdapter mAdapterProtect;
//    private List<String> dataListProtect = new ArrayList<>();
//
//    private ListView mListViewCatheters;
//    private CatheterAdapter mAdapterCatheter;
//    private List<String> dataListCatheter = new ArrayList<>();

    @Override
    protected void findViews() {
        mTextViewBedNum = (TextView) getView().findViewById(R.id.tv_bed_num);
        mTextViewName = (TextView) getView().findViewById(R.id.tv_patient_name);
        mTextViewOperationTime = (TextView) getView().findViewById(R.id.tv_operation_time);
        mTextViewBill = (TextView) getView().findViewById(R.id.tv_bill);
        mrecycleview=(RecyclerView)getView().findViewById(R.id.recycleview);
        mrecycleview2=(RecyclerView)getView().findViewById(R.id.recycleview2);

//        mListViewProtects = (ListView) getView().findViewById(R.id.lv_protects);
//        mAdapterProtect = new ProtectAdapter(dataListProtect, getContext());
//        mListViewProtects.setAdapter(mAdapterProtect);
//
//        mListViewCatheters = (ListView) getView().findViewById(R.id.lv_catheters);
//        mAdapterCatheter = new CatheterAdapter(dataListCatheter, getContext());
//        mListViewCatheters.setAdapter(mAdapterCatheter);

    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_newtab2;
    }

    @Override
    protected void initData() {
        refreshProtects(null);
        refreshCatheters(null);
//        List<String> list=new ArrayList<String>();
//        for (int i=0;i<20;i++){
//         list.add("信息"+i);
//        }
//        initWidget(list);
//        initWidget2(list);
    }

    /**
     * 刷新病床信息
     *
     * @param data
     */
    public void refreshOnlyBed(MainDataEntity data) {
        if (null == data) {
            return;
        }
        setTextViewBedNum(data.getData().getBedNum());
        setTextViewName("");
        mTextViewOperationTime.setText("");
        mTextViewBill.setText("");
        refreshProtects(null);
        refreshCatheters(null);
    }

    /**
     * 清除信息
     */
    public void refreshClear() {
        setTextViewBedNum("");
        setTextViewName("");
        mTextViewOperationTime.setText("");
        mTextViewBill.setText("");
        refreshProtects(null);
        refreshCatheters(null);
    }

    /**
     * 刷新所有数据
     *
     * @param data
     */
    public void refresh(MainDataEntity data) {
        if (null == data) {
            return;
        }
        setTextViewBedNum(data.getData().getBedNum());
        if (null != data.getData()) {
            setTextViewName(data.getData().getUserName());
        }
        if (null != data.getData()) {
            mTextViewOperationTime.setText( data.getData().getSurgeryData());
            mTextViewBill.setText(data.getData().getClasName());
            initWidget(data.getData().getReminds());
            initWidget2(data.getData().getReminds2());
//            refreshProtects(data.getData().getMeterages());//改为展示计量
//            refreshCatheters(data.getData().getCatheters());
        }
    }

    /**
     * 刷新防护内容
     *
     * @param datas
     */
    private void refreshProtects(List<String> datas) {
        if (null == datas) {
            datas = new ArrayList<>();
        }
        if (datas.size() < SHOWN_ITEM_TOTAL) {
            int size = datas.size();
            for (int i = 0; i < SHOWN_ITEM_TOTAL - size; i++) {
                datas.add("");
            }
        }
//        dataListProtect = datas;
//        mAdapterProtect.refreshDataList(dataListProtect);
    }

    /**
     * 刷新导管留置
     *
     * @param datas
     */
    private void refreshCatheters(List<String> datas) {
        if (null == datas) {
            datas = new ArrayList<>();
        }
        if (datas.size() < SHOWN_ITEM_TOTAL) {
            int size = datas.size();
            for (int i = 0; i < SHOWN_ITEM_TOTAL - size; i++) {
                datas.add("");
            }
        }
//        dataListCatheter = datas;
//        mAdapterCatheter.refreshDataList(dataListCatheter);
    }

    private void setTextViewName(String name) {
        if (null == name) {
            return;
        }
        name = name.trim();
        if (2 == name.length()) {//2个字
            mTextViewName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 90);
            mTextViewName.setText(name.charAt(0) + "  " + name.charAt(1));
        } else if (3 < name.length()) {//3个字以上
            mTextViewName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 70);
            mTextViewName.setText(name);
        } else {
            mTextViewName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 90);
            mTextViewName.setText(name);
        }
    }

    private void setTextViewBedNum(String bedNum) {
        if (null == bedNum) {
            return;
        }
        bedNum = bedNum.trim();
        if (4 < bedNum.length()) {//3个字以上
            mTextViewBedNum.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 90);
            mTextViewBedNum.setText(bedNum);
        } else {
            mTextViewBedNum.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 130);
            mTextViewBedNum.setText(bedNum);
        }
    }
    private void initWidget(List<String> list) {
        adapter = new ListArrayAdapter(getActivity(), list);
        adapter.setOnItemClickListener(this);
        mrecycleview.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mrecycleview.setHasFixedSize(true);
        mrecycleview.setAdapter(adapter);
    }
    private void initWidget2(List<String> list) {
        adapter = new ListArrayAdapter(getActivity(), list);
        adapter.setOnItemClickListener(this);
        mrecycleview2.setLayoutManager(new GridLayoutManager(getActivity(),1));
        mrecycleview2.setHasFixedSize(true);
        mrecycleview2.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
    }
}
