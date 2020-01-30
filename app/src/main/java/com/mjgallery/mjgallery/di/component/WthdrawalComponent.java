package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.WthdrawalModule;
import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawContract;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.withdraw.WithdrawActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 16:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WthdrawalModule.class, dependencies = AppComponent.class)
public interface WthdrawalComponent {
    void inject(WithdrawActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(WithdrawContract.View view);

        Builder appComponent(AppComponent appComponent);

        WthdrawalComponent build();
    }
}
