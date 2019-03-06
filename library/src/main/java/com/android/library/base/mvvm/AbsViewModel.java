package com.android.library.base.mvvm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.android.library.util.TUtil;


/**
 * @author：tqzhang on 18/7/26 16:15
 */
public class AbsViewModel<T extends AbsRepository> extends AndroidViewModel {


    public T mRepository;

    public AbsViewModel(@NonNull Application application) {
        super(application);
        mRepository = TUtil.getNewInstance(this, 0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unDisposable();
        }
    }

}
