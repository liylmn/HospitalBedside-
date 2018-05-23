package com.zbiti.hospitalbedside.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zbiti.hospitalbedside.R;

import java.util.List;
import java.util.Map;

public class BedAdapter extends BaseAdapter {

    private List<Map<String, Object>> dataList;
    private Activity mActivity;
    private LayoutInflater inflater;

    public BedAdapter(List<Map<String, Object>> dataList, Activity activity) {
        this.dataList = dataList;
        this.mActivity = activity;
        inflater = mActivity.getLayoutInflater();
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
        SecMenuHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_bed, null);
            holder = new SecMenuHolder();
            holder.tv = (TextView) convertView
                    .findViewById(R.id.tv_bed);
            convertView.setTag(holder);
        } else {
            holder = (SecMenuHolder) convertView.getTag();
        }

        holder.tv.setText((String) dataList.get(position).get("name"));

        return convertView;
    }

    private class SecMenuHolder {
        TextView tv;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }

}
