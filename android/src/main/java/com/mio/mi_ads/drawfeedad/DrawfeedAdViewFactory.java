package com.mio.mi_ads.drawfeedad;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMessageCodec;
import io.flutter.plugin.platform.PlatformView;
import io.flutter.plugin.platform.PlatformViewFactory;

public class DrawfeedAdViewFactory extends PlatformViewFactory {

    private Activity activity;
    private BinaryMessenger messenger;

    public DrawfeedAdViewFactory(@NonNull Activity activity,
                                 @NonNull BinaryMessenger messenger) {
        super(StandardMessageCodec.INSTANCE);
        this.activity = activity;
        this.messenger = messenger;
    }

    @NonNull
    @Override
    public PlatformView create(Context context, int viewId, @Nullable Object args) {
        return new DrawfeedAdView(activity, context, messenger, viewId, args);
    }
}
