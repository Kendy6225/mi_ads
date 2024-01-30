import 'package:flutter/material.dart';
import 'package:mi_ads/bannerad/banner_ad_view.dart';
import 'package:mi_ads/drawfeedad/drawfeed_ad_view.dart';
import 'package:mi_ads/nativead/native_ad_view.dart';

class BannerPage extends StatelessWidget {
  late BannerAdView bannerAdView;

  @override
  Widget build(BuildContext context) {
    bannerAdView = BannerAdView(
      codeId: '2a8bffd350d01b273640317dfae4eedd',
      width: 330,
      height: 110,
      margin: const EdgeInsets.only(
        top: 100,
        left: 16,
        right: 16,
      ),
      color: Colors.red,
    );
    return Scaffold(
      body: Column(
        children: [
          bannerAdView,
          InkWell(
            onTap: () {
              bannerAdView.showAd();
            },
            child: Container(
              height: 40,
              margin: EdgeInsets.only(top: 12),
              child: Center(child: Text('showAd')),
            ),
          ),
        ],
      ),
    );
  }
}

class DrawFeedPage extends StatelessWidget {
  String codeId;

  DrawFeedPage({
    required this.codeId,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          DrawFeedAdView(
              codeId: codeId,
              width: 300,
              height: 210,
              margin: const EdgeInsets.only(
                top: 80,
                left: 16,
                right: 16,
              ),
              color: Colors.yellow),
        ],
      ),
    );
  }
}

class NativeAdPage extends StatelessWidget {

  String codeId;

  NativeAdPage(this.codeId);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        children: [
          NativeAdView(
            codeId: codeId,
            width: 300,
            height: 200,
          )
        ],
      ),
    );
  }
}
