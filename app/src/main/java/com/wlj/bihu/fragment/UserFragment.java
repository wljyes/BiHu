package com.wlj.bihu.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.wlj.bihu.R;
import com.wlj.bihu.activity.Login;
import com.wlj.bihu.data.User;
import com.wlj.bihu.utils.ApiUrl;
import com.wlj.bihu.utils.GlideApp;
import com.wlj.bihu.utils.ImageUtil;
import com.wlj.bihu.utils.MyApplication;
import com.wlj.bihu.utils.ToastUtil;
import com.wlj.bihu.utils.UpLoadQiNiu;
import com.wlj.bihu.utils.http.HttpCallBackListener;
import com.wlj.bihu.utils.http.HttpUtils;
import com.wlj.bihu.utils.json.JsonParse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import static android.app.Activity.RESULT_OK;


public class UserFragment extends BaseFragment implements View.OnClickListener {

    private ImageView avatar;
    private TextView username;
    private View changeAvatar;
    private View quitLogin;
    private String imagePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container,false);

        avatar = view.findViewById(R.id.iv_user_avatar);
        username = view.findViewById(R.id.tv_user_name);
        changeAvatar = view.findViewById(R.id.change_avatar);
        View changePassword = view.findViewById(R.id.change_password);
        quitLogin = view.findViewById(R.id.quit_login);

        username.setText(User.getUser().getUsername());
        loadAvatar();

        changeAvatar.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        quitLogin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.quit_login:
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                this.getActivity().finish();
                break;
            case R.id.change_avatar:
                changeAvatar();
                break;
            case R.id.change_password:
                setNewPassword();
                break;
            default:
                break;
        }
    }


    private void setNewPassword() {
        final EditText editText = new EditText(this.getContext());
        new AlertDialog.Builder(this.getContext()).setTitle("输入新密码")
                .setView(editText)
                .setNegativeButton( "取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changePassword(editText.getText().toString());
                    }
                }).show();
    }


    private void changePassword(String newPassword) {
        String postData = "password=" + newPassword + "&token=" + User.getUser().getToken();
        HttpUtils.sendHttpRequest(ApiUrl.CHANGE_PASSWORD, "POST", postData, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JsonParse.jsonParse(response, JsonParse.CHANGE_PASSWORD);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                ToastUtil.toast("网络错误");
                e.printStackTrace();
            }
        });
    }

    private void changeAvatar() {
//        if (ContextCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.
//                PERMISSION_GRANTED) {
            //直接使用Fragment中的requestPermissions(),AppCompat.requestPermissions()会回调Activity的onRequestPermissionSResult()
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        } else {
//            ImageUtil.openAlbum(this);
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BaseFragment.CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        //Android 4.4 以上需要解析图片Uri
                        imagePath = ImageUtil.getImagePathOnkitKat(data);
                        upLoadImage(imagePath);
                    }
                    else {
                        imagePath = ImageUtil.getImagePathBeforeKitKat(data);
                        upLoadImage(imagePath);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void upLoadImage(String imagePath) {
        File file = new File(imagePath);
        UpLoadQiNiu.put(file, null, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                try {
                    if (response != null) {
                        String imageUrl = UpLoadQiNiu.host + response.getString("key");
                        User.getUser().setAvatarAdress(imageUrl);
                        upLoadAvatar(User.getUser().getAvatarAdress());
                        loadAvatar();
                    } else {
                        ToastUtil.toast("无网络连接");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }

    private void upLoadAvatar(String avatarAddress) {
        String postData = "token=" + User.getUser().getToken() + "&avatar=" + avatarAddress;
        HttpUtils.sendHttpRequest(ApiUrl.MODIFY_AVATAR, "POST", postData, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    JSONObject data = new JSONObject(response);
                    Log.d("resssss", response.toString());
                    int status = data.getInt("status");
                    if (status == 200) {
                        ToastUtil.toast("头像上传成功");
                    } else {
                        ToastUtil.toast("上传失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                ToastUtil.toast("网络错误");
                e.printStackTrace();
            }
        });
    }

    private void loadAvatar() {
        GlideApp.with(this)
                .asBitmap()
                .load(User.getUser().getAvatarAdress())
                .centerCrop()
                .error(R.drawable.ic_avatar_default)
                .into(avatar);
    }
}
