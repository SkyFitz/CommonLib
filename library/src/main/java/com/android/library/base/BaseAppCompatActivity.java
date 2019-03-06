package com.android.library.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.library.R;
import com.android.library.util.ToastUtils;
import com.android.library.util.netstatu.NetChangeObserver;
import com.android.library.util.netstatu.NetStateReceiver;
import com.android.library.util.netstatu.NetUtils;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by jact on 2016/2/7.
 */
public abstract class BaseAppCompatActivity extends RxAppCompatActivity {

    protected Context mContext = null;
    protected Bundle bundle;
    protected MaterialDialog loading = null;
    protected int page = 1;
    protected final static int PAGE_START = 1;
    protected static final int PAGE_SIZE = 10;
    protected LoadService mLoadService;

    /**
     * network status
     * 网络状态
     */
    protected NetChangeObserver mNetChangeObserver = null;

    /**
     * overridePendingTransition mode
     * Activity 过渡动画类型
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_left);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.slide_out_in_top, R.anim.slide_out_out_top);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        this.mContext = this;
//        ButterKnife.bind(this);

        BaseAppManager.getInstance().addActivity(this);
        if(isBroadCast()) {
            mNetChangeObserver = new NetChangeObserver() {
                @Override
                public void onNetConnected(NetUtils.NetType type) {
                    super.onNetConnected(type);
                    onNetworkConnected(type);
                }

                @Override
                public void onNetDisConnect() {
                    super.onNetDisConnect();
                    onNetworkDisConnected();
                }
            };

            NetStateReceiver.registerObserver(mNetChangeObserver);
            NetStateReceiver.registerNetworkStateReceiver(mContext);
        }
        initInjector();
        initView(savedInstanceState);
        initData();
    }

    protected void initData(){

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void finish() {
        super.finish();
        if (toggleOverridePendingTransition()) {
            switch (getOverridePendingTransitionMode()) {
                case LEFT:
                    overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_left);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.slide_out_in_top, R.anim.slide_out_out_top);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.slide_out_to_bottom);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.scale_in, R.anim.scale_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //ButterKnife.unbind(this);
        BaseAppManager.getInstance().removeActivity(this);
        if(isBroadCast()) {
            NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
            NetStateReceiver.unRegisterNetworkStateReceiver(mContext);
        }
        if(loading != null && loading.isShowing()){
            loading.hide();
            loading.dismiss();

            loading = null;
        }
    }


    public void showLoadingDialog(String content){
        if(isFinishing()){
            return;
        }
        if(loading == null){
            loading = new MaterialDialog.Builder(this)
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

    /**
     * startActivity
     *
     * @param clazz
     */
    public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
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
    public void readyGo(Class<?> clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     */
    public void readyGo(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
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
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void showMsg(String msg){
        ToastUtils.ShortToast(getApplicationContext(), msg);
    }

    public void showLongMsg(String msg){
        ToastUtils.LongToast(getApplicationContext(), msg);
    }

    public void getRemoteData(){

    }

    /**
     * init com.android.project.activity.view
     *
     * @return
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * toggle overridePendingTransition
     *
     * @return
     */
    protected abstract boolean toggleOverridePendingTransition();

    /**
     * get the overridePendingTransition mode
     */
    protected abstract TransitionMode getOverridePendingTransitionMode();

    protected abstract boolean isBroadCast();

    /**
     * get ContentViewLayoutID
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();

    protected void initInjector() {

    }

    /**
     * network connected
     */
    protected abstract void onNetworkConnected(NetUtils.NetType type);

    /**
     * network disconnected
     */
    protected abstract void onNetworkDisConnected();

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
