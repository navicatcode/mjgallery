package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.HisInformationWorksModule;
import com.mjgallery.mjgallery.mvp.contract.hisinformation.HisInformationWorksContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.hisinformation.HisInformationWorksFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 12:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HisInformationWorksModule.class, dependencies = AppComponent.class)
public interface HisInformationWorksComponent {
    void inject(HisInformationWorksFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(HisInformationWorksContract.View view);

        Builder appComponent(AppComponent appComponent);

        HisInformationWorksComponent build();
    }
}
