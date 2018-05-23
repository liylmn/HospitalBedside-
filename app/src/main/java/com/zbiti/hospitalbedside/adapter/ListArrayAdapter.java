package com.zbiti.hospitalbedside.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.zbiti.hospitalbedside.R;

import java.util.List;

import static cn.jiguang.c.a.m;

public class ListArrayAdapter extends RecyclerView.Adapter<ListArrayAdapter.MyViewHolder> implements View.OnClickListener {
    private Activity mActivity;
//    private List<DateListEntity.DataBean> mlist;
    private List<String> mlist;

    private OnRecyclerViewItemClickListener listener;

    public ListArrayAdapter(FragmentActivity activity, List<String> list) {
        mActivity=activity;
        mlist=list;
    }


//    public ListArrayAdapter(Activity mActivity, List<DateListEntity.DataBean> mlist){
//         this.mActivity=mActivity;
//         this.mlist=mlist;
//     }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v,(int)v.getTag());
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public ListArrayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(mActivity).inflate(R.layout.item_newtab2, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListArrayAdapter.MyViewHolder holder, int position) {
        holder.mtvdata1.setText(mlist.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView mtvdata1;

        public MyViewHolder(View view)
        {
            super(view);
            mtvdata1 = (TextView) view.findViewById(R.id.tv_tab2item);

        }

    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

}