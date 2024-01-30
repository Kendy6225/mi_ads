package com.mio.mi_ads;

import android.app.Activity;
import android.util.Log;

import com.mio.mi_ads.bannerad.BannerExpressAdViewFactory;
import com.mio.mi_ads.drawfeedad.DrawfeedAdViewFactory;
import com.mio.mi_ads.encourage.EncourageVideoAd;
import com.mio.mi_ads.nativead.NativeAdViewFactory;

import io.flutter.embedding.engine.plugins.FlutterPlugin;

public class FlutterAdViewPlugin {

    public static void registerWith(FlutterPlugin.FlutterPluginBinding binding, Activity activity) {

        Log.d("FlutterAdViewPlugin", "register FlutterAdViewPlugin activity = " + activity);
        //注册banner广告
        binding.getPlatformViewRegistry().registerViewFactory(
                FlutterAdPluginConfig.ID_BANNER_VIEW,
                new BannerExpressAdViewFactory(activity, binding.getBinaryMessenger()));
        //注册原生模版广告，信息流广告
        binding.getPlatformViewRegistry().registerViewFactory(
                FlutterAdPluginConfig.ID_DRAWFEED_VIEW,
                new DrawfeedAdViewFactory(activity, binding.getBinaryMessenger()));
        //注册原生自渲染广告
        binding.getPlatformViewRegistry().registerViewFactory(
                FlutterAdPluginConfig.ID_NATIVE_VIEW,
                new NativeAdViewFactory(activity, binding.getBinaryMessenger()));
    }

}
