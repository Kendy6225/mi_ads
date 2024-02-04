package com.mio.mi_ads.nativead;

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
import com.miui.zeus.mimo.sdk.NativeAd;
import com.miui.zeus.mimo.sdk.NativeAdData;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

/**
 * 原生自渲染广告
 * 注意：对接原生自渲染广告形式，需向对接的商务经理申请使用权限，权限开通后，可在米盟SSP后台申请创建原生自渲染广告位。
 * 开发者使用自渲染方式展示广告时，请务必保证：
 * 1、广告中有明确的广告和来源标识，米盟广告logo文件详见压缩包png文件
 * 2、具备广告关闭选项且保证终端用户可以一键关闭
 * 不得违规使用自渲染方式展示广告，否则由此带来的后果，由开发者自己负责。
 */
public class NativeAdView implements PlatformView, MethodChannel.MethodCallHandler {

    private static final String TAG = "MimoSDKManager-NativeAdView";

    private Activity activity;
    private Context context;

    private FrameLayout mContainer;

    private int expressViewWidth;
    private int expressViewHeight;

    //广告参数
    private String mCodeId;

    private MethodChannel mChannel;

    private NativeAd mAd;

    public NativeAdView(Activity activity, Context context, BinaryMessenger messenger, int viewId, Object args) {
        this.activity = activity;
        this.context = context;
        this.mAd = new NativeAd();
        mChannel = new MethodChannel(messenger, FlutterAdPluginConfig.ID_NATIVE_VIEW + "_" + viewId);
        mChannel.setMethodCallHandler((call, result) -> {
            switch (call.method) {
                case "dispose":
                    dispose();
                    result.success(null);
                    break;
            }
        });
        Map map = new Gson().fromJson(new Gson().toJson(args), Map.class);
        this.mCodeId = (String) map.get("advertId");
        this.expressViewWidth = (int) ((double) map.get("width"));
        this.expressViewHeight = (int) ((double) map.get("height"));
        this.mContainer = new FrameLayout(context);
        this.mContainer.setLayoutParams(new ViewGroup.LayoutParams(expressViewWidth, expressViewHeight));
        loadAd();
    }

    private void loadAd() {
        if (mAd == null)
            return;
        mAd.load(mCodeId, new NativeAd.NativeAdLoadListener() {

            @Override
            public void onAdLoadSuccess(NativeAdData nativeAdData) {
                // 请求广告成功, 返回了广告数据对象 nativeAdData
                MimoSDKManager.log(TAG, "onAdLoadSuccess");
                activity.runOnUiThread(() -> {
                    mChannel.invokeMethod("onAdLoadSuccess", nativeAdData);
                    showAd();
                });
            }

            @Override
            public void onAdLoadFailed(int error, String errorMsg) {
                // 请求广告失败
                MimoSDKManager.log(TAG, "onAdLoadFailed code = " + error + " msg = " + errorMsg);
                activity.runOnUiThread(() -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", error);
                    map.put("msg", errorMsg);
                    mChannel.invokeMethod("onAdLoadFailed", map);
                });
            }
        });
    }

    private void showAd() {
        if (mAd == null)
            return;
        mAd.registerAdView(mContainer, new NativeAd.NativeAdInteractionListener() {

            @Override
            public void onAdClick() {
                // 广告被点击
                MimoSDKManager.log(TAG, "onAdClick");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdClick", null));
            }

            @Override

            public void onAdShow() {
                // 广告展示
                MimoSDKManager.log(TAG, "onAdShow");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdShow", null));
            }

            @Override
            public void onAdClosed() {
                MimoSDKManager.log(TAG, "onAdClosed");
                activity.runOnUiThread(() -> mChannel.invokeMethod("onAdClosed", null));
            }

            @Override
            public void onRenderFail(int code, String msg) {
                MimoSDKManager.log(TAG, "onRenderFail code = " + code + " msg = " + msg);
                activity.runOnUiThread(() -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", code);
                    map.put("msg", msg);
                    mChannel.invokeMethod("onRenderFail", map);
                });
            }

        });
        mAd.setDownLoadListener(new NativeAd.NativeDownloadListener() {

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
                showAd();
                break;
        }
    }
}
