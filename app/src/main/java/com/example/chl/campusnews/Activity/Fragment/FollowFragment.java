package com.example.chl.campusnews.Activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chl.campusnews.Adapter.FollowListAdapter;
import com.example.chl.campusnews.Model.PersonInfo;
import com.example.chl.campusnews.R;
import com.example.chl.campusnews.View.MyLetterView;
import com.pinyin4android.PinyinUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FollowFragment extends Fragment {

    private Context mContext;
    private View view;
    private LayoutInflater inflater;
    private MyLetterView letterView;
    private ListView list_view;
    private FollowListAdapter adapter;
    private ArrayList<PersonInfo> personInfos = new ArrayList<>();// 好友列表

    private TextView overlay; // 对话框首字母textview
    private OverlayThread overlayThread = new OverlayThread(); // 隐藏首字母对话框任务
    private WindowManager windowManager;
    private Handler handler = new Handler();

    public FollowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        view = inflater.inflate(R.layout.fragment_follow, container, false);
        mContext = this.getContext();
        initNav();
        initOverlay();
        initLetterView();
        initListView();
        initData();
        return view;
    }

    private void initNav() {
        view.findViewById(R.id.right_click_region_a).setVisibility(View.GONE);
        ((TextView)view.findViewById(R.id.nav_title)).setText("关注的人");
    }

    private void initListView() {
        list_view = (ListView) view.findViewById(R.id.list_view);

        adapter = new FollowListAdapter(getActivity(), personInfos);
        list_view.setAdapter(adapter);
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Intent intent = new Intent(mContext, PersonalProfileActivity.class);
//                intent.putExtra("mParam1", mParam1);
//                intent.putExtra("be_user_id", personInfos.get(position).getId());
//                startActivity(intent);
                Toast.makeText(view.getContext(), "相当于点击头像", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initLetterView() {
        letterView = (MyLetterView) view.findViewById(R.id.letterView);
        letterView.setOnTouchingLetterChangedListener(new LetterViewListener());
    }

    /**
     * a-z排序
     */
    @SuppressWarnings("rawtypes")//重要点
            Comparator comparator = new Comparator<PersonInfo>() {
        @Override
        public int compare(PersonInfo lhs, PersonInfo rhs) {
            String a = lhs.getPinyin().substring(0, 1);
            String b = rhs.getPinyin().substring(0, 1);
            if ("#".equals(a)) {
                return 1;
            } else if ("#".equals(b)) {
                return -1;
            }
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {

        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    @Override
    public void onDetach() {
        if (windowManager != null) {
            windowManager.removeViewImmediate(overlay);
            windowManager = null;
        }
        super.onDetach();
    }

    private class LetterViewListener implements MyLetterView.OnTouchingLetterChangedListener {

        @Override
        //overlay覆盖物
        public void onTouchingLetterChanged(final String s) {
            if (adapter.getAlphaIndexer().get(s) != null) {
                int position = adapter.getAlphaIndexer().get(s);
                list_view.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1000);
            }
        }
    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    private void initData() {
        makeData("AMQ");
        makeData("apple");
        makeData("苹果");
        makeData("农机呀");
        makeData("诺基亚");
        makeData("潘兴杨");
        makeData("盘阳河");
        makeData("六六六");
        makeData("溜溜溜");
        makeData("123");
        Collections.sort(this.personInfos, comparator);//排序
        adapter.notifyDataSetChanged();
    }

    private void makeData(String name) {
        PersonInfo personInfo = new PersonInfo();
        personInfo.setName(name);
        String pinYin = PinyinUtil.toPinyin(getContext(), name);
        if ("".equals(pinYin)) {
            pinYin = "#";
        }
        personInfo.setPinyin(pinYin);
        personInfos.add(personInfo);
    }
}
