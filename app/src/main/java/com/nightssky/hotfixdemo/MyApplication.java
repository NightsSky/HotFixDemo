package com.nightssky.hotfixdemo;

import android.app.Application;

import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by user on 2017/4/18.
 */

public class MyApplication extends Application {


    public interface MsgDisplayListener {
        void handle(String msg);
    }

    public static MsgDisplayListener msgDisplayListener = null;
    public static StringBuilder cacheMsg = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();


        initHotFix();
    }

    /**
     * 部分错误代码参照
     *
     * code: 1 补丁加载成功
     * code: 6 服务端没有最新可用的补丁
     * code: 11 RSASECRET错误，官网中的密钥是否正确请检查
     * code: 12 当前应用已经存在一个旧补丁, 应用重启尝试加载新补丁
     * code: 13 补丁加载失败, 导致的原因很多种, 比如UnsatisfiedLinkError等异常, 此时应该严格检查logcat异常日志
     * code: 16 APPSECRET错误，官网中的密钥是否正确请检查
     * code: 18 一键清除补丁
     * code: 19 连续两次queryAndLoadNewPatch()方法调用不能短于3s
     */
    private void initHotFix() {
        SophixManager.getInstance().setContext(this)
                .setAppVersion("1.2.3")//当前版本号
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(int mode, int code, String info, int handlePatchVersion) {
                        String msg = new StringBuilder("").append("Mode:").append(mode)
                                .append(" Code:").append(code)
                                .append(" Info:").append(info)
                                .append(" HandlePatchVersion:").append(handlePatchVersion).toString();
                        if (msgDisplayListener != null) {
                            msgDisplayListener.handle(msg);
                        } else {
                            cacheMsg.append("\n").append(msg);
                        }
                    }
                }).initialize();

    }
}
