package com.beibei.init.common.networkJava;

import com.beibei.init.common.constant.Constant;
import com.beibei.init.common.network.model.ResultBean;
import com.beibei.init.common.utils.SignUtil;
import com.dashen.utils.LogUtils;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HttpUtil {
    private static volatile HttpUtil httpUtil = null;

    private HttpUtil() {
    }

    public static HttpUtil getInstance() {
        if (httpUtil == null) {
            synchronized (HttpUtil.class) {
                if (httpUtil == null) {
                    httpUtil = new HttpUtil();
                }
            }
        }
        return httpUtil;
    }

    public <T> void request(Observable<ResultBean<T>> observable, final OnResultListener<T> onResultListener) {
        // 步骤7：发送网络请求
        observable.subscribeOn(Schedulers.io())               // 在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 回到主线程 处理请求结果
                .subscribe(new Observer<ResultBean<T>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<T> result) {
                        onResultListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        onResultListener.onError(e);
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "请求成功");
                    }
                });
    }

    public interface OnResultListener<T> {
        void onSuccess(T result);

        void onError(Throwable error);

        void onMessage();
    }

    public HashMap<String, String> dataDealWith(String data) {
        LogUtils.e("------------OkHttp---------data-------" + data);
        String partner = Constant.PARTNER;
        String sign = SignUtil.dataDealWith(data);
        HashMap<String, String> map = new HashMap<>();
        map.put("data", data);
        map.put("partner", partner);
        map.put("sign", sign);
        return map;
    }
}
