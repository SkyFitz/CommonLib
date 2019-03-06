package com.android.library.util.rx;

import com.android.library.base.BaseApplication;
import com.android.library.util.netstatu.NetUtils;
import com.android.library.util.network.ServerException;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

public abstract class RxSubscriber<T> extends DisposableSubscriber<T> {


    public RxSubscriber() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        onLoading();
        if (!NetUtils.isNetworkAvailable(BaseApplication.instance())) {
            onNoNetWork();
            cancel();
            return;
        }
    }

    @Override
    public void onComplete() {
        onLoadingEnd();
    }

    protected void onLoading() {

    }

    protected void onLoadingEnd(){}


    protected void onNoNetWork() {

    }

    @Override
    public void onError(Throwable e) {
        String message = null;
        if (e instanceof UnknownHostException) {
            message = "没有网络";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            message = httpException.code() + "";
        } else if (e instanceof SocketTimeoutException) {
            message = "网络连接超时";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException) {
            message = "解析错误";
        } else if (e instanceof ConnectException) {
            message = "连接失败";
        } else if (e instanceof ServerException) {
            message = ((ServerException) e).message;
        }
        onFailure(message);
        onLoadingEnd();
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    /**
     * success
     *
     * @param t
     */
    public abstract void onSuccess(T t);

    /**
     * failure
     *
     * @param msg
     */
    public abstract void onFailure(String msg);
}
