package com.mjgallery.mjgallery.di.module;

import dagger.Binds;
import dagger.Module;

import com.mjgallery.mjgallery.mvp.contract.mine.PersonalActivityCenterContract;
import com.mjgallery.mjgallery.mvp.model.mine.PersonalActivityCenterModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/20/2019 19:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PersonalActivityCenterModule {

    @Binds
    abstract PersonalActivityCenterContract.Model bindPersonalActivityCenterModel(PersonalActivityCenterModel model);
}