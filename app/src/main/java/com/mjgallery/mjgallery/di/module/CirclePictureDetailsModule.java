package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.discovery.CirclePictureDetailsContract;
import com.mjgallery.mjgallery.mvp.model.discovery.CirclePictureDetailsModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/01/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CirclePictureDetailsModule {

    @Binds
    abstract CirclePictureDetailsContract.Model bindCirclePictureDetailsModel(CirclePictureDetailsModel model);
}