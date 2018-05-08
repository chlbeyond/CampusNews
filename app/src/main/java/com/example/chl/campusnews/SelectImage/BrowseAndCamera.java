package com.example.chl.campusnews.SelectImage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.example.chl.campusnews.SelectImage.ImageLoader.SelectImage;
import com.example.chl.campusnews.Util.Global;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BrowseAndCamera {
    private String directory = null;
    private String fileName = null;
    private Activity activity;
    private int browseRequestCode;
    private int cameraRequestCode;
    private int type;
    public int num;
    public void selectDialog(Activity activity, int browseRequestCode, int cameraRequestCode, int type) {
        this.type = type;
        this.activity = activity;
        this.browseRequestCode = browseRequestCode;
        this.cameraRequestCode = cameraRequestCode;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("").setItems(new String[]{"拍摄照片", "相册选择"}, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        openCamera();
                        break;
                    case 1:
                         browseImage();
                        break;
                }
            }
        }).show();
    }

    public String[] getCameraFilename() {
        return new String[]{directory, fileName};
    }

    public boolean browseImage() {
        if (type == 1) {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                Intent intent;
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                } else {
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                }

                this.activity.startActivityForResult(intent, this.browseRequestCode);

            } else {
                Toast.makeText(this.activity, "无法打开系统相册", Toast.LENGTH_SHORT).show();
            }
        } else if (type != 1){
            selectManyPic(type );
        }
        return true;
    }

    public void openCamera2() {
        Uri imageUri;//获取系统版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 判断存储卡是否可以用,可用进行存储

        if (hasSdcard()) {
            if (currentapiVersion < 24) {//7.0前面的版本
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (directory == null){
                    directory = CameraFile.getMyCameraCacheDir(this.activity);
                }
                fileName = CameraFile.createUniqueFileName(directory);
                Global.current_image_url = directory + fileName;
                Global.camera_image_url = directory + fileName;
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(directory + fileName)));
                this.activity.startActivityForResult(intent2, this.cameraRequestCode);
            }else{
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String filename = timeStampFormat.format(new Date());
                File tempFile = new File(Environment.getExternalStorageDirectory(),filename + ".jpg");

                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                fileName = CameraFile.createUniqueFileName( tempFile.getAbsolutePath());
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                Global.current_image_url = Global.getRealFilePath(activity,imageUri);
                activity.startActivityForResult(intent,this.cameraRequestCode);
            }
        }

    }


    //临时使用
    public void openCamera2(Activity content,int cameraRequestC) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (directory == null) {
            directory = CameraFile.getMyCameraCacheDir(content);
        }
        fileName = CameraFile.createUniqueFileName(directory);
        Global.current_image_url = directory + fileName;
        Global.camera_image_url = directory + fileName;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(directory + fileName)));
        content.startActivityForResult(intent,cameraRequestC);
    }

    public static void startActionCapture(Activity activity, File file, int requestCode) {
        if (activity == null) {
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(activity, file));
        activity.startActivityForResult(intent, requestCode);
    }

    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "你的应用名.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public boolean browseImage2(Activity ac,int cameraRequestC) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Intent intent;
            if (Build.VERSION.SDK_INT < 19) {
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
            } else {
                intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            }
            ac.startActivityForResult(intent,cameraRequestC);
        } else {
            Toast.makeText(ac, "无法打开系统相册", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    public void selectManyPic(int type){
        Intent intent = new Intent();
        intent.setClass(activity, SelectImage.class);
        if(Global.getRunningActivityName(activity).equals("narrowandenlarge.mapannals.Activity.SendNewsActivity")){
            intent.putExtra("num",num);
            Global.IMAGENUMBER=String.valueOf(num);
        }
        activity.startActivityForResult(intent,type);
    }


    public void openCamera3(Activity content,int cameraRequestC) {
        Uri imageUri;//获取系统版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 判断存储卡是否可以用,可用进行存储

        if (hasSdcard()) {
            if (currentapiVersion < 24) {//7.0前面的版本
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (directory == null){
                    directory = CameraFile.getMyCameraCacheDir(content);
                }
                fileName = CameraFile.createUniqueFileName(directory);
                Global.current_image_url = directory + fileName;
                Global.camera_image_url = directory + fileName;
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(directory + fileName)));
                content.startActivityForResult(intent2,cameraRequestC);
            }else{
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String filename = timeStampFormat.format(new Date());
                File tempFile = new File(Environment.getExternalStorageDirectory(),filename + ".jpg");

                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                fileName = CameraFile.createUniqueFileName( tempFile.getAbsolutePath());
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                imageUri = content.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                Global.current_image_url = Global.getRealFilePath(content,imageUri);
                content.startActivityForResult(intent,cameraRequestC);
            }
        }
    }

    /*
     * 判断sdcard是否被挂载
     */
    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public void openCamera() {
        Uri imageUri;//获取系统版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 判断存储卡是否可以用,可用进行存储

        if (hasSdcard()) {
            if (currentapiVersion < 24) {//7.0前面的版本
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (directory == null){
                    directory = CameraFile.getMyCameraCacheDir(this.activity);
                }
                fileName = CameraFile.createUniqueFileName(directory);
                Global.current_image_url = directory + fileName;
                Global.camera_image_url = directory + fileName;
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(directory + fileName)));
                this.activity.startActivityForResult(intent2, this.cameraRequestCode);
            }else{
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
                String filename = timeStampFormat.format(new Date());
                File tempFile = new File(Environment.getExternalStorageDirectory(),filename + ".jpg");

                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                fileName = CameraFile.createUniqueFileName( tempFile.getAbsolutePath());
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                imageUri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                Global.current_image_url = Global.getRealFilePath(activity,imageUri);
                Global.camera_image_url = Global.getRealFilePath(activity,imageUri);
                activity.startActivityForResult(intent,this.cameraRequestCode);
            }
        }
    }


    /**
     * 鐩告満鎷嶇収鍥剧墖瀛樻斁浣嶇疆
     *
     * @author hydro
     */
    public static class CameraFile {
        /**
         * 鍒涘缓鍞竴鏂囦欢鍚�
         *
         * @param dir
         * @return
         */
        public static String createUniqueFileName(String dir) {
            String filename = String.valueOf(System.currentTimeMillis())
                    + "_" + Math.round(Math.random() * 1000) + ".jpg";
            File file = new File(dir + filename);
            while (file.exists()) {
                filename = String.valueOf(System.currentTimeMillis())
                        + "_" + Math.round(Math.random() * 1000) + ".jpg";
                file = new File(dir + filename);
            }
            return filename;
        }

        /**
         * 鐓х墖瀛樻斁璺緞
         *
         * @param context
         * @return
         */
        public static String getMyCameraCacheDir(Context context) {
            String dir = getCacheDir(context) + "camera" + File.separator;
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            return dir;
        }

        private static String getCacheDir(Context context) {
            String path;
            File file = context.getExternalCacheDir();
            if (file != null) {
                path = file.getPath() + File.separator;
            } else {
                file = context.getCacheDir();
                path = file.getPath();
            }
            return path;
        }
    }
    public void getImagesNum(int imageNum){
        this.num =imageNum;
    }
}
