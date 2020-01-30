package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.DiscoveryVideoModule;
import com.mjgallery.mjgallery.mvp.contract.discovery.DiscoveryVideoContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.discovery.DiscoveryVideoFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 18:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DiscoveryVideoModule.class, dependencies = AppComponent.class)
public interface DiscoveryVideoComponent {
    void inject(DiscoveryVideoFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DiscoveryVideoContract.View view);

        Builder appComponent(AppComponent appComponent);

        DiscoveryVideoComponent build();
    }
}