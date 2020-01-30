package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.search.SearchAllContract;
import com.mjgallery.mjgallery.mvp.model.search.SearchAllModel;

import dagger.Binds;
import dagger.Module;


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
@Module
public abstract class SearchAllModule {

    @Binds
    abstract SearchAllContract.Model bindSearchAllModel(SearchAllModel model);
}