package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.LoginTransparentModule;
import com.mjgallery.mjgallery.mvp.contract.login.LoginTransparentContract;
import com.mjgallery.mjgallery.mvp.ui.activity.LoginTransparentActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = LoginTransparentModule.class, dependencies = AppComponent.class)
public interface LoginTransparentComponent {
    void inject(LoginTransparentActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(LoginTransparentContract.View view);

        Builder appComponent(AppComponent appComponent);

        LoginTransparentComponent build();
    }
}
