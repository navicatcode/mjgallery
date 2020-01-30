package com.mjgallery.mjgallery.mvp.model.mine;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;

import com.mjgallery.mjgallery.mvp.contract.mine.MineContract;
import com.mjgallery.mjgallery.mvp.model.bean.UserInformation;
import com.mjgallery.mjgallery.mvp.model.bean.WithdrawDepositBean;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/15/2019 08:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MineModel extends BaseModel implements MineContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<UserInformation>> getUserInformation(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUserInformation(map))
                .flatMap(new Function<Observable<BaseResponse<UserInformation>>, ObservableSource<BaseResponse<UserInformation>>>() {
                    @Override
                    public ObservableSource<BaseResponse<UserInformation>> apply(@NonNull Observable<BaseResponse<UserInformation>> listObservable) throws Exception {
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
    public Observable<BaseResponse> getUpdatePerfect(Map<String, Object> map, MultipartBody.Part file) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getUpdatePerfect(map,file))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }

}
