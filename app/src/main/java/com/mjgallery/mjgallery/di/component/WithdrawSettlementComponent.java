package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.WithdrawSettlementModule;
import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawSettlementContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.mine.withdraw.WithdrawSettlementFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/24/2019 08:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = WithdrawSettlementModule.class, dependencies = AppComponent.class)
public interface WithdrawSettlementComponent {
    void inject(WithdrawSettlementFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(WithdrawSettlementContract.View view);

        Builder appComponent(AppComponent appComponent);

        WithdrawSettlementComponent build();
    }
}