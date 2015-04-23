package com.talkingsdk.vivo;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.app.Application;

import com.vivo.account.base.accounts.VivoAccountManager;
import com.vivo.account.base.accounts.OnVivoAccountChangedListener;
import com.vivo.account.base.activity.LoginActivity;
import android.os.Looper;

import com.talkingsdk.MainApplication;
import com.talkingsdk.SdkCommonObject;
import com.talkingsdk.models.LoginData;
import com.talkingsdk.models.PayData;
import com.bbk.payment.PaymentActionDetailsInit;
import com.bbk.payment.PaymentActivity;
public abstract class SdkObject extends SdkCommonObject {
    private static final String TAG = "SdkObject";
    public final static String KEY_LOGIN_RESULT = "LoginResult";
    public final static String KEY_SWITCH_ACCOUNT = "switchAccount";
    public final static String KEY_NAME = "name";
    public final static String KEY_OPENID = "openid";
    public final static String KEY_AUTHTOKEN = "authtoken";
    public final static String KEY_SHOW_TEMPLOGIN = "showTempLogin";


    private boolean _restart = false;
    private boolean _isNewUser = false;
    private LoginData ld = null;
    private PayData _payData = null;
    private LoginCode _loginCode = null;
    private VivoAccountManager mVivoAccountManager;

    OnVivoAccountChangedListener mOnVivoAccountChangedListener = new OnVivoAccountChangedListener(){
            @Override
            public void onAccountLogin(String name, String openid, String authtoken) {
                // TODO Auto-generated method stub
                Log.d(TAG, "name="+name+", openid="+openid+", authtoken="+authtoken);
                ld = new LoginData();
                ld.setUsername(name);
                ld.setSessionId(authtoken);
                SdkObject.this.setLoginData(ld);
                // 账号登录成功，此时可用初始化玩家游戏数据
                String tip = "账号登录成功";
                toastMakeText(tip);
                onLoginedRequest(ld, 200);
                Intent intent = new Intent(getParentActivity(),
                        GameActivity.class);
                intent.putExtra("userid", ld.getUsername());
                intent.putExtra("password", ld.getSessionId());
                // getParentActivity().startActivity(intent);
                // getParentActivity().finish();
            }
            //第三方游戏不需要使用此回调
            @Override
            public void onAccountRemove(boolean isRemoved) {
            }

        };

    @Override
    public void onActivityCreate(Activity parentActivity) {
        super.onActivityCreate(parentActivity);
        mVivoAccountManager = VivoAccountManager.getInstance(parentActivity);
        mVivoAccountManager.registeListener(mOnVivoAccountChangedListener);
        //login();
    }

    @Override
    public void onActivityDestroy() {
        Log.d(TAG, "onDestroy");
        mVivoAccountManager.unRegistListener(mOnVivoAccountChangedListener);

    }

    @Override
    public void login() {
            Looper.prepare();
            Intent loginIntent = new Intent(getParentActivity(), LoginActivity.class);
            // loginIntent.putExtra(KEY_SHOW_TEMPLOGIN, false);
            getParentActivity().startActivity(loginIntent);
          
    }

    public void changeAccount() {
        getParentActivity().runOnUiThread(new Runnable() {
            public void run() {
                Intent swithIntent = new Intent(getParentActivity(), LoginActivity.class);
                swithIntent.putExtra(KEY_SWITCH_ACCOUNT, true);
    //          swithIntent.putExtra(KEY_SHOW_TEMPLOGIN, false);
                getParentActivity().startActivity(swithIntent);
            }
        });
    }

    @Override
    public void logout() {
    }

    protected void toastMakeText(final String text) {
        getParentActivity().runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(getParentActivity(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void doFinishPayProcess(int code) {
        PayCode payCode = PayCode.Success;
        onPaidRequest(_payData, payCode.ordinal());
        MainApplication.getInstance().notifyGamePaid(_payData,
                payCode.ordinal());
    }

    public void pay(PayData payData) {
        _payData = payData;

        Bundle localBundle = new Bundle();
        localBundle.putString("transNo", "12345678");// 交易流水号，由订单推送接口返回
        localBundle.putString("accessKey", "123456789");// 由订单推送接口返回
        localBundle.putString("productName", "name");//商品名称
        localBundle.putString("productDes", "desc");//商品描述
        localBundle.putLong("price", 1000);//价格,单位为分（1000即10.00元）
        localBundle.putString("appId", "20140506144229103478");// appid为vivo开发者平台中生成的App ID

        // 以下为可选参数，能收集到务必填写，如未填写，掉单、用户密码找回等问题可能无法解决。
        localBundle.putString("blance", "100yuanbao");
        localBundle.putString("vip", "vip2");
        localBundle.putInt("level", 35);
        localBundle.putString("party", "gift");
        localBundle.putString("roleId", "roleId");
        localBundle.putString("roleName", "roleName");
        localBundle.putString("serverName", "serverInfo");
        localBundle.putString("extInfo", "extenParam");
        localBundle.putBoolean("logOnOff", true);   
        Intent target = new Intent(getParentActivity(), PaymentActivity.class);
        target.putExtra("payment_params", localBundle);
        //startActivityForResult(target, 1);
        getParentActivity().startActivity(target);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

 

        Bundle extras = data.getBundleExtra("pay_info");
        String trans_no = extras.getString("transNo");
        boolean pay_result = extras.getBoolean("pay_result");
        String res_code = extras.getString("result_code");
        String pay_msg = extras.getString("pay_msg");
    }

    private double floatToDouble(float f) {
        return Double.parseDouble(String.valueOf(f));
    }

    @Override
    public void onApplicationStart(Application obj) {
    }

    @Override
    public void onApplicationTerminate() {
    }
    //显示浮标
    public void showToolBar()
    {

    }
    //关闭浮标
    public void destroyToolBar()
    {

    }
    //显示用户中心
    public void showUserCenter()
    {
        
    }
    @Override
    public void setRestartWhenSwitchAccount(boolean restart) {
        _restart = restart;
    }

    @Override
    public LoginCode getLoginCode() {
        return LoginCode.Success;
    }

    private boolean isAppForeground = true;

    @Override
    public void onGameResume() {
        if (!isAppForeground) {
            isAppForeground = true;
        }
    }

    @Override
    public void onGameFade() {
        if (!isAppOnForeground()) {
            isAppForeground = false;
        }
    }

    public void onKeyBack() {
        getParentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
    }
}
