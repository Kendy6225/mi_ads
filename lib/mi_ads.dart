import 'package:mi_ads/mi_ads_callback.dart';

import 'mi_ads_platform_interface.dart';

class MiAds {
  static Future<void> initSDK() async {
    return await MiAdsPlatform.instance.initSDK();
  }

  static Future<bool> isInitSuccess() async {
    return await MiAdsPlatform.instance.isInitSuccess();
  }

  static Future<bool> isHasInit() async {
    return await MiAdsPlatform.instance.isHasInit();
  }

  static Future setDebugOn(bool enable) async {
    return await MiAdsPlatform.instance.setDebugOn(enable);
  }

  static Future setPersonalizedAd(bool enable) async {
    return await MiAdsPlatform.instance.setPersonalizedAd(enable);
  }



  static Future<void> showSplashAd({
    required String codeId,
    VideoCallback? videoCallback,
    DownloadCallback? downloadCallback,
  }) async {
    // SplashAd ad = SplashAd(
    //   videoCallback: videoCallback,
    //   downloadCallback: downloadCallback,
    // );
    return await MiAdsPlatform.instance.showSplashAd(codeId: codeId);
  }

}
