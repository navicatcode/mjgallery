package com.mjgallery.mjgallery.mvp.model.account;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.account.AccountUpdloginPwdInfoContract;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 12:51
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AccountUpdloginPwdInfoModel extends BaseModel implements AccountUpdloginPwdInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AccountUpdloginPwdInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> getChangeLoginPassword(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getChangeLoginPassword(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse> getLoginOut(String token) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLoginOut(token))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }
}