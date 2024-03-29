package com.mio.mi_ads.drawfeedad;

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
import com.miui.zeus.mimo.sdk.TemplateAd;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

/**
 * 原生模版广告，信息流广告
 * 信息流广告在V5.0.0版本起升级为原生模板广告，支持个性化调整模板样式，原信息流大图对应原生模板上图下文；原信息流小图广告
 * 对应原生模板左文右图(带标题)；原信息流组图对应原生模板上文下图(组图)
 */
public class DrawfeedAdView implements PlatformView, MethodChannel.MethodCallHandler {

    private static final String TAG = "MimoSDKManager-DrawfeedAdView";
    private Activity activity;
    private Context context;

    private FrameLayout mContainer;

    private int expressViewWidth;
    private int expressViewHeight;

    //广告参数
    private String mCodeId;

    private MethodChannel mChannel;

    private TemplateAd mAd;

    public DrawfeedAdView(Activity activity, Context context, BinaryMessenger messenger, int viewId, Object args) {
        this.activity = activity;
        this.context = context;
        this.mAd = new TemplateAd();
        mChannel = new MethodChannel(messenger, FlutterAdPluginConfig.ID_DRAWFEED_VIEW + "_" + viewId);
        mChannel.setMethodCallHandler(this);
        Map map = new Gson().fromJson(new Gson().toJson(args), Map.class);
        this.mCodeId = (String) map.get("advertId");
        this.expressViewWidth = (int) (((double) map.get("width")) * 1);
        this.expressViewHeight = (int) ((double) map.get("height") * 1);
        this.mContainer = new FrameLayout(context);
        this.mContainer.setLayoutParams(new ViewGroup.LayoutParams(expressViewWidth, expressViewHeight));
        loadAd();
    }

    private void loadAd() {
        if (mAd == null)
            return;
        mAd.load(mCodeId, new TemplateAd.TemplateAdLoadListener() {

            @Override
            public void onAdLoaded() {
                // 加载成功, 在需要的时候在此处展示广告
                MimoSDKManager.log(TAG, "onAdLoadSuccess");
                activity.runOnUiThread(() -> {
                    mChannel.invokeMethod("onAdLoadSuccess", null);
                    showAd();
                });
            }

            @Override
            public void onAdLoadFailed(int errorCode, String errorMessage) {
                // 加载失败
                MimoSDKManager.log(TAG, "onAdLoadFailed code = " + errorCode + " msg = " + errorMessage);
                activity.runOnUiThread(() -> {
                    Map map = new HashMap();
                    map.put("code", errorCode);
                    map.put("msg", errorMessage);
                    mChannel.invokeMethod("onAdLoadFailed", map);
                });
            }

        });
    }

    private void showAd() {
        if (mAd == null)
            return;
        mAd.show(mContainer, new TemplateAd.TemplateAdInteractionListener() {

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
            public void onAdDismissed() {
                // 广告消失
                MimoSDKManager.log(TAG, "onAdClosed");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdClosed", null));
            }

            @Override
            public void onAdRenderFailed(int errorCode, String errorMsg) {
                // 广告渲染失败
                MimoSDKManager.log(TAG, "onAdRenderFailed code = " + errorCode + " msg = " + errorMsg);
                activity.runOnUiThread(() -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", errorCode);
                    map.put("msg", errorMsg);
                    mChannel.invokeMethod("onAdRenderFailed", map);
                });
            }

        });

        mAd.setDownloadListener(new TemplateAd.TemplateDownLoadListener() {

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
        if (mContainer != null) {
            mContainer.removeAllViews();
            mContainer = null;
        }
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        switch (call.method) {
            case "showAd":
                showAd();
                break;
        }
    }
}
