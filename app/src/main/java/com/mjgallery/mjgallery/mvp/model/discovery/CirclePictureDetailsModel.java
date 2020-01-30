package com.mjgallery.mjgallery.mvp.model.discovery;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.discovery.CirclePictureDetailsContract;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoverSubCommentDetailDosBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDetailBean;

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
 * Created by MVPArmsTemplate on 09/01/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CirclePictureDetailsModel extends BaseModel implements CirclePictureDetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CirclePictureDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<DiscoveryDetailBean>> getDiscoveryDetail(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getDiscoveryDetail(map))
                .flatMap(new Function<Observable<BaseResponse<DiscoveryDetailBean>>, ObservableSource<BaseResponse<DiscoveryDetailBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<DiscoveryDetailBean>> apply(@NonNull Observable<BaseResponse<DiscoveryDetailBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse> getThumbUpDiscovery(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getThumbUpDiscovery(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }

    @Override
    public Observable<BaseResponse<DiscoveryCommentsBean>> getCommentDetail(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getDiscoveryCommentDetail(map))
                .flatMap(new Function<Observable<BaseResponse<DiscoveryCommentsBean>>, ObservableSource<BaseResponse<DiscoveryCommentsBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<DiscoveryCommentsBean>> apply(@NonNull Observable<BaseResponse<DiscoveryCommentsBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }







    @Override
    public Observable<BaseResponse<DiscoverSubCommentDetailDosBean>> getDiscoverSubCommentDetailDosBean(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getDiscoverSubCommentDetailDosBean(map))
                .flatMap(new Function<Observable<BaseResponse<DiscoverSubCommentDetailDosBean>>, ObservableSource<BaseResponse<DiscoverSubCommentDetailDosBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<DiscoverSubCommentDetailDosBean>> apply(@NonNull Observable<BaseResponse<DiscoverSubCommentDetailDosBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }


    @Override
    public Observable<BaseResponse> getAddCollection(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getAddCollection(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }

    @Override
    public Observable<BaseResponse> getCancelCollection(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getCollectionDelete(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse> getAddCommentDiscovery(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getAddCommentDiscovery(map))
                .flatMap(new Function<Observable<BaseResponse<DiscoveryCommentsBean.DataBean>>, ObservableSource<BaseResponse<DiscoveryCommentsBean.DataBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<DiscoveryCommentsBean.DataBean>> apply(@NonNull Observable<BaseResponse<DiscoveryCommentsBean.DataBean>> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }

    @Override
    public Observable<BaseResponse> getCircleDeleteComment(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getCircleDeleteComment(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }
}