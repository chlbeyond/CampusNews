package com.example.chl.campusnews.Http;

import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UploadUtil {
    private static final String TAG = "UploadUtil";
    private static final String USER_AGENT = "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
            + "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1";

    /**
     * POST
     *
     * @param url   文件上传提交地址
     * @param param 其他参数
     * @return 上传结果
     * @throws Exception
     */
    public static String doPost(String url, Map<String, Object> param) throws Exception {
        OkHttpClient httpclient = DomainCross.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        if (param != null && param.size() > 0) {
            Set<String> keys = param.keySet();
            for (Object o : keys) {
                String key = (String) o;
                builder.add(key, String.valueOf(param.get(key)));
            }
        }
        FormBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", USER_AGENT)
                .post(body)
                .build();

        try {
            Response response = httpclient.newCall(request).execute();
            if (response.isSuccessful()) {
                String str = response.body().string();
                Log.i("DomainCross.response:", url + "##" + str);
                return str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param url
     * @param param
     * @param file
     * @return
     * @throws Exception
     */
    public String doPost(String url, Map<String, String> param, File file) throws Exception {
        OkHttpClient httpclient = DomainCross.getInstance();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        if (param != null && !param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        if (file != null && file.exists()) {
            builder.addFormDataPart("uploadedfile", file.getName(),
                    RequestBody.create(MediaType.parse(getFileMimeType(file)), file));
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", USER_AGENT)
                .post(body)
                .build();

        try {
            Response response = httpclient.newCall(request).execute();
            if (response.isSuccessful()) {
                String str = response.body().string();
                Log.i("DomainCross.response:", url + "##" + str);
                return str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getFileMimeType(File file) {
        String mime = "application/octet-stream";
        String fileName, suffix, type;
        int index;

        if (file != null && file.exists() && !file.isDirectory()) {
            fileName = file.getName();
            if (!fileName.equals("") && !fileName.endsWith(".")) {
                index = fileName.lastIndexOf(".");
                if (index != -1) {
                    suffix = fileName.substring(index + 1).toLowerCase(Locale.US);
                    type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
                    if (type != null && !type.isEmpty()) {
                        mime = type;
                    }
                }
            }
        }
        return mime;
    }

    public static String doGet(String url) {
        OkHttpClient httpclient = DomainCross.getInstance();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = httpclient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
