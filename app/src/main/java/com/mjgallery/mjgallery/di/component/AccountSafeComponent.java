package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.AccountSafeModule;
import com.mjgallery.mjgallery.mvp.contract.account.AccountSafeContract;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.AccountSafeActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 12:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AccountSafeModule.class, dependencies = AppComponent.class)
public interface AccountSafeComponent {
    void inject(AccountSafeActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(AccountSafeContract.View view);

        Builder appComponent(AppComponent appComponent);

        AccountSafeComponent build();
    }
}