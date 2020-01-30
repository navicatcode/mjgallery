package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.DiscoveryRecommendedModule;
import com.mjgallery.mjgallery.mvp.contract.discovery.DiscoveryRecommendedContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.discovery.DiscoveryRecommendedFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 18:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DiscoveryRecommendedModule.class, dependencies = AppComponent.class)
public interface DiscoveryRecommendedComponent {
    void inject(DiscoveryRecommendedFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DiscoveryRecommendedContract.View view);

        Builder appComponent(AppComponent appComponent);

        DiscoveryRecommendedComponent build();
    }
}