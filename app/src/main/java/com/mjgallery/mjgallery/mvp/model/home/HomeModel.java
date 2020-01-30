package com.mjgallery.mjgallery.mvp.model.home;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.home.HomeContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.NoticeBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.LotteryPlan;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.http.FieldMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 10:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
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
    public Observable<BaseResponse<LotteryPlan>> getLotteryPlan(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getLotteryPlan(map))
                .flatMap(new Function<Observable<BaseResponse<LotteryPlan>>, ObservableSource<BaseResponse<LotteryPlan>>>() {
                    @Override
                    public ObservableSource<BaseResponse<LotteryPlan>> apply(@NonNull Observable<BaseResponse<LotteryPlan>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse<List<LotteryRecordBean>>> getRecord(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getRecord(map))
                .flatMap(new Function<Observable<BaseResponse<List<LotteryRecordBean>>>, ObservableSource<BaseResponse<List<LotteryRecordBean>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<LotteryRecordBean>>>
                    apply(@NonNull Observable<BaseResponse<List<LotteryRecordBean>>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse<LotteryRecordBean.LotteryRecordDtosBean>> getLotteryRecordDtosBean(@FieldMap Map<String, Object> map) {
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
    public Observable<BaseResponse<List<String>>> getChangeGetIpList() {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getChangeGetIpList())
                .flatMap(new Function<Observable<BaseResponse<List<String>>>, ObservableSource<BaseResponse<List<String>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<String>>> apply(@NonNull Observable<BaseResponse<List<String>>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }


}
