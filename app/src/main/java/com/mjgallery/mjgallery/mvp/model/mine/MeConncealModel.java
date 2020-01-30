package com.mjgallery.mjgallery.mvp.model.mine;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.mine.MeConncealContract;

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
 * Created by MVPArmsTemplate on 08/31/2019 21:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MeConncealModel extends BaseModel implements MeConncealContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MeConncealModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> getMePrivacy(Map<String, Object> map) {
        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getMePrivacy(map))
                .flatMap(new Function<Observable<BaseResponse>, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(@NonNull Observable<BaseResponse> listObservable) throws Exception {
                        return listObservable;
                    }
                });
    }
}