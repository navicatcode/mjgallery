package com.mjgallery.mjgallery.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.event.MyAttentionUpDataTopEvent;
import com.mjgallery.mjgallery.event.RemoveTokenEvent;
import com.mjgallery.mjgallery.mvp.contract.mine.myattention.MyAttentionOneContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyAttentionItemBean;

import org.simple.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/18/2019 15:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MyAttentionOnePresenter extends BasePresenter<MyAttentionOneContract.Model, MyAttentionOneContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MyAttentionOnePresenter(MyAttentionOneContract.Model model, MyAttentionOneContract.View rootView) {
        super(model, rootView);
    }


    public void requestDada(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getMyConcern(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<MyAttentionItemBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<MyAttentionItemBean>> listBaseResponse) {
                        if (listBaseResponse.getResult() != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            EventBus.getDefault().post(new MyAttentionUpDataTopEvent(1, listBaseResponse.getCount()));
                            mRootView.onMyConcern(listBaseResponse.getResult());
                        }
                    }
                });

    }


    /**
     * @param map
     */
    public void requestCancelUser(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getCancelUser(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse listBaseResponse) {
                        if (listBaseResponse.getResult() != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onCancelUser(listBaseResponse);
                        }
                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
