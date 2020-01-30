package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.LotteryDateModule;
import com.mjgallery.mjgallery.mvp.contract.lottery.LotteryDateContract;
import com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord.LotteryDateActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/18/2019 22:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LotteryDateModule.class, dependencies = AppComponent.class)
public interface LotteryDateComponent {
    void inject(LotteryDateActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(LotteryDateContract.View view);

        Builder appComponent(AppComponent appComponent);

        LotteryDateComponent build();
    }
}