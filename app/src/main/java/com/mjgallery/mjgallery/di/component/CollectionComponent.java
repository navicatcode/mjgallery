package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.CollectionModule;
import com.mjgallery.mjgallery.mvp.contract.CollectionContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.CollectionFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 20:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = CollectionModule.class, dependencies = AppComponent.class)
public interface CollectionComponent {
    void inject(CollectionFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(CollectionContract.View view);

        Builder appComponent(AppComponent appComponent);

        CollectionComponent build();
    }
}
