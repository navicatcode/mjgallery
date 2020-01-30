package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.SearchDrawingsModule;
import com.mjgallery.mjgallery.mvp.contract.search.SearchDrawingsContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.search.SearchDrawingsFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/16/2019 21:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SearchDrawingsModule.class, dependencies = AppComponent.class)
public interface SearchDrawingsComponent {
    void inject(SearchDrawingsFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(SearchDrawingsContract.View view);

        Builder appComponent(AppComponent appComponent);

        SearchDrawingsComponent build();
    }
}