package com.mio.mi_ads.bannerad;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.mio.mi_ads.FlutterAdPluginConfig;
import com.mio.mi_ads.MimoSDKManager;
import com.miui.zeus.mimo.sdk.BannerAd;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

public class BannerExpressAdView implements PlatformView, MethodChannel.MethodCallHandler {

    private static final String TAG = "MimoSDKManager-BannerExpressAdView";

    private Activity activity;
    private Context context;

    private FrameLayout mContainer;

    private int expressViewWidth;
    private int expressViewHeight;

    //广告参数
    private String mCodeId;

    private MethodChannel mChannel;

    private BannerAd mAd;

    public BannerExpressAdView(Activity activity, Context context, BinaryMessenger messenger, int viewId, Object args) {
        this.activity = activity;
        this.context = context;
        this.mAd = new BannerAd();
        mChannel = new MethodChannel(messenger, FlutterAdPluginConfig.ID_BANNER_VIEW + "_" + viewId);
        mChannel.setMethodCallHandler(this);
        MimoSDKManager.log(TAG, FlutterAdPluginConfig.ID_BANNER_VIEW + "_" + viewId);
        Map map = new Gson().fromJson(new Gson().toJson(args), Map.class);
        this.mCodeId = (String) map.get("advertId");
        final double w = (double) map.get("width");
        final double h = (double) map.get("height");
        this.expressViewWidth = (int) (w * 1);
        this.expressViewHeight = (int) (h * 1);
        this.mContainer = new FrameLayout(context);
        this.mContainer.setLayoutParams(new ViewGroup.LayoutParams(expressViewWidth, expressViewHeight));
        loadExpressAd();
    }

    private void loadExpressAd() {
        mAd.loadAd(mCodeId, new BannerAd.BannerLoadListener() {
            //请求成功回调
            @SuppressLint("LongLogTag")
            @Override
            public void onBannerAdLoadSuccess() {
                MimoSDKManager.log(TAG, "onAdLoadSuccess");
                activity.runOnUiThread(() -> {
                    mChannel.invokeMethod("onAdLoadSuccess", null);
                    showAd();
                });

            }

            //请求失败回调
            @Override
            public void onAdLoadFailed(int errorCode, String errorMsg) {
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
        mAd.showAd(activity, mContainer, new BannerAd.BannerInteractionListener() {

            @Override
            public void onAdClick() {
                // 广告被点击
                MimoSDKManager.log(TAG, "onAdClick");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdClick", null));
            }

            @Override
            public void onAdShow() {
                // 广告被展示
                MimoSDKManager.log(TAG, "onAdShow");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdShow", null));
            }

            @Override
            public void onAdDismiss() {
                // 广告消失
                MimoSDKManager.log(TAG, "onAdClosed");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdClosed", null));
            }

            @Override
            public void onRenderSuccess() {
                // 广告渲染成功
                MimoSDKManager.log(TAG, "onRenderSuccess");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onRenderSuccess", null));
            }

            @Override
            public void onRenderFail(int code, String msg) {
                // 广告渲染失败
                MimoSDKManager.log(TAG, "onRenderFail code = " + code + " msg = " + msg);
                activity.runOnUiThread(() -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", code);
                    map.put("msg", msg);
                    mChannel.invokeMethod("onRenderFail", map);
                });
            }

        });
        mAd.setDownLoadListener(new BannerAd.BannerDownloadListener() {

            @Override
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

    @Nullable
    @Override
    public View getView() {
        return mContainer;
    }

    @Override
    public void dispose() {
        if (mAd != null) {
            mAd.destroy();
            mAd = null;
        }
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        switch (call.method) {
            case "showAd":
                MimoSDKManager.log(TAG, "showAd");
                showAd();
                break;
        }
    }
}
