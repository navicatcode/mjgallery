package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.account.AccountSafeContract;
import com.mjgallery.mjgallery.mvp.model.account.AccountSafeModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 12:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AccountSafeModule {

    @Binds
    abstract AccountSafeContract.Model bindAccountSafeModel(AccountSafeModel model);
}