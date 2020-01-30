package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.DiscoveryModule;
import com.mjgallery.mjgallery.mvp.contract.DiscoveryContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.DiscoveryFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/11/2019 21:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = DiscoveryModule.class, dependencies = AppComponent.class)
public interface DiscoveryComponent {
    void inject(DiscoveryFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(DiscoveryContract.View view);

        Builder appComponent(AppComponent appComponent);

        DiscoveryComponent build();
    }
}
