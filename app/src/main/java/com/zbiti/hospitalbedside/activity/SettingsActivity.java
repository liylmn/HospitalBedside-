package com.zbiti.hospitalbedside.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.androidlibrary.util.SPUtils;
import com.google.gson.Gson;
import com.minew.beaconset.MinewBeaconManager;
import com.zbiti.android.baseframe.http.HttpHelper;
import com.zbiti.android.baseframe.httpbase.HttpCallback;
import com.zbiti.hospitalbedside.R;
import com.zbiti.hospitalbedside.adapter.BedAdapter;
import com.zbiti.hospitalbedside.adapter.RoomAdapter;
import com.zbiti.hospitalbedside.base.BaseActivity;
import com.zbiti.hospitalbedside.common.Constant;
import com.zbiti.hospitalbedside.common.Global;
import com.zbiti.hospitalbedside.entity.BandBedSendData;
import com.zbiti.hospitalbedside.entity.BedInfo;
import com.zbiti.hospitalbedside.entity.GetRoomAndBedGetData;
import com.zbiti.hospitalbedside.entity.GetRoomAndBedSendData;
import com.zbiti.hospitalbedside.entity.RoomInfo;
import com.zbiti.hospitalbedside.entity.TestGetData;
import com.zbiti.hospitalbedside.entity.TestSendData;
import com.zbiti.hospitalbedside.entity.VersionCheckGetData;
import com.zbiti.hospitalbedside.entity.VersionCheckSendData;
import com.zbiti.hospitalbedside.fragment.FragmentCallback;
import com.zbiti.hospitalbedside.fragment.FragmentCallbackResult;
import com.zbiti.hospitalbedside.fragment.UpdateFragment;
import com.zbiti.hospitalbedside.net.JsonCallback;
import com.zbiti.hospitalbedside.net.NetApi;
import com.zbiti.hospitalbedside.push.MyUtil;
import com.zbiti.hospitalbedside.util.AppUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;

/**
 * Created by Digsol on 2016/12/14 16:46.
 */

public class SettingsActivity extends BaseActivity implements FragmentCallback {

    private static final String TAG = SettingsActivity.class.getSimpleName();

    private ImageView mImageViewBack;
    private Button mButtonNetTest;
    private EditText mEditTextPassword;
    private TextView mTextViewSysName;
    private TextView mTextViewImei;
    private EditText mEditTextIp;
    private EditText mEditTextProj;
    private EditText mEditTextPeriod;
    private Button mButtonPeriod;
    private Button mbtnblue;

    private ListView mListViewRoom;
    private RoomAdapter mAdapterRoom;
    private List<Map<String, Object>> dataListRoom = new ArrayList<Map<String, Object>>();
    private ListView mListViewBed;
    private BedAdapter mAdapterBed;
    private List<Map<String, Object>> dataListBed = new ArrayList<Map<String, Object>>();

    private List<RoomInfo> mRoomInfos = new ArrayList<>();
    private int nowRoomPosition = 0;//当前选择的房间position
    private int minuteSilent = 0;//没有操作的分钟数
    private final int MAX_SILENT = 5;

    private BroadcastReceiver mBroadcastReceiverTime = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                minuteSilent++;
                if (minuteSilent >= MAX_SILENT) {
                    SettingsActivity.this.finish();
                }
            }
        }
    };
    private TextView mTextViewUuid;
    private MinewBeaconManager mMinewBeaconManager;

    @Override
    protected int setContentView() {
        return R.layout.activity_settings;
    }

    @Override
    protected void findViews() {
        mImageViewBack = (ImageView) findViewById(R.id.iv_back);
        mButtonNetTest = (Button) findViewById(R.id.btn_nettest);
        mbtnblue = (Button) findViewById(R.id.btn_blue);
        mEditTextPassword = (EditText) findViewById(R.id.et_password);
        mTextViewSysName = (TextView) findViewById(R.id.tv_bottom);
        mTextViewImei = (TextView) findViewById(R.id.tv_top_imei);
        mTextViewUuid = (TextView) findViewById(R.id.tv_top_uuid);

        mEditTextIp = (EditText) findViewById(R.id.et_ip);
        mEditTextProj = (EditText) findViewById(R.id.et_proj);

        mEditTextPeriod = (EditText) findViewById(R.id.et_period);
        mButtonPeriod = (Button) findViewById(R.id.btn_period);

        mListViewRoom = (ListView) findViewById(R.id.lv_room);
        dataListRoom = new ArrayList<Map<String, Object>>();
        mAdapterRoom = new RoomAdapter(dataListRoom, this);
        mListViewRoom.setAdapter(mAdapterRoom);

        mListViewBed = (ListView) findViewById(R.id.lv_bed);
        dataListBed = new ArrayList<Map<String, Object>>();
        mAdapterBed = new BedAdapter(dataListBed, this);
        mListViewBed.setAdapter(mAdapterBed);
    }

    @Override
    protected void setListeners() {

        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsActivity.this.finish();
            }
        });
        mButtonNetTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minuteSilent = 0;
                closeInputMethod(SettingsActivity.this);
                if (!isPasswordCorrect()) {
                    toast("密钥错误");
                    return;
                }

                requestTest(mEditTextIp.getText().toString().trim(), mEditTextProj.getText()
                        .toString().trim());
            }
        });
//        mbtnblue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SettingsActivity.this, BlueActivity.class);
//                startActivityForResult(intent, 1);
//            }
//        });
        mListViewRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                minuteSilent = 0;
                closeInputMethod(SettingsActivity.this);
                selectRoom(i);
            }
        });
        mListViewBed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                minuteSilent = 0;
                closeInputMethod(SettingsActivity.this);
                if (!isPasswordCorrect()) {
                    toast("密钥错误");
                    return;
                }

                String imei = Global.IMEI;
                String password = mEditTextPassword.getText().toString().trim();
                String bedId = mRoomInfos.get(nowRoomPosition).getBedInfos().get(i).getBedId();
//                if (!mTextViewUuid.getText().toString().equals("UUID:")){
                    requestBandBed(imei, password, bedId);
//                }else{
//                    toast("请先链接蓝牙设备");
//                }
            }
        });
        mTextViewImei.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                minuteSilent = 0;
                closeInputMethod(SettingsActivity.this);
                if (!isPasswordCorrect()) {
                    toast("密钥错误");
                    
                    return false;
                }

                String imei = Global.IMEI;
                String password = mEditTextPassword.getText().toString().trim();
//                if (!mTextViewUuid.getText().toString().equals("")){
                    requestBandBed(imei, password, "");
//                }else{
//                    toast("请先链接蓝牙设备");
//                }
                return false;
            }
        });

        mButtonPeriod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minuteSilent = 0;
                closeInputMethod(SettingsActivity.this);
                if (!isPasswordCorrect()) {
                    toast("密钥错误");
                    return;
                }
                String period = mEditTextPeriod.getText().toString().trim();
                if (TextUtils.isEmpty(period)) {
                    toast("请填写频率");
                    return;
                }
                int periodInt = Integer.parseInt(period);
                if (periodInt == 0) {
                    toast("频率不能为0");
                    return;
                }
                Global.TIMING_PERIOD = periodInt * 1000;
                Intent intent = new Intent(Constant.ACTION_TIMING_CHANGE);
                sendBroadcast(intent);
                toast("频率修改成功");
            }
        });

//        mTextViewImei.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requestCheckUpdate(String.valueOf(AppUtil.checkVersion(SettingsActivity.this)));
//            }
//        });
    }

    @Override
    protected void initData() {
        if (TextUtils.isEmpty(Global.IMEI)) {
            Global.IMEI = MyUtil.getImei(getApplicationContext(), "");
        }
        mTextViewSysName.setText(Global.SYS_NAME);
        mTextViewImei.setText("IMEI:" + Global.IMEI);
        mTextViewUuid.setText("UUID:" + SPUtils.getValue(SettingsActivity.this, "uuid", "uuid", ""));
        mEditTextIp.setText(Constant.IP);
        mEditTextProj.setText(Constant.PROJ);
        mEditTextPeriod.setText("" + Global.TIMING_PERIOD / 1000);

//        test();
        requestGetRoomAndBed(Global.IMEI);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mBroadcastReceiverTime, filter);
    }

    @Override
    protected int setRootLayoutId() {
        return R.id.layout_root;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiverTime);
        super.onDestroy();
    }

    private void test() {
        mRoomInfos = createTestData();
        refreshList();
    }

    private List<RoomInfo> createTestData() {
        RoomInfo roomInfo;
        List<RoomInfo> roomInfos = new ArrayList<>();
        List<BedInfo> bedInfos;
        for (int i = 0; i < 20; i++) {
            roomInfo = new RoomInfo();
            roomInfo.setRoomId("" + (i + 1));
            roomInfo.setRoomNo("" + (i + 1));
            bedInfos = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                BedInfo bedInfo = new BedInfo();
                bedInfo.setBedId("" + (i * 10 + j + 1));
                bedInfo.setBedNo("" + (i * 10 + j + 1));
                bedInfo.setRoomNo("" + (i + 1));
                bedInfos.add(bedInfo);
            }
            roomInfo.setBedInfos(bedInfos);
            roomInfos.add(roomInfo);
        }
        return roomInfos;
    }

    private void refreshList() {
        parseRooms(mRoomInfos);
        mAdapterRoom.setDataList(dataListRoom);
        selectRoom(0);
    }

    private void parseRooms(List<RoomInfo> roomInfos) {
        dataListRoom = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (int i = 0; i < roomInfos.size(); i++) {
            map = new HashMap<String, Object>();
            map.put("name", roomInfos.get(i).getRoomNo() + "房");
            dataListRoom.add(map);
        }
    }

    private void parseBeds(List<BedInfo> bedInfos) {
        dataListBed = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        for (int i = 0; i < bedInfos.size(); i++) {
            map = new HashMap<String, Object>();
            map.put("name", bedInfos.get(i).getBedNo() + "床" + (TextUtils.isEmpty(bedInfos.get(i)
                    .getImei()) ? "" : "(已被绑定)"));
            dataListBed.add(map);
        }
    }

    private void selectRoom(int position) {
        nowRoomPosition = position;
        mAdapterRoom.setSelectedPostion(position);
        mAdapterRoom.notifyDataSetChanged();
        parseBeds(mRoomInfos.get(position).getBedInfos());
        mAdapterBed.setDataList(dataListBed);
        mAdapterBed.notifyDataSetChanged();
    }

    /**
     * 网络测试请求
     *
     * @param ip
     * @param proj
     */
    private void requestTest(final String ip, final String proj) {
        HttpHelper.getInstance(getApplicationContext()).request(ip + proj + Constant.Common, new
                TestSendData(Global.IMEI), TestGetData.class, new HttpCallback<TestGetData>() {
            @Override
            public void onSuccess(TestGetData responseInfo) {
                toast("网络测试通过!");
                Constant.IP = ip;
                Constant.PROJ = proj;
                Constant.setIP();
                requestGetRoomAndBed(Global.IMEI);
                SPUtils.putValue(SettingsActivity.this, "msg", "ip", ip);
                SPUtils.putValue(SettingsActivity.this, "msg", "proj", proj);
            }

            @Override
            public void onError() {
                toast("网络连接错误!");
            }
        });
    }

    /**
     * 查询病房病床信息列表
     *
     * @param imei
     */
    private void requestGetRoomAndBed(final String imei) {
//        HttpHelper.getInstance(getApplicationContext()).request(Constant.GetRoomAndBed, new
//                GetRoomAndBedSendData(imei), GetRoomAndBedGetData.class, new
//                HttpCallback<GetRoomAndBedGetData>() {
//                    @Override
//                    public void onSuccess(GetRoomAndBedGetData responseInfo) {
//                        if ("000".equals(responseInfo.getResultCode())) {
//                            mRoomInfos = responseInfo.getRoomInfos();
//                            refreshList();
//                        } else {
//                            toast("查询失败，错误码：" + responseInfo.getResultCode());
//                        }
//                    }
//
//                    @Override
//                    public void onError() {
//                        toast("网络连接错误!");
//                    }
//                });
        final Gson gson = new Gson();
        String requestStr = gson.toJson(new
                GetRoomAndBedSendData(imei));
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("param", requestStr);
        NetApi.getsetData(params, new JsonCallback<GetRoomAndBedGetData>() {
            @Override
            public void onResponse(GetRoomAndBedGetData response, int id) {
                switch (response.getResultCode()) {
                    case "000":
                        mRoomInfos = response.getRoomInfos();
                        refreshList();
                        break;
                    default:
                        toast("查询失败，错误码：" + response.getResultCode());
                        break;
                }
            }

            @Override
            public void onFail(Call call, Exception e, int id) {
                Log.e("lmn", "e:" + e);
            }
        });

    }

    /**
     * 绑定病床
     *
     * @param imei
     * @param password
     * @param bedId
     */
    private void requestBandBed(final String imei, final String password, final String bedId) {
//        HttpHelper.getInstance(getApplicationContext()).request(Constant.BandBed, new
//                BandBedSendData(imei, password, bedId), BandBedGetData.class, new
//                HttpCallback<BandBedGetData>() {
//                    @Override
//                    public void onSuccess(BandBedGetData responseInfo) {
//                        if ("000".equals(responseInfo.getResultCode())) {
//                            if ("".equals(bedId)) {
//                                toast("床位解绑成功!");
//                                //由于不通外网，所以暂时去掉推送绑定
////                                sendAliasToJPush("");
//                                SettingsActivity.this.finish();
//                            } else {
//                                toast("床位绑定成功!");
//                                //由于不通外网，所以暂时去掉推送绑定
////                                sendAliasToJPush(imei);
//                                SettingsActivity.this.finish();
//                            }
//                        } else if ("004".equals(responseInfo.getResultCode())) {
//                            toast("绑定失败，请先解绑设备");
//                        } else {
//                            toast("床位绑定失败，错误码：" + responseInfo.getResultCode());
//                        }
//                    }
//
//                    @Override
//                    public void onError() {
//                        toast("网络连接错误!");
//                    }
//                });

        final Gson gson = new Gson();
        String requestStr = gson.toJson(new
                BandBedSendData(imei, password, bedId,mTextViewUuid.getText().toString()));
        Log.e("requeststr=========",requestStr);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("param", requestStr);
        NetApi.getBanBedData(params, new JsonCallback<GetRoomAndBedGetData>() {
            @Override
            public void onResponse(GetRoomAndBedGetData response, int id) {
                switch (response.getResultCode()) {
                    case "000":
                        if ("".equals(bedId)) {
                            toast("床位解绑成功!");
                            //由于不通外网，所以暂时去掉推送绑定
//                                sendAliasToJPush("");
                            SPUtils.clear(SettingsActivity.this, "uuid");
                            SettingsActivity.this.finish();
                        } else {
                            toast("床位绑定成功!");

                            //由于不通外网，所以暂时去掉推送绑定
//                                sendAliasToJPush(imei);
                            SettingsActivity.this.finish();

                        }
                        break;
                    case "004":
                        toast("绑定失败，请先解绑设备");
                        break;
                    default:
                        toast("床位绑定失败，错误码：" + response.getResultCode());
                        break;
                }
            }

            @Override
            public void onFail(Call call, Exception e, int id) {
                Log.e("lmn", "e:" + e);
            }
        });
    }

    /**
     * 设置别名（发送给极光）
     *
     * @param alias
     */
    private void sendAliasToJPush(String alias) {
        JPushInterface.setAliasAndTags(getApplicationContext(), alias, null,
                new TagAliasCallback() {
                    @Override
                    public void gotResult(int code, String alias, Set<String> tags) {
                        switch (code) {
                            case 0://成功
                                toast("推送配置成功!");
                                SettingsActivity.this.finish();
                                break;
                            case 6002://失败
                                toast("推送配置失败!");
                                break;
                            default://失败
                                toast("推送配置失败!code:" + code);
                                break;
                        }
                    }
                });
    }

    /**
     * 密钥校验
     *
     * @return
     */
    private boolean isPasswordCorrect() {
        return Constant.SETTINGS_PASSWORD.equals(mEditTextPassword.getText().toString().trim());
    }

    /**
     * 版本检测请求
     *
     * @param versionCode
     */
    private void requestCheckUpdate(final String versionCode) {
        HttpHelper.getInstance(this).requestWithDialog(Constant.VersionCheck, new
                VersionCheckSendData(), VersionCheckGetData.class, new
                HttpCallback<VersionCheckGetData>() {
                    @Override
                    public void onSuccess(VersionCheckGetData responseInfo) {
                        int localCode = Integer.parseInt(versionCode);
                        int remoteCode = 0;
                        try {
                            remoteCode = Integer.parseInt(responseInfo.getCode());
                        } catch (Exception e) {

                        }
                        if (localCode < remoteCode) {
                            update(responseInfo.getContent(), responseInfo.getCode());
                        } else {
                            toast("当前是最新版本");
                        }
                    }

                    @Override
                    public void onError() {
                        toast("网络连接错误!");
                    }
                }, this);
    }

    /**
     * 更新版本
     *
     * @param content
     * @param code
     */
    private void update(String content, String code) {
        UpdateFragment updateFragment = new UpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putString("path", Constant.PATH_DOWNLOAD);
        bundle.putString("content", content);
        bundle.putString("code", code);
        updateFragment.setArguments(bundle);
        updateFragment.show(getFragmentManager(), UpdateFragment.class.getSimpleName());
    }

    @Override
    public void onDataResult(FragmentCallbackResult result, Object data) {
        switch (result) {
            case SUCCESS:
                Map<String, String> map = (Map<String, String>) data;
                AppUtil.installApk(this, map.get("path"), map.get("name"));
                finish();
                break;
            case FAILED:
                toast("更新失败");
                break;
            case CANCELLED:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            String uuid = data.getStringExtra("uuid");
            mTextViewUuid.setText("UUID:" + uuid);
        }
    }

}
