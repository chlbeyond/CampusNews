package com.example.chl.campusnews.Util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.chl.campusnews.Http.UploadUtil;
import com.example.chl.campusnews.Http.uploadCallBack;
import com.example.chl.campusnews.SelectImage.DialogCreator;
import com.example.chl.campusnews.Tool.ImageCompress;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 97475 on 2018/4/3.
 */

public class Global {

    public static String domainName = "http://time.schoolwo.cn";//远程域名
    public static String static_url = domainName;
    public static String imgUploadUrl = Global.static_url + "/app/uploadImage";


    public static final int IMAGE_CAMARA_ACTIVITY_CUT = 3;
    public static final int IMAGE_PHOTO_ACTIVITY = 5;
    public static final int IMAGE_CAMARA_ACTIVITY = 6;
    public static final int SELECTIMAGE = 7;


    public static int IMAGE_WIDTH = 1720;
    public static int IMAGE_HEIGHT = 2280;
    public static long IMAGE_MAX_SIZE = 512000;// 500KB


//    public static String MD5KEY = "CampusNews";
    public static String MD5KEY = "JiGaoEr";
    public static String current_image_url = "";
    public static String camera_image_url = null;
    public static String IMAGENUMBER = null;
    public static String USER_IMG = null;

    public static final String USERID = "5246";


    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static String getRunningActivityName(Activity activity) {
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

    /***
     * 单张上传图片
     */
    public static void upLoadImg(final Activity activity, final String path, final uploadCallBack callback) {
        final ArrayList photoArr = new ArrayList();
        if (path.equals("")) {

        } else {
            photoArr.add(path);
        }

        final Dialog uploadDialog = DialogCreator.createUpLoadingDialog(activity, "上传中，请稍后...");
        new Thread() {
            @Override
            public void run() {
                ArrayList uploadImagesUrl = new ArrayList();
                for (int i = 0; i < photoArr.size(); i++) {
                    uploadImagesUrl.add(Global.makePost(activity, uploadDialog, (String) photoArr.get(i), Global.imgUploadUrl));
                    System.out.println(photoArr);

                }
                if (uploadDialog != null) {
                    uploadDialog.dismiss();
                }
                if (uploadImagesUrl.size() >= 0) {
                    callback.callback(uploadImagesUrl);
                    System.out.println(uploadImagesUrl);

                } else {
                    GlobalMethod.toast("图片上传失败，请稍后重试");
                }
            }
        }.start();
    }

    public static ArrayList<String> makePost(final Context context, final Dialog dialog, final String path, final String url) {
        ArrayList<String> uploadImageUrl = new ArrayList();
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
        Map<String, String> params = new HashMap<>();
        params.put("user_id", Global.USERID);
        params.put("value", path);
        params.put("field", "user_img");
        String newPath = ImageCompress.compressBitmap(context, path, Global.IMAGE_WIDTH, Global.IMAGE_HEIGHT, Global.IMAGE_MAX_SIZE);
        File savePath = new File(newPath);
        try {
            String data = new UploadUtil().doPost(url, params, savePath);
            //
            JSONObject o = new JSONObject(data);
            String result = o.getString("data");

            if (result.contains(Global.static_url)) {
                uploadImageUrl.add(result);
            } else {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.hide();
                    }
                });
            }
        } catch (Exception e) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialog.hide();
                    GlobalMethod.toast("网络错误请重试");
                }
            });
        }
        return uploadImageUrl;
    }
}
