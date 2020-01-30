package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.PerfectInformationModule;
import com.mjgallery.mjgallery.mvp.contract.mine.PerfectInformationContract;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.PerfectInformationActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 18:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = PerfectInformationModule.class, dependencies = AppComponent.class)
public interface PerfectInformationComponent {
    void inject(PerfectInformationActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(PerfectInformationContract.View view);

        Builder appComponent(AppComponent appComponent);

        PerfectInformationComponent build();
    }
}
