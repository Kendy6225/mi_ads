import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'mi_ads_method_channel.dart';

abstract class MiAdsPlatform extends PlatformInterface {
  /// Constructs a MiAdsPlatform.
  MiAdsPlatform() : super(token: _token);

  static final Object _token = Object();

  static MiAdsPlatform _instance = MethodChannelMiAds();

  /// The default instance of [MiAdsPlatform] to use.
  ///
  /// Defaults to [MethodChannelMiAds].
  static MiAdsPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [MiAdsPlatform] when
  /// they register themselves.
  static set instance(MiAdsPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<void> initSDK() {
    throw UnimplementedError('initSDK() has not been implemented.');
  }

  Future<void> setDebugOn(bool enable) {
    throw UnimplementedError('setDebugOn() has not been implemented.');
  }

  Future<void> setPersonalizedAd(bool enable){
    throw UnimplementedError('setPersonalizedAd() has not been implemented.');
  }

  Future<bool> isInitSuccess(){
    throw UnimplementedError('isInitSuccess() has not been implemented.');
  }

  Future<bool> isHasInit(){
    throw UnimplementedError('isHasInit() has not been implemented.');
  }

  Future<void> showSplashAd({
    required String codeId,
  }) {
    throw UnimplementedError('showSplashAd() has not been implemented.');
  }
}
