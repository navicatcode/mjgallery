package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.AccountUpdPhoneInfoModule;
import com.mjgallery.mjgallery.mvp.contract.account.AccountUpdPhoneInfoContract;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.setting.AccountUpdPhoneInfoActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 13:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AccountUpdPhoneInfoModule.class, dependencies = AppComponent.class)
public interface AccountUpdPhoneInfoComponent {
    void inject(AccountUpdPhoneInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(AccountUpdPhoneInfoContract.View view);

        Builder appComponent(AppComponent appComponent);

        AccountUpdPhoneInfoComponent build();
    }
}