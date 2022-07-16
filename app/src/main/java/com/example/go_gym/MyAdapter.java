package com.example.go_gym;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    public List<Information> data;

    //构造器
    public MyAdapter(Context context,List<Information> info){
        this.context = context;
        this.data = info;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView id,name,age,height,weight;
        public MyViewHolder(View view) {
            super(view);
            profile = (ImageView) view.findViewById(R.id.imageView);
            id = (TextView) view.findViewById(R.id.ad_id);
            name = (TextView) view.findViewById(R.id.ad_name);
            age = (TextView) view.findViewById(R.id.ad_age);
            height = (TextView) view.findViewById(R.id.ad_height);
            weight = (TextView) view.findViewById(R.id.ad_weight);

            //为ItemView添加点击事件
            itemView.setOnClickListener(MyAdapter.this);
            profile.setOnClickListener(MyAdapter.this);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(position+1+"");
        holder.name.setText(data.get(position).name+"");
        holder.age.setText(data.get(position).age+"");
        holder.height.setText(data.get(position).height+"");
        holder.weight.setText(data.get(position).weight+"");

        holder.itemView.setTag(position);
        holder.profile.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //=======================以下为item中的button控件点击事件处理===================================

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if (mOnItemClickListener != null) {
            switch (view.getId()){
                case R.id.imageView:
                    mOnItemClickListener.onItemClick(view, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(view, ViewName.ITEM, position);
                    break;
            }
        }
    }
}
