package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.mine.myattention.MyAttentionContract;
import com.mjgallery.mjgallery.mvp.model.mine.myattention.MyAttentionModel;

import dagger.Binds;
import dagger.Module;


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
@Module
public abstract class MyAttentionModule {

    @Binds
    abstract MyAttentionContract.Model bindMyAttentionModel(MyAttentionModel model);
}
