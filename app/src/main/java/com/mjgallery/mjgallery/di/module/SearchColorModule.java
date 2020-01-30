package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.search.SearchColorContract;
import com.mjgallery.mjgallery.mvp.model.search.SearchColorModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/23/2019 14:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SearchColorModule {

    @Binds
    abstract SearchColorContract.Model bindSearchColorModel(SearchColorModel model);
}