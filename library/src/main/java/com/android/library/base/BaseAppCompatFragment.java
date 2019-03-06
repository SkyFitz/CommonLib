package com.android.library.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.library.R;
import com.android.library.util.ToastUtils;
import com.android.library.util.netstatu.NetChangeObserver;
import com.android.library.util.netstatu.NetStateReceiver;
import com.android.library.util.netstatu.NetUtils;
import com.kingja.loadsir.core.LoadService;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by jact on 2016/2/7.
 */
public abstract class BaseAppCompatFragment extends Fragment {

    protected int page = 1;
    protected final static int PAGE_START = 1;
    protected final static int PAGE_SIZE = 10;
    protected Context mContext = null;
    protected MaterialDialog loading = null;
    protected Bundle bundle = null;
    protected LoadService mLoadService;
    /**
     * network status
     */
    protected NetChangeObserver mNetChangeObserver = null;

    /**
     * overridePendingTransition mode
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    /**
     * 是否可见状态
     */
    private boolean isVisible;
    /**
     * 标志位，View已经初始化完成。
     * 2016/04/29
     * 用isAdded()属性代替
     * 2016/05/03
     * isPrepared还是准一些,isAdded有可能出现onCreateView没走完但是isAdded了
     */
    private boolean isPrepared;
    /**
     * 是否第一次加载
     */
    private boolean isFirstLoad = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in,R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }*/
        View view;
        if (getContentViewLayoutID() != 0) {
            view = setContentView(inflater, container, getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        this.mContext = this.getActivity();

        initInjector();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(savedInstanceState);
        // 懒加载
        isFirstLoad = true;
        isPrepared = true;
        lazyLoad();
    }

    public abstract View setContentView(LayoutInflater inflater, ViewGroup container, int layoutResID);

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    /**
     * 要实现延迟加载Fragment内容,需要在 onCreateView
     * isPrepared = true;
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            //if (!isAdded() || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        initData();
    }

    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        //NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        if(loading != null && loading.isShowing()){
            loading.hide();
            loading.dismiss();

            loading = null;
        }
    }

    public void showLoadingDialog(String content){
        if(getActivity().isFinishing()){
            return;
        }
        if(loading == null){
            loading = new MaterialDialog.Builder(getActivity())
                    .progress(true, 0)
                    .content(content)
                    .canceledOnTouchOutside(false)
                    .build();
        }
        loading.show();
    }

    public void hideLoadingDialog(){
        if(loading != null && loading.isShowing()){
            loading.dismiss();
        }
    }

    public void showMsg(String msg){
        ToastUtils.ShortToast(getActivity().getApplicationContext(), msg);
    }

    public void showLongMsg(String msg){
        ToastUtils.LongToast(getActivity().getApplicationContext(), msg);
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this.mContext, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this.mContext, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this.mContext, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this.mContext, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this.mContext, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    /**
     * init com.android.project.activity.view
     *
     * @return
     */
    protected abstract void initView(Bundle state);

    protected void initInjector(){

    };

    /**
     * get ContentViewLayoutID
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();

    public void onRefreshStart(){

    }

    public void onRefreshComplete(){

    }

    public String getText(TextView tv){
        return tv.getText().toString().trim();
    }

    public String getText(EditText tv){
        return tv.getText().toString().trim();
    }
}
