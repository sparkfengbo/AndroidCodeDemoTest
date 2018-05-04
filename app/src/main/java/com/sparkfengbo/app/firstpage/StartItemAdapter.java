package com.sparkfengbo.app.firstpage;


import com.sparkfengbo.app.R;
import com.sparkfengbo.app.android.aidltest.AIDLTestActivity;
import com.sparkfengbo.app.android.aidltest.AnotherAshmemActivty;
import com.sparkfengbo.app.android.aidltest.AnotherInnnerAshmemService;
import com.sparkfengbo.app.android.aidltest.AshmemActivty;
import com.sparkfengbo.app.android.annotations.AnotationActivity;
import com.sparkfengbo.app.android.aidltest.MessengerTestActivity;
import com.sparkfengbo.app.android.ThreadTest.ThreadAndHandlerActivity;
import com.sparkfengbo.app.android.base.BaseItemData;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengbo on 2018/1/15.
 */

public class StartItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private List<BaseItemData> mData;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public StartItemAdapter(Context context) {
        mContext = context;

        mData = new ArrayList<>();

        /**
         * 类别
         */
        TestTitle titleJava = new TestTitle("Java");
        TestTitle titleAndroid = new TestTitle("Android");
        TestTitle titleSJMS = new TestTitle("设计模式");


        /**
         * Item
         */
        //TODO 这里添加ITEM
        TestItem itemAnotation = new TestItem("注解", 0, 1);
        TestItem itemActivityExport = new TestItem("ActivityExport", 0, 2);
        TestItem itemAIDL = new TestItem("AIDL", 0, 3);
        TestItem itemHandler = new TestItem("Handler", 0, 4);
        TestItem itemMessenger = new TestItem("Messenger", 0, 5);
        TestItem itemAshemem = new TestItem("Ashmem", 0, 6);
        TestItem itemAnotherAshemem = new TestItem("AnotherAshmem", 0, 7);



        /**
         * 和Java相关的
         */
        mData.add(titleJava);

        /**
         * Android
         */
        mData.add(titleAndroid);
        mData.add(itemAnotation);
        mData.add(itemActivityExport);
        mData.add(itemAIDL);
        mData.add(itemMessenger);
        mData.add(itemHandler);
        mData.add(itemAshemem);
        mData.add(itemAnotherAshemem);




//        mData.add(titleSJMS);

    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null && view.getTag() instanceof Integer) {
            mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
        }

        if (view.getTag() instanceof Integer) {

            //TODO 这里添加点击事件
            Integer id = (Integer) view.getTag();
            if (id == 1) {
                Intent i = new Intent(mContext, AnotationActivity.class);
                mContext.startActivity(i);
            } else if (id == 2) {

            } else if (id == 3) {
                Intent i = new Intent(mContext, AIDLTestActivity.class);
                mContext.startActivity(i);
            } else if (id == 4) {
                Intent i = new Intent(mContext, ThreadAndHandlerActivity.class);
                mContext.startActivity(i);
            } else if (id == 6) {
                Intent i = new Intent(mContext, AshmemActivty.class);
                mContext.startActivity(i);
            } else if (id == 7) {
                Intent i = new Intent(mContext, AnotherAshmemActivty.class);
                mContext.startActivity(i);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == BaseItemData.TYPE_TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.start_page_title, parent, false);
            view.setOnClickListener(this);
            TitleViewHolder viewHolder = new TitleViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.start_page_item, parent, false);
            view.setOnClickListener(this);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TitleViewHolder) {
            TitleViewHolder viewHolder = (TitleViewHolder) holder;
            viewHolder.mTextView.setText(mData.get(position).title);
            viewHolder.itemView.setTag(mData.get(position).id);
        } else if (holder instanceof ItemViewHolder) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.mTextView.setText(mData.get(position).title);
            viewHolder.itemView.setTag(mData.get(position).id);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData != null && mData.size() > position) {
            if (mData.get(position).type == BaseItemData.TYPE_TITLE) {
                return BaseItemData.TYPE_TITLE;
            } else {
                return BaseItemData.TYPE_ITEM;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public TitleViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.title);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public List<BaseItemData> getData() {
        return this.mData;
    }
}
