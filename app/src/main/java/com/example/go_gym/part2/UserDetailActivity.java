package com.example.go_gym.part2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.go_gym.R;
import com.example.go_gym.SelectActivity;

import java.util.Set;

public class UserDetailActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 10;//其是需要自己定义的局部常量。
    private BluetoothAdapter mBluetoothAdapter;

    TextView push_up_old,push_up_new,squat_old,squat_new,plank_old,plank_new;
    Button blueTooth_check,turn_on,turn_off;
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
        blueTooth_check = findViewById(R.id.BlueTooth_check_support);
        turn_on = findViewById(R.id.turn_on);
        turn_off = findViewById(R.id.turn_off);


        push_up_old.setOnClickListener(view -> {
            Intent intent0 = new Intent();
            intent0.setClass(UserDetailActivity.this,PlayActivity.class);
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

        blueTooth_check.setOnClickListener(view -> {
            //查看设备当前蓝牙状态
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                showToast("设备不支持蓝牙！");
            } else {
                if (mBluetoothAdapter.isEnabled())
                    showToast("蓝牙状态为打开");
                else
                    showToast("蓝牙状态为关闭");
            }
        });

        turn_on.setOnClickListener(view -> {
            //打开蓝牙
            if (mBluetoothAdapter.isEnabled())
                showToast("蓝牙已经打开了");
            else{
                //向系统请求开启蓝牙
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);//结果返回回调到onActivityResult()
            }
        });

        turn_off.setOnClickListener(view -> {
            //关闭蓝牙
            if (!mBluetoothAdapter.isEnabled())
                showToast("蓝牙已经关闭了");
            else{
                mBluetoothAdapter.disable();
                showToast("成功关闭蓝牙");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT)
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