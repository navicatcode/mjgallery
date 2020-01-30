package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.SelectApplicableModule;
import com.mjgallery.mjgallery.mvp.contract.SelectApplicableContract;
import com.mjgallery.mjgallery.mvp.ui.activity.SelectApplicableActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/21/2019 18:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SelectApplicableModule.class, dependencies = AppComponent.class)
public interface SelectApplicableComponent {
    void inject(SelectApplicableActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(SelectApplicableContract.View view);

        Builder appComponent(AppComponent appComponent);

        SelectApplicableComponent build();
    }
}