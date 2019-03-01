package com.wlj.bihu.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wlj.bihu.R;
import com.wlj.bihu.holder.QuestionListViewHolder;
import com.wlj.bihu.utils.ActivityController;
import com.wlj.bihu.utils.MyApplication;

public class BaseActivity extends AppCompatActivity {

    public static final int TOAST = 0;
    public static final int TOAST_WITH_DURATION = 1;
    public static final int FAVORITE = 2;
    public static final int FAVORITE_CANCEL = 3;
    public static final int EXCITING = 4;
    public static final int EXCITING_CANCEL = 5;
    public static final int NAIVE = 6;
    public static final int NAIVE_CANCEL = 7;



    @SuppressLint("HandlerLeak")
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ImageView icon;
            TextView numberTv;
            QuestionListViewHolder holder;
            switch(msg.what) {
                case TOAST:
                    Toast.makeText(MyApplication.getContext(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case TOAST_WITH_DURATION:
                    Toast.makeText(MyApplication.getContext(), msg.obj.toString(), msg.arg1).show();
                    break;
                case FAVORITE:
                    holder = (QuestionListViewHolder) msg.obj;
                    icon = holder.favorite;
                    icon.setImageResource(R.drawable.ic_question_heart_filled);
                    break;
                case FAVORITE_CANCEL:
                    holder = (QuestionListViewHolder) msg.obj;
                    icon = holder.favorite;
                    icon.setImageResource(R.drawable.ic_question_heart);
                    break;
                case EXCITING:
                    holder = (QuestionListViewHolder) msg.obj;
                    icon = holder.exciting;
                    icon.setImageResource(R.drawable.ic_exciting_pressed);
                    numberTv = holder.excitingNumber;
                    addNumber(numberTv);
                    break;
                case EXCITING_CANCEL:
                    holder = (QuestionListViewHolder) msg.obj;
                    icon = holder.exciting;
                    icon.setImageResource(R.drawable.ic_exciting);
                    numberTv = holder.excitingNumber;
                    subNumber(numberTv);
                    break;
                case NAIVE:
                    holder = (QuestionListViewHolder) msg.obj;
                    icon = holder.naive;
                    icon.setImageResource(R.drawable.ic_naive_pressed);
                    numberTv = holder.naiveNumber;
                    addNumber(numberTv);
                    break;
                case NAIVE_CANCEL:
                    holder = (QuestionListViewHolder) msg.obj;
                    icon = holder.naive;
                    icon.setImageResource(R.drawable.ic_naive);
                    numberTv = holder.naiveNumber;
                    subNumber(numberTv);
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

    public static void addNumber(TextView textView) {
        try {
            int number = Integer.parseInt(textView.getText().toString());
            number++;
            textView.setText(number + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void subNumber(TextView textView) {
        try {
            int number = Integer.parseInt(textView.getText().toString());
            number--;
            textView.setText(number + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
