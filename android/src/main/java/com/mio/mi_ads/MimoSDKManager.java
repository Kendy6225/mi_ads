package com.mio.mi_ads;

import android.content.Context;
import android.util.Log;

import com.miui.zeus.mimo.sdk.MimoSdk;
import com.miui.zeus.mimo.sdk.server.MimoAdServer;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodChannel;

public class MimoSDKManager {
    public static final String TAG = "MimoSDKManager";
    public static MimoSDKManager instance;

    private static boolean isDebugOn = false;

    private MimoSDKManager() {
        instance = new MimoSDKManager();
    }

    public static synchronized MimoSDKManager getInstance() {
        if (instance == null)
            instance = new MimoSDKManager();
        return instance;
    }

    public static void initSDK(Context context, MethodChannel channel) {
        MimoSdk.init(context, new MimoSdk.InitCallback() {
            @Override
            public void success() {
                Log.d(TAG, "initSuccess");
                channel.invokeMethod("onInitSuccess", null);
            }

            @Override
            public void fail(int code, String msg) {
                //4000 本地执行API错误导致初始化错误码
                //5000 传入context为null
                Map map = new HashMap();
                map.put("code", code);
                map.put("msg", msg);
                Log.d(TAG, "initFailed code = " + code + " msg = " + msg);
                channel.invokeMethod("onInitFailed", map);
            }
        });
        setDebugOn(false);
    }

    public static void setPersonalizedAdEnabled(boolean enable) {
        MimoSdk.setPersonalizedAdEnabled(enable);//true 开启，false 关闭
    }

    public static void setDebugOn(boolean enable) {
        isDebugOn = enable;
        MimoSdk.setDebugOn(enable);
    }

    public static boolean isInitSuccess() {
        return MimoSdk.isInitSuccess();
    }

    public static boolean isHasInit() {
        return MimoSdk.isHasInit();
    }

    public static void log(String TAG, String msg) {
        if (true) {
            Log.d(TAG, msg);
        }
    }
}
