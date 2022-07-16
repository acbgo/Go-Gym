package com.example.go_gym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
public class MainActivity extends AppCompatActivity {
    Button button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //将状态栏设置为透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        button = (Button) findViewById(R.id.button_select);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();//通过intent来实现页面的跳转
                intent.setClass(MainActivity.this, SelectActivity.class);//确定需要跳转的页面
                startActivity(intent);//跳转
                finish();//结束当前界面
            }
        });
    }
}