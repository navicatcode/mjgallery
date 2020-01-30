package com.mjgallery.mjgallery.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.mjgallery.mjgallery.mvp.contract.hisinformation.CommentInformationContract;
import com.mjgallery.mjgallery.mvp.model.hisinformation.CommentInformationModel;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/30/2019 10:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CommentInformationModule {

    @Binds
    abstract CommentInformationContract.Model bindCommentInformationModel(CommentInformationModel model);
}