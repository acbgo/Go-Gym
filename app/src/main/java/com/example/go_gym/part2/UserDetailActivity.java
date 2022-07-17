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
    Button blueTooth_check;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        //将状态栏设置为透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //获取主要的控件
        push_up_old = findViewById(R.id.push_up_old);
        push_up_new = findViewById(R.id.push_up_new);
        squat_old = findViewById(R.id.squat_old);
        squat_new = findViewById(R.id.squat_new);
        plank_old = findViewById(R.id.plank_old);
        plank_new = findViewById(R.id.plank_new);
        blueTooth_check = findViewById(R.id.BlueTooth_check);


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
            //查看设备是否支持蓝牙
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                Toast.makeText(this, "设备不支持蓝牙！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "设备支持蓝牙！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onStart() {
        super.onStart();
        blueTooth_check.setOnClickListener(view -> {
            if (!mBluetoothAdapter.isEnabled()) {
                //向系统请求开启蓝牙
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);//结果返回回调到onActivityResult()
            }else {
                //已经开启了蓝牙
                Toast.makeText(this,"蓝牙已经开启",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT){
            Toast.makeText(this,"蓝牙已经开启",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(UserDetailActivity.this, SelectActivity.class);
        startActivity(intent);
        finish();
    }
}