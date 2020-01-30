package com.mjgallery.mjgallery.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.mjgallery.mjgallery.mvp.contract.mine.VIPRewardsContract;
import com.mjgallery.mjgallery.mvp.model.mine.VIPRewardsModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/22/2019 13:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class VIPRewardsModule {

    @Binds
    abstract VIPRewardsContract.Model bindVIPRewardsModel(VIPRewardsModel model);
}