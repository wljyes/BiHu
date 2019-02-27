package com.wlj.bihu.utils.json;

import android.content.Context;
import android.widget.Toast;

import com.wlj.bihu.data.User;
import com.wlj.bihu.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParse {
    public static final int REGISTER = 0;
    public static final int LOGIN = 1;
    public static final int CHANGE_PASSWORD = 2;
    public static JSONObject jsonObject;

    public static boolean jsonParse(String jsonData, int jsonWhat) {
        try {
            jsonObject = new JSONObject(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch(jsonWhat) {
            case REGISTER:
                return parseLoginOrRegister("注册成功");
            case LOGIN:
                return parseLoginOrRegister("登录成功");
            case CHANGE_PASSWORD:
                parseChangePassword();
                break;

        }
        return false;
    }

    private static boolean parseLoginOrRegister(String success) {
        try {
            int status = jsonObject.getInt("status");
            switch (status) {
                case 200:
                    ToastUtil.toast(success);//注意线程
                    //得到json中的data
                    JSONObject data = jsonObject.getJSONObject("data");
                    //给对象属性赋值
                    User.getUser().setId(data.getInt("id"));
                    User.getUser().setUsername(data.getString("username"));
                    User.getUser().setAvatarAdress(data.getString("avatar"));
                    User.getUser().setToken(data.getString("token"));
                    return true;
                default:
                    ToastUtil.toast(jsonObject.getString("info"));
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void parseChangePassword() {
        try {
            int status = jsonObject.getInt("status");
            if (status == 200) {
                ToastUtil.toast("修改成功");
                JSONObject data = jsonObject.getJSONObject("data");
                String newToken = data.getString("token");
                User.getUser().setToken(newToken);
            }
            else {
                String info = jsonObject.getString("info");
                ToastUtil.toast(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}