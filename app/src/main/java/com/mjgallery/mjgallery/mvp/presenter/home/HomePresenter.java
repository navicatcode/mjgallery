package com.mjgallery.mjgallery.mvp.presenter.home;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.contract.home.HomeContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.NoticeBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.LotteryPlan;

import java.util.ArrayList;
import java.util.HashMap;
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
 * Created by MVPArmsTemplate on 08/06/2019 10:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    public HomePresenter(HomeContract.Model model, HomeContract.View rootView) {
        super(model, rootView);
    }



    public void externalStorage() {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
                mRootView.onDoShare();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("Request permissions failure");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showMessage("Need to go to the settings");
            }
        }, mRootView.getRxPermissions(), mErrorHandler);
    }


    //查看当前开奖结果
    public void getLotteryRecordDtosBean(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getLotteryRecordDtosBean(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<LotteryRecordBean.LotteryRecordDtosBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<LotteryRecordBean.LotteryRecordDtosBean> listBaseResponse) {
                        if (listBaseResponse.getResult() != null) {
                            mRootView.onLotteryRecordDtosBean(listBaseResponse.getResult());
                        }
                    }
                });
    }


    public void getAdvert(int type){
        mRootView.showLoading();
        mModel.getAdvert(type)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<HomeBanner>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<HomeBanner>> listBaseResponse) {
                        if (listBaseResponse.getResult() != null) {
                            mRootView.getHomeBanner(listBaseResponse.getResult());
                        }
                    }
                });
    }


    public void getNotice(){
        mRootView.showLoading();
        mModel.getNotice()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<NoticeBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<NoticeBean>> noticeBeans) {
                        if (noticeBeans.getResult() != null && noticeBeans.getCode() == 0) {
                            List<String> stringList = new ArrayList<>();
                            for (int i = 0; i < noticeBeans.getResult().size(); i++) {
                                String content= noticeBeans.getResult().get(i).getContent();
                                stringList.add(content);
                            }
                            mRootView.getNoticeBanner(stringList);

                        }
                    }
                });
    }

    public void getPictureMenu(){
        mRootView.showLoading();
        mModel.getPictureMenu()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<String>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<String>> listBaseResponse) {
                        if (listBaseResponse.getResult() != null) {
                            mRootView.getPictureMenu(listBaseResponse.getResult());

                        }
                    }
                });

    }



    public void requestDiscovery(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getSmallPictrues(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<HomeBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<HomeBean>> listBaseResponse) {
                        if (listBaseResponse.getResult() != null) {
                            mRootView.getSmallPictrues(listBaseResponse.getResult());
                        }
                    }
                });
    }


    public void getLotteryPlan(Map<String,Object> map) {
        mRootView.showLoading();
        mModel.getLotteryPlan(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<LotteryPlan>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<LotteryPlan> listBaseResponse) {
                        if (listBaseResponse.getCode() ==0) {
                            mRootView.onLotteryPlan(listBaseResponse.getResult());
                        }
                    }
                });


    }

    public void getRecord() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageIndex", 0);
        map.put("pageSize", 1);
        mModel.getRecord(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<LotteryRecordBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<LotteryRecordBean>> listBaseResponse) {
                        if (listBaseResponse.getCode() == 0) {
                            mRootView.onRecord(listBaseResponse.getResult());
                        }
                    }
                });
    }

    public void getChangeGetIpList(){
        mRootView.showLoading();
        mModel.getChangeGetIpList()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<String>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<String>> listBaseResponse) {
                        if (listBaseResponse.getResult() != null) {
                            mRootView.onChangeGetIpList(listBaseResponse);

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
