package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawDepositStatusContract;
import com.mjgallery.mjgallery.mvp.model.withdraw.WithdrawDepositStatusModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 21:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class WithdrawDepositStatusModule {

    @Binds
    abstract WithdrawDepositStatusContract.Model bindWithdrawDepositStatusModel(WithdrawDepositStatusModel model);
}