
export 'mi_ads_callback_handler.dart';
/// 广告请求成功
typedef OnAdRequestSuccess = Function();

/// 广告缓存成功
typedef OnAdLoadSuccess = Function(dynamic adData);

/// 广告请求加载失败
typedef OnAdLoadFailed = Function(int code, String msg);

/// 广告被点击
typedef OnAdClick = Function();

/// 广告展示
typedef OnAdShow = Function();

/// 广告关闭
typedef OnAdClosed = Function();

/// 广告渲染成功
typedef OnAdRenderSuccess = Function();

/// 广告渲染失败
typedef OnAdRenderFailed = Function(int code, String msg);

/// 视频开始播放
typedef OnVideoStart = Function();

/// 视频暂停
typedef OnVideoPause = Function();

/// 视频继续播放
typedef OnVideoResume = Function();

/// 视频播放完成
typedef OnVideoComplete = Function();

/// 跳过视频播放（仅激励广告）
typedef OnVideoSkip = Function();

/// 图片类型广告播放完成（仅激励广告）
typedef OnPicAdEnd = Function();

/// 激励回调（仅激励广告）
typedef OnReward = Function();

/// 开始下载
typedef OnDownloadStart = Function();

/// 下载进度
typedef OnDownloadProgress = Function(int progress);

/// 下载暂停
typedef OnDownloadPause = Function();

/// 下载取消
typedef OnDownloadCancel = Function();

/// 下载失败
typedef OnDownloadFail = Function(int code);

/// 下载结束
typedef OnDownloadFinish = Function();

/// 开始安装
typedef OnInstallStart = Function();

/// 安装失败
typedef OnInstallFail = Function(int code);

/// 安装成功
typedef OnInstallSuccess = Function();

/// 视图广告
class ViewLoadCallback {
  OnAdLoadSuccess? onAdLoadSuccess;
  OnAdLoadFailed? onAdLoadFailed;
  OnAdClick? onAdClick;
  OnAdShow? onAdShow;
  OnAdClosed? onAdClosed;
  OnAdRenderSuccess? onAdRenderSuccess; //仅banner广告
  OnAdRenderFailed? onAdRenderFailed;

  ViewLoadCallback({
    this.onAdLoadSuccess,
    this.onAdLoadFailed,
    this.onAdClick,
    this.onAdShow,
    this.onAdClosed,
    this.onAdRenderSuccess,
    this.onAdRenderFailed,
  });
}

/// 激励视频和插屏广告
class VideoCallback extends ViewLoadCallback {
  OnAdRequestSuccess? onAdRequestSuccess;

  OnVideoStart? onVideoStart;
  OnVideoPause? onVideoPause;
  OnVideoResume? onVideoResume; //仅插屏
  OnVideoSkip? onVideoSkip;
  OnVideoComplete? onVideoComplete;
  OnPicAdEnd? onPicAdEnd; //仅激励广告
  OnReward? onReward; //仅激励广告

  VideoCallback({
    super.onAdLoadSuccess,
    super.onAdLoadFailed,
    super.onAdShow,
    super.onAdClick,
    super.onAdClosed,
    super.onAdRenderFailed,
    this.onAdRequestSuccess,
    this.onVideoStart,
    this.onVideoPause,
    this.onVideoSkip,
    this.onVideoComplete,
    this.onPicAdEnd,
    this.onReward,
  });
}

class DownloadCallback {
  OnDownloadStart? onDownloadStart;
  OnDownloadProgress? onDownloadProgress;
  OnDownloadPause? onDownloadPause;
  OnDownloadCancel? onDownloadCancel;
  OnDownloadFail? onDownloadFail;
  OnDownloadFinish? onDownloadFinish;
  OnInstallStart? onInstallStart;
  OnInstallFail? onInstallFail;
  OnInstallSuccess? onInstallSuccess;

  DownloadCallback({
    this.onDownloadStart,
    this.onDownloadProgress,
    this.onDownloadPause,
    this.onDownloadCancel,
    this.onDownloadFail,
    this.onDownloadFinish,
    this.onInstallStart,
    this.onInstallFail,
    this.onInstallSuccess,
  });
}
