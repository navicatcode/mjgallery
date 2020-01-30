package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawContract;
import com.mjgallery.mjgallery.mvp.model.withdraw.WthdrawalModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 16:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class WthdrawalModule {

    @Binds
    abstract WithdrawContract.Model bindWthdrawalModel(WthdrawalModel model);
}
