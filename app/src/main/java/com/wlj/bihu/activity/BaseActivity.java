package com.wlj.bihu.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.wlj.bihu.utils.ActivityController;
import com.wlj.bihu.utils.MyApplication;

public class BaseActivity extends AppCompatActivity {

    public static final int TOAST = 0;
    public static final int TOAST_WITH_DURATION = 1;

    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case TOAST:
                    Toast.makeText(MyApplication.getContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case TOAST_WITH_DURATION:
                    Toast.makeText(MyApplication.getContext(), msg.obj.toString(), msg.arg1).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
