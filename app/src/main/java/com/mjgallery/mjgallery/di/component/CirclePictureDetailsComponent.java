package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.CirclePictureDetailsModule;
import com.mjgallery.mjgallery.mvp.contract.discovery.CirclePictureDetailsContract;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.CirclePictureDetailsActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/01/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = CirclePictureDetailsModule.class, dependencies = AppComponent.class)
public interface CirclePictureDetailsComponent {
    void inject(CirclePictureDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(CirclePictureDetailsContract.View view);

        Builder appComponent(AppComponent appComponent);

        CirclePictureDetailsComponent build();
    }
}