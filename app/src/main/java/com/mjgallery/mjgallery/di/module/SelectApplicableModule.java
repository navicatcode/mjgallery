package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.SelectApplicableContract;
import com.mjgallery.mjgallery.mvp.model.SelectApplicableModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/21/2019 18:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SelectApplicableModule {

    @Binds
    abstract SelectApplicableContract.Model bindSelectApplicableModel(SelectApplicableModel model);
}