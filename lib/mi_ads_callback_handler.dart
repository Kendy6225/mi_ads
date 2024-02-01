import 'package:flutter/services.dart';
import 'package:mi_ads/mi_ads_callback.dart';

Future<dynamic> videoCallHandler(
  MethodCall call, {
  required VideoCallback videoCallback,
  required DownloadCallback downloadCallback,
}) async {
  switch (call.method) {
    case 'onAdRequestSuccess':
      print("videoCallHandler >>> onAdRequestSuccess");
      videoCallback.onAdRequestSuccess?.call();
      break;
    case 'onVideoStart':
      print(
          "videoCallHandler >>> onVideoStart ${videoCallback.onVideoStart}");
      videoCallback.onVideoStart?.call();
      break;
    case 'onVideoPause':
      videoCallback.onVideoPause?.call();
      break;
    case 'onVideoResume':
      videoCallback.onVideoResume?.call();
      break;
    case 'onVideoSkip':
      videoCallback.onVideoSkip?.call();
      break;
    case 'onVideoComplete':
      videoCallback.onVideoComplete?.call();
      break;
    case 'onPicAdEnd':
      videoCallback.onPicAdEnd?.call();
      break;
    case 'onReward':
      videoCallback.onReward?.call();
      break;
    default:
      return await viewCallHandler(
        call,
        viewLoadCallback: videoCallback,
        downloadCallback: downloadCallback,
      );
  }
  return Future.value(null);
}

Future<dynamic> viewCallHandler(
  MethodCall call, {
  required ViewLoadCallback viewLoadCallback,
  required DownloadCallback downloadCallback,
}) async {
  switch (call.method) {
    case 'onAdLoadSuccess':
      if (viewLoadCallback.onAdLoadSuccess != null) {
        viewLoadCallback.onAdLoadSuccess?.call(call.arguments);
      }
      break;
    case 'onAdLoadFailed':
      Map map = call.arguments;
      int code = map['code'];
      String msg = map['msg'];
      if (viewLoadCallback.onAdLoadFailed != null) {
        viewLoadCallback.onAdLoadFailed?.call(code, msg);
      }
      break;
    case 'onAdClick':
      viewLoadCallback.onAdClick?.call();
      break;
    case 'onAdShow':
      viewLoadCallback.onAdShow?.call();
      break;
    case 'onAdClosed':
      viewLoadCallback.onAdClosed?.call();
      break;
    case 'onRenderSuccess':
      viewLoadCallback.onAdRenderSuccess?.call();
      break;
    case 'onRenderFail':
      Map map = call.arguments;
      int code = map['code'];
      String msg = map['msg'];
      if (viewLoadCallback.onAdRenderFailed != null) {
        viewLoadCallback.onAdRenderFailed?.call(code, msg);
      }
      break;
    case 'onDownloadStarted':
      downloadCallback.onDownloadStart?.call();
      break;
    case 'onDownloadProgressUpdated':
      if (downloadCallback.onDownloadProgress != null) {
        downloadCallback.onDownloadProgress?.call(call.arguments);
      }
      break;
    case 'onDownloadPaused':
      downloadCallback.onDownloadPause?.call();
      break;
    case 'onDownloadCancel':
      downloadCallback.onDownloadCancel?.call();
      break;
    case 'onDownloadFailed':
      if (downloadCallback.onDownloadFail != null) {
        downloadCallback.onDownloadFail?.call(call.arguments);
      }
      break;
    case 'onDownloadFinished':
      downloadCallback.onDownloadFinish?.call();
      break;
    case 'onInstallStart':
      downloadCallback.onInstallStart?.call();
      break;
    case 'onInstallFailed':
      if (downloadCallback.onInstallFail != null) {
        downloadCallback.onInstallFail?.call(call.arguments);
      }
      break;
    case 'onInstallSuccess':
      downloadCallback.onInstallSuccess?.call();
      break;
  }
  return Future.value(null);
}
