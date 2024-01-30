package com.mio.mi_ads;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.mio.mi_ads.encourage.EncourageVideoAd;
import com.mio.mi_ads.screenvideoad.ScreenVideoAd;
import com.mio.mi_ads.splashad.SplashActivity;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * MiAdsPlugin
 */
public class MiAdsPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private static final String TAG = "MiAdsPlugin";

    private MethodChannel channel;

    private FlutterActivity mActivity;
    private Context mContext;

    private FlutterPluginBinding mFlutterPluginBinding;

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        this.mActivity = (FlutterActivity) binding.getActivity();
        FlutterAdViewPlugin.registerWith(mFlutterPluginBinding, mActivity);
        ///初始化激励视频
        EncourageVideoAd.getInstance().init(mActivity, mFlutterPluginBinding.getBinaryMessenger());
        //初始化插屏
        ScreenVideoAd.getInstance().init(mActivity, mFlutterPluginBinding.getBinaryMessenger());
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        mActivity = (FlutterActivity) binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {
        mActivity = null;
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        mFlutterPluginBinding = flutterPluginBinding;
        mContext = flutterPluginBinding.getApplicationContext();
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "mi_ads");
        channel.setMethodCallHandler(this);
        Log.d(TAG, "onAttachedToEngine = " + mActivity);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case "initSDK":
                MimoSDKManager.initSDK(mContext, channel); //状态回调初始化成功，初始化失败
                result.success(null);
                break;
            case "setDebugOn":
                MimoSDKManager.setDebugOn((Boolean) call.arguments);
                result.success(null);
                break;
            case "setPersonalizedAd":
                MimoSDKManager.setPersonalizedAdEnabled((Boolean) call.arguments); //个性化广告开关
                result.success(null);
                break;
            case "isInitSuccess":
                result.success(MimoSDKManager.isInitSuccess());
                break;
            case "isHasInit":
                result.success(MimoSDKManager.isHasInit());
                break;
            case "showSplashAd":
                final String codeId = (String) call.arguments;
                Intent intent = new Intent();
                intent.setClass(mContext, SplashActivity.class);
                SplashActivity.setMessenger(mFlutterPluginBinding.getBinaryMessenger());
                Bundle bundle = new Bundle();
                bundle.putString("codeId", codeId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }


}
