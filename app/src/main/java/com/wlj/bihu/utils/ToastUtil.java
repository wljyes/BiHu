package com.wlj.bihu.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.wlj.bihu.activity.BaseActivity;

public class ToastUtil {

    public static void toast(String info) {
        Message message = new Message();
        message.what = BaseActivity.TOAST;
        message.obj = info;
        BaseActivity.handler.sendMessage(message);
    }

    public static void toast(String info, int duration) {
        Message message = new Message();
        message.what = BaseActivity.TOAST_WITH_DURATION;
        message.obj = info;
        message.arg1 = duration;
        BaseActivity.handler.sendMessage(message);
    }


}
