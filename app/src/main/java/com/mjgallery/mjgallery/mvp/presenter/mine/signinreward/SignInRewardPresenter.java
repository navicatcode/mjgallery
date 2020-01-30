package com.mjgallery.mjgallery.mvp.presenter.mine.signinreward;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.event.RemoveTokenEvent;
import com.mjgallery.mjgallery.mvp.contract.mine.signinreward.SignInRewardContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.RewardViewBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.SignRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.SignRewardBean;

import org.simple.eventbus.EventBus;

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
 * Created by MVPArmsTemplate on 10/24/2019 09:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SignInRewardPresenter extends BasePresenter<SignInRewardContract.Model, SignInRewardContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SignInRewardPresenter(SignInRewardContract.Model model, SignInRewardContract.View rootView) {
        super(model, rootView);
    }


    /**
     * 签到记录
     *
     * @param map
     */
    public void getSignRecord(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getSignRecord(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<SignRecordBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<SignRecordBean> listBaseResponse) {
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onSignRecord(listBaseResponse.getResult());
                        }
                    }
                });

    }


    /**
     * 累积签到整体展示
     */
    public void getRewardView(String token) {
        mRootView.showLoading();
        mModel.getRewardView(token)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<RewardViewBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<RewardViewBean> listBaseResponse) {
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onRewardView(listBaseResponse.getResult());
                        }
                    }
                });

    }


    /**
     * 累积签到整体展示
     */
    public void getReward(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getReward(map)
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
                        EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                        mRootView.onReward(listBaseResponse);
                    }
                });

    }


    /**
     * 查看奖励
     */
    public void getSignReward(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getSignReward(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<SignRewardBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<SignRewardBean> listBaseResponse) {
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            if (listBaseResponse.getResult() != null) {
                                mRootView.onSignReward(listBaseResponse.getResult());
                            } else {
                                mRootView.onSignRewardError(listBaseResponse.getMessage());
                            }

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
