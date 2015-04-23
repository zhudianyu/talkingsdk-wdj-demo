#ifndef __NOTIFICATION_H__
#define __NOTIFICATION_H__

#ifdef __cplusplus
extern "C" {
#endif

void Java_com_talkingsdk_MainApplication_notifyGameLogined(JNIEnv*  env, jobject thiz, jobject loginData, int code);
void Java_com_talkingsdk_MainApplication_notifyGameLogout(JNIEnv*  env, jobject thiz, int code);
void Java_com_talkingsdk_MainApplication_notifyGamePaid(JNIEnv*  env, jobject thiz, jobject payData, int code);

#ifdef __cplusplus
}
#endif

#endif
