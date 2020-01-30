package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.HisInformationModule;
import com.mjgallery.mjgallery.mvp.contract.hisinformation.HisInformationContract;
import com.mjgallery.mjgallery.mvp.ui.activity.HisInformationActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 12:29
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = HisInformationModule.class, dependencies = AppComponent.class)
public interface HisInformationComponent {
    void inject(HisInformationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(HisInformationContract.View view);

        Builder appComponent(AppComponent appComponent);

        HisInformationComponent build();
    }
}
