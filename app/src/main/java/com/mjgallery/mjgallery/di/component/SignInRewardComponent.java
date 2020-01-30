package com.mjgallery.mjgallery.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mjgallery.mjgallery.di.module.SignInRewardModule;
import com.mjgallery.mjgallery.mvp.contract.mine.signinreward.SignInRewardContract;

import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.signinreward.SignInRewardActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 09:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SignInRewardModule.class, dependencies = AppComponent.class)
public interface SignInRewardComponent {
    void inject(SignInRewardActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(SignInRewardContract.View view);

        Builder appComponent(AppComponent appComponent);

        SignInRewardComponent build();
    }
}