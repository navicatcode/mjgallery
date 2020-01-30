package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.HisInformationLikeModule;
import com.mjgallery.mjgallery.mvp.contract.hisinformation.HisInformationLikeContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.hisinformation.HisInformationLikeFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 12:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = HisInformationLikeModule.class, dependencies = AppComponent.class)
public interface HisInformationLikeComponent {
    void inject(HisInformationLikeFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(HisInformationLikeContract.View view);

        Builder appComponent(AppComponent appComponent);

        HisInformationLikeComponent build();
    }
}
