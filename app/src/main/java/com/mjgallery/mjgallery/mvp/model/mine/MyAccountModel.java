package com.mjgallery.mjgallery.mvp.model.mine;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.mine.MyAccountContract;
import com.mjgallery.mjgallery.mvp.model.bean.AndroidGetAccountXBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyAccountUserBean;

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
 * Created by MVPArmsTemplate on 08/14/2019 10:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MyAccountModel extends BaseModel implements MyAccountContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MyAccountModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<AndroidGetAccountXBean>> getAndroidGetAccount(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getAndroidGetAccount(map))
                .flatMap(new Function<Observable<BaseResponse<AndroidGetAccountXBean>>, ObservableSource<BaseResponse<AndroidGetAccountXBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<AndroidGetAccountXBean>> apply(@NonNull Observable<BaseResponse<AndroidGetAccountXBean>> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }

    @Override
    public Observable<BaseResponse<MyAccountUserBean>> getMyAccountUser(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getMyAccountUser(map))
                .flatMap(new Function<Observable<BaseResponse<MyAccountUserBean>>, ObservableSource<BaseResponse<MyAccountUserBean>>>() {
                    @Override
                    public ObservableSource<BaseResponse<MyAccountUserBean>> apply(@NonNull Observable<BaseResponse<MyAccountUserBean>> listObservable) throws Exception {
                        return listObservable;

                    }
                });
    }
}
