import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'mi_ads_platform_interface.dart';

/// An implementation of [MiAdsPlatform] that uses method channels.
class MethodChannelMiAds extends MiAdsPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('mi_ads');

  @override
  Future<void> initSDK() async {
    await methodChannel.invokeMethod<String>('initSDK');
  }

  @override
  Future<void> setDebugOn(bool enable) async {
    await methodChannel.invokeMethod("setDebugOn", enable);
  }

  @override
  Future<void> setPersonalizedAd(bool enable) async {
    await methodChannel.invokeMethod('setPersonalizedAd', enable);
  }

  @override
  Future<void> showSplashAd({
    required String codeId,
  }) async {
    await methodChannel.invokeMethod('showSplashAd', codeId);
    return Future(() => null);
  }

  @override
  Future<bool> isInitSuccess() async {
    return await methodChannel.invokeMethod('isInitSuccess', null);
  }

  @override
  Future<bool> isHasInit() async {
    return await methodChannel.invokeMethod('isHasInit', null);
  }
}
