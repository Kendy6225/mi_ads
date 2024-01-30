import 'package:mi_ads/mi_ads_callback.dart';
import 'package:mi_ads/splashad/splash_ad.dart';

import 'mi_ads_platform_interface.dart';

class MiAds {
  static Future<void> initSDK() async {
    return await MiAdsPlatform.instance.initSDK();
  }

  static Future<bool> isInitSuccess() async {
    return Future(() => true);
  }

  static Future setDebugOn(bool enable) async {
    return await MiAdsPlatform.instance.setDebugOn(enable);
  }

  static Future<void> showSplashAd({
    required String codeId,
    VideoCallback? videoCallback,
    DownloadCallback? downloadCallback,
  }) async {
    SplashAd ad = SplashAd(
      videoCallback: videoCallback,
      downloadCallback: downloadCallback,
    );
    return await MiAdsPlatform.instance.showSplashAd(codeId: codeId);
  }

}
