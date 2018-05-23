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

public class RoomAdapter extends BaseAdapter {

    private List<Map<String, Object>> dataList;
    private Activity mActivity;
    private LayoutInflater inflater;

    private int selectedPostion = 0;

    public RoomAdapter(List<Map<String, Object>> dataList, Activity activity) {
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
        PriMenuHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_room, null);
            holder = new PriMenuHolder();
            holder.tv = (TextView) convertView
                    .findViewById(R.id.tv_room);
            convertView.setTag(holder);
        } else {
            holder = (PriMenuHolder) convertView.getTag();
        }

        holder.tv.setText((String) dataList.get(position).get("name"));

        if (position == selectedPostion) {
            convertView.setBackgroundResource(R.color.color_title);
            holder.tv.setTextColor(mActivity.getResources().getColor(R.color.white));
        } else {
            convertView.setBackgroundResource(R.color.white);
            holder.tv.setTextColor(mActivity.getResources().getColor(R.color.color_title));
        }

        return convertView;
    }

    private class PriMenuHolder {
        TextView tv;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }

    public void setSelectedPostion(int selectedPostion) {
        this.selectedPostion = selectedPostion;
    }

}
