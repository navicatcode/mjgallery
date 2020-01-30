package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.MeConncealModule;
import com.mjgallery.mjgallery.mvp.contract.mine.MeConncealContract;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.MeConncealActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 21:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MeConncealModule.class, dependencies = AppComponent.class)
public interface MeConncealComponent {
    void inject(MeConncealActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MeConncealContract.View view);

        Builder appComponent(AppComponent appComponent);

        MeConncealComponent build();
    }
}