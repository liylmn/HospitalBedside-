package com.zbiti.hospitalbedside.activity.bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.androidlibrary.util.SPUtils;
import com.minew.beaconset.BluetoothState;
import com.minew.beaconset.ConnectionState;
import com.minew.beaconset.MinewBeacon;
import com.minew.beaconset.MinewBeaconConnection;
import com.minew.beaconset.MinewBeaconConnectionListener;
import com.minew.beaconset.MinewBeaconManager;
import com.minew.beaconset.MinewBeaconManagerListener;
import com.zbiti.hospitalbedside.MainActivity;
import com.zbiti.hospitalbedside.R;

import java.util.Collections;
import java.util.List;

public class BlueActivity extends AppCompatActivity {

    private RecyclerView mRecycle;
    private MinewBeaconManager mMinewBeaconManager;
    private BeaconListAdapter mAdapter;
    UserRssi comp = new UserRssi();
    private ProgressDialog mpDialog;
    public static MinewBeacon clickBeacon;
    private static final int REQUEST_ENABLE_BT = 2;
    private String mMac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_blue);
        initView();
        initManager();
        checkBluetooth();

        dialogshow();
        mMinewBeaconManager.startService();
    }

    private void initView() {
        mRecycle = (RecyclerView) findViewById(R.id.main_recyeler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycle.setLayoutManager(layoutManager);
        mAdapter = new BeaconListAdapter();
        mRecycle.setAdapter(mAdapter);
        mRecycle.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager
                .HORIZONTAL));
    }

    private void initManager() {
        mMinewBeaconManager = MinewBeaconManager.getInstance(this);
    }

    /**
     * check Bluetooth state
     */
    private void checkBluetooth() {
        BluetoothState bluetoothState = mMinewBeaconManager.checkBluetoothState();
        switch (bluetoothState) {
            case BluetoothStateNotSupported:
                Toast.makeText(this, "Not Support BLE", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BluetoothStatePowerOff:
                showBLEDialog();
                break;
            case BluetoothStatePowerOn:
                break;
        }
    }

    private void initListener() {
        //scan listener;
        mMinewBeaconManager.setMinewbeaconManagerListener(new MinewBeaconManagerListener() {
            @Override
            public void onUpdateBluetoothState(BluetoothState state) {
                switch (state) {
                    case BluetoothStatePowerOff:
                        Toast.makeText(getApplicationContext(), "bluetooth off", Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothStatePowerOn:
                        Toast.makeText(getApplicationContext(), "bluetooth on", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onRangeBeacons(List<MinewBeacon> beacons) {
                Collections.sort(beacons, comp);
                mAdapter.setData(beacons);
                Log.e("blue==","1111");
            }

            @Override
            public void onAppearBeacons(List<MinewBeacon> beacons) {
            }

            @Override
            public void onDisappearBeacons(List<MinewBeacon> beacons) {

            }
        });

        mAdapter.setOnItemClickLitener(new BeaconListAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
//                mpDialog.setMessage(getString(R.string.connecting)
//                        + mAdapter.getData(position).getName());
//                mpDialog.show();
//                mMinewBeaconManager.stopScan();
//                //connect to beacon
//                MinewBeaconConnection minewBeaconConnection = new MinewBeaconConnection(BlueActivity.this, mAdapter.getData(position));
//                minewBeaconConnection.setMinewBeaconConnectionListener(minewBeaconConnectionListener);
//                mMac = mAdapter.getData(position).getMacAddress();
//                minewBeaconConnection.connect();
                SPUtils.putValue(BlueActivity.this, "uuid", "uuid", mAdapter.getData(position).getUuid());
                Intent data = new Intent(); //同调用者一样 需要一个意图 把数据封装起来
                data.putExtra("uuid", mAdapter.getData(position).getUuid());
                setResult(1, data);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    //connect listener;
    MinewBeaconConnectionListener minewBeaconConnectionListener = new MinewBeaconConnectionListener() {
        @Override
        public void onChangeState(MinewBeaconConnection connection, ConnectionState state) {
            switch (state) {
                case BeaconStatus_Connected:
                    mpDialog.dismiss();
                    Intent intent = new Intent(BlueActivity.this, DetilActivity.class);
                    intent.putExtra("mac", connection.setting.getMacAddress());
                    startActivity(intent);

                    break;
                case BeaconStatus_ConnectFailed:
                case BeaconStatus_Disconnect:
                    if (mpDialog != null) {
                        mpDialog.dismiss();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "连接断开", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    break;
            }
        }

        @Override
        public void onWriteSettings(MinewBeaconConnection connection, boolean success) {

        }
    };

    @Override
    protected void onResume() {
        mMinewBeaconManager.startScan();
        initListener();
        super.onResume();
    }

    @Override
    protected void onPause() {
//        mMinewBeaconManager.stopScan();
        super.onPause();
    }

    protected void dialogshow() {
        mpDialog = new ProgressDialog(BlueActivity.this);
        mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mpDialog.setTitle(null);//
        mpDialog.setIcon(null);//
        mpDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface arg0) {

            }
        });
        mpDialog.setCancelable(true);//
        mpDialog.setCanceledOnTouchOutside(false);

        mpDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    dialog.dismiss();
                    //如果正在连接，就去主动断开连接
                    MinewBeaconConnection minewBeaconConnection = MinewBeaconConnection.minewBeaconConnections.get(mMac);
                    if (minewBeaconConnection != null) {
                        minewBeaconConnection.disconnect();
                    }
                }
                return false;
            }
        });
    }

    private void showBLEDialog() {
        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                mMinewBeaconManager.startScan();
                break;
        }
    }

    @Override
    protected void onDestroy() {
//        mMinewBeaconManager.stopService();
        super.onDestroy();
    }
}
