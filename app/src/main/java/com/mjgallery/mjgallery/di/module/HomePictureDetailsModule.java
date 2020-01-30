package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.home.HomePictureDetailsContract;
import com.mjgallery.mjgallery.mvp.model.home.HomePictureDetailsModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/19/2019 18:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomePictureDetailsModule {

    @Binds
    abstract HomePictureDetailsContract.Model bindHomePictureDetailsModel(HomePictureDetailsModel model);
}