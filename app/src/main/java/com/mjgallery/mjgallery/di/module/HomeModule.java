package com.mjgallery.mjgallery.di.module;


import androidx.fragment.app.FragmentActivity;

import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.mvp.contract.home.HomeContract;
import com.mjgallery.mjgallery.mvp.model.home.HomeModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 10:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomeModule {

    @ActivityScope
    @Provides
    static RxPermissions provideRxPermissions(HomeContract.View view) {
        return new RxPermissions((FragmentActivity) view.getActivity());
    }

    @Binds
    abstract HomeContract.Model bindHomeModel(HomeModel model);

}
