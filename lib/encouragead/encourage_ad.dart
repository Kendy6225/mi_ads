import 'package:flutter/services.dart';
import 'package:mi_ads/mi_ads_callback.dart';

class EncourageAd {
  final _channel = const MethodChannel('com.mio.mi_ads/EncourageAd');

  EncourageAd({
    required VideoCallback videoCallback,
    required DownloadCallback downloadCallback,
  }) {
    _channel.setMethodCallHandler((call) async {
      return await videoCallHandler(
        call,
        videoCallback: videoCallback,
        downloadCallback: downloadCallback,
      );
    });
  }

  Future loadAd(String codeId) async {
    return await _channel.invokeMethod('loadAd', codeId);
  }

  Future showAd() async {
    return await _channel.invokeMethod('showAd', null);
  }

  Future dispose() async {
    return await _channel.invokeMethod('dispose', null);
  }
}
