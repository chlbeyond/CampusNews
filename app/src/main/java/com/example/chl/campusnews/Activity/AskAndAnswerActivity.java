package com.example.chl.campusnews.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chl.campusnews.Adapter.MyRecyclerViewAdapter;
import com.example.chl.campusnews.Model.NewsRecyclerItemInfo;
import com.example.chl.campusnews.R;
import com.example.chl.campusnews.Util.GlobalMethod;
import com.example.chl.campusnews.View.NineGridLayout.NineGridLayoutExtends;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AskAndAnswerActivity extends BaseActivity implements View.OnClickListener {

    private ImageView head_image;
    private TextView name;
    private TextView question;

    private XRecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private List<NewsRecyclerItemInfo> newsRecyclerItemInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_and_answer);

        initNav();
        initHead();
        initRecyclerView();
        initData();
    }

    private void initHead() {
        findViewById(R.id.head_ll).setOnClickListener(this);
        head_image = (ImageView) findViewById(R.id.head_image);
        name = (TextView) findViewById(R.id.name);
        question = (TextView) findViewById(R.id.question);
        findViewById(R.id.answer).setOnClickListener(this);

    }

    private void initNav() {
        findViewById(R.id.nav_left_region).setOnClickListener(this);
        ((TextView) findViewById(R.id.nav_title)).setText("你问我答");
    }

    private void initRecyclerView() {
        recyclerView = (XRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        LinearLayoutManager manager = new LinearLayoutManager(AskAndAnswerActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        recyclerView.setLimitNumberToCallLoadMore(0);//还有多少个item未被浏览时触发加载更多操作
        adapter = new MyRecyclerViewAdapter(AskAndAnswerActivity.this, newsRecyclerItemInfos, MyRecyclerViewAdapter.ASKACTIVITY);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {//上拉刷新
                recyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {//下拉加载
                if (true) {//当前页不等于总页数时加载更多数据
                    recyclerView.loadMoreComplete(); //加载完成
                } else {
                    recyclerView.setNoMore(true); //已经到底，没有更多数据
                    adapter.notifyDataSetChanged();
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.nav_left_region:
                finish();
                break;
            case R.id.answer:
                intent = new Intent(AskAndAnswerActivity.this, ReleaseActivity.class);
                intent.putExtra(ReleaseActivity.FROM, ReleaseActivity.ASKACTIVITY);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void initData() {
        NewsRecyclerItemInfo newsRecyclerItemInfo = new NewsRecyclerItemInfo("头像","潘晓阳","愚人节这天，不少同学早早地就来到了教室。大家互相骗人，眉飞色舞，即使是真话也没有人相信。"
                ,"1赞同","1评论","2018-1-1");
        newsRecyclerItemInfos.add(newsRecyclerItemInfo);
        newsRecyclerItemInfo = new NewsRecyclerItemInfo("头像","潘中阳","星期六下午，我来到校园里，准备与伙伴们办一期“学雷锋、学赖宁”的黑板报。路过三年级走廊时，听到一阵锤子敲打的声音。我想这一定是学校木工叔叔在修课桌椅子，没在意，径直朝班上走去。",
                "2赞同","2评论","2018-2-2");
        newsRecyclerItemInfos.add(newsRecyclerItemInfo);
        newsRecyclerItemInfo = new NewsRecyclerItemInfo("头像","潘大洋","时间尚早，其他几个伙伴还没来。我想起早上几个同学说，有几张椅子坏了，于是就搬了两张到三楼准备给木工修理。 　　我搬着椅子来到乓年级走廊，那锤子敲打桌椅的声音依然那么清脆。我循声走去。刚到教室外面，就听到一阵感人的对话：\n" +
                "\n" +
                "该文章《校园里的新鲜事作文,校园里的新鲜事》来源于出国留学网，网址：https://zw.liuxue86.com/z/2941515.html","3赞同","3评论","2018-3-3");
        newsRecyclerItemInfos.add(newsRecyclerItemInfo);
        newsRecyclerItemInfo = new NewsRecyclerItemInfo("头像","盘中餐","我在门外探头一望：在讲台边已经修好许多张了。这时，爸爸正走到孩子身边，伸手摇了摇椅子，显然他在检查孩子钉的椅子牢不牢。\n" +
                "\n" +
                "该文章《校园里的新鲜事作文,校园里的新鲜事》来源于出国留学网，网址：https://zw.liuxue86.com/z/2941515.html","4赞同","4评论","2018-1-4");
        newsRecyclerItemInfos.add(newsRecyclerItemInfo);
        newsRecyclerItemInfo = new NewsRecyclerItemInfo("头像","潘潇潇","孩子眼尖，他瞧见我站在教室门边，而且拿着椅子，跑过来问：“大哥哥，你这椅子要修吗?”"
                , "5赞同","5评论","2018-2-5");
        newsRecyclerItemInfos.add(newsRecyclerItemInfo);
        adapter.notifyDataSetChanged();
    }

}
