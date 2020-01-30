package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.HomePictureDetailsModule;
import com.mjgallery.mjgallery.mvp.contract.home.HomePictureDetailsContract;
import com.mjgallery.mjgallery.mvp.ui.activity.home.HomePictureDetailsActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/19/2019 18:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HomePictureDetailsModule.class, dependencies = AppComponent.class)
public interface HomePictureDetailsComponent {
    void inject(HomePictureDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(HomePictureDetailsContract.View view);

        Builder appComponent(AppComponent appComponent);

        HomePictureDetailsComponent build();
    }
}