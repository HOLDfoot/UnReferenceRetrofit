# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

## -------------------------------------------
##     retrofit+rxjava 混淆 混淆
## -------------------------------------------
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
#rx
-dontwarn sun.misc.**
-dontwarn rx.**
-keep class rx.** {*;}
-keep class rx.android.** {*;}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#fastjson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# 保留继承的
-keep public class * extends com.xxwolo.netlib.net.bean.BaseReqBean.**
#不要混淆BaseReqBean所有子类的属性与方法
-keepclasseswithmembers class * extends com.xxwolo.netlib.net.bean.BaseReqBean{
    <fields>;
    <methods>;
}
-keepclasseswithmembers class * extends com.xxwolo.netlib.net.bean.BaseRespBean{
    <fields>;
    <methods>;
}
# 不混淆retrofit包下的类
-keep class com.xxwolo.netlib.net.retrofit.** {*;}