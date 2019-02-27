package com.wlj.bihu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wlj.bihu.R;
import com.wlj.bihu.data.User;
import com.wlj.bihu.utils.ActivityController;
import com.wlj.bihu.utils.ApiUrl;
import com.wlj.bihu.utils.MyApplication;
import com.wlj.bihu.utils.ToastUtil;
import com.wlj.bihu.utils.http.HttpCallBackListener;
import com.wlj.bihu.utils.http.HttpUtils;
import com.wlj.bihu.utils.json.JsonParse;

public class Login extends BaseActivity implements View.OnClickListener {

    private EditText usernameEdit;
    private EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button cancel = findViewById(R.id.btn_login_cancel);
        Button login = findViewById(R.id.btn_login);
        TextView fastRegister = findViewById(R.id.fast_register);
        usernameEdit = findViewById(R.id.edit_username);
        passwordEdit = findViewById(R.id.edit_password);

        //设置监听器
        cancel.setOnClickListener(this);//context对象是OnClickListener对象
        login.setOnClickListener(this);
        fastRegister.setOnClickListener(this);
        //当账号密码填完后让登录按钮变色
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityController.finishAll();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillUsername(usernameEdit, passwordEdit);
    }

    @Override
    public void onClick(final View v) {
        switch(v.getId()) {
            case R.id.btn_login_cancel:
                ActivityController.finishAll();
                break;

            case R.id.fast_register:
                //Toast.makeText(this, "转到注册页面", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Register.class);
                startActivity(intent);
                break;

            case R.id.btn_login:
                login(v);
                break;
            default:
                break;
        }
    }

    private void login(final View v) {
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        String postData = "username=" + username + "&password=" + password;
        //网络请求
        HttpUtils.sendHttpRequest(ApiUrl.LOGIN, "POST",
                postData, new HttpCallBackListener() {
                    @Override
                    public void onFinish(final String response) {
                        Boolean isSuccessful = JsonParse.jsonParse(response, JsonParse.LOGIN);
                        if (isSuccessful) {
                            rememberPassword(User.getUser().getUsername());
                            Log.d("name", User.getUser().getUsername());
                            Intent toMainPage = new Intent(v.getContext(), MainPage.class);
                            startActivity(toMainPage);
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        ToastUtil.toast("网络错误，请检查网络连接");
                        e.printStackTrace();
                    }
                });
    }

    private void fillUsername(EditText usernameEdit, EditText passwordEdit) {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("user_data", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        if (!username.equals("")) {
            usernameEdit.setText(username);
            usernameEdit.clearFocus();
            passwordEdit.requestFocus();
        }
    }

    public static void rememberPassword(String username) {
        SharedPreferences sp = MyApplication.getContext().getSharedPreferences("user_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.apply();
    }
}
