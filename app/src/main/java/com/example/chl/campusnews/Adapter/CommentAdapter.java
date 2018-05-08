package com.example.chl.campusnews.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.chl.campusnews.Model.CommentInfo;
import com.example.chl.campusnews.R;
import com.example.chl.campusnews.Util.GlobalMethod;
import java.util.ArrayList;



public class CommentAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CommentInfo> commentArray; //评论数组

    public CommentAdapter() {
    }

    public CommentAdapter(Context context, ArrayList<CommentInfo> commentArray) {
        this.context = context;
        this.commentArray = commentArray;
    }


    @Override
    public int getCount() {
        if (commentArray != null && commentArray.size() != 0) {
            return commentArray.size();
        }
        return 0;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView != null) {
            view = convertView;
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.comment_list_item, parent, false);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.list_item_ll = (LinearLayout) view.findViewById(R.id.list_item_ll);
            holder.txt_comment = (TextView) view.findViewById(R.id.txt_comment);

            view.setTag(holder);
        }
        //给相应位置的文字赋内容
        if (commentArray.size() != 0) {
            CommentInfo info = commentArray.get(position);

            StringBuilder actionText = new StringBuilder();

            //谁回复
            actionText.append("<a style=\"text-decoration:none;\" href='name' ><font color='#1468a3'>"
                    + info.getUser_nickname_comment() + "</font> </a>");

            // 回复谁，被回复的人可能不存在。
            if (info.getTo_user_comment() != null && info.getTo_user_comment().length() > 0) {
                actionText.append("回复");
                actionText.append("<font color='#1468a3'><a style=\"text-decoration:none;\" href='toName'>"
                        + info.getTo_user_comment() + " " + " </a></font>");
            }
            // 内容
            actionText.append("<font color='#484848'><a style=\"text-decoration:none;\" href='content'>"
                    + ":" + info.getContent_comment() + " " + " </a></font>");

            holder.txt_comment.setText(Html.fromHtml(actionText.toString()));
            holder.txt_comment.setMovementMethod(LinkMovementMethod
                    .getInstance());
            CharSequence text = holder.txt_comment.getText();
            int ends = text.length();
            Spannable spannable = (Spannable) holder.txt_comment.getText();
            URLSpan[] urlspan = spannable.getSpans(0, ends, URLSpan.class);
            SpannableStringBuilder stylesBuilder = new SpannableStringBuilder(text);
            stylesBuilder.clearSpans();

            for (URLSpan url : urlspan) {
                FeedTextViewURLSpan myURLSpan;
                    myURLSpan = new FeedTextViewURLSpan(url.getURL(),
                            context);
                stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url),
                        spannable.getSpanEnd(url), spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            holder.txt_comment.setText(stylesBuilder);
//            holder.txt_comment.setFocusable(true);
//            holder.txt_comment.setClickable(true);
//            holder.txt_comment.setLongClickable(false);

        }

        return view;
    }

    static class FeedTextViewURLSpan extends ClickableSpan {
        private String clickString;
        private Context context;

        public FeedTextViewURLSpan(String clickString, Context context) {
            this.clickString = clickString;
            this.context = context;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
            //给标记的部分 的文字 添加颜色
            if (clickString.equals("toName")) {
                ds.setColor(context.getResources().getColor(R.color.nav));
            } else if (clickString.equals("name")) {
                ds.setColor(context.getResources().getColor(R.color.nav));
            }
        }

        @Override
        public void onClick(View widget) {
            // 根据文字的标记 来进行相应的 响应事件
            Intent intent;
            if (clickString.equals("toName")) {
                //可以再次进行跳转activity的操作
//                Toast.makeText(context,"点击了"+info.getTo_user_comment(),Toast.LENGTH_SHORT).show();
//                if (info.getTo_user_id_comment().equals(Global.USERID)) {
                    GlobalMethod.toast("点击toName");
//
//                } else {
//                    intent = new Intent(context, PersonalProfileActivity.class);
//                    intent.putExtra("be_user_id", info.getTo_user_id_comment());
//                    context.startActivity(intent);
//                }
            } else if (clickString.equals("name")) {
                //可以再次进行跳转activity的操作
//                if (info.getUser_id_comment().equals(Global.USERID)) {
                GlobalMethod.toast("点击name");

//                } else {
//                    intent = new Intent(context, PersonalProfileActivity.class);
//                    intent.putExtra("be_user_id", info.getUser_id_comment());
//                    context.startActivity(intent);
//                }
            } else if (clickString.equals("content")) {
                GlobalMethod.toast("点击content");
                //只有评论的人是自己，才能够删除评论
//                if (info.getUser_id_comment().equals(Global.USERID)) {
////                    AppContext.toast("删除弹窗");
//
//                } else {
//
//                        //时间廊、信息详情回复评论
//                        CommentFun.inputComment(context, recyclerView, view, mListener, info.getShuo_user_id(),
//                                info.getShuoId(), info, commentArray, new CommentFun.InputCommentListener() {
//                                    @Override
//                                    public void onCommitComment() {
////                            adapter.notifyDataSetChanged();
////                            if (mListener != null) mListener.NoticeListReflesh(MyRecyclerViewAdapter.OnClick.CLICKAGREE);
//                                    }
//                                });
//                }
            }
        }
    }

    class ViewHolder {
        LinearLayout list_item_ll;
        TextView txt_comment;
    }

}
