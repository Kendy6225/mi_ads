import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:mi_ads/mi_ads_callback.dart';

class BannerAdView extends StatelessWidget {
  final String _viewType = 'com.mio.mi_ads/BannerExpressAdView';

  late MethodChannel _channel;
  double width;
  double height;
  EdgeInsetsGeometry? margin;
  EdgeInsetsGeometry? padding;
  Color? color;
  BorderRadius? radius;

  String codeId;
  ViewLoadCallback? viewLoadCallback;
  DownloadCallback? downloadCallback;

  BannerAdView({
    super.key,
    required this.codeId,
    required this.width,
    required this.height,
    this.margin,
    this.padding,
    this.color,
    this.viewLoadCallback,
    this.downloadCallback,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      width: width,
      height: height,
      margin: margin,
      padding: padding,
      decoration: BoxDecoration(
        color: color ?? const Color.fromARGB(1, 255, 255, 255),
        borderRadius: radius ?? BorderRadius.zero,
      ),
      child: AndroidView(
        viewType: _viewType,
        creationParams: {
          'advertId': codeId,
          'width': width,
          'height': height,
        },
        onPlatformViewCreated: _registerChannel,
        creationParamsCodec: const StandardMessageCodec(),
      ),
    );
  }

  void _registerChannel(int id) {
    _channel = MethodChannel("${_viewType}_$id");
    _channel.setMethodCallHandler((call) async {
      return await viewCallHandler(
        call,
        viewLoadCallback: viewLoadCallback,
        downloadCallback: downloadCallback,
      );
    });
  }

  Future showAd() async {
    await _channel.invokeMethod('showAd');
  }
}
