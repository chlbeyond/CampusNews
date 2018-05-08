package com.example.chl.campusnews.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chl.campusnews.Model.PersonInfo;
import com.example.chl.campusnews.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by chl on 2018/3/20.
 */

public class FollowListAdapter extends BaseAdapter {

    Context mContext;
    private LayoutInflater inflater;
    private List<PersonInfo> dataList;
    private HashMap<String, Integer> alphaIndexer = new HashMap<>();// 存放存在的汉语拼音首字母和与之对应的列表位置

    public FollowListAdapter(Context mContext, ArrayList<PersonInfo> personInfos) {
        this.mContext = mContext;
        this.dataList = personInfos;
        this.inflater = LayoutInflater.from(mContext);
        change(); //构造时改变alphaIndexer的值
    }

    public HashMap<String, Integer> getAlphaIndexer() {
        return this.alphaIndexer;
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.think_other_listview_item, null);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.head_icon);
            holder.name = (TextView) convertView.findViewById(R.id.head_name);
            holder.alpha = (TextView) convertView.findViewById(R.id.letter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PersonInfo info = dataList.get(position);
        holder.name.setText(info.getName());
        String currentStr = getAlpha(info.getPinyin());
        String previewStr = (position - 1) >= 0 ? getAlpha(dataList
                .get(position - 1).getPinyin()) : " ";
        if (!previewStr.equals(currentStr)) {
            holder.alpha.setVisibility(View.VISIBLE);
            //这里是设置首字母的 比如 重庆  C首字母
            holder.alpha.setText(currentStr);
        } else {
            holder.alpha.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView icon; // 头像
        TextView name; // 名字
        TextView alpha; // 首字母标题
    }

    // 获得汉语拼音首字母
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else if (str.equals("0")) {
            return "系统消息";
        } else if (str.equals("1")) {
            return "群聊";
        } else if (str.equals("2")) {

            return "标签";
        } else {
            return "#";
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        change();//数据源改变时，改变alphaIndexer的值
    }

    private void change() {
        alphaIndexer.clear();
        for (int i = 0; i < this.dataList.size(); i++) {
            // 当前汉语拼音首字母
            String currentStr = getAlpha(this.dataList.get(i).getPinyin());
            // 上一个汉语拼音首字母，如果不存在为" "
            String previewStr = (i - 1) >= 0 ? getAlpha(this.dataList.get(i - 1)
                    .getPinyin()) : " ";
            if (!previewStr.equals(currentStr)) {//当前首字母与上一个不一样则添加到alphaIndexer中
                String name = getAlpha(this.dataList.get(i).getPinyin());
                alphaIndexer.put(name, i);
            }
        }
    }
}
