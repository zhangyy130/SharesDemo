package com.learnice.sharesdemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.Stock.Say;

import java.util.List;

/**
 * Created by Xuebin He on 2016/6/24.
 */
public class SayAdapter extends RecyclerView.Adapter<SayAdapter.MyViewHolder> {
    LayoutInflater layoutInflater;
    List<Say> list;
    public SayAdapter(Context context, List<Say> list) {
        this.layoutInflater=LayoutInflater.from(context);
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.say_list_item,null);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.sayName.setText(list.get(position).getUserName());
        holder.SayContent.setText(list.get(position).getContent());
        holder.SayContentTime.setText(list.get(position).getContentTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
    TextView sayName,SayContent,SayContentTime;
        public MyViewHolder(View itemView) {
            super(itemView);
            sayName= (TextView) itemView.findViewById(R.id.say_name);
            SayContent= (TextView) itemView.findViewById(R.id.say_content);
            SayContentTime= (TextView) itemView.findViewById(R.id.say_content_time);
        }
    }
}
