package com.mjgallery.mjgallery.mvp.model.lottery;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.lottery.LotteryRecordContract;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;

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
 * Created by MVPArmsTemplate on 08/09/2019 15:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LotteryRecordModel extends BaseModel implements LotteryRecordContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public LotteryRecordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<List<LotteryRecordBean>>> getRecord(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getRecord(map))
                .flatMap(new Function<Observable<BaseResponse<List<LotteryRecordBean>>>,
                        ObservableSource<BaseResponse<List<LotteryRecordBean>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<LotteryRecordBean>>>
                    apply(@NonNull Observable<BaseResponse<List<LotteryRecordBean>>> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }

    @Override
    public Observable<BaseResponse<List<String>>> getPlanListByMonth(String year,String month) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getPlanListByMonth(year,month))
                .flatMap(new Function<Observable<BaseResponse<List<String>>>,
                        ObservableSource<BaseResponse<List<String>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<String>>>
                    apply(@NonNull Observable<BaseResponse<List<String>>> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }

}
