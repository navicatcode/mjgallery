package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.PetElvesDetailsModule;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.PetElvesDetailsContract;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata.PetElvesDetailsActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 08:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PetElvesDetailsModule.class, dependencies = AppComponent.class)
public interface PetElvesDetailsComponent {
    void inject(PetElvesDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(PetElvesDetailsContract.View view);

        Builder appComponent(AppComponent appComponent);

        PetElvesDetailsComponent build();
    }
}