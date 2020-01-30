package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.WXEntryContract;
import com.mjgallery.mjgallery.mvp.model.WXEntryModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 20:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class WXEntryModule {

    @Binds
    abstract WXEntryContract.Model bindWXEntryModel(WXEntryModel model);
}
