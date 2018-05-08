package com.example.chl.campusnews.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chl.campusnews.Adapter.CommentAdapter;
import com.example.chl.campusnews.Model.CommentInfo;
import com.example.chl.campusnews.R;
import com.example.chl.campusnews.Tool.ImageCompress;
import com.example.chl.campusnews.Util.Global;
import com.example.chl.campusnews.Util.GlobalMethod;
import com.example.chl.campusnews.View.NineGridLayout.NineGridLayoutExtends;
import com.example.chl.campusnews.View.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

public class SingleInfoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView head_image;
    private TextView name;
    private TextView content;
    private NineGridLayoutExtends layout_nine_grid;

    private ImageView like_btn;
    private TextView like_num;
    private ImageView comment;
    private TextView comment_num;

    private NoScrollListView listview;
    private EditText say_something;

    private List<String> mUrlList = new ArrayList<>();
    private ArrayList<CommentInfo> commentArray = new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_info);

        initData();


        initNav();
        initHead();
        initCommentNav();
        initListView();
    }

    private void initData() {
        mUrlList.add("http://time.schoolwo.cn/upload/images/15255019499.jpg");
        mUrlList.add("http://time.schoolwo.cn/upload/images/15255019499.jpg");
        mUrlList.add("http://time.schoolwo.cn/upload/images/15255019499.jpg");

        for (int i = 0; i < 5; i++) {
            CommentInfo info = new CommentInfo();
            info.setUser_nickname_comment("评论者" + i);
            info.setTo_user_comment("被回复者"+i);
            info.setContent_comment("你好，我是" + i);
            commentArray.add(info);
        }
    }

    private void initListView() {
        listview = (NoScrollListView)findViewById(R.id.listview);
        listview.setAdapter(new CommentAdapter(SingleInfoActivity.this, commentArray));
        say_something = (EditText) findViewById(R.id.say_something);
    }

    private void initCommentNav() {
        findViewById(R.id.like_ll).setOnClickListener(this);
        like_btn = (ImageView) findViewById(R.id.like_btn);
        like_num = (TextView) findViewById(R.id.like_num);
        findViewById(R.id.comment_ll).setOnClickListener(this);
        comment = (ImageView) findViewById(R.id.comment);
        comment_num = (TextView) findViewById(R.id.comment_num);
        findViewById(R.id.more_ll).setOnClickListener(this);
    }

    private void initHead() {
        findViewById(R.id.head_ll).setOnClickListener(this);
        head_image = (ImageView) findViewById(R.id.head_image);
        name = (TextView) findViewById(R.id.name);
        content = (TextView) findViewById(R.id.content);
        content.setText("星期六下午，我来到校园里，准备与伙伴们办一期“学雷锋、学赖宁”的黑板报。路过三年级走廊时，听到一阵锤子敲打的声音。" +
                "我想这一定是学校木工叔叔在修课桌椅子，没在意，径直朝班上走去。");

        layout_nine_grid = (NineGridLayoutExtends) findViewById(R.id.layout_nine_grid);
        layout_nine_grid.setIsShowAll(false);//九宫格图片
        layout_nine_grid.setUrlList(mUrlList);
    }

    private void initNav() {
        findViewById(R.id.right_click_region_a).setOnClickListener(this);
        ((TextView) findViewById(R.id.nav_title)).setText("新鲜事");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_click_region_a:
                finish();
                break;
            case R.id.head_ll:
                GlobalMethod.toast("查看资料");
                break;
            case R.id.like_ll:
                GlobalMethod.toast("点赞");
                break;
            case R.id.comment_ll:
                GlobalMethod.toast("评论");
                break;
            case R.id.more_ll:
                GlobalMethod.toast("转发");
                break;
            default:
                break;
        }
    }
}
