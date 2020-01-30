package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.SearchAllModule;
import com.mjgallery.mjgallery.mvp.contract.search.SearchAllContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.search.SearchAllFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/23/2019 14:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SearchAllModule.class, dependencies = AppComponent.class)
public interface SearchAllComponent {
    void inject(SearchAllFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(SearchAllContract.View view);

        Builder appComponent(AppComponent appComponent);

        SearchAllComponent build();
    }
}