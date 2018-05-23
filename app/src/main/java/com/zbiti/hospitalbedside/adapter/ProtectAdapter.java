package com.zbiti.hospitalbedside.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zbiti.hospitalbedside.R;

import java.util.List;

public class ProtectAdapter extends BaseAdapter {

    private List<String> dataList;
    private LayoutInflater inflater;

    public ProtectAdapter(List<String> dataList, Context context) {
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_tab2, null);
            holder = new MyHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        holder.tv.setText(dataList.get(position));
        return convertView;
    }

    private class MyHolder {
        TextView tv;
    }

    public void refreshDataList(List<String> dataList) {
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }

}
