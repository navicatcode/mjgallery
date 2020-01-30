package com.mjgallery.mjgallery.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mjgallery.mjgallery.di.module.PersonalActivityCenterModule;
import com.mjgallery.mjgallery.mvp.contract.mine.PersonalActivityCenterContract;

import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.PersonalActivityCenterActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/20/2019 19:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PersonalActivityCenterModule.class, dependencies = AppComponent.class)
public interface PersonalActivityCenterComponent {
    void inject(PersonalActivityCenterActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(PersonalActivityCenterContract.View view);

        Builder appComponent(AppComponent appComponent);

        PersonalActivityCenterComponent build();
    }
}