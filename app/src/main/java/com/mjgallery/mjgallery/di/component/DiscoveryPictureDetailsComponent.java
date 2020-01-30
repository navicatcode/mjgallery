package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.DiscoveryPictureDetailsModule;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.DiscoveryPictureDetailsContract;
import com.mjgallery.mjgallery.mvp.ui.activity.discovery.DiscoveryPictureDetailsActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/22/2019 14:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DiscoveryPictureDetailsModule.class, dependencies = AppComponent.class)
public interface DiscoveryPictureDetailsComponent {
    void inject(DiscoveryPictureDetailsActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DiscoveryPictureDetailsContract.View view);

        Builder appComponent(AppComponent appComponent);

        DiscoveryPictureDetailsComponent build();
    }
}