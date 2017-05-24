package com.learnice.base_library.base_adapter;

import java.util.List;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.learnice.base_library.R;
import java.util.ArrayList;

/**
 * Created by Xuebin He on 2017/4/25.
 * e-mail:learnice.he@gmail.com.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements LoadMore {

    private OnItemClickListener mOnItemClickListener;

    private List<T> mData = new ArrayList<>();

    private DiffUtilCallback<T> mCallBack;

    private boolean mDetectMoves;

    private boolean mOpenAutoLoadMore;

    private OnLoadMoreListener mOnLoadMoreListener;

    @LayoutRes
    private int mLoadMoreLayout = R.layout.layout_default_loadmore;

    @LoadState
    private int mLoadState;

    private boolean mIsLoading = false;


    public void setData(List<? extends T> data) {


        //没有设置callback 直接刷新数据
        if (mCallBack == null) {
            mData.clear();
            if (data != null) {
                mData.addAll(data);
            }
            notifyDataSetChanged();
            return;
        }
        //有callback
        final List<T> oldData = new ArrayList<>(mData);
        mData.clear();
        if (data != null) {
            mData.addAll(data);
        }

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldData.size();
            }

            @Override
            public int getNewListSize() {
                return mData.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                T oldItem = oldData.get(oldItemPosition);
                T newItem = mData.get(newItemPosition);
                return mCallBack.areItemsTheSame(oldItem, newItem);
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                T oldItem = oldData.get(oldItemPosition);
                T newItem = mData.get(newItemPosition);
                return mCallBack.areContentsTheSame(oldItem, newItem);
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                T oldItem = oldData.get(oldItemPosition);
                T newItem = mData.get(newItemPosition);
                return mCallBack.getChangePayload(oldItem, newItem);
            }
        }, mDetectMoves);

        result.dispatchUpdatesTo(new ListUpdateCallback() {
            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }

            @Override
            public void onChanged(int position, int count, Object payload) {
                notifyItemRangeChanged(position, count, payload);
            }
        });
    }

    public void setCallBack(DiffUtilCallback<T> callback) {
        this.mCallBack = callback;
        this.mDetectMoves = true;
    }

    public void setCallBack(DiffUtilCallback<T> callBack, boolean detectMoves) {
        this.mCallBack = callBack;
        this.mDetectMoves = detectMoves;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int layoutRes) {
        BaseViewHolder baseViewHolder = new BaseViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(layoutRes, parent, false));
        if (layoutRes == mLoadMoreLayout) {
            bindLoadMore(baseViewHolder);
        } else {
            bindData(baseViewHolder, layoutRes);
        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        int index = position;
        if (index < mData.size()) {
            convert(holder, mData.get(position), position);
            return;
        }
        if (canAutoLoadMore()) {
            if (mLoadState == LOADING && !mIsLoading) {
                mIsLoading = true;
                mOnLoadMoreListener.onLoadMore();
            }

            convertLoadMore(holder, mLoadState);
        }

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        int loadMoreCount = mOpenAutoLoadMore ? 1 : 0;
        return mData.size() + loadMoreCount;
    }

    @Override
    public int getItemViewType(int position) {
        int index = position;
        if (index < mData.size()) {
            return getLayoutRes(0);
        }
        return mLoadMoreLayout;
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    private void bindLoadMore(BaseViewHolder viewHolder) {
        viewHolder.setOnItemClickListner(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mLoadState == LOAD_FAILED) {
                    mLoadState = LOADING;
                    notifyItemChanged(position);
                }
            }
        });
    }

    private void bindData(BaseViewHolder viewHolder, int layoutRes) {
        viewHolder.setOnItemClickListner(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            }
        });
        bind(viewHolder, layoutRes);
    }

    private void convertLoadMore(BaseViewHolder holder, int mLoadState) {
        switch (mLoadState) {
            case LOADING:
                holder.setVisibility(R.id.li_load_layout, View.VISIBLE)
                        .setText(R.id.load_tips, "正在加载")
                        .setVisibility(R.id.progress_bar, View.VISIBLE);
                break;
            case LOAD_COMPLETED:
                holder.setVisibility(R.id.li_load_layout, View.GONE);
                break;
            case LOAD_FAILED:
                holder.setVisibility(R.id.li_load_layout, View.VISIBLE)
                        .setText(R.id.load_tips, "加载失败")
                        .setVisibility(R.id.progress_bar, View.GONE);
                break;
        }

    }

    //-----------loadmore------------
    @Override
    public boolean canAutoLoadMore() {
        return mOnLoadMoreListener != null && mOpenAutoLoadMore;
    }

    @Override
    public void openAutoLoadMore(boolean open) {
        mIsLoading = false;
        if (canAutoLoadMore() && !open) {
            notifyDataSetChanged();
        }
        if (!mOpenAutoLoadMore && open) {
            mLoadState = LOADING;
            notifyDataSetChanged();
        }
        mOpenAutoLoadMore = open;
    }

    @Override
    public void loadCompleted() {
        mLoadState = LOAD_COMPLETED;
        mIsLoading = false;
        if (canAutoLoadMore()) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    @Override
    public void loadFailed() {
        mLoadState = LOAD_FAILED;
        mIsLoading = false;
        if (canAutoLoadMore()) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    @Override
    public void setLoadMoreLayout(@LayoutRes int moreLayout) {

    }

    //-----------loadmore------------

    protected abstract void bind(BaseViewHolder viewHolder, int layoutRes);

    @LayoutRes
    public abstract int getLayoutRes(int index);

    public abstract void convert(BaseViewHolder holder, T data, int index);
}
