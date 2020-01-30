package com.mjgallery.mjgallery.mvp.model.withdraw;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.app.api.CommonService;

import com.mjgallery.mjgallery.mvp.contract.withdraw.WithdrawPayContract;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.MultipartBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/24/2019 08:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class WithdrawPayModel extends BaseModel implements WithdrawPayContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public WithdrawPayModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse<String>> getWithdrawMsg(Map<String, Object> map, MultipartBody.Part parts) {

        if (parts!=null){
            return Observable.just (mRepositoryManager
                    .obtainRetrofitService(CommonService.class)
                    .getWithdrawMsgImg(map,parts))
                    .flatMap(new Function<Observable<BaseResponse<String>>, ObservableSource<BaseResponse<String>>>() {
                        @Override
                        public ObservableSource<BaseResponse<String>> apply(@NonNull Observable<BaseResponse<String>> listObservable) throws Exception {
                            return listObservable;

                        }
                    });
        }else{
            return Observable.just (mRepositoryManager
                    .obtainRetrofitService(CommonService.class)
                    .getWithdrawMsg(map))
                    .flatMap(new Function<Observable<BaseResponse<String>>, ObservableSource<BaseResponse<String>>>() {
                        @Override
                        public ObservableSource<BaseResponse<String>> apply(@NonNull Observable<BaseResponse<String>> listObservable) throws Exception {
                            return listObservable;

                        }
                    });
        }

    }
}