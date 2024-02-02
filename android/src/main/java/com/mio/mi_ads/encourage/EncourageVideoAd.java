package com.mio.mi_ads.encourage;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.mio.mi_ads.FlutterAdPluginConfig;
import com.mio.mi_ads.MimoSDKManager;
import com.miui.zeus.mimo.sdk.RewardVideoAd;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * 激励视频
 */
public class EncourageVideoAd implements MethodChannel.MethodCallHandler {

    private static final String TAG = "MimoSDKManager-EncourageVideoAd";

    private Activity activity;

    //广告参数
    private MethodChannel mChannel;

    private RewardVideoAd mAd;

    private static EncourageVideoAd instance;

    private EncourageVideoAd() {
    }

    public static synchronized EncourageVideoAd getInstance() {
        if (instance == null) {
            instance = new EncourageVideoAd();
        }
        return instance;
    }

    public void init(Activity activity, BinaryMessenger messenger) {
        this.activity = activity;
        this.mChannel = new MethodChannel(messenger, FlutterAdPluginConfig.ID_ENCOURAGE_AD);
        this.mChannel.setMethodCallHandler(this);
    }

    private void loadAd(String codeId) {
        this.mAd = new RewardVideoAd();
        mAd.loadAd(codeId, new RewardVideoAd.RewardVideoLoadListener() {

            @Override
            public void onAdRequestSuccess() {
                //广告请求成功
                MimoSDKManager.log(TAG, "onAdRequestSuccess");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdRequestSuccess", null));
            }

            @Override
            public void onAdLoadSuccess() {
                //广告加载（缓存）成功
                MimoSDKManager.log(TAG, "onAdLoadSuccess");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdLoadSuccess", null));
            }

            @Override
            public void onAdLoadFailed(int errorCode, String errorMsg) {
                //广告加载失败
                MimoSDKManager.log(TAG, "onAdLoadFailed code = " + errorCode + " msg = " + errorMsg);
                activity.runOnUiThread(() -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", errorCode);
                    map.put("msg", errorMsg);
                    mChannel.invokeMethod("onAdLoadFailed", map);
                });
            }

        });
    }

    private void showAd() {
        mAd.showAd(activity, new RewardVideoAd.RewardVideoInteractionListener() {

            @Override
            public void onAdPresent() {
                // 广告被曝光
                MimoSDKManager.log(TAG, "onAdPresent");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdShow", null));
            }

            @Override
            public void onAdClick() {
                // 广告被点击
                MimoSDKManager.log(TAG, "onAdClick");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdClick", null));
            }

            @Override
            public void onAdDismissed() {
                // 广告消失
                MimoSDKManager.log(TAG, "onAdClosed");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdClosed", null));
            }

            @Override
            public void onAdFailed(String errorMsg) {
                // 渲染失败
                MimoSDKManager.log(TAG, "onAdFailed msg = " + errorMsg);
                activity.runOnUiThread(() -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 1);
                    map.put("msg", errorMsg);
                    mChannel.invokeMethod("onAdLoadFailed", map);
                });
            }

            @Override
            public void onVideoStart() {
                //视频开始播放
                MimoSDKManager.log(TAG, "onVideoStart");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onVideoStart", null));
            }

            @Override
            public void onVideoPause() {
                //视频暂停
                MimoSDKManager.log(TAG, "onVideoPause");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onVideoPause", null));
            }

            @Override
            public void onVideoSkip() {
                //跳过视频播放
                MimoSDKManager.log(TAG, "onVideoSkip");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onVideoSkip", null));
            }

            @Override
            public void onVideoComplete() {
                // 视频播放完成
                MimoSDKManager.log(TAG, "onVideoComplete");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onVideoComplete", null));
            }

            @Override
            public void onPicAdEnd() {
                //图片类型广告播放完成
                MimoSDKManager.log(TAG, "onPicAdEnd");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onPicAdEnd", null));
            }

            @Override
            public void onReward() {
                //激励回调
                MimoSDKManager.log(TAG, "onReward");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onReward", null));
            }

        });
        mAd.setDownloadListener(new RewardVideoAd.RewardVideoDownloadListener() {

            public void onDownloadStarted() {
                //开始下载
                MimoSDKManager.log(TAG, "onDownloadStarted");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onDownloadStarted", null));
            }

            @Override
            public void onDownloadProgressUpdated(int progress) {
                //下载进度，例如：${progress}%
                MimoSDKManager.log(TAG, "onDownloadProgressUpdated progress = " + progress);
                activity.runOnUiThread(() -> mChannel.invokeMethod("onDownloadProgressUpdated", progress));
            }

            @Override
            public void onDownloadPaused() {
                //下载暂停
                MimoSDKManager.log(TAG, "onDownloadPaused");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onDownloadPaused", null));
            }

            @Override
            public void onDownloadCancel() {
                //取消下载
                MimoSDKManager.log(TAG, "onDownloadCancel");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onDownloadCancel", null));
            }

            @Override
            public void onDownloadFailed(int errorCode) {
                //下载失败， 若需要了解errorCode具体含义，请咨询米盟
                MimoSDKManager.log(TAG, "onDownloadFailed code = " + errorCode);
                activity.runOnUiThread(() -> mChannel.invokeMethod("onDownloadFailed", errorCode));
            }

            @Override
            public void onDownloadFinished() {
                //下载结束
                MimoSDKManager.log(TAG, "onDownloadFinished");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onDownloadFinished", null));
            }

            @Override
            public void onInstallStart() {
                //开始安装
                MimoSDKManager.log(TAG, "onInstallStart");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onInstallStart", null));
            }

            @Override
            public void onInstallFailed(int errorCode) {
                //安装失败
                MimoSDKManager.log(TAG, "onInstallFailed code = " + errorCode);
                activity.runOnUiThread(() -> mChannel.invokeMethod("onInstallFailed", errorCode));
            }

            @Override
            public void onInstallSuccess() {
                //安装成功
                MimoSDKManager.log(TAG, "onInstallSuccess");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onInstallSuccess", null));
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
