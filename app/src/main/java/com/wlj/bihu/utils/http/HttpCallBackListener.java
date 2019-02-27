package com.wlj.bihu.utils.http;

public interface HttpCallBackListener {
    void onFinish(String response);

    void onError(Exception e);
}
