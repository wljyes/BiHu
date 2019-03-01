package com.wlj.bihu.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wlj.bihu.R;
import com.wlj.bihu.utils.ApiUrl;
import com.wlj.bihu.utils.http.HttpUtils;

public class QuestionListViewHolder extends RecyclerView.ViewHolder {

    public ImageView avatar;
    public ImageView imageView_1;
    public ImageView imageView_2;
    public ImageView imageView_3;
    public ImageView BigImageView;
    public ImageView exciting;
    public ImageView naive;
    public ImageView favorite;
    public TextView username;
    public TextView latestReplyTime;
    public TextView title;
    public TextView content;
    public TextView replyNumber;
    public TextView excitingNumber;
    public TextView naiveNumber;


    public QuestionListViewHolder(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.question_avatar);
        imageView_1 = itemView.findViewById(R.id.question_image_1);
        imageView_2 = itemView.findViewById(R.id.question_image_2);
        imageView_3 = itemView.findViewById(R.id.question_image_3);
        BigImageView = itemView.findViewById(R.id.image_only_one);
        exciting = itemView.findViewById(R.id.iv_question_exciting);
        naive = itemView.findViewById(R.id.iv_question_naive);
        favorite = itemView.findViewById(R.id.iv_question_favorite);
        username = itemView.findViewById(R.id.question_username);
        latestReplyTime = itemView.findViewById(R.id.question_latest_reply_time);
        title = itemView.findViewById(R.id.question_title);
        content = itemView.findViewById(R.id.question_content);
        replyNumber = itemView.findViewById(R.id.tv_reply_number);
        excitingNumber = itemView.findViewById(R.id.tv_exciting_number);
        naiveNumber = itemView.findViewById(R.id.tv_naive_number);
    }
}
