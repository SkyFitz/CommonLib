package com.android.library.base.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.android.library.base.BaseAppCompatActivity;
import com.android.library.base.BaseAppManager;
import com.android.library.constant.StateConstants;
import com.android.library.event.LiveBus;
import com.android.library.util.StringUtils;
import com.android.library.util.TUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：tqzhang on 18/8/10 11:40
 */
public abstract class AbsLifecycleActivity<T extends AbsViewModel> extends BaseAppCompatActivity {

    protected T mViewModel;

    protected Object mStateEventKey;

    protected String mStateEventTag;

    private List<Object> eventKeys = new ArrayList<>();

    public AbsLifecycleActivity() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        if (null != mViewModel) {
            dataObserver();
            mStateEventKey = getStateEventKey();
            mStateEventTag = getStateEventTag();
            eventKeys.add(new StringBuilder((String) mStateEventKey).append(mStateEventTag).toString());
//            LiveBus.getDefault().subscribe(mStateEventKey, mStateEventTag).observe(this, observer);
            LiveEventBus.get()
                    .with(StringUtils.mergeEventKey(mStateEventKey , mStateEventTag), String.class)
                    .observe(this, observer);
        }
    }

    /**
     * ViewPager +fragment tag
     *
     * @return
     */
    protected String getStateEventTag() {
        return "";
    }

    /**
     * get state page event key
     *
     * @return
     */
    protected abstract Object getStateEventKey();

    protected <T extends ViewModel> T VMProviders(AppCompatActivity fragment, @NonNull Class modelClass) {
        return (T) ViewModelProviders.of(fragment).get(modelClass);

    }

    protected void dataObserver() {

    }

    protected <T> LiveEventBus.Observable<T> registerObserver(Object eventKey, Class<T> tClass) {

        return registerObserver(eventKey, null, tClass);
    }

    protected <T> LiveEventBus.Observable<T> registerObserver(String tag, Class<T> tClass) {

        return registerObserver(getStateEventKey(), tag, tClass);
    }

    protected <T> LiveEventBus.Observable<T> registerObserver(Object eventKey, String tag, Class<T> tClass) {
        String event = StringUtils.mergeEventKey(eventKey , tag);
        //eventKeys.add(event);
        //return LiveBus.getDefault().subscribe(eventKey, tag, tClass);

        return LiveEventBus.get()
                .with(event, tClass);
    }

//    @Override
//    protected void onStateRefresh() {
//        showLoading();
//    }
//
//    protected void showError(Class<? extends BaseStateControl> stateView, Object tag) {
//        loadManager.showStateView(stateView, tag);
//    }
//
//    protected void showError(Class<? extends BaseStateControl> stateView) {
//        showError(stateView, null);
//    }
//
//    protected void showSuccess() {
//        loadManager.showSuccess();
//    }

    protected Observer observer = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String state) {
            if (!TextUtils.isEmpty(state)) {
                if (StateConstants.ERROR_STATE.equals(state)) {
                    //showError(ErrorState.class, "2");
                } else if (StateConstants.NET_WORK_STATE.equals(state)) {
                    //showError(ErrorState.class, "1");
                } else if (StateConstants.LOADING_STATE.equals(state)) {

                } else if (StateConstants.SUCCESS_STATE.equals(state)) {

                }else if (StateConstants.LOADING_DIALOG_START_STATE.equals(state)) {
                    // Loading Start
                    showLoadingDialog("加载中...");
                }else if (StateConstants.LOADING_DIALOG_END_STATE.equals(state)) {
                    // Loading End
                    hideLoadingDialog();
                }else if (StateConstants.LOADING_REFRESH_START_STATE.equals(state)) {
                    // Refresh Start
                    onRefreshStart();
                }else if (StateConstants.LOADING_REFRESH_END_STATE.equals(state)) {
                    // Refresh End
                    onRefreshComplete();
                }else if(StateConstants.LOGIN_OUT_STATE.equals(state)){
                    BaseAppManager.getInstance().clear();
                }else{
                    showMsg(state);
                    //hideLoadingDialog();
                    //onRefreshComplete();
                }
            }
        }
    };

    protected abstract void loginOut();
}
