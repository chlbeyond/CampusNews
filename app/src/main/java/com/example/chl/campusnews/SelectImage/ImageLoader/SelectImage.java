package com.example.chl.campusnews.SelectImage.ImageLoader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chl.campusnews.Http.UploadUtil;
import com.example.chl.campusnews.R;
import com.example.chl.campusnews.SelectImage.ImageFloder;
import com.example.chl.campusnews.Tool.ImageCompress;
import com.example.chl.campusnews.Util.Global;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public  class SelectImage extends Activity implements ListImageDirPopupWindow.OnImageDirSelected {
    private ProgressDialog mProgressDialog;
    private int mPicsSize;
    private File mImgDir;
    private List<String> mImgs;
    private GridView mGirdView;
    private MyAdapter mAdapter;
    private HashSet<String> mDirPaths = new HashSet<>();
    private List<ImageFloder> mImageFloders = new ArrayList<>();
    private RelativeLayout mBottomLy;
    private TextView mChooseDir;
    private TextView mImageCount;
    int totalCount = 0;
    private ImageView left_back;
    private int mScreenHeight;
    private int mun = 9;
    private ListImageDirPopupWindow mListImageDirPopupWindow;
    private Button img_num = null;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0) {
                mProgressDialog.dismiss();
                data2View();
                initListDirPopupWindw();
            }
            if (msg.what == 1) {
                img_num.setText("发送("+String.valueOf(num+Integer.parseInt(msg.obj.toString()))+"/9)");
            }
            if (msg.what == 2) {
                Toast.makeText(SelectImage.this, "已经选够九张啦", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_image);
        config();
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        img_num = (Button) findViewById(R.id.send);
        if(num!=0){img_num.setText("选择("+num+"/9)");}
        img_num.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                List<String> a = mAdapter.mSelectedImage;
                String image_list = "";
                for (int i = 0; i < a.size(); i++) {
                    image_list = image_list + a.get(i) + ",";
                }
                Intent intent = new Intent();
                intent.putExtra("result", image_list);
                intent.putExtra("num",num);
                SelectImage.this.setResult(Global.SELECTIMAGE, intent);
                SelectImage.this.finish();
            }
        });
        left_back = (ImageView) findViewById(R.id.left_back);
        left_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                SelectImage.this.finish();
            }
        });
        initView();
        getImages();
        initEvent();
    }

    private void config(){
        if(getIntent().getIntExtra("num",0)!=0){
            num = getIntent().getIntExtra("num",0);
        }
    }

    private void data2View() {
        if (mImgDir == null) {
            Toast.makeText(getApplicationContext(), "扫描结果：没有任何图片", Toast.LENGTH_SHORT).show();
            return;
        }
        mImgs = Arrays.asList(mImgDir.list());
        List<String> b = new ArrayList<>();
        for (int i = mImgs.size() - 1; i >= 0; i--) {
            b.add(mImgs.get(i));
        }

        mAdapter = new MyAdapter(getApplicationContext(), b,R.layout.grid_item, mImgDir.getAbsolutePath(), mHandler,num);
        mAdapter.mSelectedImage.clear();
        mGirdView.setAdapter(mAdapter);
        mImageCount.setText(totalCount + "张");
    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                LayoutParams.MATCH_PARENT, (int) (mScreenHeight * 0.7),
                mImageFloders, LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                String firstImage = null;
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = SelectImage.this
                        .getContentResolver();
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",//条件
                        new String[]{"image/jpeg", "image/png"},//条件值
                        MediaStore.Images.Media.DATE_MODIFIED);//order
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }

                    String list[] = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            return filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg");
                        }
                    });

                    int picSize;
                    if (list != null) {
                        picSize = list.length;
                    } else {
                        picSize = 0;
                    }
                    totalCount += picSize;
                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();
                mDirPaths = null;
                // 通知Handler扫描图片完成
                Message msg = new Message();
                msg.what = 0;
                mHandler.sendMessage(msg);

            }
        }).start();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);
        mImageCount = (TextView) findViewById(R.id.id_total_count);
        mBottomLy = (RelativeLayout) findViewById(R.id.id_bottom_ly);
    }

    private void initEvent() {
        mBottomLy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListImageDirPopupWindow.setAnimationStyle(R.style.anim_popup_dir);
                mListImageDirPopupWindow.showAsDropDown(mBottomLy, 0, 0);

                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = .3f;
                getWindow().setAttributes(lp);
            }
        });
    }

    @Override
    public void selected(ImageFloder floder) {
        if (num <= 9) {
        mImgDir = new File(floder.getDir());
        mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".jpg") || filename.endsWith(".png")
                        || filename.endsWith(".jpeg");
            }
        }));
        mAdapter = new MyAdapter(getApplicationContext(), mImgs,
                R.layout.grid_item, mImgDir.getAbsolutePath(), mHandler,num);
        mGirdView.setAdapter(mAdapter);
        mImageCount.setText(floder.getCount() + "张");
        mChooseDir.setText(floder.getName());
        mListImageDirPopupWindow.dismiss();
        } else {
            Toast.makeText(SelectImage.this, "已经选够九张啦", Toast.LENGTH_SHORT).show();
        }
    }

    public void makePost(final Context context, final String path, final String url, final String pic_name) {
        final ProgressDialog dialog;
        dialog = ProgressDialog.show(context, "上传中", "上传中...");
        mProgressDialog.dismiss();
        Thread d = new Thread() {
            @Override
            public void run() {
                UploadUtil uu = new UploadUtil();
                Map<String, String> params = new HashMap<>();
                params.put("pic_name", pic_name);
                String newPath = ImageCompress.compressBitmap(context, path,
                        Global.IMAGE_WIDTH, Global.IMAGE_HEIGHT,
                        Global.IMAGE_MAX_SIZE);
                File savePath = new File(newPath);
                try {
                    final String result = uu.doPost(url, params, savePath);
                    if (result.contains(".")) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                            }

                        });
                    } else {
                        ((Activity) context).runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                dialog.dismiss();
                            }

                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        d.start();
    }
}
