package com.example.go_gym;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.go_gym.part2.ModifyActivity;
import com.example.go_gym.part2.UserDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {
    private static List<Information> information = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private static int position;
    public static void setPosition(int po){position = po;}
    public static int getPosition(){return position;}
    public static void setInformation(List<Information> list){information = list;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        //将状态栏设置为透明
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        initView();
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        myAdapter = new MyAdapter(this, information);
        recyclerView.setAdapter(myAdapter);
        //设置item及item中控件的点击事件
        myAdapter.setOnItemClickListener(myClickListener);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        Button add = (Button) findViewById(R.id.button_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//              点击的时候可以跳转到添加页面
                Intent intent = new Intent(SelectActivity.this, AddActivity.class);
                startActivity(intent);
                SelectActivity.this.finish();
            }
        });
    }

    private void initData() {
        List<Information> new_info = new ArrayList<>();
        MyDBHelper myDBHelper = new MyDBHelper(SelectActivity.this, "data.db", null, 1);
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users", new String[]{});
        while (cursor.moveToNext()) {
            int newId = cursor.getInt(0);
            String newName = cursor.getString(1);
            int newAge = cursor.getInt(2);
            int newHeight = cursor.getInt(3);
            int newWeight = cursor.getInt(4);
            Information info = new Information(newId, newAge, newHeight, newWeight, newName);
            new_info.add(info);
        }
        information = new_info;
    }

    /*
     * item＋item里的控件点击监听事件
     */
    private MyAdapter.OnItemClickListener myClickListener = new MyAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, MyAdapter.ViewName viewName, int position) {
            setPosition(position);
            switch (v.getId()) {
                case R.id.imageView:
                    Intent intent = new Intent(getApplicationContext(), UserDetailActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    Intent intent1 = new Intent(getApplicationContext(), ModifyActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
            }
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
    }
}