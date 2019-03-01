package com.wlj.bihu.adapter;

import android.content.Context;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.wlj.bihu.R;
import com.wlj.bihu.activity.BaseActivity;
import com.wlj.bihu.data.Question;
import com.wlj.bihu.data.QuestionList;
import com.wlj.bihu.data.User;
import com.wlj.bihu.fragment.QuestionListFragment;
import com.wlj.bihu.holder.QuestionListViewHolder;
import com.wlj.bihu.utils.ApiUrl;
import com.wlj.bihu.utils.GlideApp;
import com.wlj.bihu.utils.ToastUtil;
import com.wlj.bihu.utils.http.HttpCallBackListener;
import com.wlj.bihu.utils.http.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListViewHolder> {

    private List<QuestionList.DataBean.QuestionsBean> questions = new ArrayList<>();
    private Context context;

    public QuestionListAdapter(List<QuestionList.DataBean.QuestionsBean> questions) {
        this.questions = questions;
    }

    public void setQuestions(List<QuestionList.DataBean.QuestionsBean> questions) {
        this.questions.addAll(questions);
    }

    @NonNull
    @Override
    public QuestionListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_question, viewGroup, false);
        final QuestionListViewHolder holder = new QuestionListViewHolder(view);
        setOnclickListener(holder);
        context = view.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionListViewHolder holder, int i) {
        QuestionList.DataBean.QuestionsBean question = questions.get(i);
        holder.username.setText(question.getAuthorName());
        holder.latestReplyTime.setText(question.getRecent());
        holder.title.setText(question.getTitle());
        holder.content.setText(question.getContent());
        holder.replyNumber.setText(question.getAnswerCount() + "");
        holder.excitingNumber.setText(question.getExciting() + "");
        holder.naiveNumber.setText(question.getNaive() + "");
        if (question.getRecent() == null) {
            holder.latestReplyTime.setText(question.getDate());
        }
        if (question.getTitle().equals("")) {
            holder.title.setVisibility(View.GONE);
        }
        if (question.getContent().equals("")) {
            holder.content.setVisibility(View.GONE);
        }
        if (question.isIs_exciting()) {
            holder.exciting.setImageResource(R.drawable.ic_exciting_pressed);
        } else {
            holder.exciting.setImageResource(R.drawable.ic_exciting);
        }
        if (question.isIs_favorite()) {
            holder.favorite.setImageResource(R.drawable.ic_question_heart_filled);
        } else {
            holder.favorite.setImageResource(R.drawable.ic_question_heart);
        }
        if (question.isIs_naive()) {
            holder.naive.setImageResource(R.drawable.ic_naive_pressed);
        } else {
            holder.naive.setImageResource(R.drawable.ic_naive);
        }
        ImageView[] imageViews = {holder.imageView_1, holder.imageView_2, holder.imageView_3, holder.BigImageView};
        loadImage(question, holder.avatar, imageViews);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    private void loadImage(QuestionList.DataBean.QuestionsBean question, ImageView avatar, ImageView[] imageViews) {
        GlideApp.with(context)
                .asBitmap()
                .load(question.getAuthorAvatar())
                .placeholder(R.drawable.ic_avatar_default)
                .error(R.drawable.ic_avatar_default)
                .into(avatar);
        if (question.getImages() != null && !question.getImages().equals("")) {
            String[] imageUrls = question.getImages().split(",");
            if (imageUrls.length > 1) {
                for (int i = 0; i < imageUrls.length && i < 3; i++) {
                    GlideApp.with(context)
                            .load(imageUrls[i])
                            .placeholder(R.color.interval_gray)
                            .error(R.color.interval_gray)
                            .into(imageViews[i]);
                }
            }
            else {
                for (ImageView imageView : imageViews) {
                    imageView.setVisibility(View.GONE);
                }
                imageViews[3].setVisibility(View.VISIBLE);
                GlideApp.with(context)
                        .load(imageUrls[0])
                        .placeholder(R.color.interval_gray)
                        .error(R.color.interval_gray)
                        .into(imageViews[3]);
            }
        } else {
            for (ImageView imageView : imageViews) {
                imageView.setVisibility(View.GONE);
            }
        }
    }

    private void setOnclickListener(final QuestionListViewHolder holder) {
        holder.exciting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QuestionList.DataBean.QuestionsBean question = questions.get(holder.getAdapterPosition());
                String postData = "id=" + question.getId() + "&type=1&token=" + User.getUser().getToken();
                HttpUtils.sendHttpRequest(question.isIs_exciting() ? ApiUrl.CANCEL_EXCITING : ApiUrl.EXCITING,
                            "POST", postData, new HttpCallBackListener() {
                    @Override
                    public void onFinish(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int status = jsonObject.getInt("status");
                            if (status == 200) {
                                question.setIs_exciting(!question.isIs_exciting());
                                onIconPressed(R.id.iv_question_exciting, !question.isIs_exciting(), holder);
                            } else {
                                ToastUtil.toast("失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(Exception e) {
                        ToastUtil.toast("网络失败");
                    }
                });
            }
        });

        holder.naive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QuestionList.DataBean.QuestionsBean question = questions.get(holder.getAdapterPosition());
                String postData = "id=" + question.getId() + "&type=1&token=" + User.getUser().getToken();
                HttpUtils.sendHttpRequest(question.isIs_naive() ? ApiUrl.CANCELNAIVE : ApiUrl.NAIVE,
                        "POST", postData, new HttpCallBackListener() {
                            @Override
                            public void onFinish(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int status = jsonObject.getInt("status");
                                    if (status == 200) {
                                        question.setIs_naive(!question.isIs_naive());
                                        onIconPressed(R.id.iv_question_naive, !question.isIs_naive(), holder);
                                    } else {
                                        ToastUtil.toast("失败");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onError(Exception e) {
                                ToastUtil.toast("失败");
                            }
                        });
            }
        });

        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QuestionList.DataBean.QuestionsBean question = questions.get(holder.getAdapterPosition());
                String postData = "qid=" + question.getId() + "&token=" + User.getUser().getToken();
                HttpUtils.sendHttpRequest(question.isIs_favorite() ? ApiUrl.CANCEL_FAVORITE : ApiUrl.FAVORITE,
                        "POST", postData, new HttpCallBackListener() {
                            @Override
                            public void onFinish(String response) {
                                try {
                                    Log.d("ffffffff", response);
                                    JSONObject jsonObject = new JSONObject(response);
                                    int status = jsonObject.getInt("status");
                                    if (status == 200) {
                                        question.setIs_favorite(!question.isIs_favorite());
                                        onIconPressed(R.id.iv_question_favorite, !question.isIs_favorite(), holder);
                                    } else {
                                        ToastUtil.toast("失败");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onError(Exception e) {
                                ToastUtil.toast("网络错误");
                            }
                        });
            }
        });
    }

    private void onIconPressed(int viewId, boolean isCancel, QuestionListViewHolder holder) {
        Message message = new Message();
        message.obj = holder;
        switch(viewId) {
            case R.id.iv_question_favorite:
                message.what = isCancel ? BaseActivity.FAVORITE_CANCEL : BaseActivity.FAVORITE;
                BaseActivity.handler.sendMessage(message);
                break;
            case R.id.iv_question_exciting:
                message.what = isCancel ? BaseActivity.EXCITING_CANCEL : BaseActivity.EXCITING;
                BaseActivity.handler.sendMessage(message);
                break;
            case R.id.iv_question_naive:
                message.what = isCancel ? BaseActivity.NAIVE_CANCEL : BaseActivity.NAIVE;
                BaseActivity.handler.sendMessage(message);
                break;
            default:
                break;
        }
    }

}
