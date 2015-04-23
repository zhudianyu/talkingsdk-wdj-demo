LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := game

LOCAL_MODULE_FILENAME := libgame

LOCAL_SRC_FILES := game.cpp \
                   AppDelegate.cpp \
                   HelloWorldScene.cpp \
                   Notification.cpp

LOCAL_C_INCLUDES := $(LOCAL_PATH)

LOCAL_WHOLE_STATIC_LIBRARIES := cocos2dx_static cocosdenshion_static cocos_extension_static cocos_lua_static SdkBase_release

include $(BUILD_SHARED_LIBRARY)
$(call import-module,SdkBase_release)
$(call import-module,CocosDenshion/android)
$(call import-module,cocos2dx)
$(call import-module,extensions)
$(call import-module,scripting/lua/proj.android)
