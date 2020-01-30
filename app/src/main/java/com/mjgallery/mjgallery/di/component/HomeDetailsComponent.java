package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.HomeDetailsModule;
import com.mjgallery.mjgallery.mvp.contract.home.HomeDetailsContract;
import com.mjgallery.mjgallery.mvp.ui.activity.home.HomeDetailsActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 14:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HomeDetailsModule.class, dependencies = AppComponent.class)
public interface HomeDetailsComponent {
    void inject(HomeDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(HomeDetailsContract.View view);

        Builder appComponent(AppComponent appComponent);

        HomeDetailsComponent build();
    }
}
