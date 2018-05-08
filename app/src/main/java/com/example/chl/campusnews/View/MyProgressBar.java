package com.example.chl.campusnews.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.chl.campusnews.R;

/**
 * Created by Administrator on 2017/9/7.
 */

public class MyProgressBar extends RelativeLayout {
    private View view;
    private TextView tipContent;

    public MyProgressBar(Context context) {
        super(context);
        initView();
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        view = LayoutInflater.from(getContext()).inflate(R.layout.custom_progress_view,this,false);
        tipContent = (TextView)view.findViewById(R.id.tip_content);
        addView(view);
    }

    public void setContent(String content){
        tipContent.setText(content);
    }

}
