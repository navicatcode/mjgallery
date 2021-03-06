package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.DiscoveryDataLatestModule;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.DiscoveryDataLatestContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.discovery.discoverydata.DiscoveryDataLatestFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 20:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DiscoveryDataLatestModule.class, dependencies = AppComponent.class)
public interface DiscoveryDataLatestComponent {
    void inject(DiscoveryDataLatestFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DiscoveryDataLatestContract.View view);

        Builder appComponent(AppComponent appComponent);

        DiscoveryDataLatestComponent build();
    }
}