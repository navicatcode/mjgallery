package com.mjgallery.mjgallery.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.SplashContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeBean;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/10/2019 13:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SplashModel extends BaseModel implements SplashContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SplashModel(IRepositoryManager repositoryManager) {
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
    public Observable<BaseResponse<List<HomeBean>>> getSmallPictrues(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getSmallPictrues(map))
                .flatMap(new Function<Observable<BaseResponse<List<HomeBean>>>, ObservableSource<BaseResponse<List<HomeBean>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<HomeBean>>> apply(@NonNull Observable<BaseResponse<List<HomeBean>>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse<List<String>>> getPictureMenu() {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getPictureMenu())
                .flatMap(new Function<Observable<BaseResponse<List<String>>>, ObservableSource<BaseResponse<List<String>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<String>>> apply(@NonNull Observable<BaseResponse<List<String>>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

}