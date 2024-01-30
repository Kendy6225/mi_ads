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
        mAd.load(mCodeId, new TemplateAd.TemplateAdLoadListener() {

            @Override
            public void onAdLoaded() {
                // 加载成功, 在需要的时候在此处展示广告
                mChannel.invokeMethod("onAdLoadSuccess", null);
                MimoSDKManager.log(TAG, "onAdLoadSuccess");
                showAd();
            }

            @Override
            public void onAdLoadFailed(int errorCode, String errorMessage) {
                // 加载失败
                Map map = new HashMap();
                map.put("code", errorCode);
                map.put("msg", errorMessage);
                mChannel.invokeMethod("onAdLoadFailed", map);
                MimoSDKManager.log(TAG, "onAdLoadFailed code = " + errorCode + " msg = " + errorMessage);
            }

        });
    }

    private void showAd() {
        mAd.show(mContainer, new TemplateAd.TemplateAdInteractionListener() {

            @Override
            public void onAdShow() {
                // 广告展示
                mChannel.invokeMethod("onAdShow", null);
                MimoSDKManager.log(TAG, "onAdShow");
            }

            @Override

            public void onAdClick() {
                // 广告被点击
                mChannel.invokeMethod("onAdClick", null);
                MimoSDKManager.log(TAG, "onAdClick");
            }

            @Override
            public void onAdDismissed() {
                // 广告消失
                mChannel.invokeMethod("onAdClosed", null);
                MimoSDKManager.log(TAG, "onAdClosed");
            }

            @Override
            public void onAdRenderFailed(int errorCode, String errorMsg) {
                // 广告渲染失败
                Map<String, Object> map = new HashMap<>();
                map.put("code", errorCode);
                map.put("msg", errorMsg);
                mChannel.invokeMethod("onAdRenderFailed", map);
                MimoSDKManager.log(TAG, "onAdRenderFailed code = " + errorCode + " msg = " + errorMsg);
            }

        });

        mAd.setDownloadListener(new TemplateAd.TemplateDownLoadListener() {

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
