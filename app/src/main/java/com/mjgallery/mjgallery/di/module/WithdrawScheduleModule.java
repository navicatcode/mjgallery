package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawScheduleContract;
import com.mjgallery.mjgallery.mvp.model.withdraw.WithdrawScheduleModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/17/2019 15:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class WithdrawScheduleModule {

    @Binds
    abstract WithdrawScheduleContract.Model bindWithdrawScheduleModel(WithdrawScheduleModel model);
}
