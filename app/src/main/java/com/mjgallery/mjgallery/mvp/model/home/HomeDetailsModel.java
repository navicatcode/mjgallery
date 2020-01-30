package com.mjgallery.mjgallery.mvp.model.home;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.home.HomeDetailsContract;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeSubCommentDetailDosBean;

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
 * Created by MVPArmsTemplate on 08/09/2019 14:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class HomeDetailsModel extends BaseModel implements HomeDetailsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeDetailsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<HomeDetailsBean>> getPictureDetail(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getPictureDetail(map))
                .flatMap(new Function<Observable<BaseResponse<HomeDetailsBean>>, ObservableSource<BaseResponse<HomeDetailsBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<HomeDetailsBean>> apply(@NonNull Observable<BaseResponse<HomeDetailsBean>> listObservable) throws Exception {
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
    public Observable<BaseResponse<HomeDetailsCommentsBean>> getCommentDetail(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getCommentDetail(map))
                .flatMap(new Function<Observable<BaseResponse<HomeDetailsCommentsBean>>, ObservableSource<BaseResponse<HomeDetailsCommentsBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<HomeDetailsCommentsBean>> apply(@NonNull Observable<BaseResponse<HomeDetailsCommentsBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse<HomeSubCommentDetailDosBean>> getSubCommentDetail(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getSubCommentDetail(map))
                .flatMap(new Function<Observable<BaseResponse<HomeSubCommentDetailDosBean>>, ObservableSource<BaseResponse<HomeSubCommentDetailDosBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<HomeSubCommentDetailDosBean>> apply(@NonNull Observable<BaseResponse<HomeSubCommentDetailDosBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });

    }

    @Override
    public Observable<BaseResponse> getDeleteComment(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getDeleteComment(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }

    @Override
    public Observable<BaseResponse> getLike(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLike(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
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
    public Observable<BaseResponse> getAddCommentRecommend(Map<String, Object> map) {
        return null;
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
//        return Observable.just(mRepositoryManager
//                .obtainRetrofitService(CommonService.class)
//                .getAddCommentRecommend(map))
//                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
//                    @Override
//                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
//                        return listObservable;
//
//                    }
//                });
    }


}
