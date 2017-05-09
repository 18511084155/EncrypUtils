package com.woodys.security.utils;

import android.text.TextUtils;
import android.util.Pair;

import java.util.Random;

/**
 * Created by woodys on 16/3/30.
 */
public class RandomValidateCode {
    private static final char[] chars = {'0','1','2','3','4','5','6','7','8','9',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
            'r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H',
            'I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    /**
     * 获得一个随机的字符串 返回Color对象
     * @return
     */
    public static char getRandomCode() {
        Random ran = new Random();
        return chars[ran.nextInt(chars.length)];
    }


    /**
     * 获取干扰字符
     * @param source
     * @param c
     * @return
     */
    public static String getDisturbString(String source, char c) {
        Logger.e("getDisturbString:" + source +"   ："+c);
        int index=((int)c)%10%3;
        Random ran = null;
        String target= String.valueOf(c)+source;
        for (int i=0;i<index;i++){
            ran=new Random();
            target+=chars[ran.nextInt(chars.length)];
        }
        return target;
    }

    /**
     * 反向获取加密的randomCode和加密后的字符串
     * @param source
     * @return
     */
    public static Pair<Character,String> getDisturbKey(String source){
        Pair<Character, String> stringPair = null;
        if(!TextUtils.isEmpty(source)){
            char randomCode = source.charAt(0);
            int index = ((int)randomCode)%10%3;
            String encryptTxt = source.substring(1,source.length()-index);
            stringPair = new Pair<>(randomCode,encryptTxt);
        }
        return stringPair;
    }


}
