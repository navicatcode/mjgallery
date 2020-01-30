package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.DiscoveryDataHotModule;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.DiscoveryDataHotContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.discovery.discoverydata.DiscoveryDataHotFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 20:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = DiscoveryDataHotModule.class, dependencies = AppComponent.class)
public interface DiscoveryDataHotComponent {
    void inject(DiscoveryDataHotFragment activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DiscoveryDataHotContract.View view);

        Builder appComponent(AppComponent appComponent);

        DiscoveryDataHotComponent build();
    }
}