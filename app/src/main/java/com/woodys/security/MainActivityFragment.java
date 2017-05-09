package com.woodys.security;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.woodys.security.utils.NetUtils;
import com.woodys.security.utils.PubKeySignature;
import com.woodys.security.utils.RandomValidateCode;
import com.woodys.security.utils.SecurityHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //简单加解密演示，前期准备的key和内容
        String key = "abc12344";
        String content = "hello我事oh一个my人god ad=1&carrier=中国联通&catid=0&channel_code=c1005&client_version=2.0.4&debug=1&device_id=358239057388623&device_type=2&from=2&id=5850912&phone_code=79c5991b7ed9ec53&phone_network=WIFI&phone_sim=1&uid=32451&uuid=6fc88c90db244116a02b5dbc638e2683&sign=582a6c46f16312446c4888c5a9bdcea5";
        ((TextView)view.findViewById(R.id.tv_key)).setText(getResources().getString(R.string.key,key));
        ((TextView)view.findViewById(R.id.tv_content)).setText(getResources().getString(R.string.content,content));



        //简单加密演示
        long s1 = System.currentTimeMillis();
        String encryptTxt = SecurityHelper.encryptDES(key, content);
        ((TextView)view.findViewById(R.id.tv_encrypt_title)).setText(getResources().getString(R.string.encrypt_title,String.valueOf(System.currentTimeMillis()-s1)));
        ((TextView)view.findViewById(R.id.tv_encryptTxt)).setText(encryptTxt);

        //简单解密演示
        long s2 = System.currentTimeMillis();
        String decryptTxt = SecurityHelper.decryptDES(key, encryptTxt);
        ((TextView)view.findViewById(R.id.tv_decrypt_title)).setText(getResources().getString(R.string.decrypt_title,String.valueOf(System.currentTimeMillis()-s2)));
        ((TextView)view.findViewById(R.id.tv_decryptTxt)).setText(decryptTxt);

        List<Pair<String, String>> paramsPairs = new ArrayList<>();
        paramsPairs.add(new Pair("access","3G"));//网络状态 3G/4G...
        paramsPairs.add(new Pair("app-version", "1.0.0"));//应用版本号
        paramsPairs.add(new Pair("device-platform", "android"));//设置系统名称
        paramsPairs.add(new Pair("os-version", NetUtils.getUrlParamEncode(android.os.Build.DISPLAY)));//系统版本号
        paramsPairs.add(new Pair("os-api", String.valueOf(Build.VERSION.SDK_INT)));//系统初始化id
        paramsPairs.add(new Pair("device-model", NetUtils.getUrlParamEncode(Build.MODEL)));


        //-----------------进阶加密（带有混淆干扰码的加密）---------------------

        long s3 = System.currentTimeMillis();
        //获取带有混淆字段的加密串
        String encryptParamsByUrl = NetUtils.getParamsByRequestUrl(paramsPairs);
        ((TextView)view.findViewById(R.id.tv_encrypt_title1)).setText(getResources().getString(R.string.encrypt_title,String.valueOf(System.currentTimeMillis()-s3)));
        ((TextView)view.findViewById(R.id.tv_encryptTxt1)).setText(encryptParamsByUrl);

        //解码过程
        long s4 = System.currentTimeMillis();

        Pair<Character, String> disturbKey = RandomValidateCode.getDisturbKey(encryptParamsByUrl);
        //> 获取干扰字符
        char randomCode = disturbKey.first;
        ((TextView)view.findViewById(R.id.tv_randomCode)).setText(getResources().getString(R.string.random_code,String.valueOf(randomCode)));
        //> 获取去除干扰码后的真实加密串
        String encryptText = disturbKey.second;
        ((TextView)view.findViewById(R.id.tv_encryptText)).setText(getResources().getString(R.string.encrypt_text,encryptText));
        //> 获取证书签名的部分信息作为动态的public_key
        String public_key = PubKeySignature.getSingInfo(NetUtils.getContext(),randomCode);
        ((TextView)view.findViewById(R.id.tv_public_key)).setText(getResources().getString(R.string.public_key,public_key));
        //> 解密后字符串
        String decryptParamsByUrl = SecurityHelper.decryptDES(public_key,encryptText);
        ((TextView)view.findViewById(R.id.tv_decrypt_title1)).setText(getResources().getString(R.string.decrypt_title,String.valueOf(System.currentTimeMillis()-s4)));
        ((TextView)view.findViewById(R.id.tv_decryptTxt1)).setText(getResources().getString(R.string.content,decryptParamsByUrl));

        //这里为了图省事，就直接把解密后的原串
        ((TextView)view.findViewById(R.id.tv_content1)).setText(decryptParamsByUrl);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
