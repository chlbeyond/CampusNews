package com.example.chl.campusnews.SelectImage.ImageLoader;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.chl.campusnews.R;
import com.example.chl.campusnews.SelectImage.Utils.CommonAdapter;
import com.example.chl.campusnews.SelectImage.Utils.ViewHolder;

import java.util.LinkedList;
import java.util.List;



public class MyAdapter extends CommonAdapter<String> {

    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public static List<String> mSelectedImage = new LinkedList<String>();
    /**
     * 文件夹路径
     */
    private String mDirPath;
    private Handler handler;
    private int num = 0;

    public MyAdapter(Context context, List<String> mDatas, int itemLayoutId,
                     String dirPath, Handler handler, int num) {
        super(context, mDatas, itemLayoutId);
        this.mDirPath = dirPath;
        this.handler = handler;
        this.num = num;
    }


    @Override
    public void convert(final ViewHolder helper, final String item) {
        //设置no_pic
        helper.setImageResource(R.id.id_item_image, R.mipmap.pictures_no);
        helper.setImageResource(R.id.id_item_select, R.mipmap.picture_unselected);
        helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

        final ImageView mImageView = helper.getView(R.id.id_item_image);
        final ImageView mSelect = helper.getView(R.id.id_item_select);

        mImageView.setColorFilter(null);
        mImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSelectedImage.contains(mDirPath + "/" + item)) {
                    mSelectedImage.remove(mDirPath + "/" + item);
                    mSelect.setImageResource(R.mipmap.picture_unselected);
                    mImageView.setColorFilter(null);
                    sendMsg(mSelectedImage.size());
                } else
                // 未选择该图片
                {
                    if (mSelectedImage.size() < 9-num) {
                        mSelectedImage.add(mDirPath + "/" + item);
                        mSelect.setImageResource(R.mipmap.pictures_selected);
                        mImageView.setColorFilter(Color.parseColor("#77000000"));
                        sendMsg(mSelectedImage.size());
                    } else {
                        Message msg = new Message();
                        msg.what = 2;
                        handler.sendMessage(msg);
                    }
                }
            }
        });

        if (mSelectedImage.contains(mDirPath + "/" + item)) {
            mSelect.setImageResource(R.mipmap.pictures_selected);
            mImageView.setColorFilter(Color.parseColor("#77000000"));
        }

    }

    public void sendMsg(int num) {
        Message msg = new Message();
        msg.obj = num;
        msg.what = 1;
        handler.sendMessage(msg);
    }
}
