package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.di.module.WXEntryModule;
import com.mjgallery.mjgallery.mvp.contract.WXEntryContract;
import com.mjgallery.mjgallery.wxapi.WXEntryActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 20:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = WXEntryModule.class, dependencies = AppComponent.class)
public interface WXEntryComponent {
    void inject(WXEntryActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(WXEntryContract.View view);

        Builder appComponent(AppComponent appComponent);

        WXEntryComponent build();
    }
}
