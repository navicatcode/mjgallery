package com.mjgallery.mjgallery.mvp.model.mine;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.mine.MyWalletContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MeMyAccount;
import com.mjgallery.mjgallery.mvp.model.bean.WithdrawDepositBean;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


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
@ActivityScope
public class MyWalletModel extends BaseModel implements MyWalletContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyWalletModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<WithdrawDepositBean>> getMeWithdrawMsg(String token) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getMeWithdrawMsg(token))
                .flatMap(new Function<Observable<BaseResponse<WithdrawDepositBean>>, ObservableSource<BaseResponse<WithdrawDepositBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<WithdrawDepositBean>> apply(@NonNull Observable<BaseResponse<WithdrawDepositBean>> listObservable) throws Exception {
                        return listObservable;

                    }
                });

    }

    @Override
    public Observable<BaseResponse<WithdrawDepositBean>> getMeWithdraw(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getMeWithdraw(map))
                .flatMap(new Function<Observable<BaseResponse<WithdrawDepositBean>>, ObservableSource<BaseResponse<WithdrawDepositBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<WithdrawDepositBean>> apply(@NonNull Observable<BaseResponse<WithdrawDepositBean>> listObservable) throws Exception {
                        return listObservable;

                    }
                });

    }

    @Override
    public Observable<BaseResponse<MeMyAccount>> getMeMyAccount(String token) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getMeMyAccount(token))
                .flatMap(new Function<Observable<BaseResponse<MeMyAccount>>, ObservableSource<BaseResponse<MeMyAccount>>>() {
                    @Override
                    public ObservableSource<BaseResponse<MeMyAccount>> apply(@NonNull Observable<BaseResponse<MeMyAccount>> listObservable) throws Exception {
                        return listObservable;

                    }
                });

    }
}