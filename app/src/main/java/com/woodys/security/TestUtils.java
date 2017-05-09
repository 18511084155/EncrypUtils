package com.woodys.security;

import android.util.Base64;

import com.woodys.security.utils.SecurityHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by woodys on 2017/4/27.
 */

public class TestUtils {

    public void testMain() {
        String encryptTxt = "";
        String content = "hello我事oh一个my人god ad=1&carrier=中国联通&catid=0&channel_code=c1005&client_version=2.0.4&debug=1&device_id=358239057388623&device_type=2&from=2&id=5850912&phone_code=79c5991b7ed9ec53&phone_network=WIFI&phone_sim=1&uid=32451&uuid=6fc88c90db244116a02b5dbc638e2683&sign=582a6c46f16312446c4888c5a9bdcea5";

        String plainTxt = "Hello World!";
        try {
            System.out.println(plainTxt);
            encryptTxt = SecurityHelper.encryptDES("abc", plainTxt);
            plainTxt = SecurityHelper.decryptDES("abc", encryptTxt);
            System.out.println(encryptTxt.hashCode());
            System.out.println(plainTxt.hashCode());
            System.out.println(content.hashCode());
            System.out.println(encryptTxt);
            System.out.println(plainTxt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void test1() {
        String content = "hello我事oh一个my人god ad=1&carrier=中国联通&catid=0&channel_code=c1005&client_version=2.0.4&debug=1&device_id=358239057388623&device_type=2&from=2&id=5850912&phone_code=79c5991b7ed9ec53&phone_network=WIFI&phone_sim=1&uid=32451&uuid=6fc88c90db244116a02b5dbc638e2683&sign=582a6c46f16312446c4888c5a9bdcea5";
        String password = "12345678";
        // 加密
        System.out.println("加密前：" + content);
        String s = encrypt(content, password);
        System.out.println("加密后："+s);
        // 解密

        String s1 = decrypt(s, password);
        System.out.println("解密后：" +s1);

    }

    public void test2(){
        String tempString = "hello我事oh一个my人god ad=1&carrier=中国联通&catid=0&channel_code=c1005&client_version=2.0.4&debug=1&device_id=358239057388623&device_type=2&from=2&id=5850912&phone_code=79c5991b7ed9ec53&phone_network=WIFI&phone_sim=1&uid=32451&uuid=6fc88c90db244116a02b5dbc638e2683&sign=582a6c46f16312446c4888c5a9bdcea5";

        System.out.println("压缩前字符串内容："+tempString);
        System.out.println("压缩前字符串大小:"+tempString.length());

        String resultString = compactString(tempString);
        String resultString1 =new String(Base64.encode(resultString.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP));

        System.out.println("Base64.encode压缩后字符串内容："+resultString1);
        System.out.println("压缩后字符串内容："+resultString);
        System.out.println("压缩后字符串大小："+resultString.length());

        String convertString = decompressionString(resultString);

        String convertString1 =new String(Base64.decode(convertString.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP));

        System.out.println("Base64.decode解压后字符串内容："+convertString1);
        System.out.println("解压后字符串内容："+convertString);
        System.out.println("解压后字符串大小："+convertString.length());
    }


    public void test3(){
        try
        {
            String str = "hello我事oh一个my人god ad=1&carrier=中国联通&catid=0&channel_code=c1005&client_version=2.0.4&debug=1&device_id=358239057388623&device_type=2&from=2&id=5850912&phone_code=79c5991b7ed9ec53&phone_network=WIFI&phone_sim=1&uid=32451&uuid=6fc88c90db244116a02b5dbc638e2683&sign=582a6c46f16312446c4888c5a9bdcea5";

            String comp = compress(str);
            System.out.println(str.length());
            System.out.println("压缩以后："+comp);
            System.out.println(comp.length());
            String umComp = uncompress(comp);
            System.out.println("解缩以后："+umComp);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void test4(){
        String s = "hello我事oh一个my人god ad=1&carrier=中国联通&catid=0&channel_code=c1005&client_version=2.0.4&debug=1&device_id=358239057388623&device_type=2&from=2&id=5850912&phone_code=79c5991b7ed9ec53&phone_network=WIFI&phone_sim=1&uid=32451&uuid=6fc88c90db244116a02b5dbc638e2683&sign=582a6c46f16312446c4888c5a9bdcea5";

        System.out.println("原始：" + s);
        System.out.println("MD5后：" + string2MD5(s));
        System.out.println("加密的：" + convertMD5(s));
        System.out.println("解密的：" + convertMD5(convertMD5(s)));
    }



    public static String encrypt(String bef_aes, String password) {
        byte[] byteContent = null;
        try {
            byteContent = bef_aes.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encrypt(byteContent,password);
    }
    public static String encrypt(byte[] content, String password) {
        try {
            SecretKey secretKey = getKey(password);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            String aft_aes = parseByte2HexStr(result);
            return aft_aes; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String decrypt(String aft_aes, String password) {
        try {
            byte[] content = parseHexStr2Byte(aft_aes);
            SecretKey secretKey = getKey(password);
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            String bef_aes = new String(result);
            return bef_aes; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int value = Integer.parseInt(hexStr.substring(i*2, i*2+2), 16);
            result[i] = (byte)value;
        }
        return result;
    }
    public static SecretKey getKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            _generator.init(128,secureRandom);
            return _generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("初始化密钥出现异常");
        }
    }

    /**
     * 通过接口compactString()的压缩方式进行解压
     * @param tempString
     * @return
     */
    public static String decompressionString(String tempString){
        char[] tempBytes = tempString.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tempBytes.length; i++) {
            char c = tempBytes[i];
            char firstCharacter = (char) (c >>> 8);
            char secondCharacter = (char) ((byte)c);
            sb.append(firstCharacter);
            if(secondCharacter != 0)
                sb.append(secondCharacter);
        }
        return sb.toString();
    }


    /**
     * 对需要进行压缩的字符串进行压缩，返回一个相对较小的字符串
     * @param tempString
     * @return
     */
    public static String compactString(String tempString) {
        StringBuffer sb = new StringBuffer();
        byte[] tempBytes = tempString.getBytes();
        for (int i = 0; i < tempBytes.length; i+=2) {
            char firstCharacter = (char)tempBytes[i];
            char secondCharacter = 0;
            if(i+1<tempBytes.length)
                secondCharacter = (char)tempBytes[i+1];
            firstCharacter <<= 8;
            sb.append((char)(firstCharacter+secondCharacter));
        }
        return sb.toString();
    }



    // 压缩
    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1");
    }

    // 解压缩
    public static String uncompress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(str
                .getBytes("ISO-8859-1"));
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        return out.toString();
    }


    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++){
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr){

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }
}
