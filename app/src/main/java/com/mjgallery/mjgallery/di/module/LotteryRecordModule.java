package com.mjgallery.mjgallery.di.module;

import com.mjgallery.mjgallery.mvp.contract.lottery.LotteryRecordContract;
import com.mjgallery.mjgallery.mvp.model.lottery.LotteryRecordModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 15:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class LotteryRecordModule {

    @Binds
    abstract LotteryRecordContract.Model bindLotteryRecordModel(LotteryRecordModel model);
}
