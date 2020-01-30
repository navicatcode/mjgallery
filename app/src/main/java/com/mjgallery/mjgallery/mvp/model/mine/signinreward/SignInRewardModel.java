package com.mjgallery.mjgallery.mvp.model.mine.signinreward;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.mine.signinreward.SignInRewardContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.RewardBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.RewardViewBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.SignRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.SignRewardBean;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 09:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SignInRewardModel extends BaseModel implements SignInRewardContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SignInRewardModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<SignRecordBean>> getSignRecord(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getSignRecord(map))
                .flatMap(new Function<Observable<BaseResponse<SignRecordBean>>, ObservableSource<BaseResponse<SignRecordBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<SignRecordBean>> apply(@NonNull Observable<BaseResponse<SignRecordBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse<RewardViewBean>> getRewardView(String token) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getRewardView(token))
                .flatMap(new Function<Observable<BaseResponse<RewardViewBean>>, ObservableSource<BaseResponse<RewardViewBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<RewardViewBean>> apply(@NonNull Observable<BaseResponse<RewardViewBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse<RewardBean>> getReward(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getReward(map))
                .flatMap(new Function<Observable<BaseResponse<RewardBean>>, ObservableSource<BaseResponse<RewardBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<RewardBean>> apply(@NonNull Observable<BaseResponse<RewardBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse<SignRewardBean>> getSignReward(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getSignReward(map))
                .flatMap(new Function<Observable<BaseResponse<SignRewardBean>>, ObservableSource<BaseResponse<SignRewardBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<SignRewardBean>> apply(@NonNull Observable<BaseResponse<SignRewardBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }
//
//    @Override
//    public Observable<BaseResponse> getReward(Map<String, Object> map) {
//        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
//        return Observable.just(mRepositoryManager
//                .obtainRetrofitService(CommonService.class)
//                .getReward(map))
//                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
//                    @Override
//                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
//                        return listObservable;
//                    }
//                });
//    }
}