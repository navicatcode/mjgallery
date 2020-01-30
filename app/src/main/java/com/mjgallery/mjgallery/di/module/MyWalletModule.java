package com.mjgallery.mjgallery.di.module;

import dagger.Binds;
import dagger.Module;

import com.mjgallery.mjgallery.mvp.contract.mine.MyWalletContract;
import com.mjgallery.mjgallery.mvp.model.mine.MyWalletModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/20/2019 19:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class MyWalletModule {

    @Binds
    abstract MyWalletContract.Model bindMyWalletModel(MyWalletModel model);
}