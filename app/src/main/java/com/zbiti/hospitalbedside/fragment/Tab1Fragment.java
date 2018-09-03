package com.zbiti.hospitalbedside.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zbiti.hospitalbedside.R;
import com.zbiti.hospitalbedside.base.BaseFragment;
import com.zbiti.hospitalbedside.entity.MainDataEntity;
import com.zbiti.hospitalbedside.widget.MarqueeTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;

/**
 * 基础数据
 *
 * @author hp
 */
public class Tab1Fragment extends BaseFragment {

    private static int page = 0;
    private final int NURSING_TOTAL = 12;//护理信息展示格总个数
    static ViewPager mViewPager;

    private TextView mTextViewBedNum;//床位号
    private TextView mTextViewName;//姓名
    private TextView mTextViewDoctor;//责任医生
    private TextView mTextViewSex;//性别
    private TextView mTextViewNum;//住院号
    private TextView mTextViewAge;//年龄
    private TextView mTextViewInTime;//入院时间
    private ImageView mCodeImg;//二维码
    private MarqueeTextView mTextViewDiagnosis;//诊断信息
    private TextView mTextViewId;//ID
    private static List<List<String>> list_num;
    private static List<List<String>> list;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获得刚才发送的Message对象，然后在这里进行UI操作
            setpage(msg.what);
        }
    };
    final Runnable changeView = new Runnable() {
        public void run() {
            handler.postDelayed(this, 5000);
        }
    };
    private Timer mTimer;
    //主线程的Handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获得刚才发送的Message对象，然后在这里进行UI操作
            Log.e("lmn", "------------> msg.what = " + msg.what);
            Log.e("lmn", "------------> page = " +page);
            setpage(msg.what);
        }
    };

    @Override
    protected void findViews() {
        mTextViewBedNum = (TextView) getView().findViewById(R.id.tv_bed_num);
        mTextViewName = (TextView) getView().findViewById(R.id.tv_patient_name);
        mTextViewDoctor = (TextView) getView().findViewById(R.id.tv_doctor);
        mTextViewSex = (TextView) getView().findViewById(R.id.tv_sex);
        mTextViewNum = (TextView) getView().findViewById(R.id.tv_patient_num);
        mTextViewAge = (TextView) getView().findViewById(R.id.tv_age);
        mTextViewInTime = (TextView) getView().findViewById(R.id.tv_in_time);
        mTextViewDiagnosis = (MarqueeTextView) getView().findViewById(R.id.tv_diagnosis);
        mTextViewId = (TextView) getView().findViewById(R.id.tv_patient_id);
        mViewPager = (ViewPager) getView().findViewById(R.id.ViewPager);
        mCodeImg = (ImageView) getView().findViewById(R.id.codeImg);
    }

    @Override
    protected void setListeners() {
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_newtab1;
    }

    @Override
    protected void initData() {
//        handler.postDelayed(changeView, 5000);
    }

    /**
     * 刷新床位信息
     *
     * @param data
     */
    public void refreshOnlyBed(MainDataEntity data) {
        setTextViewBedNum(data.getData().getBedNum());
        setTextViewName("");
    }

    /**
     * 清除信息
     */
    public void refreshClear() {

        setTextViewBedNum("");
        setTextViewName("");
        mTextViewDoctor.setText("");
        mTextViewSex.setText("");
        mTextViewNum.setText("");
        mTextViewAge.setText("");
        mTextViewInTime.setText("");
        mTextViewId.setText("");
        mTextViewDiagnosis.setText("");
    }

    /**
     * 刷新所有信息
     *
     * @param data
     */
    public void refresh(final MainDataEntity data) {
        setTextViewBedNum(data.getData().getBedNum());
        if (null != data.getData()) {
            setTextViewName(data.getData().getUserName());
            mTextViewDoctor.setText(data.getData().getDoctorName());
            mTextViewSex.setText(data.getData().getGender());
            mTextViewNum.setText(data.getData().getHosNum());
            mTextViewAge.setText(data.getData().getAge());
            mTextViewInTime.setText(data.getData().getHosTime());
            mTextViewId.setText(data.getData().getIdNum());
            mTextViewDiagnosis.setText(data.getData().getInformation());
            list = new ArrayList<List<String>>();
            list_num = new ArrayList<List<String>>();
            //文字集合
            List<String> data1=new ArrayList<String>();
            //数字集合
            List<String> data1_num=new ArrayList<String>();
            for (int i = 0; i < data.getData().getTouseNames().size(); i ++) {
                List<String> strings=getnumb( data.getData().getTouseNames().get(i));
                if (strings.size()>1){
                    if (strings.size()==3){
                        data1.add(strings.get(0));
                        data1_num.add(strings.get(1)+"."+strings.get(2));
                    }else{
                        data1.add(strings.get(0));
                        data1_num.add(strings.get(1));
                    }
                }else {
                    data1.add(strings.get(0));
                    data1_num.add("");
                }
            }

            for (int i = 0; i < data1.size(); i += 10) {
                Log.e("i=====", i + "");

                if (data1.size() > 10) {
                    if (i + 10 > data1.size()) {
                        List newlist = new ArrayList(data1.subList(i, data1.size()));
                        list.add(newlist);
                    } else {
                        List newlist = new ArrayList(data1.subList(i, i + 10));
                        list.add(newlist);
                    }

                } else {
                    List newlist = data1;
                    list.add(newlist);
                }
            }
            for (int i = 0; i < data1_num.size(); i += 10) {
                if (data1_num.size() > 10) {
                    if (i + 10 >data1_num.size()) {
                        List newlist = new ArrayList(data1_num.subList(i, data1.size()));
                        list_num.add(newlist);
                    } else {
                        List newlist = new ArrayList(data1_num.subList(i, i + 10));
                        list_num.add(newlist);
                    }

                } else {
                    List newlist = data1_num;
                    list_num.add(newlist);
                }
            }
            List<String> strings=new ArrayList<String>();
            mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
                @Override
                public Fragment getItem(int position) {

                    Bundle bundle=new Bundle();
                    if (list.size()==0){

                        bundle.putStringArrayList("list",new ArrayList<String>());
                        bundle.putStringArrayList("list_num",new ArrayList<String>());
                        return  Tab1childFragment.newInstance(bundle);

                    }else{

                        bundle.putStringArrayList("list", (ArrayList<String>) list.get(position));
                        bundle.putStringArrayList("list_num", (ArrayList<String>) list_num.get(position));
                        return  Tab1childFragment.newInstance(bundle);

                    }
                }

                @Override
                public int getCount() {
//                return (int) Math.floor(list.size() / 10);
                    return list.size()==0?1:list.size();
                }
            });
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(final int position) {
                    // 把当前显示的position传递出去
                    page = position;

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

           if (mTimer==null){
            mTimer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {

                    mHandler.sendEmptyMessage(list.size());


                }
            };
            mTimer.schedule(timerTask, 5000,5000);
           }
        }
//        ImageFactory.getLoader().loadNet(mCodeImg,
//                data.getData().getCodeImg(), new ILoader.Options(R.mipmap.loading_img, R.mipmap.loading_img));
    }

    private Map<String, String> createNursingMap(String content) {
        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        return map;
    }

    private void setTextViewName(String name) {
        if (null == name) {
            return;
        }
        name = name.trim();
        if (2 == name.length()) {//2个字
            mTextViewName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 120);
            mTextViewName.setText(name.charAt(0) + "  " + name.charAt(1));
        } else if (3 < name.length()) {//3个字以上
            mTextViewName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 100);
            mTextViewName.setText(name);
        } else {
            mTextViewName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 120);
            mTextViewName.setText(name);
        }
    }

    private void setTextViewBedNum(String bedNum) {
        if (null == bedNum) {
            return;
        }
        bedNum = bedNum.trim();
        if (3 < bedNum.length()) {//3个字以上
            mTextViewBedNum.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 80);
            mTextViewBedNum.setText(bedNum);
        } else {
            mTextViewBedNum.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 120);
            mTextViewBedNum.setText(bedNum);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public static void setpage(int  list) {
        if (list> 1) {
            if (page + 1 == list) {
                mViewPager.setCurrentItem(page);
                page = 0;
            } else {
                mViewPager.setCurrentItem(page);
                page++;
            }

        }
    }
    /**
     * 停止轮询
     */
    private void cancelTiming() {
        if (null != mTimer) {
            mTimer.cancel();
        }
    }

    @Override
    public void onDestroy() {
        cancelTiming();
        super.onDestroy();
    }
    //利用正则把文字和数字分开
    public List<String> getnumb(String string){
        List<String> strings=new ArrayList<String>();
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
        Matcher m = p.matcher( string );
        while ( m.find() ) {
            strings.add(m.group());
        }
        return strings;
    }
}
