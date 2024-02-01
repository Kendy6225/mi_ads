import 'package:flutter/cupertino.dart';
import 'package:flutter/services.dart';
import 'package:mi_ads/mi_ads_callback.dart';

final class NativeAdView extends StatelessWidget {
  final String _viewType = 'com.mio.mi_ads/NativeAdView';
  late MethodChannel _channel;
  final double width;
  final double height;

  final EdgeInsetsGeometry? margin;
  final EdgeInsetsGeometry? padding;
  final Color? color;
  final BorderRadius? radius;

  final String codeId;

  final ViewLoadCallback viewLoadCallback;
  final DownloadCallback downloadCallback;

  NativeAdView({
    super.key,
    required this.codeId,
    required this.width,
    required this.height,
    required this.viewLoadCallback,
    required this.downloadCallback,
    this.margin,
    this.padding,
    this.color,
    this.radius,
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
}
