package com.mjgallery.mjgallery.mvp.model.discovery.discoverydata;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;

import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.DiscoveryDataLatestContract;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDataBean;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/31/2019 20:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class DiscoveryDataLatestModel extends BaseModel implements DiscoveryDataLatestContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public DiscoveryDataLatestModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }


    @Override
    public Observable<BaseResponse<List<DiscoveryDataBean>>> getDiscoveryDataList(int type) {

        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getDiscoveryDataList(type))
                .flatMap(new Function<Observable<BaseResponse<List<DiscoveryDataBean>>>, ObservableSource<BaseResponse<List<DiscoveryDataBean>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<DiscoveryDataBean>>> apply(@NonNull Observable<BaseResponse<List<DiscoveryDataBean>>> listObservable) throws Exception {
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