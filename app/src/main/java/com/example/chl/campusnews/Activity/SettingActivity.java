package com.example.chl.campusnews.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ImageView;

import com.example.chl.campusnews.Http.YueDongHttpPostCallback;
import com.example.chl.campusnews.Http.uploadCallBack;
import com.example.chl.campusnews.R;
import com.example.chl.campusnews.Tool.ServerAnswer;
import com.example.chl.campusnews.Util.Global;
import com.example.chl.campusnews.Util.GlobalMethod;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;

public class SettingActivity extends BaseActivity implements View.OnClickListener{

    private ImageView user_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        user_image = (ImageView) findViewById(R.id.user_image);
        user_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_image:
                GlobalMethod.SelectCategory(SettingActivity.this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Global.IMAGE_PHOTO_ACTIVITY) {
            if (data != null) {
                Uri selectedImg = data.getData();
                String userImg_url = Global.getRealFilePath(SettingActivity.this, selectedImg);
                Global.current_image_url = userImg_url;
                Global.USER_IMG = userImg_url;
//                crop();
//            }
//        } else if (requestCode == Global.IMAGE_CAMARA_ACTIVITY_CUT) {
                try {
//                Bitmap bitmap = data.getParcelableExtra("data");
//                Uri selectedImg = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
//                final String userImg_url = Global.getRealFilePath(this, selectedImg);
                    //上传图片
                    Global.upLoadImg(this, userImg_url, new uploadCallBack() {
                        @Override
                        public void callback(final ArrayList arrayList) {
                            SettingActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Global.USER_IMG = arrayList.get(0).toString().substring(1, arrayList.get(0).toString().length() - 1);
                                    updateUserImg(arrayList.get(0).toString().substring(1, arrayList.get(0).toString().length() - 1));
                                }
                            });
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (requestCode == Global.IMAGE_CAMARA_ACTIVITY) {
            Uri selectedImg = Uri.parse("file://" + Global.camera_image_url);
            String userImg_url = Global.getRealFilePath(SettingActivity.this, selectedImg);
            Global.current_image_url = userImg_url;
            Global.USER_IMG = userImg_url;
            try {
                //上传图片
                Global.upLoadImg(this, userImg_url, new uploadCallBack() {
                    @Override
                    public void callback(final ArrayList arrayList) {
                        SettingActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Global.USER_IMG = arrayList.get(0).toString().substring(1, arrayList.get(0).toString().length() - 1);
                                updateUserImg(arrayList.get(0).toString().substring(1, arrayList.get(0).toString().length() - 1));
                            }
                        });
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 剪切图片
     *
     * @function:
     * @author:Jerry
     * @date:2013-12-30
     */
    public void crop() {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        File file = new File(Global.current_image_url);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //通过FileProvider创建一个content类型的Uri
            Uri uri = FileProvider.getUriForFile(this, "com.example.chl.campusnews.takephoto.fileprovider", file);
            intent.setDataAndType(uri, "image/*");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "image/*");
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("back-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, Global.IMAGE_CAMARA_ACTIVITY_CUT);
    }

    //修改用户头像
    private void updateUserImg(final String img) {
        GlobalMethod.ModifyMyInfo(Global.USERID, img, "user_img", new YueDongHttpPostCallback() {
            @Override
            public void callBack(final String backJson) {
                SettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ServerAnswer json;
                        try {
                            json = new ServerAnswer(backJson);
                            if (json.result == 1) {
                                Global.USER_IMG = img;
                                GlobalMethod.toast("头像上传成功！");
                                GlobalMethod.setImageViewImg(user_image, Global.USER_IMG);
                            } else {
                                GlobalMethod.toast("头像上传失败！");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}