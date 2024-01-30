import 'package:flutter/services.dart';
import 'package:mi_ads/mi_ads_callback.dart';

Future<dynamic> videoCallHandler(
  MethodCall call, {
  VideoCallback? videoCallback,
  DownloadCallback? downloadCallback,
}) async {
  switch (call.method) {
    case 'onAdRequestSuccess':
      videoCallback?.onAdRequestSuccess;
      break;
    case 'onVideoStart':
      videoCallback?.onVideoStart;
      break;
    case 'onVideoPause':
      videoCallback?.onVideoPause;
      break;
    case 'onVideoResume':
      videoCallback?.onVideoResume;
      break;
    case 'onVideoSkip':
      videoCallback?.onVideoSkip;
      break;
    case 'onVideoComplete':
      videoCallback?.onVideoComplete;
      break;
    case 'onPicAdEnd':
      videoCallback?.onPicAdEnd;
      break;
    case 'onReward':
      videoCallback?.onReward;
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
  ViewLoadCallback? viewLoadCallback,
  DownloadCallback? downloadCallback,
}) async {
  switch (call.method) {
    case 'onAdLoadSuccess':
      if (viewLoadCallback?.onAdLoadSuccess != null) {
        viewLoadCallback?.onAdLoadSuccess!(call.arguments);
      }
      break;
    case 'onAdLoadFailed':
      Map map = call.arguments;
      int code = map['code'];
      String msg = map['msg'];
      if (viewLoadCallback?.onAdLoadFailed != null) {
        viewLoadCallback?.onAdLoadFailed!(code, msg);
      }
      break;
    case 'onAdClick':
      viewLoadCallback?.onAdClick;
      break;
    case 'onAdShow':
      viewLoadCallback?.onAdShow;
      break;
    case 'onAdClosed':
      viewLoadCallback?.onAdClosed;
      break;
    case 'onRenderSuccess':
      viewLoadCallback?.onAdRenderSuccess;
      break;
    case 'onRenderFail':
      Map map = call.arguments;
      int code = map['code'];
      String msg = map['msg'];
      if (viewLoadCallback?.onAdRenderFailed != null) {
        viewLoadCallback?.onAdRenderFailed!(code, msg);
      }
      break;
    case 'onDownloadStarted':
      downloadCallback?.onDownloadStart;
      break;
    case 'onDownloadProgressUpdated':
      if (downloadCallback?.onDownloadProgress != null) {
        downloadCallback?.onDownloadProgress!(call.arguments);
      }
      break;
    case 'onDownloadPaused':
      downloadCallback?.onDownloadPause;
      break;
    case 'onDownloadCancel':
      downloadCallback?.onDownloadCancel;
      break;
    case 'onDownloadFailed':
      if (downloadCallback?.onDownloadFail != null) {
        downloadCallback?.onDownloadFail!(call.arguments);
      }
      break;
    case 'onDownloadFinished':
      downloadCallback?.onDownloadFinish;
      break;
    case 'onInstallStart':
      downloadCallback?.onInstallStart;
      break;
    case 'onInstallFailed':
      if (downloadCallback?.onInstallFail != null) {
        downloadCallback?.onInstallFail!(call.arguments);
      }
      break;
    case 'onInstallSuccess':
      downloadCallback?.onInstallSuccess;
      break;
  }
  return Future.value(null);
}
