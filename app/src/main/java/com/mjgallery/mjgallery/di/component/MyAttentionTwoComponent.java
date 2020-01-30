package com.mjgallery.mjgallery.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.mjgallery.mjgallery.di.module.MyAttentionTwoModule;
import com.mjgallery.mjgallery.mvp.contract.mine.myattention.MyAttentionTwoContract;
import com.mjgallery.mjgallery.mvp.ui.fragment.mine.myattention.MyAttentionTwoFragment;

import dagger.BindsInstance;
import dagger.Component;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/18/2019 15:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = MyAttentionTwoModule.class, dependencies = AppComponent.class)
public interface MyAttentionTwoComponent {
    void inject(MyAttentionTwoFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder view(MyAttentionTwoContract.View view);

        Builder appComponent(AppComponent appComponent);

        MyAttentionTwoComponent build();
    }
}
