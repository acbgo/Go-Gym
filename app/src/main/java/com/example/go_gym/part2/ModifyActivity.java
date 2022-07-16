package com.example.go_gym.part2;

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

import com.example.go_gym.Information;
import com.example.go_gym.MyDBHelper;
import com.example.go_gym.R;
import com.example.go_gym.SelectActivity;

import java.util.ArrayList;
import java.util.List;

public class ModifyActivity extends AppCompatActivity {
    private int userId = SelectActivity.getPosition()+1;
    private EditText et_userName,et_age,et_height,et_weight;
    private Button save,delete,back;
    private String userName,age,height,weight;
    MyDBHelper myDBHelper = new MyDBHelper(ModifyActivity.this,"data.db",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        //将状态栏设置为透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //获取相应控件
        et_userName = (EditText) findViewById(R.id.user_name);
        et_age = (EditText) findViewById(R.id.user_age);
        et_height = (EditText) findViewById(R.id.user_height);
        et_weight = (EditText) findViewById(R.id.user_weight);
        save = (Button) findViewById(R.id.button_save);
        delete = (Button) findViewById(R.id.button_delete);
        back = (Button) findViewById(R.id.button_back);

        //查询并填充数据
        SQLiteDatabase db_query = myDBHelper.getWritableDatabase();
        Cursor cursor = db_query.rawQuery("SELECT * from users WHERE Id = ?",new String[]{String.valueOf(userId)});
        if (cursor.moveToNext()) {
            et_userName.setText(cursor.getString(1));
            et_age.setText(String.valueOf(cursor.getInt(2)));
            et_height.setText(String.valueOf(cursor.getInt(3)));
            et_weight.setText(String.valueOf(cursor.getInt(4)));
        }

        //对保存按钮响应点击事件
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db_alter = myDBHelper.getWritableDatabase();
                getEditString();
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(ModifyActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(age)){
                    Toast.makeText(ModifyActivity.this, "请输入您的年龄", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(height)){
                    Toast.makeText(ModifyActivity.this, "请输入您的身高", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(weight)){
                    Toast.makeText(ModifyActivity.this, "请输入您的体重", Toast.LENGTH_SHORT).show();
                    return;
                } else if(ifExits(userName,userId)){
                    Toast.makeText(ModifyActivity.this, "用户名已存在，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Toast.makeText(ModifyActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                }
                ContentValues values = new ContentValues();
                values.put("user",userName);
                values.put("age",age);
                values.put("height",height);
                values.put("weight",weight);
                db_alter.update("users",values,"Id = ?",new String[]{String.valueOf(userId)});

                //修改成功后跳转回上一个界面
                Intent intent = new Intent();
                intent.setClass(ModifyActivity.this, SelectActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //对删除按钮响应点击事件,删除后，先保存数据库中的数据，然后清空，然后重新添加
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db_delete = myDBHelper.getWritableDatabase();
                db_delete.delete("users","Id = ?",new String[]{String.valueOf(userId)});
                Toast.makeText(ModifyActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                //查询删除之后的数据
                List<Information> information = new ArrayList<>();
                Cursor cursor1 = db_delete.rawQuery("SELECT * FROM users", new String[]{});
                while (cursor1.moveToNext()) {
                    int newId = information.size()+1;
                    String newName = cursor1.getString(1);
                    int newAge = cursor1.getInt(2);
                    int newHeight = cursor1.getInt(3);
                    int newWeight = cursor1.getInt(4);
                    Information info = new Information(newId, newAge, newHeight, newWeight, newName);
                    information.add(info);
                }
                db_delete.delete("users",null,null);
                for (int i = 0; i < information.size(); i++) {
                    Information in = information.get(i);
                    ContentValues values = new ContentValues();
                    values.put("Id",in.getId());
                    values.put("user",in.getName());
                    values.put("age",in.getAge());
                    values.put("height",in.getHeight());
                    values.put("weight",in.getWeight());
                    db_delete.insert("users",null,values);
                }
                SelectActivity.setInformation(information);
                Intent intent = new Intent();
                intent.setClass(ModifyActivity.this,SelectActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //对返回按钮响应点击事件
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(ModifyActivity.this,SelectActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(ModifyActivity.this,SelectActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean ifExits(String str, int id){
        SQLiteDatabase db_query = myDBHelper.getWritableDatabase();
        Cursor cursor = db_query.rawQuery("SELECT * from users where user = ? and Id != ?",new String[]{str,String.valueOf(id)});
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
}