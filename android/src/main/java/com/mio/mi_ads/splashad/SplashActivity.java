package com.mio.mi_ads.splashad;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mio.mi_ads.FlutterAdPluginConfig;
import com.mio.mi_ads.MimoSDKManager;
import com.mio.mi_ads.R;
import com.miui.zeus.mimo.sdk.SplashAd;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class SplashActivity extends Activity implements MethodChannel.MethodCallHandler {

    private static final String TAG = "MimoSDKManager-SplashActivity";

    private FrameLayout mContainer;
    private String mCodeId;

    private static BinaryMessenger mMessenger;

    private MethodChannel mChannel;

    private SplashAd mAd;


    public static void setMessenger(BinaryMessenger messenger) {
        mMessenger = messenger;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splash_activity);
        mCodeId = getIntent().getExtras().getString("codeId");
        if (TextUtils.isEmpty(mCodeId)) {
            finish();
            return;
        }
        mContainer = findViewById(R.id.container);
        this.mAd = new SplashAd();

        mChannel = new MethodChannel(mMessenger, FlutterAdPluginConfig.ID_SPLASH_VIEW);
        mChannel.setMethodCallHandler(this);
        loadAndShow();
    }

    private void loadAndShow() {
        mAd.loadAndShow(mContainer, mCodeId, new SplashAd.SplashAdListener() {

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
            public void onAdLoadFailed(int errorCode, String errorMsg) {
                // 广告加载失败
                mContainer.setVisibility(View.GONE);
                Map<String, Object> map = new HashMap<>();
                map.put("code", errorCode);
                map.put("msg", errorMsg);
                mChannel.invokeMethod("onAdLoadFailed", map);
                MimoSDKManager.log(TAG, "onAdLoadFailed code = " + errorCode + " msg = " + errorMsg);
            }

            @Override
            public void onAdLoaded() {
                // 广告加载成功
                mChannel.invokeMethod("onAdLoadSuccess", null);
                MimoSDKManager.log(TAG, "onAdLoadSuccess");
            }

            @Override
            public void onAdRenderFailed() {
                //广告渲染失败
                mContainer.setVisibility(View.GONE);
                Map<String, Object> map = new HashMap<>();
                map.put("code", 1);
                map.put("msg", "");
                mChannel.invokeMethod("onRenderFail", map);
            }

        });

        mAd.setSplashDownloadListener(new SplashAd.SplashDownloadListener() {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mContainer != null) {
            mContainer.removeAllViews();
            mContainer = null;
        }
        if (mAd != null) {
            mAd.destroy();
            mAd = null;
        }
        if (mChannel != null) {
            mChannel.setMethodCallHandler(null);
            mChannel = null;
        }
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {

    }
}
