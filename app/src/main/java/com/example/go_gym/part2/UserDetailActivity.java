package com.example.go_gym.part2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.example.go_gym.R;
import com.example.go_gym.SelectActivity;

public class UserDetailActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 10;//其是需要自己定义的局部常量。
    private BluetoothAdapter mBluetoothAdapter;

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
                switch (item.getItemId()) {
                    case R.id.BlueTooth_check_state:
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
                        break;
                    case R.id.turn_on:
                        //打开蓝牙
                        if (mBluetoothAdapter.isEnabled())
                            showToast("蓝牙已经打开了");
                        else {
                            //向系统请求开启蓝牙
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);//结果返回回调到onActivityResult()
                        }
                        break;
                    case R.id.turn_off:
                        //关闭蓝牙
                        if (!mBluetoothAdapter.isEnabled())
                            showToast("蓝牙已经关闭了");
                        else {
                            mBluetoothAdapter.disable();
                            showToast("成功关闭蓝牙");
                        }
                        break;
                }
                Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
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