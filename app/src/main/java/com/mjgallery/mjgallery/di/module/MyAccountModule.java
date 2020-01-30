package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.mine.MyAccountContract;
import com.mjgallery.mjgallery.mvp.model.mine.MyAccountModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 10:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class MyAccountModule {

    @Binds
    abstract MyAccountContract.Model bindMyAccountModel(MyAccountModel model);
}
