package com.android.library.base.mvvm;



import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author：tqzhang on 18/7/26 16:15
 */
public abstract class AbsRepository {

    private CompositeDisposable mCompositeDisposable;


    public AbsRepository() {

    }

    public abstract String getEventKey();

    protected void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void unDisposable() {
        if (mCompositeDisposable != null && mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
    }
}
