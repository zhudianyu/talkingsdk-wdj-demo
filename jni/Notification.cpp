#include "cocos2d.h"
#include "platform/android/jni/JniHelper.h"
#include <jni.h>

#include "Notification.h"

#define  LOG_TAG    "notification"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)

void Java_com_talkingsdk_MainApplication_notifyGameLogined(JNIEnv*  env, jobject thiz, jobject loginData, int code)
{
    LOGD("Java_com_talkingsdk_MainApplication_notifyGameLogined");
}
void Java_com_talkingsdk_MainApplication_notifyGameLogout(JNIEnv*  env, jobject thiz, int code)
{
    LOGD("Java_com_talkingsdk_MainApplication_notifyGameLogout");
}
void Java_com_talkingsdk_MainApplication_notifyGamePaid(JNIEnv*  env, jobject thiz, jobject payData, int code)
{
    LOGD("Java_com_talkingsdk_MainApplication_notifyGamePaid");
}
