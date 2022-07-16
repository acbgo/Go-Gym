package com.example.go_gym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    private static final String DB_name = "data.db";
    private EditText et_userName,et_age,et_height,et_weight;
    private Button confirm;
    private String userName,age,height,weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //将状态栏设置为透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        init();
    }

    public void init(){
        MyDBHelper myDBHelper = new MyDBHelper(AddActivity.this,DB_name,null,1);
        SQLiteDatabase db_write = myDBHelper.getWritableDatabase();

        //获取对应控件
        et_userName = (EditText)findViewById(R.id.user_name);
        et_age = (EditText) findViewById(R.id.user_age);
        et_height = (EditText) findViewById(R.id.user_height);
        et_weight = (EditText) findViewById(R.id.user_weight);
        confirm = (Button) findViewById(R.id.button_save);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEditString();
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(AddActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(age)){
                    Toast.makeText(AddActivity.this, "请输入您的年龄", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(height)){
                    Toast.makeText(AddActivity.this, "请输入您的身高", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(weight)){
                    Toast.makeText(AddActivity.this, "请输入您的体重", Toast.LENGTH_SHORT).show();
                    return;
                } else if(ifExits(userName)){
                    Toast.makeText(AddActivity.this, "用户名已存在，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(AddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                }
                ContentValues values = new ContentValues();
                values.put("user",userName);
                values.put("age",age);
                values.put("height",height);
                values.put("weight",weight);
                db_write.insert("users",null,values);

                //添加成功后跳转回上一个界面
                Intent intent = new Intent();
                intent.setClass(AddActivity.this, SelectActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public boolean ifExits(String str){
        MyDBHelper myDBHelper = new MyDBHelper(AddActivity.this,DB_name,null,1);
        SQLiteDatabase db_query = myDBHelper.getWritableDatabase();
        Cursor cursor = db_query.rawQuery("SELECT * from users where user = ?",new String[]{str});
        if(cursor.getCount() == 0)
            return false;
        else
            return true;
    }
    private void getEditString(){
        userName = et_userName.getText().toString().trim();
        age = et_age.getText().toString().trim();
        height = et_height.getText().toString().trim();
        weight = et_weight.getText().toString().trim();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(AddActivity.this,SelectActivity.class);
        startActivity(intent);
        finish();
    }
}