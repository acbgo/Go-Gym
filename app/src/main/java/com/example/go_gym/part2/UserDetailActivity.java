package com.example.go_gym.part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.go_gym.R;
import com.example.go_gym.SelectActivity;

public class UserDetailActivity extends AppCompatActivity {
    TextView push_up_old,push_up_new,squat_old,squat_new,plank_old,plank_new;

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
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(UserDetailActivity.this, SelectActivity.class);
        startActivity(intent);
        finish();
    }
}