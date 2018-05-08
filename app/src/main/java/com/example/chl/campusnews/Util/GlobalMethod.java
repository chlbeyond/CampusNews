package com.example.chl.campusnews.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chl.campusnews.Http.DomainCross;
import com.example.chl.campusnews.Http.NameValuePair;
import com.example.chl.campusnews.Http.YueDongHttpPostCallback;
import com.example.chl.campusnews.MyApplication;
import com.example.chl.campusnews.R;
import com.example.chl.campusnews.SelectImage.BrowseAndCamera;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by chl on 2018/3/19.
 */

public class GlobalMethod {

    private static BrowseAndCamera browseAndCamera;

    /**
     * 使继承AppCompatActivity的Activity的状态栏沉浸式
     */
    public static void initState(AppCompatActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 全局吐司
     */
    public static void toast(String text) {
        Toast.makeText(MyApplication.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断手机号
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(147)|(15[^4,\\D])|(17[0-9])|(18[1-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static void SelectCategory(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermissionCamera = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
            int checkCallPhonePermissionR = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            int checkCallPhonePermissionW = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermissionCamera != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 0);
            }
            if (checkCallPhonePermissionR != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
            if (checkCallPhonePermissionW != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
            changeUserImage(activity);
        } else {
            changeUserImage(activity);
        }
    }

    public static void changeUserImage(Activity activity) {
        if (browseAndCamera == null) {
            browseAndCamera = new BrowseAndCamera();
        }
        browseAndCamera.selectDialog(activity, Global.IMAGE_PHOTO_ACTIVITY, Global.IMAGE_CAMARA_ACTIVITY, 1);
    }

    /**
     * 修改我的设置（修改名字，头像、、、等）
     *
     * @param user_id
     * @param field
     * @param callback
     */
    public static void ModifyMyInfo(final String user_id, final String value, final String field, final YueDongHttpPostCallback callback) {
        new Thread() {
            @Override
            public void run() {
                List<NameValuePair> send = new ArrayList<>();
                send.add(new NameValuePair("user_id", user_id));
                send.add(new NameValuePair("value", value));
                send.add(new NameValuePair("field", field));
                String response = DomainCross.send(Global.domainName + "/app/ModifyMyInfo", send);
                callback.callBack(response);
            }
        }.start();
    }

    public static void setImageViewImg(ImageView imageView, String imgStr) {
        Context context = MyApplication.getContext();
        imageView.setTag(null);
        Glide.with(context)
                .load(imgStr)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.mipmap.gavatar_circle)
                .error(R.mipmap.gavatar_circle)
                .into(imageView);
    }

}
