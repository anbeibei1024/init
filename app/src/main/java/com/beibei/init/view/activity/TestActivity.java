package com.beibei.init.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.beibei.init.R;
import com.beibei.init.common.network.model.ResultBean;
import com.beibei.init.common.networkJava.HttpUtil;
import com.beibei.init.common.networkJava.helper.RetrofitHelper;
import com.beibei.init.common.networkJava.model.Translation1;
import com.beibei.init.common.networkJava.request.InitDataNoParamRequest;
import com.beibei.init.common.networkJava.request.RequestInterface;
import com.dashen.utils.GsonUtils;

import java.util.HashMap;

import io.reactivex.Observable;


public class TestActivity extends AppCompatActivity {

    private static String TAG = "------RxjavaRequestActivity1----";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRequest();
//        postRequest();
    }

    public void getRequest() {
        RequestInterface request = RetrofitHelper.createRequest(RequestInterface.class);
        //对 发送请求 进行封装(设置需要翻译的内容)
//        Observable<Translation1> observable = request.getCall("I love you");

        InitDataNoParamRequest paramRequest = new InitDataNoParamRequest("126");
        HashMap<String, String> map = HttpUtil.getInstance().dataDealWith(GsonUtils.toJson(paramRequest));

        Observable<ResultBean<Translation1>> observable1 = request.getUserInfo(map);

        HttpUtil.getInstance().request(observable1, new HttpUtil.OnResultListener<Translation1>() {
            @Override
            public void onSuccess(Translation1 result) {
                Log.e(TAG, "----------onSuccess------------"+result);
//                System.out.println(result.getTranslateResult().get(0).get(0).getTgt());
            }

            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "----------------------" + error.getMessage());
            }

            @Override
            public void onMessage() {

            }
        });
    }

//    public void postRequest() {
//        RequestInterface request = RetrofitHelper.createRequest(RequestInterface.class);
//        //对 发送请求 进行封装(设置需要翻译的内容)
//        Observable<Translation1> observable = request.getCall("I love you");
//
//        HttpUtil.getInstance().request(observable, new HttpUtil.OnResultListener<Translation1>() {
//            @Override
//            public void onSuccess(Translation1 result) {
//                Log.e(TAG, "----------------------");
//                System.out.println(result.getTranslateResult().get(0).get(0).getTgt());
//            }
//
//            @Override
//            public void onError(Throwable error) {
//                Log.e(TAG, "----------------------" + error.getMessage());
//            }
//
//            @Override
//            public void onMessage() {
//
//            }
//        });
//    }
}
