package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.PetElvesModule;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.PetElvesContract;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.discoverydata.PetElvesActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/03/2019 21:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PetElvesModule.class, dependencies = AppComponent.class)
public interface PetElvesComponent {
    void inject(PetElvesActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(PetElvesContract.View view);

        Builder appComponent(AppComponent appComponent);

        PetElvesComponent build();
    }
}