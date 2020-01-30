package com.mjgallery.mjgallery.mvp.model.mine;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.mine.PersonalActivityCenterContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.NoticeBean;
import com.mjgallery.mjgallery.mvp.model.bean.WithdrawDepositBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.personal.PersonalBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/20/2019 19:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PersonalActivityCenterModel extends BaseModel implements PersonalActivityCenterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public PersonalActivityCenterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<HomeBanner>>> getAdvert(int type) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getAdvert(type))
                .flatMap(new Function<Observable<BaseResponse<List<HomeBanner>>>, ObservableSource<BaseResponse<List<HomeBanner>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<HomeBanner>>> apply(@NonNull Observable<BaseResponse<List<HomeBanner>>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse<List<NoticeBean>>> getNotice() {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getNotice())
                .flatMap(new Function<Observable<BaseResponse<List<NoticeBean>>>, ObservableSource<BaseResponse<List<NoticeBean>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<NoticeBean>>> apply(@NonNull Observable<BaseResponse<List<NoticeBean>>> listObservable) throws Exception {
                        return listObservable;

                    }
                });

    }

    @Override
    public Observable<BaseResponse<PersonalBean>> getMeMyTaskAll(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getMeMyTaskAll(map))
                .flatMap(new Function<Observable<BaseResponse<PersonalBean>>, ObservableSource<BaseResponse<PersonalBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<PersonalBean>> apply(@NonNull Observable<BaseResponse<PersonalBean>> listObservable) throws Exception {
                        return listObservable;

                    }
                });

    }

    @Override
    public Observable<BaseResponse> getMeGetReceive(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getMeGetReceive(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }
}