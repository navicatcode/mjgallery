package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.LotteryRecordModule;
import com.mjgallery.mjgallery.mvp.contract.lottery.LotteryRecordContract;
import com.mjgallery.mjgallery.mvp.ui.activity.lotteryrecord.LotteryRecordActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 15:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LotteryRecordModule.class, dependencies = AppComponent.class)
public interface LotteryRecordComponent {
    void inject(LotteryRecordActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(LotteryRecordContract.View view);

        Builder appComponent(AppComponent appComponent);

        LotteryRecordComponent build();
    }
}
