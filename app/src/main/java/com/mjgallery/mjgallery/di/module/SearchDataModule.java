package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.search.SearchDataContract;
import com.mjgallery.mjgallery.mvp.model.search.SearchDataModel;

import dagger.Binds;
import dagger.Module;


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
@Module
public abstract class SearchDataModule {

    @Binds
    abstract SearchDataContract.Model bindSearchDataModel(SearchDataModel model);
}