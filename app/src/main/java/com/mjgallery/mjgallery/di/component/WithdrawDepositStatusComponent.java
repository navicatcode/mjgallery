package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.WithdrawDepositStatusModule;
import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawDepositStatusContract;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.withdraw.WithdrawDepositStatusActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 21:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WithdrawDepositStatusModule.class, dependencies = AppComponent.class)
public interface WithdrawDepositStatusComponent {
    void inject(WithdrawDepositStatusActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(WithdrawDepositStatusContract.View view);

        Builder appComponent(AppComponent appComponent);

        WithdrawDepositStatusComponent build();
    }
}