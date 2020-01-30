package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.MyAccountModule;
import com.mjgallery.mjgallery.mvp.contract.mine.MyAccountContract;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.MyAccountActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 10:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MyAccountModule.class, dependencies = AppComponent.class)
public interface MyAccountComponent {
    void inject(MyAccountActivity fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MyAccountContract.View view);

        Builder appComponent(AppComponent appComponent);

        MyAccountComponent build();
    }
}
