package com.android.library.base.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.android.library.base.BaseAppCompatFragment;
import com.android.library.base.BaseAppLazyFragment;
import com.android.library.base.BaseAppManager;
import com.android.library.constant.StateConstants;
import com.android.library.event.LiveBus;
import com.android.library.util.StringUtils;
import com.android.library.util.TUtil;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * @author：tqzhang on 18/7/10 16:20
 */
public abstract class AbsLifecycleFragment<T extends AbsViewModel> extends BaseAppCompatFragment {

    protected T mViewModel;

    protected Object mStateEventKey;

    protected String mStateEventTag;

    private List<Object> eventKeys = new ArrayList<>();

    @Override
    public void initView(Bundle state) {
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        if (null != mViewModel) {
            dataObserver();
            mStateEventKey = getStateEventKey();
            mStateEventTag = getStateEventTag();
            eventKeys.add(new StringBuilder((String) mStateEventKey).append(mStateEventTag).toString());
            //LiveBus.getDefault().subscribe(mStateEventKey, mStateEventTag).observe(this, observer);
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

    /**
     * create ViewModelProviders
     *
     * @return ViewModel
     */
    protected <T extends ViewModel> T VMProviders(BaseAppCompatFragment
                                                          fragment, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(fragment).get(modelClass);

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
//        if (TextUtils.isEmpty(tag)) {
//            event = (String) eventKey;
//        } else {
//            event = eventKey + tag;
//        }
//        eventKeys.add(event);
//        return LiveBus.getDefault().subscribe(eventKey, tag, tClass);
        return LiveEventBus.get()
                .with(event, tClass);
    }


//    @Override
//    protected void onStateRefresh() {
//        showLoading();
//    }


    /**
     * 获取网络数据
     */
    protected void getRemoteData() {

    }

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
//
//    protected void showLoading() {
//        loadManager.showStateView(LoadingState.class);
//    }
//
//
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
                    loginOut();
                }else{
                    showMsg(state);
                    //hideLoadingDialog();
                    //onRefreshComplete();
                }
            }
        }
    };

    protected abstract void loginOut();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        if (eventKeys != null && eventKeys.size() > 0) {
//            for (int i = 0; i < eventKeys.size(); i++) {
//                LiveBus.getDefault().clear(eventKeys.get(i));
//            }
//        }
    }
}
