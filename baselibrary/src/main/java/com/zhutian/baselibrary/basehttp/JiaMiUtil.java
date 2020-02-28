package com.zhutian.baselibrary.basehttp;

import android.annotation.SuppressLint;

import com.facebook.common.util.Hex;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JiaMiUtil {
    /**
     * 定义加密方式
     * MAC算法可选以下多种算法
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    private static final String KEY_MAC = "HmacMD5";

    private static final String KEY_MAC_SHA1 = "HmacSHA1";

    private static final String CHARSET_UTF8 = "UTF-8";


    /**
     * 使用 HmacSHA1 加密
     * @param encryptText
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String hmacSha1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] text = encryptText.getBytes(CHARSET_UTF8);
        byte[] keyData = encryptKey.getBytes(CHARSET_UTF8);

        SecretKeySpec secretKey = new SecretKeySpec(keyData, KEY_MAC_SHA1);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return byte2hex(mac.doFinal(text));
    }
    //二行制转字符串
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }


    @SuppressLint("NewApi")
    final static Base64.Encoder encoder = Base64.getEncoder();
    @SuppressLint("NewApi")
    final static Base64.Decoder decoder = Base64.getDecoder();
    @SuppressLint("NewApi")
    public static String encodeBase64(String text) {
//        byte[] textByte = text.getBytes(StandardCharsets.UTF_8);
//        String encodedText = encoder.encodeToString(textByte);
//        return encodedText;
        return encoder.encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }



    /** 16进制的字符串数组 */
    private final static String[] hexDigitsStrings = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    /** 16进制的字符集 */
    private final static char [] hexDigitsChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * MD5加密字符串
     *
     * @param source 源字符串
     *
     * @return 加密后的字符串
     *
     */
    public static String getMD5(String source) {
        String mdString = null;
        if (source != null) {
            try {
                mdString = getMD5(source.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return mdString;
    }

    /**
     * MD5加密以byte数组表示的字符串
     *
     * @param source 源字节数组
     *
     * @return 加密后的字符串
     */
    public static String getMD5(byte[] source) {
        String s = null;

        final int temp = 0xf;
        final int arraySize = 32;
        final int strLen = 16;
        final int offset = 4;
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            md.update(source);
            byte [] tmp = md.digest();
            char [] str = new char[arraySize];
            int k = 0;
            for (int i = 0; i < strLen; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigitsChar[byte0 >>> offset & temp];
                str[k++] = hexDigitsChar[byte0 & temp];
            }
            s = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}
