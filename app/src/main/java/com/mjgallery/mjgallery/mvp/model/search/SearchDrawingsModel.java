package com.mjgallery.mjgallery.mvp.model.search;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;
import com.mjgallery.mjgallery.mvp.contract.search.SearchDrawingsContract;
import com.mjgallery.mjgallery.mvp.model.bean.search.SearchDrawingsBean;

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
 * Created by MVPArmsTemplate on 09/16/2019 21:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class SearchDrawingsModel extends BaseModel implements SearchDrawingsContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SearchDrawingsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<BaseResponse<List<SearchDrawingsBean>>> getSearchDrawings(Map<String, Object> map) {

        //使用rxcache缓存,上拉刷新则不读取缓存,加载更多读取缓存
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CommonService.class)
                .getSearchDrawings(map))
                .flatMap(new Function<Observable<BaseResponse<List<SearchDrawingsBean>>>, ObservableSource<BaseResponse<List<SearchDrawingsBean>>>>() {
                    @Override
                    public ObservableSource<BaseResponse<List<SearchDrawingsBean>>> apply(@NonNull Observable<BaseResponse<List<SearchDrawingsBean>>> listObservable) throws Exception {
                        return listObservable;


                    }
                });
    }
}