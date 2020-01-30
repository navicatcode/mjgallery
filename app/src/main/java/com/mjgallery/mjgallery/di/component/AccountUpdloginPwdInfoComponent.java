package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.AccountUpdloginPwdInfoModule;
import com.mjgallery.mjgallery.mvp.contract.account.AccountUpdloginPwdInfoContract;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.setting.AccountUpdLoginPwdInfoActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 12:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AccountUpdloginPwdInfoModule.class, dependencies = AppComponent.class)
public interface AccountUpdloginPwdInfoComponent {
    void inject(AccountUpdLoginPwdInfoActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(AccountUpdloginPwdInfoContract.View view);

        Builder appComponent(AppComponent appComponent);

        AccountUpdloginPwdInfoComponent build();
    }
}