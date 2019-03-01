package com.wlj.bihu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.wlj.bihu.R;
import com.wlj.bihu.adapter.QuestionListAdapter;
import com.wlj.bihu.data.Question;
import com.wlj.bihu.data.QuestionList;
import com.wlj.bihu.data.User;
import com.wlj.bihu.utils.ApiUrl;
import com.wlj.bihu.utils.ToastUtil;
import com.wlj.bihu.utils.UpLoadQiNiu;
import com.wlj.bihu.utils.http.HttpCallBackListener;
import com.wlj.bihu.utils.http.HttpUtils;

import java.util.ArrayList;
import java.util.List;

public class QuestionListFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_list, container,false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        RecyclerView recyclerView = view.findViewById(R.id.rv_question_list);
        recyclerView.setLayoutManager(layoutManager);
        List<QuestionList.DataBean.QuestionsBean> questions = new ArrayList<>();
        QuestionListAdapter adapter = new QuestionListAdapter(questions);
        recyclerView.setAdapter(adapter);
        getQuestionList(adapter);
        return view;
    }

    public void getQuestionList(final QuestionListAdapter adapter) {
        String postData = "page=1&count=15&token=" + User.getUser().getToken();
        HttpUtils.sendHttpRequest(ApiUrl.GET_QUESTION_LIST, "POST", postData, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                Gson gson = new Gson();
                QuestionList questionList = gson.fromJson(response, QuestionList.class);
                Log.d("responessssss", response);
                adapter.setQuestions(questionList.getData().getQuestions());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                ToastUtil.toast("error");
                e.printStackTrace();
            }
        });
    }

}
