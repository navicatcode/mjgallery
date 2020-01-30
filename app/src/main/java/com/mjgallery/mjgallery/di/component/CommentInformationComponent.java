package com.mjgallery.mjgallery.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.mjgallery.mjgallery.di.module.CommentInformationModule;
import com.mjgallery.mjgallery.mvp.contract.hisinformation.CommentInformationContract;

import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.mvp.ui.fragment.hisinformation.CommentInformationFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/30/2019 10:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CommentInformationModule.class, dependencies = AppComponent.class)
public interface CommentInformationComponent {
    void inject(CommentInformationFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(CommentInformationContract.View view);

        Builder appComponent(AppComponent appComponent);

        CommentInformationComponent build();
    }
}