package com.woodys.security.utils;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by woodys on 2017/4/27.
 */

public class NetUtils {
    private static Context appContext;
    static {
        appContext = getContext();
    }

    public static Context getContext() {
        if (appContext == null) {
            try {
                Application application=(Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
                appContext = application.getApplicationContext();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return appContext;
    }

    /**
     * 更具参数拼接加密url字符串(针对参数加密)
     * <p>
     * 根据params参数对,获取请求url(对参数进行encode处理)
     *
     * @param params
     * @return
     */
    public static String getParamsByRequestUrl(List<Pair<String, String>> params) {
        char randomCode = RandomValidateCode.getRandomCode();
        String url = "";
        if (null != params && !params.isEmpty()) {
            int size = params.size();
            for (int i = 0; i < size; i++) {
                Pair<String, String> pair = params.get(i);
                url += ((i > 0 ? "&" : "") + pair.first + "=" + NetUtils.getUrlParamEncode(pair.second));
            }
        }
        return RandomValidateCode.getDisturbString(!TextUtils.isEmpty(url) ? SecurityHelper.encryptDES(PubKeySignature.getSingInfo(getContext(), randomCode), url) : "", randomCode);
    }

    /**
     * 对url中的参数进行处理
     *
     * @param param
     * @return
     */
    public static String getUrlParamEncode(String param) {
        String encodeParam = null;
        if (!TextUtils.isEmpty(param)) {
            encodeParam = URLEncoder.encode(param);
        }
        return encodeParam;
    }

}
