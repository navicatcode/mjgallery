package com.mjgallery.mjgallery.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mjgallery.mjgallery.di.module.MyWalletModule;
import com.mjgallery.mjgallery.mvp.contract.mine.MyWalletContract;

import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.MyWalletActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/20/2019 19:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyWalletModule.class, dependencies = AppComponent.class)
public interface MyWalletComponent {
    void inject(MyWalletActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MyWalletContract.View view);

        Builder appComponent(AppComponent appComponent);

        MyWalletComponent build();
    }
}