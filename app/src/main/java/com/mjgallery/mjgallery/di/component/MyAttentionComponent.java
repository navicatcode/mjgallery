package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.MyAttentionModule;
import com.mjgallery.mjgallery.mvp.contract.mine.myattention.MyAttentionContract;
import com.mjgallery.mjgallery.mvp.ui.activity.mine.MyAttentionActivity;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/18/2019 10:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MyAttentionModule.class, dependencies = AppComponent.class)
public interface MyAttentionComponent {
    void inject(MyAttentionActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MyAttentionContract.View view);

        Builder appComponent(AppComponent appComponent);

        MyAttentionComponent build();
    }
}
