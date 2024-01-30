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
                mChannel.invokeMethod("onAdLoadSuccess", null);
                MimoSDKManager.log(TAG, "onAdLoadSuccess");
                showAd();
            }

            //请求失败回调
            @Override
            public void onAdLoadFailed(int errorCode, String errorMsg) {
                Map<String, Object> map = new HashMap<>();
                map.put("code", errorCode);
                map.put("msg", errorMsg);
                mChannel.invokeMethod("onAdLoadFailed", map);
                MimoSDKManager.log(TAG, "onAdLoadFailed code = " + errorCode + " msg = " + errorMsg);
            }
        });
    }

    private void showAd() {
        mAd.showAd(activity, mContainer, new BannerAd.BannerInteractionListener() {

            @Override
            public void onAdClick() {
                // 广告被点击
                mChannel.invokeMethod("onAdClick", null);
                MimoSDKManager.log(TAG, "onAdClick");
            }

            @Override
            public void onAdShow() {
                // 广告被展示
                mChannel.invokeMethod("onAdShow", null);
                MimoSDKManager.log(TAG, "onAdShow");
            }

            @Override
            public void onAdDismiss() {
                // 广告消失
                mChannel.invokeMethod("onAdClosed", null);
                MimoSDKManager.log(TAG, "onAdClosed");
            }

            @Override
            public void onRenderSuccess() {
                // 广告渲染成功
                mChannel.invokeMethod("onRenderSuccess", null);
                MimoSDKManager.log(TAG, "onRenderSuccess");
            }

            @Override
            public void onRenderFail(int code, String msg) {
                // 广告渲染失败
                Map<String, Object> map = new HashMap<>();
                map.put("code", code);
                map.put("msg", msg);
                mChannel.invokeMethod("onRenderFail", map);
                MimoSDKManager.log(TAG, "onRenderFail code = " + code + " msg = " + msg);
            }

        });
        mAd.setDownLoadListener(new BannerAd.BannerDownloadListener() {

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
