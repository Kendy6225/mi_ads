package com.mio.mi_ads.screenvideoad;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.mio.mi_ads.FlutterAdPluginConfig;
import com.mio.mi_ads.MimoSDKManager;
import com.miui.zeus.mimo.sdk.InterstitialAd;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * 插屏广告
 */
public class ScreenVideoAd implements MethodChannel.MethodCallHandler {

    private static final String TAG = "MimoSDKManager-ScreenVideoAd";

    private Activity activity;

    private MethodChannel mChannel;

    private InterstitialAd mAd;

    private static ScreenVideoAd instance;

    private ScreenVideoAd() {
    }

    public static synchronized ScreenVideoAd getInstance() {
        if (instance == null) {
            instance = new ScreenVideoAd();
        }
        return instance;
    }

    public void init(Activity activity, BinaryMessenger messenger) {
        this.activity = activity;
        this.mChannel = new MethodChannel(messenger, FlutterAdPluginConfig.ID_SCREEN_VIDEO_AD);
        this.mChannel.setMethodCallHandler(this);
    }

    private void loadAd(String codeId) {
        this.mAd = new InterstitialAd();
        mAd.loadAd(codeId, new InterstitialAd.InterstitialAdLoadListener() {

            @Override
            public void onAdRequestSuccess() {
                //广告请求成功
                MimoSDKManager.log(TAG, "onAdRequestSuccess");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdRequestSuccess", null));
            }

            @Override
            public void onAdLoadSuccess() {
                //广告加载（缓存）成功，在需要的时候在此处展示广告
                MimoSDKManager.log(TAG, "onAdLoadSuccess");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdLoadSuccess", null));
            }

            @Override
            public void onAdLoadFailed(int errorCode, String errorMsg) {
                // 请求加载失败
                activity.runOnUiThread(() -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", errorCode);
                    map.put("msg", errorMsg);
                    mChannel.invokeMethod("onAdLoadFailed", map);
                });
                MimoSDKManager.log(TAG, "onAdLoadFailed code = " + errorCode + " msg = " + errorMsg);
            }
        });
    }

    public void showAd() {
        mAd.show(activity, new InterstitialAd.InterstitialAdInteractionListener() {

            @Override
            public void onAdClick() {
                // 广告被点击
                mChannel.invokeMethod("onAdClick", null);
                MimoSDKManager.log(TAG, "onAdClick");
            }

            @Override
            public void onAdShow() {
                // 广告展示
                mChannel.invokeMethod("onAdShow", null);
                MimoSDKManager.log(TAG, "onAdShow");
            }

            @Override
            public void onAdClosed() {
                // 广告关闭
                mChannel.invokeMethod("onAdClosed", null);
                MimoSDKManager.log(TAG, "onAdClosed");
            }

            @Override
            public void onRenderFail(int errorCode, String errorMsg) {
                // 广告渲染失败
                Map<String, Object> map = new HashMap<>();
                map.put("code", errorCode);
                map.put("msg", errorMsg);
                mChannel.invokeMethod("onAdLoadFailed", map);
                MimoSDKManager.log(TAG, "onAdLoadFailed code = " + errorCode + " msg = " + errorMsg);
            }

            public void onVideoStart() {
                //视频开始播放
                mChannel.invokeMethod("onVideoStart", null);
                MimoSDKManager.log(TAG, "onVideoStart");
            }

            @Override
            public void onVideoPause() {
                //视频暂停
                mChannel.invokeMethod("onVideoPause", null);
                MimoSDKManager.log(TAG, "onVideoPause");
            }

            @Override
            public void onVideoResume() {
                //视频继续播放;
                mChannel.invokeMethod("onVideoResume", null);
                MimoSDKManager.log(TAG, "onVideoResume");
            }

            @Override
            public void onVideoEnd() {
                //视频播放结束;
                mChannel.invokeMethod("onVideoComplete", null);
                MimoSDKManager.log(TAG, "onVideoComplete");
            }

        });

        mAd.setDownloadListener(new InterstitialAd.InterstitialDownloadListener() {

            @Override
            public void onDownloadStarted() {
                //开始下载
                mChannel.invokeMethod("onDownloadStarted", null);
                MimoSDKManager.log(TAG, "onDownloadStarted");
            }

            @Override
            public void onDownloadProgressUpdated(int progress) {
                //下载进度，例如：${progress}%
                mChannel.invokeMethod("onDownloadProgressUpdated", progress);
                MimoSDKManager.log(TAG, "onDownloadProgressUpdated progress = " + progress);
            }

            @Override
            public void onDownloadPaused() {
                //下载暂停
                mChannel.invokeMethod("onDownloadPaused", null);
                MimoSDKManager.log(TAG, "onDownloadPaused");
            }

            @Override
            public void onDownloadCancel() {
                //取消下载
                mChannel.invokeMethod("onDownloadCancel", null);
                MimoSDKManager.log(TAG, "onDownloadCancel");
            }

            @Override
            public void onDownloadFailed(int errorCode) {
                //下载失败， 若需要了解errorCode具体含义，请咨询米盟
                mChannel.invokeMethod("onDownloadFailed", errorCode);
                MimoSDKManager.log(TAG, "onDownloadFailed code = " + errorCode);
            }

            @Override
            public void onDownloadFinished() {
                //下载结束
                mChannel.invokeMethod("onDownloadFinished", null);
                MimoSDKManager.log(TAG, "onDownloadFinished");
            }

            @Override
            public void onInstallStart() {
                //开始安装
                mChannel.invokeMethod("onInstallStart", null);
                MimoSDKManager.log(TAG, "onInstallStart");
            }

            @Override
            public void onInstallFailed(int errorCode) {
                //安装失败
                mChannel.invokeMethod("onInstallFailed", errorCode);
                MimoSDKManager.log(TAG, "onInstallFailed code = " + errorCode);
            }

            @Override
            public void onInstallSuccess() {
                //安装成功
                mChannel.invokeMethod("onInstallSuccess", null);
                MimoSDKManager.log(TAG, "onInstallSuccess");
            }
        });

    }

    public void dispose() {
        if (mAd != null) {
            mAd.destroy();
            mAd = null;
        }
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        switch (call.method) {
            case "loadAd":
                loadAd((String) call.arguments);
                break;
            case "showAd":
                showAd();
                break;
            case "dispose":
                dispose();
                break;
        }
    }
}
