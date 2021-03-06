package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.search.SearchBlackAndWhiteContract;
import com.mjgallery.mjgallery.mvp.model.search.SearchBlackAndWhiteModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/23/2019 14:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SearchBlackAndWhiteModule {

    @Binds
    abstract SearchBlackAndWhiteContract.Model bindSearchBlackAndWhiteModel(SearchBlackAndWhiteModel model);
}