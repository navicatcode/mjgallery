package com.mjgallery.mjgallery.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mjgallery.mjgallery.di.module.VIPWithdrawModule;
import com.mjgallery.mjgallery.mvp.contract.mine.VIPWithdrawContract;

import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.mvp.ui.fragment.mine.mywallet.VIPWithdrawFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/22/2019 14:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = VIPWithdrawModule.class, dependencies = AppComponent.class)
public interface VIPWithdrawComponent {
    void inject(VIPWithdrawFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(VIPWithdrawContract.View view);

        Builder appComponent(AppComponent appComponent);

        VIPWithdrawComponent build();
    }
}