# mi_ads

A new Flutter plugin of xiaomi advert for Android.

The Mi Meng Advertising SDK (Android) is a software toolkit launched by the Xiaomi Mobile Advertising Alliance to help developers easily monetize advertisements on the MIUI system platform. The current advertising forms supported by the MiMeng Advertising SDK include banner banner ads, insert screen ads, incentive video ads, open screen ads, native template ads, and native self rendering ads.

### doc
[https://dev.mi.com/distribute/doc/details?pId=1659#_2]

## 1.Configuration
### Permission
```xml
<manifest>
    <!--necessary-->
    <uses-permission android:name="android.permission.INTERNET"/>
    <!--non-necessary -->
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
</manifest>
```

### Provider
- To avoid affecting the use of download type advertisements, regardless of the stage of the app, the provider needs to be configured normally in the manifest file.
- To avoid affecting the conversion and revenue of advertisements, please make sure to configure xxx.fileprovider in the inventory file.
- ${applicationId} must be consistent with the developer package name, otherwise it will cause a crash issue.
```xml
<provider
    android:name="androidx.core.content.FileProvider"
    android:authorities="${applicationId}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/file_paths" />
</provider>
```

### Add http support

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
    <debug-overrides>
        <trust-anchors>
            <!-- Trust user added CAs while debuggable only -->
            <certificates src="system" />
            <certificates src="user" />
        </trust-anchors>
    </debug-overrides>
</network-security-config>
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest>
     <application android:networkSecurityConfig="@xml/network_security_config">
     </application>
</manifest>
```

### Note
- mimo_sdk_5.2.4 and above supoort armeabi-v7a,arm64-v8a.
- close compress for so 
```text
android {
     packagingOptions {
         doNotStrip "*/*/libmimo_1011.so"
         doNotStrip "*/*/libzeusLib.so"
    }
}
```

### proguard
```text
-keep class com.miui.zeus.** { *; }
```

## 2.Usage

### initSDK
```dart

```


