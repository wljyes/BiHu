package com.wlj.bihu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wlj.bihu.R;
import com.wlj.bihu.data.User;
import com.wlj.bihu.utils.ApiUrl;
import com.wlj.bihu.utils.ToastUtil;
import com.wlj.bihu.utils.http.HttpCallBackListener;
import com.wlj.bihu.utils.http.HttpUtils;
import com.wlj.bihu.utils.json.JsonParse;


public class Register extends BaseActivity implements View.OnClickListener{

    private EditText usernameEdit;
    private EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        View inLogin = findViewById(R.id.include_login);
        TextView registerTextView = inLogin.findViewById(R.id.tv_login);
        registerTextView.setText(R.string.register);

        Button loginButton = inLogin.findViewById(R.id.btn_login);
        loginButton.setText(R.string.register);
        loginButton.setOnClickListener(this);

        TextView fastRegister = inLogin.findViewById(R.id.fast_register);
        fastRegister.setVisibility(View.GONE);

        Button cancel = inLogin.findViewById(R.id.btn_login_cancel);
        cancel.setOnClickListener(this);

        usernameEdit = inLogin.findViewById(R.id.edit_username);
        passwordEdit = inLogin.findViewById(R.id.edit_password);
    }

    @Override
    public void onClick(final View v) {
        switch(v.getId()) {
            case R.id.btn_login_cancel:
                this.finish();
                break;
            case R.id.btn_login:
                register(v);
                break;
            default:
                break;
        }
    }

    private void register(final View v) {
        String username = usernameEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        if ((username.length() > 1 && username.length() < 11)
                && (password.length() > 5 && password.length() < 19)) {
            String postData = "username=" + username + "&password=" + password;
            HttpUtils.sendHttpRequest(ApiUrl.REGISTER,
                    "POST", postData, new HttpCallBackListener() {
                        @Override
                        public void onFinish(final String response) {
                            Boolean isSuccessful = JsonParse.jsonParse(response, JsonParse.REGISTER);
                            if (isSuccessful) {
                                Login.rememberPassword(User.getUser().getUsername());
                                Intent intent = new Intent(v.getContext(), Login.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            ToastUtil.toast("网络错误，请检查网络连接");
                            e.printStackTrace();
                        }
                    });
        }
    }

}
