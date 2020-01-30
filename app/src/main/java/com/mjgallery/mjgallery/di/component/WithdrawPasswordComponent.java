package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.WithdrawPasswordModule;
import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawPasswordContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.mine.withdraw.WithdrawPasswordFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/24/2019 08:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = WithdrawPasswordModule.class, dependencies = AppComponent.class)
public interface WithdrawPasswordComponent {
    void inject(WithdrawPasswordFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(WithdrawPasswordContract.View view);

        Builder appComponent(AppComponent appComponent);

        WithdrawPasswordComponent build();
    }
}