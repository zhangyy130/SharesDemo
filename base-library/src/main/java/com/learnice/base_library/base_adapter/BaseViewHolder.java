package com.learnice.base_library.base_adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by Xuebin He on 2017/4/25.
 * e-mail:learnice.he@gmail.com.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private Object[] mIdsAndViews = new Object[0];
    private OnItemClickListener mOnItemClickListener;
    private boolean mInitClickListener;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mInitClickListener = false;
    }

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    };

    public void setOnItemClickListner(OnItemClickListener onItemClickListner) {
        this.mOnItemClickListener = onItemClickListner;
        if (onItemClickListner != null && !mInitClickListener) {
            mInitClickListener = true;
            this.itemView.setOnClickListener(mOnClickListener);
        }
    }

    public BaseViewHolder setTextColor(@IdRes int id, @ColorInt int resId) {
        TextView view = find(id);
        if (view != null) {
            view.setTextColor(resId);
        }
        return this;
    }

    public BaseViewHolder setText(@IdRes int id, @StringRes int resId) {
        TextView view = find(id);
        if (view != null) {
            view.setText(resId);
        }
        return this;
    }

    public BaseViewHolder setText(@IdRes int id, String text) {
        TextView view = find(id);
        if (view != null) {
            view.setText(text);
        }
        return this;
    }

    public BaseViewHolder setImage(@IdRes int id, @DrawableRes int resId) {
        ImageView view = find(id);
        if (view != null) {
            view.setImageResource(resId);
        }
        return this;
    }

    public BaseViewHolder setImage(@IdRes int id, Drawable drawable) {
        ImageView view = find(id);
        if (view != null) {
            view.setImageDrawable(drawable);
        }
        return this;
    }

    public BaseViewHolder setVisibility(@IdRes int id, int visibility) {
        View view = find(id);
        if (view != null) {
            view.setVisibility(visibility);
        }
        return this;
    }

    public BaseViewHolder setBackgroundColor(@IdRes int id, @ColorInt int color) {
        View view = find(id);
        if (view != null) {
            view.setBackgroundColor(color);
        }
        return this;
    }

    public <T extends View> T find(@IdRes int viewId) {
        int indexToAdd = -1;
        for (int i = 0; i < mIdsAndViews.length; i += 2) {
            Integer id = (Integer) mIdsAndViews[i];
            //找到已经缓存的控件
            if (id != null && id == viewId) {
                return (T) mIdsAndViews[i + 1];
            }
            //没找到控件，但是缓存数组中有空位
            if (id == null) {
                indexToAdd = i;
                break;
            }
        }
        //没找到控件，缓存数组没有空位
        if (indexToAdd == -1) {
            indexToAdd = mIdsAndViews.length;
            mIdsAndViews = Arrays.copyOf(mIdsAndViews, indexToAdd < 2 ? 2 : indexToAdd * 2);
        }
        //在数组中添加viewid 和 view
        mIdsAndViews[indexToAdd] = viewId;
        mIdsAndViews[indexToAdd + 1] = itemView.findViewById(viewId);
        return (T) mIdsAndViews[indexToAdd + 1];
    }

}
