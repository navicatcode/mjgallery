package com.mjgallery.mjgallery.mvp.model.discovery.discoverydata;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.DiscoveryPictureDetailsContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoverSubCommentDetailDosBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDetailBean;

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
 * Created by MVPArmsTemplate on 09/22/2019 14:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class DiscoveryPictureDetailsModel extends BaseModel implements DiscoveryPictureDetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DiscoveryPictureDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
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
    public Observable<BaseResponse> getConcernUser(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getConcernUser(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }

    @Override
    public Observable<BaseResponse> getCancelUser(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getCancelUser(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }

    @Override
    public Observable<BaseResponse<LotteryRecordBean.LotteryRecordDtosBean>> getLotteryRecordDtosBean(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLotteryRecordDtosBean(map))
                .flatMap(new Function<Observable<BaseResponse<LotteryRecordBean.LotteryRecordDtosBean>>, ObservableSource<BaseResponse<LotteryRecordBean.LotteryRecordDtosBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<LotteryRecordBean.LotteryRecordDtosBean>> apply(@NonNull Observable<BaseResponse<LotteryRecordBean.LotteryRecordDtosBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });

    }

    @Override
    public Observable<BaseResponse<List<String>>> getSelectByYery(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getSelectByYery(map))
                .flatMap(new Function<Observable<BaseResponse<List<String>>>, ObservableSource<BaseResponse<List<String>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<String>>> apply(@NonNull Observable<BaseResponse<List<String>>> listObservable) throws Exception {
                        return listObservable;
                    }
                });

    }

    @Override
    public Observable<BaseResponse> getStepOn(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getStepOn(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }


    @Override
    public Observable<BaseResponse<DiscoveryCommentsBean.DataBean>> getAddCommentDiscovery(Map<String, Object> map) {
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


    @Override
    public Observable<BaseResponse<List<String>>> getTermsByYear(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getTermsByYear(map))
                .flatMap(new Function<Observable<BaseResponse<List<String>>>, ObservableSource<BaseResponse<List<String>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<String>>> apply(@NonNull Observable<BaseResponse<List<String>>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
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
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
}