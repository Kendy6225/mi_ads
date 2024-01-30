import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:mi_ads/mi_ads_callback.dart';

class SplashAd {
  final String _viewType = 'com.mio.mi_ads/SplashAdView';

  late MethodChannel _channel;
  Color? color;
  BorderRadius? radius;

  VideoCallback? videoCallback;
  DownloadCallback? downloadCallback;

  SplashAd({
    this.videoCallback,
    this.downloadCallback,
  }) {
    _registerChannel();
  }

  void _registerChannel() {
    _channel = MethodChannel(_viewType);
    _channel.setMethodCallHandler((MethodCall call) async {
      return await videoCallHandler(
        call,
        videoCallback: videoCallback,
        downloadCallback: downloadCallback,
      );
    });
  }
}
