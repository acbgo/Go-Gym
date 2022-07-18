package com.example.go_gym.part2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.example.go_gym.R;
import com.example.go_gym.SelectActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDetailActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 10;//其是需要自己定义的局部常量。
    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    TextView push_up_old, push_up_new, squat_old, squat_new, plank_old, plank_new;
    Button bt_menu;
    Toast myToast;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);


        //获取主要的控件
        push_up_old = findViewById(R.id.push_up_old);
        push_up_new = findViewById(R.id.push_up_new);
        squat_old = findViewById(R.id.squat_old);
        squat_new = findViewById(R.id.squat_new);
        plank_old = findViewById(R.id.plank_old);
        plank_new = findViewById(R.id.plank_new);
        bt_menu = findViewById(R.id.button_bt);


        push_up_old.setOnClickListener(view -> {
            Intent intent0 = new Intent();
            intent0.setClass(UserDetailActivity.this, PlayActivity.class);
            startActivity(intent0);
            finish();
        });

        push_up_new.setOnClickListener(view -> {

        });

        squat_old.setOnClickListener(view -> {

        });

        squat_new.setOnClickListener(view -> {

        });

        plank_old.setOnClickListener(view -> {

        });

        plank_new.setOnClickListener(view -> {

        });

        bt_menu.setOnClickListener(view -> {
            showPopupMenu(bt_menu);
        });

    }

    private void showPopupMenu(Button button) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, button);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (R.id.BlueTooth_check_state == item.getItemId()){
                    //查看设备当前蓝牙状态
                    if (mBluetoothAdapter == null) {
                        showToast("设备不支持蓝牙！");
                    } else {
                        if (mBluetoothAdapter.isEnabled())
                            showToast("蓝牙状态为打开");
                        else
                            showToast("蓝牙状态为关闭");
                    }
                } else if (R.id.turn_on == item.getItemId()){
                    //打开蓝牙
                    if (mBluetoothAdapter.isEnabled())
                        showToast("蓝牙已经打开了");
                    else {
                        //向系统请求开启蓝牙
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);//结果返回回调到onActivityResult()
                    }
                } else if (R.id.turn_off == item.getItemId()){
                    //关闭蓝牙
                    if (!mBluetoothAdapter.isEnabled())
                        showToast("蓝牙已经关闭了");
                    else {
                        mBluetoothAdapter.disable();
                        showToast("成功关闭蓝牙");
                    }
                } else if (R.id.bt_connected == item.getItemId()){
                   showBondedDevice(mBluetoothAdapter);
                   Intent intent = new Intent();
                   intent.setClass(UserDetailActivity.this,DeviceActivity.class);
                   startActivity(intent);
                   finish();
                }
                return false;
            }
        });
        popupMenu.show();
    }

    //获取已绑定设备信息和连接状态
    List<BluetoothDevice> connected;
    @SuppressLint({"MissingPermission", "SetTextI18n"})
    private void showBondedDevice(BluetoothAdapter bluetoothAdapter) {
        try {
            bluetoothAdapter.getName();
            Log.d("Jason", "Name:" + bluetoothAdapter.getName() + "   Mac:" + bluetoothAdapter.getAddress());
        }catch (Exception e){
            e.printStackTrace();
        }
        Set<BluetoothDevice> deviceList = bluetoothAdapter.getBondedDevices();

        for (BluetoothDevice device : deviceList) {
            Log.d("Jason", "Name:" + device.getName() + "   Mac:" + device.getAddress());
            try {
                //使用反射调用获取设备连接状态方法
                Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);
                isConnectedMethod.setAccessible(true);
                boolean isConnected = (boolean) isConnectedMethod.invoke(device, (Object[]) null);
                Log.d("Jason", "来自用户详情页的isConnected：" + isConnected);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT)
            showToast("成功打开蓝牙");
        else
            showToast("打开蓝牙失败");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(UserDetailActivity.this, SelectActivity.class);
        startActivity(intent);
        finish();
    }

    private void showToast(String text){
        if (myToast == null)
            myToast = Toast.makeText(this,text,Toast.LENGTH_SHORT);
        else
            myToast.setText(text);
        myToast.show();
    }
}