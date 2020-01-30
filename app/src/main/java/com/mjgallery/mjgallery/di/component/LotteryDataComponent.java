package com.mjgallery.mjgallery.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mjgallery.mjgallery.di.module.LotteryDataModule;
import com.mjgallery.mjgallery.mvp.contract.lottery.LotteryDataContract;

import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord.LotteryDataActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 17:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LotteryDataModule.class, dependencies = AppComponent.class)
public interface LotteryDataComponent {
    void inject(LotteryDataActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(LotteryDataContract.View view);

        Builder appComponent(AppComponent appComponent);

        LotteryDataComponent build();
    }
}