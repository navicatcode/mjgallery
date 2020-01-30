package com.mjgallery.mjgallery.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mjgallery.mjgallery.di.module.MyVIPModule;
import com.mjgallery.mjgallery.mvp.contract.mine.myvip.MyVIPContract;

import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.myvip.MyVIPActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/20/2019 19:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = MyVIPModule.class, dependencies = AppComponent.class)
public interface MyVIPComponent {
    void inject(MyVIPActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MyVIPContract.View view);

        Builder appComponent(AppComponent appComponent);

        MyVIPComponent build();
    }
}