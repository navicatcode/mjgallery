package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.IFoundModule;
import com.mjgallery.mjgallery.mvp.contract.mine.IFoundContract;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.IFoundActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = IFoundModule.class, dependencies = AppComponent.class)
public interface IFoundComponent {
    void inject(IFoundActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(IFoundContract.View view);

        Builder appComponent(AppComponent appComponent);

        IFoundComponent build();
    }
}
