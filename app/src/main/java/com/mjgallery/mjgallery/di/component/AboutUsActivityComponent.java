package com.mjgallery.mjgallery.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mjgallery.mjgallery.di.module.AboutUsActivityModule;
import com.mjgallery.mjgallery.mvp.contract.mine.AboutUsActivityContract;

import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.AboutUsActivityActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/12/2019 15:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = AboutUsActivityModule.class, dependencies = AppComponent.class)
public interface AboutUsActivityComponent {
    void inject(AboutUsActivityActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(AboutUsActivityContract.View view);

        Builder appComponent(AppComponent appComponent);

        AboutUsActivityComponent build();
    }
}