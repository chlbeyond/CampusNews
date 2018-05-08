package com.example.chl.campusnews.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chl.campusnews.Activity.AskAndAnswerActivity;
import com.example.chl.campusnews.Activity.SingleAnswerActivity;
import com.example.chl.campusnews.Activity.SingleInfoActivity;
import com.example.chl.campusnews.Model.NewsRecyclerItemInfo;
import com.example.chl.campusnews.MyApplication;
import com.example.chl.campusnews.R;
import com.example.chl.campusnews.Util.GlobalMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chl on 2018/3/20.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String NEWSFRAGMENT = "newsFragment";
    public static final String CLUBFRAGMENT = "clubFragment";
    public static final String ASKFRAGMENT = "askFragment";

    public static final String MENEWSFRAGMENT = "meNewsFragment";
    public static final String MENANSWERFRAGMENT = "meAnswerFragment";
    public static final String MEASKFRAGMENT = "meAskFragment";

    public static final String ASKACTIVITY = "askActivity";

    private String from;

    private Context mContext;
    private List<NewsRecyclerItemInfo> newsRecyclerItemInfos;

    public MyRecyclerViewAdapter(Activity context, List<NewsRecyclerItemInfo> newsRecyclerItemInfos) {
        this.mContext = context;
        this.newsRecyclerItemInfos = newsRecyclerItemInfos;
    }
    public MyRecyclerViewAdapter(Activity context, List<NewsRecyclerItemInfo> newsRecyclerItemInfos, String from) {
        this.mContext = context;
        this.newsRecyclerItemInfos = newsRecyclerItemInfos;
        this.from = from;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_recycler_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsRecyclerItemInfo newsRecyclerItemInfo = newsRecyclerItemInfos.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.headIcon.setImageResource(R.mipmap.ic_launcher_round);
        viewHolder.name.setText(newsRecyclerItemInfo.getName());
        viewHolder.content.setText(newsRecyclerItemInfo.getContent());
        viewHolder.agree.setText(newsRecyclerItemInfo.getAgree());
        viewHolder.comment.setText(newsRecyclerItemInfo.getComment());
        viewHolder.time.setText(newsRecyclerItemInfo.getTime());

        if (!TextUtils.isEmpty(newsRecyclerItemInfo.getQuestion())) {//如果问题不为空，则是问答页面的
            viewHolder.question.setVisibility(View.VISIBLE);
            viewHolder.question.setText(newsRecyclerItemInfo.getQuestion());
        } else {
            viewHolder.question.setVisibility(View.GONE);
        }

        viewHolder.personalData_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalMethod.toast("查看个人资料");
            }
        });

        viewHolder.content_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals(MyRecyclerViewAdapter.ASKFRAGMENT)) {//问答进去
                    mContext.startActivity(new Intent(mContext, AskAndAnswerActivity.class));
                } else if (from.equals(MyRecyclerViewAdapter.ASKACTIVITY)) {
                    mContext.startActivity(new Intent(mContext, SingleAnswerActivity.class));
                } else {
                    mContext.startActivity(new Intent(mContext, SingleInfoActivity.class));
                }
            }
        });

        if (from.equals(MyRecyclerViewAdapter.MENEWSFRAGMENT)) {
            viewHolder.personalData_ll.setVisibility(View.GONE);
            viewHolder.delect.setVisibility(View.VISIBLE);
            viewHolder.delect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalMethod.toast("删除");
                }
            });
        }

        if (from.equals(MyRecyclerViewAdapter.MENANSWERFRAGMENT)) {
            viewHolder.personalData_ll.setVisibility(View.GONE);
        }

        if (from.equals(MyRecyclerViewAdapter.MEASKFRAGMENT)) {
            viewHolder.personalData_ll.setVisibility(View.GONE);
            viewHolder.content.setVisibility(View.GONE);
            viewHolder.comment.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return newsRecyclerItemInfos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout personalData_ll;
        LinearLayout content_ll;
        ImageView headIcon;
        TextView name;
        TextView question;
        TextView content;
        TextView agree;
        TextView comment;
        TextView time;
        TextView delect;

        public ViewHolder(View itemView) {
            super(itemView);
            personalData_ll = (LinearLayout) itemView.findViewById(R.id.personalData_ll);
            content_ll = (LinearLayout) itemView.findViewById(R.id.content_ll);
            headIcon = (ImageView) itemView.findViewById(R.id.headIcon);
            name = (TextView) itemView.findViewById(R.id.name);
            question = (TextView) itemView.findViewById(R.id.question);
            content = (TextView) itemView.findViewById(R.id.content);
            agree = (TextView) itemView.findViewById(R.id.agree);
            comment = (TextView) itemView.findViewById(R.id.comment);
            time = (TextView) itemView.findViewById(R.id.time);
            delect = (TextView) itemView.findViewById(R.id.delect);
        }
    }
}
