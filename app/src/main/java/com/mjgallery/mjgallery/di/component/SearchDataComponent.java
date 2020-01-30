package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.SearchDataModule;
import com.mjgallery.mjgallery.mvp.contract.search.SearchDataContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.search.SearchDataFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/18/2019 13:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = SearchDataModule.class, dependencies = AppComponent.class)
public interface SearchDataComponent {
    void inject(SearchDataFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(SearchDataContract.View view);

        Builder appComponent(AppComponent appComponent);

        SearchDataComponent build();
    }
}