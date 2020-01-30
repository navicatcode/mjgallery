package com.mjgallery.mjgallery.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mjgallery.mjgallery.di.module.InvitedPlayersModule;
import com.mjgallery.mjgallery.mvp.contract.mine.InvitedPlayersContract;

import com.jess.arms.di.scope.ActivityScope;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.invitedplayers.InvitedPlayersActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/21/2019 12:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = InvitedPlayersModule.class, dependencies = AppComponent.class)
public interface InvitedPlayersComponent {
    void inject(InvitedPlayersActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(InvitedPlayersContract.View view);

        Builder appComponent(AppComponent appComponent);

        InvitedPlayersComponent build();
    }
}