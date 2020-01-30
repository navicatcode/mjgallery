package com.mjgallery.mjgallery.di.module;

import dagger.Binds;
import dagger.Module;

import com.mjgallery.mjgallery.mvp.contract.mine.AboutUsActivityContract;
import com.mjgallery.mjgallery.mvp.model.mine.AboutUsActivityModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/12/2019 15:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AboutUsActivityModule {

    @Binds
    abstract AboutUsActivityContract.Model bindAboutUsActivityModel(AboutUsActivityModel model);
}