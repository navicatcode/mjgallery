package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.account.AccountUpdtransPwdInfoContract;
import com.mjgallery.mjgallery.mvp.model.account.AccountUpdtransPwdInfoModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 14:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AccountUpdtransPwdInfoModule {

    @Binds
    abstract AccountUpdtransPwdInfoContract.Model bindAccountUpdtransPwdInfoModel(AccountUpdtransPwdInfoModel model);
}