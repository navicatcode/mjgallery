package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.SettingModule;
import com.mjgallery.mjgallery.mvp.contract.setting.SettingContract;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.SettingActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/17/2019 11:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = SettingModule.class, dependencies = AppComponent.class)
public interface SettingComponent {
    void inject(SettingActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(SettingContract.View view);

        Builder appComponent(AppComponent appComponent);

        SettingComponent build();
    }
}
