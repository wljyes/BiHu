package com.wlj.bihu.utils.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    public static void sendHttpRequest(final String address, final String method, final String postData, final HttpCallBackListener listener) {
        new Thread(new Runnable() { //匿名内部类， new XXX即返回一个对象
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod(method);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    if (method.equals("POST")) {
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.writeBytes(postData);
                    }

                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if(listener != null) {
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    if(listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if(reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}
