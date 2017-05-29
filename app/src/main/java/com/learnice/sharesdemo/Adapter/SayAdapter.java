package com.learnice.sharesdemo.Adapter;

import com.learnice.base_library.base_adapter.BaseAdapter;
import com.learnice.base_library.base_adapter.BaseViewHolder;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.bean.tb_say;

/**
 * Created by Xuebin He on 2016/6/24.
 */
public class SayAdapter extends BaseAdapter<tb_say> {


    @Override
    protected void bind(BaseViewHolder viewHolder, int layoutRes) {

    }

    @Override
    public int getLayoutRes(int index) {
        return R.layout.say_list_item;
    }

    @Override
    public void convert(BaseViewHolder holder, tb_say data, int index) {
        holder.setText(R.id.say_name,data.getUserName());
        holder.setText(R.id.say_content,data.getContent());
        holder.setText(R.id.say_content_time,data.getCreatedAt());
    }
}
