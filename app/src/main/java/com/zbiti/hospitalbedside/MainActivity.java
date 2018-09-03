package com.zbiti.hospitalbedside;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.androidlibrary.imageloader.ILoader;
import com.android.androidlibrary.imageloader.ImageFactory;
import com.google.gson.Gson;
import com.squareup.leakcanary.RefWatcher;
import com.zbiti.hospitalbedside.activity.SettingsActivity;
import com.zbiti.hospitalbedside.adapter.MyFragmentPagerAdapter;
import com.zbiti.hospitalbedside.base.BaseActivity;
import com.zbiti.hospitalbedside.common.Constant;
import com.zbiti.hospitalbedside.common.Global;
import com.zbiti.hospitalbedside.entity.GetRoomAndBedGetData;
import com.zbiti.hospitalbedside.entity.MainDataEntity;
import com.zbiti.hospitalbedside.entity.RouteBean;
import com.zbiti.hospitalbedside.fragment.Tab1Fragment;
import com.zbiti.hospitalbedside.fragment.Tab2Fragment;
import com.zbiti.hospitalbedside.net.JsonCallback;
import com.zbiti.hospitalbedside.net.NetApi;
import com.zbiti.hospitalbedside.push.MyReceiver;
import com.zbiti.hospitalbedside.push.MyUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

public class MainActivity extends BaseActivity {

    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private TextView mTextViewSysTime;
    private TextView mTextViewSysName;
    private TextView mTextViewEnghosName;
    private ImageView mHosimg;

    private Tab1Fragment mTab1Fragment;
    private Tab2Fragment mTab2Fragment;
    private static final int REQUEST_ENABLE_BT = 2;
    private MessageReceiver mMessageReceiver;
    private final int TIMING_DELAY = 5000;//轮询首次延时
    private Timer mTimer1;
    private Timer mTimer2;
    private long timeDiff = 0;//服务器与本地的时间差值 timeDiff=server-local

    private BroadcastReceiver mBroadcastReceiverTimingChange = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Constant.ACTION_TIMING_CHANGE.equals(intent.getAction())) {
                cancelTiming();
                timingRequest();
            }
        }
    };

    private BroadcastReceiver mBroadcastReceiverTime = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                refreshSysTime(parseSysTime(System.currentTimeMillis() + timeDiff));
            }
        }
    };


    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {

        mViewPager = (ViewPager) findViewById(R.id.vp);
        setupViewPager();
        mTextViewSysTime = (TextView) findViewById(R.id.tv_top_time);
        mTextViewSysName = (TextView) findViewById(R.id.hosname);
        mTextViewEnghosName = (TextView) findViewById(R.id.EnghosName);
        mHosimg = (ImageView) findViewById(R.id.hosimg);
    }

    @Override
    protected void setListeners() {


        mTextViewSysTime.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        registerMessageReceiver();
        timingRequest();
    }

    @Override
    protected int setRootLayoutId() {
        return R.id.layout_root;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onresume", "imei");
        if (TextUtils.isEmpty(Global.IMEI)) {
            Global.IMEI = MyUtil.getImei(getApplicationContext(), "");
        }
        Log.e("lmn", "imei" + Global.IMEI);
        refreshSysTime(parseSysTime(System.currentTimeMillis() + timeDiff));
                requestGetBedInfo(Global.IMEI);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        unregisterReceiver(mBroadcastReceiverTime);
        unregisterReceiver(mBroadcastReceiverTimingChange);
        cancelTiming();
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }


    private void setupViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        mTab1Fragment = new Tab1Fragment();
        mTab2Fragment = new Tab2Fragment();
        fragments.add(mTab1Fragment);
        fragments.add(mTab2Fragment);
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                fragments);
        mViewPager.setAdapter(mMyFragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 0) {
                    mTimer1 = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(0);
                        }
                    };
                    mTimer1.schedule(timerTask, 5000);
                } else {
                    if (null != mTimer1) {
                        mTimer1.cancel();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 查询病床信息
     *
     * @param imei
     */
    private void requestGetBedInfo(final String imei) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("imei", imei);
        Log.e("url","gggggg"+Constant.GetBedInfo);
        NetApi.getMainData(params, new JsonCallback<MainDataEntity>() {
            @Override
            public void onResponse(MainDataEntity response, int id) {
                switch (response.getResultCode()) {
                    case "0":
                        Log.e("response==",response.getData().toString());
                        handleResponse(response);
                        ImageFactory.getLoader().loadNet(mHosimg,
                                response.getData().getHosImg(), new ILoader.Options(R.mipmap.loading_img, R.mipmap.loading_img));
                        break;
                    default:
                        Toast.makeText(MainActivity.this, response.getResMsg(), Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFail(Call call, Exception e, int id) {
                Log.e("lmn", "e:" + e);
//                refreshClear();
                String s="{resultCode : 0,data : {\"information\":\"重症急性胰腺炎\",\"hosNum\":\"1008841169\",\"idNum\":\"119\",\"enghosName\":\"Nanjing General Hospital\",\"codeImg\":\"http://114.242.18.143:8082/eoms/qrCode.png\",\"touseNames\":[\"磺胺过敏\",\"干体重81\",\"本次前81.0\",\"预脱0\",\"防压疮\",\"防拔管\",\"防误吸\",\"防走失\"],\"clasName\":\"全费\",\"reminds2\":[\"CVC/PICC导管\",\"导尿管/膀胱穿刺\",\"CRRT导管\",\"负压引流管\",\"胃肠减压管\"],\"hosName\":\"南京军区总医院\",\"reminds\":[\"尿液计量\",\"造口液计量\",\"腹腔液计量\"],\"hosImg\":\"http://114.242.18.143:8082/eoms/hosImg.png\",\"age\":\"56\",\"userName\":\"赵黎霞\",\"gender\":\"女\",\"hosTime\":\"2017-05-25 12:17\",\"surgeryData\":\"2017-06-11 16:31\",\"bedNum\":\"6\",\"doctorName\":\"柯路\"},resMsg : 成功}";
                Gson gson=new Gson();
                handleResponse(gson.fromJson(s,MainDataEntity.class));
            }
        });
    }

    /**
     * 刷新系统名称，并计算服务器与本地时间差
     *
     * @param data
     */
    private void refreshSysName(MainDataEntity data) {
        if (null == data) {
            return;
        }
        if (null != data.getData().getHosName()) {
            Global.SYS_NAME = data.getData().getHosName();
        }
        mTextViewSysName.setText(Global.SYS_NAME);
        mTextViewEnghosName.setText(data.getData().getEnghosName());
        if (!TextUtils.isEmpty(data.getData().getHosTime())) {
        }
    }

    /**
     * 刷新系统时间
     *
     * @param time
     */
    private void refreshSysTime(String time) {
        Global.SYS_TIME = time;
        mTextViewSysTime.setText(Global.SYS_TIME);
    }

    /**
     * 刷新病床信息
     *
     * @param data
     */
    private void refreshOnlyBed(MainDataEntity data) {
        refreshSysName(data);
        mTab1Fragment.refreshOnlyBed(data);
        mTab2Fragment.refreshOnlyBed(data);
    }

    /**
     * 刷新所有信息
     *
     * @param data
     */
    private void refreshAll(MainDataEntity data) {
        refreshSysName(data);
        mTab1Fragment.refresh(data);
        mTab2Fragment.refresh(data);
    }

    /**
     * 清除信息
     *
     */
    private void refreshClear() {
        mTab1Fragment.refreshClear();
        mTab2Fragment.refreshClear();
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MyReceiver.MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);

        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mBroadcastReceiverTime, filter);

        filter = new IntentFilter();
        filter.addAction(Constant.ACTION_TIMING_CHANGE);
        registerReceiver(mBroadcastReceiverTimingChange, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (MyReceiver.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(MyReceiver.KEY_MESSAGE);
                Gson gson = new Gson();
                RouteBean routeBean = gson.fromJson(messge, RouteBean.class);
                if (Constant.GetBedInfoRoute.equals(routeBean.getRoute())) {
                    MainDataEntity data = gson.fromJson(messge, MainDataEntity.class);
                    handleResponse(data);
                }
            }
        }
    }

    /**
     * 格式化时间
     *
     * @param timeMillis
     * @return
     */
    private String parseSysTime(long timeMillis) {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.setTime(new Date(timeMillis));
        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH) + 1);
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String week = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(c.get(Calendar.MINUTE));

        month = 1 == month.length() ? "0" + month : month;
        day = 1 == day.length() ? "0" + day : day;
        hour = 1 == hour.length() ? "0" + hour : hour;
        minute = 1 == minute.length() ? "0" + minute : minute;
        if ("1".equals(week)) {
            week = "日";
        } else if ("2".equals(week)) {
            week = "一";
        } else if ("3".equals(week)) {
            week = "二";
        } else if ("4".equals(week)) {
            week = "三";
        } else if ("5".equals(week)) {
            week = "四";
        } else if ("6".equals(week)) {
            week = "五";
        } else if ("7".equals(week)) {
            week = "六";
        }
        return year + "年" + month + "月" + day + "日 " + "星期" + week + " " + hour + ":" + minute;
    }

    /**
     * 处理响应信息，http和推送共用
     *
     * @param responseInfo
     */
    private void handleResponse(MainDataEntity responseInfo) {
        if ("0".equals(responseInfo.getResultCode())) {
            refreshAll(responseInfo);
        } else {
            toast("失败，错误码：" + responseInfo.getResultCode());
        }
    }

    //主线程的Handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获得刚才发送的Message对象，然后在这里进行UI操作
            Log.e("lmn", "------------> msg.what = " + msg.what);
            requestGetBedInfo(Global.IMEI);
        }
    };

    /**
     * 轮询请求
     */
    private void timingRequest() {
        mTimer2 = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);

            }
        };
        mTimer2.schedule(timerTask, Global.TIMING_PERIOD, Global.TIMING_PERIOD);
    }

    /**
     * 停止轮询
     */
    private void cancelTiming() {
        if (null != mTimer1) {
            mTimer1.cancel();
        }
        if (null != mTimer2) {
            mTimer2.cancel();
        }
    }


    @Override
    protected void onPause() {

        super.onPause();
    }

    private void showBLEDialog() {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendblue(final String imei) {
        Log.e("url=========",Constant.SENDMESSAGE);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("imei", imei);
        NetApi.sendblue(params, new JsonCallback<GetRoomAndBedGetData>() {
            @Override
            public void onResponse(GetRoomAndBedGetData response, int id) {
                switch (response.getResultCode()) {
                    case "0":

                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFail(Call call, Exception e, int id) {
                Log.e("lmn", "e:" + e);
            }
        });
    }


}
