package com.mjgallery.mjgallery.mvp.model.mine.myvip;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.mine.myvip.MyVIPContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.myvip.MyVIPMessageBean;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/20/2019 19:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MyVIPModel extends BaseModel implements MyVIPContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyVIPModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<MyVIPMessageBean>> getMyVip(String token) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getMyVip(token))
                .flatMap(new Function<Observable<BaseResponse<MyVIPMessageBean>>, ObservableSource<BaseResponse<MyVIPMessageBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<MyVIPMessageBean>> apply(@NonNull Observable<BaseResponse<MyVIPMessageBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }

    @Override
    public Observable<BaseResponse<MyVIPMessageBean>> getMyVipMessage(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getMyVipMessage(map))
                .flatMap(new Function<Observable<BaseResponse<MyVIPMessageBean>>, ObservableSource<BaseResponse<MyVIPMessageBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<MyVIPMessageBean>> apply(@NonNull Observable<BaseResponse<MyVIPMessageBean>> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }
}