import 'package:flutter/material.dart';
import 'dart:async';

import 'package:mi_ads/mi_ads.dart';
import 'package:mi_ads/mi_ads_callback.dart';
import 'package:mi_ads_example/banner_page.dart';

import 'package:mi_ads/encouragead/encourage_ad.dart';
import 'package:mi_ads/screenad/screen_ad.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    MiAds.initSDK();
    MiAds.setDebugOn(true);
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: HomePage(),
    );
  }
}

class HomePage extends StatelessWidget {
  EncourageAd? encourageAd;
  ScreenVideoAd? screenVideoAd;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plugin example app'),
      ),
      body: Column(
        children: [
          _buildButton('开屏', () {
            MiAds.showSplashAd(
                codeId: '95416cd90dad32599051e91abfb5d031',
                videoCallback: VideoCallback(onAdLoadFailed: (code, msg) {
                  print('======> onAdLoadFailed: code = $code msg = $msg');
                }));
            // _toNext(context,
            //     SplashAdPage(codeId: '22aa1b45d3522ce35d53537eaf17873c'));
          }),
          _buildButton('半屏插屏加载', () {
            //插屏广告
            screenVideoAd = ScreenVideoAd(
              videoCallback: VideoCallback(),
              downloadCallback: DownloadCallback(),
            );
            screenVideoAd?.loadAd('ac4985f2820b8291d2080ed76c4cf300');
          }),
          _buildButton('全屏插屏加载', () {
            //插屏广告
            screenVideoAd = ScreenVideoAd(
              videoCallback: VideoCallback(),
              downloadCallback: DownloadCallback(),
            );
            screenVideoAd?.loadAd('6488b333dd55596ef3f7ac5993ff1dfe');
          }),
          _buildButton('全屏插屏显示', () {
            screenVideoAd?.showAd();
          }),
          _buildButton('激励视频加载', () {
            encourageAd = EncourageAd(
                videoCallback: VideoCallback(onAdLoadSuccess: (data) {
                  print('激励视频数据 = $data');
                }),
                downloadCallback: DownloadCallback());
            encourageAd?.loadAd(
              '785ae1952c1ff94c3492c7a1777284df',
            );
          }),
          _buildButton('激励视频显示', () {
            encourageAd?.showAd();
          }),
          _buildButton('banner', () {
            _toNext(context, BannerPage());
          }),
          _buildButton('原生模版Drawfeed大', () {
            _toNext(
                context,
                DrawFeedPage(
                  codeId: '515c8de0d6c4bfeca5a64c3e16a2d769',
                ));
          }),
          _buildButton('原生模版Drawfeed', () {
            _toNext(
                context,
                DrawFeedPage(
                  codeId: 'ff82e3140677ef737b53bc686bf3fcaa',
                ));
          }),
          _buildButton('原生自渲染广告Native', () {
            _toNext(context, NativeAdPage('ff82e3140677ef737b53bc686bf3fcaa'));
          }),
        ],
      ),
    );
  }

  Widget _buildButton(String label, onTap) {
    return InkWell(
      onTap: onTap,
      child: Container(
        height: 44,
        margin: const EdgeInsets.only(left: 16, right: 16, top: 12),
        decoration: const BoxDecoration(
          color: Colors.deepOrange,
          borderRadius: BorderRadius.all(Radius.circular(10)),
        ),
        child: Center(
          child: Text(
            label,
            style: const TextStyle(color: Colors.white, fontSize: 14),
          ),
        ),
      ),
    );
  }

  void _toNext(BuildContext context, Widget widget) {
    Navigator.push(context, MaterialPageRoute(builder: (ctx) {
      return widget;
    }));
  }
}
