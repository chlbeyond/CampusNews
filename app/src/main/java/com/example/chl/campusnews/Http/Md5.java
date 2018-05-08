package com.example.chl.campusnews.Http;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
* @author Administrator
* @Description	对外提供md5加密
*/
public class Md5 {
    /*
     * @param 源字符串
     * @back 目标加密后字符串
     */
    public static String getMD5(String sourceStr) throws NoSuchAlgorithmException {
        String result;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(sourceStr.getBytes());
        byte b[] = md.digest();
        int i;
        StringBuilder buf = new StringBuilder("");
        for (byte aB : b) {
            i = aB;
            if (i < 0) i += 256;
            if (i < 16) buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        result = buf.toString();
        return result;
    }
}