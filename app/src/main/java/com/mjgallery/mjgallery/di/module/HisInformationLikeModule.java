package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.hisinformation.HisInformationLikeContract;
import com.mjgallery.mjgallery.mvp.model.hisinformation.HisInformationLikeModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 12:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HisInformationLikeModule {

    @Binds
    abstract HisInformationLikeContract.Model bindHisInformationLikeModel(HisInformationLikeModel model);
}
