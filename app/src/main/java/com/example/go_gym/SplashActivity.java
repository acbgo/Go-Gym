package com.example.go_gym;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

        Thread myThread=new Thread(){//创建子线程
            @Override
            public void run() {
                try{
                    sleep(1000);//使程序休眠1秒
                    Intent it=new Intent(getApplicationContext(), MainActivity.class);//启动MainActivity
                    startActivity(it);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }
}
