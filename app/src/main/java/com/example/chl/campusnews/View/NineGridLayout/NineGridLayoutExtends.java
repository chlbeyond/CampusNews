package com.example.chl.campusnews.View.NineGridLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 描述:
 * 作者：CHL
 * 时间：2017/11/23
 */
public class NineGridLayoutExtends extends NineGridLayout {

    protected static final int MAX_W_H_RATIO = 3;

    public NineGridLayoutExtends(Context context) {
        super(context);
    }

    public NineGridLayoutExtends(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {

        ImageLoaderUtil.displayImage(mContext, imageView, url, ImageLoaderUtil.getPhotoImageOption(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                int newW;
                int newH;
                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                    newW = parentWidth / 2;
                    newH = newW * 5 / 3;
                } else if (h < w) {//h:w = 2:3
                    newW = parentWidth * 2 / 3;
                    newH = newW * 2 / 3;
                } else {//newH:h = newW :w
                    newW = parentWidth / 2;
                    newH = h * newW / w;
                }
                setOneImageLayoutParams(imageView, newW, newH);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        ImageLoaderUtil.getImageLoader(mContext).displayImage(url, imageView, ImageLoaderUtil.getPhotoImageOption());
    }

//    @Override
//    protected void onClickImage(int i, String url, ShuoShuoInfo info) {
////        Toast.makeText(mContext, "点击了图片" + url, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(mContext, SingleInfoActivity.class);
//        intent.putExtra("shuoInfo", (Parcelable) info);
//        intent.putParcelableArrayListExtra("commentArray", info.getCommentArray());
//        if (mContext instanceof MultifunctionalActivity) {
//            ((MultifunctionalActivity)mContext).startActivityForResult(intent, Global.SINGLEINFOACTVITY);
//        } else if (mContext instanceof DetailActivity) {
//            ((DetailActivity)mContext).startActivityForResult(intent, Global.SINGLEINFOACTVITY);
//        }else if (mContext instanceof GroupLogActivity) {
//            ((GroupLogActivity)mContext).startActivityForResult(intent, Global.SINGLEINFOACTVITY);
//        }
//    }
}
