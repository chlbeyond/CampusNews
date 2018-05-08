package com.example.chl.campusnews.Http;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.chl.campusnews.Util.Global;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class DomainCross {
    private static OkHttpClient instance;
    private static final MediaType FORM_DATA = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
    private static final String USER_AGENT = "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
            + "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1";

    static OkHttpClient getInstance() {
        if (instance == null) {
            instance = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
        }
        return instance;
    }

    private static String makeSignature(List<NameValuePair> vps) throws NoSuchAlgorithmException {
        Map<String, String> paramMap = new HashMap<>();
        for (int i = 0; i < vps.size(); i++) {
            paramMap.put(vps.get(i).getName(), vps.get(i).getValue() == null ? "" : vps.get(i).getValue());
        }

        String[] keyArray = paramMap.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keyArray) {
            stringBuilder.append(key).append(paramMap.get(key));
        }
        stringBuilder.append(Global.MD5KEY);
        return Md5.getMD5(stringBuilder.toString());
    }

    public static String send(String url, List<NameValuePair> vps) {

        try {
            String sign = makeSignature(vps);
            System.out.println("册书"+sign);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String newUrl = url;
            String paramStr = "";
            OkHttpClient httpclient = getInstance();
            FormBody.Builder builder = new FormBody.Builder();
            for (int i = 0; i < vps.size(); i++) {
                paramStr += vps.get(i).getName()+":"+vps.get(i).getValue()+",";
                builder.add(vps.get(i).getName(), vps.get(i).getValue());
            }
            FormBody body = builder.build();
            Request request = new Request.Builder()
                .url(newUrl)
                .addHeader("User-Agent", USER_AGENT)
                .post(body)
                .build();
            try {
                Log.i("DomainCross.send:",  "##" + paramStr);
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

    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            //
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            //
            //conn.connect();
            //
            InputStream is = conn.getInputStream();
            //
            bitmap = BitmapFactory.decodeStream(is);
            //
            is.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap getImage(final String url) {
        return getHttpBitmap(url);
    }

    /*从远程地址读取html内容*/
    public static String getFile(String url) {
        OkHttpClient httpClient = getInstance();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*从本地读取文件内容*/
    public static String file_get_content(String filename, Context context) throws IOException {
        InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(filename));
        BufferedReader bufReader = new BufferedReader(inputReader);
        String line;
        String Result = "";
        while ((line = bufReader.readLine()) != null) Result += line;
        return Result;
    }
}
