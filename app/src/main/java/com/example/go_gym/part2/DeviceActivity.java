package com.example.go_gym.part2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.go_gym.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeviceActivity extends AppCompatActivity {
    TextView current_name,current_mac,device1_name,device1_mac,device2_name,device2_mac;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Set<BluetoothDevice> temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        current_name = findViewById(R.id.current_name);
        current_mac = findViewById(R.id.current_mac);
        device1_name = findViewById(R.id.device1_name);
        device1_mac = findViewById(R.id.device1_mac);
        device2_name = findViewById(R.id.device2_name);
        device2_mac = findViewById(R.id.device2_mac);
        boolean flag0 = showBondedDevice(bluetoothAdapter);
        if (!flag0)
            Toast.makeText(DeviceActivity.this,"当前未连接任何蓝牙设备",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(DeviceActivity.this,UserDetailActivity.class);
        startActivity(intent);
        finish();
    }

    //获取已绑定设备信息和连接状态
    @SuppressLint({"MissingPermission", "SetTextI18n"})
    private boolean showBondedDevice(BluetoothAdapter bluetoothAdapter) {
        try {
            bluetoothAdapter.getName();
            current_name.setText("当前设备名称：" + bluetoothAdapter.getName());
            current_mac.setText("当前设备MAC：" + bluetoothAdapter.getAddress());
            Log.d("Jason", "Name:" + bluetoothAdapter.getName() + "   Mac:" + bluetoothAdapter.getAddress());
        }catch (Exception e){
            e.printStackTrace();
        }
        Set<BluetoothDevice> deviceList = bluetoothAdapter.getBondedDevices();
        temp = new HashSet<>();
        int flag = 0;
        for (BluetoothDevice device : deviceList) {
            Log.d("Jason", "Name:" + device.getName() + "   Mac:" + device.getAddress());
            try {
                //使用反射调用获取设备连接状态方法
                Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);
                isConnectedMethod.setAccessible(true);
                boolean isConnected = (boolean) isConnectedMethod.invoke(device, (Object[]) null);
                if (isConnected)
                    temp.add(device);
                if (isConnected){
                    Log.d("Jason", "enter here：");
                    if (flag == 0){
                        Log.d("Jason", "enter 0：");
                        device1_name.setText("设备1名称：" + device.getName() );
                        device1_mac.setText("设备1MAC：" + device.getAddress());
                        flag++;
                    } else if (flag == 1){
                        Log.d("Jason", "enter 1：");
                        device2_name.setText("设备2名称：" + device.getName());
                        device2_mac.setText("设备2MAC：" + device.getAddress());
                    }
                }
                Log.d("Jason", "来自设备信息页的isConnected：" + isConnected);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        if (temp.isEmpty())
            return false;
        else
            return true;
    }
}