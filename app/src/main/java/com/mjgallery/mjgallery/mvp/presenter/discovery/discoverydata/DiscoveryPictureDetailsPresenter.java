package com.mjgallery.mjgallery.mvp.presenter.discovery.discoverydata;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.event.RemoveTokenEvent;
import com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata.DiscoveryPictureDetailsContract;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoverSubCommentDetailDosBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDetailBean;

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
 * Created by MVPArmsTemplate on 09/22/2019 14:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class DiscoveryPictureDetailsPresenter extends BasePresenter<DiscoveryPictureDetailsContract.Model, DiscoveryPictureDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public DiscoveryPictureDetailsPresenter(DiscoveryPictureDetailsContract.Model model, DiscoveryPictureDetailsContract.View rootView) {
        super(model, rootView);
    }


    public void externalStorage(boolean isDoShare) {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
                if (isDoShare) {
                    mRootView.onDoShare();
                } else {
                    mRootView.onImgDownload();
                }

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



    //获取开奖期数数据
    public void getSelectByYery(Map<String, Object> map) {
        mModel.getSelectByYery(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<String>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<String>> listBaseResponse) {
                        if (listBaseResponse.getResult() != null ) {
                            mRootView.onSelectByYery(listBaseResponse.getResult());
                        }
                    }
                });
    }


    public void getDiscoveryDetail(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getDiscoveryDetail(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DiscoveryDetailBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<DiscoveryDetailBean> listBaseResponse) {
                        if (listBaseResponse.getResult() != null && listBaseResponse.getCode() == 0) {
                            mRootView.onDiscoveryDetail(listBaseResponse.getResult());

                        }
                    }
                });


    }


    //添加收藏
    public void getAddCollection(Map<String, Object> map) {
        mModel.getAddCollection(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse listBaseResponse) {
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onAddCollection(listBaseResponse);

                        }
                    }
                });
    }




    public void getDiscoverSubCommentDetailDosBean(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getDiscoverSubCommentDetailDosBean(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DiscoverSubCommentDetailDosBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<DiscoverSubCommentDetailDosBean> listBaseResponse) {
                        if (listBaseResponse.getResult() != null) {
                            mRootView.onDiscoverSubCommentDetailDosBean(listBaseResponse.getResult());

                        }
                    }
                });


    }


    //添加关注人
    public void getStepOn(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getStepOn(map)
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
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onStepOn(listBaseResponse);
                        }
                    }
                });
    }


    //得到评论记录
    public void getCommentDetail(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getCommentDetail(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DiscoveryCommentsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<DiscoveryCommentsBean> listBaseResponse) {
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onCommentDetail(listBaseResponse.getResult());
                        }
                    }
                });


    }


    //添加评论
    public void getAddCommentDiscovery(Map<String, Object> map) {
        mModel.getAddCommentDiscovery(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<DiscoveryCommentsBean.DataBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<DiscoveryCommentsBean.DataBean> listBaseResponse) {
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onAddCommentDiscovery(listBaseResponse);
                        }
                    }
                });
    }


    //取消收藏
    public void getCancelCollection(Map<String, Object> map) {
        mModel.getCancelCollection(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse listBaseResponse) {
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onCancelCollection(listBaseResponse);

                        }
                    }
                });
    }

    //点赞
    public void getThumbUpDiscovery(Map<String, Object> map) {
        mModel.getThumbUpDiscovery(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse listBaseResponse) {
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onThumbUpDiscovery(listBaseResponse);
                        }
                    }
                });
    }

    //删除评论
    public void getCircleDeleteComment(Map<String, Object> map) {
        mModel.getCircleDeleteComment(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse listBaseResponse) {
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onCircleDeleteComment(listBaseResponse);

                        }
                    }
                });
    }


    //取消关注人
    public void getCancelUser(Map<String, Object> map) {
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
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onCancelUser(listBaseResponse);
                        }
                    }
                });

    }


    //添加关注人
    public void getConcernUser(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getConcernUser(map)
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
                        if (listBaseResponse != null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onConcernUser(listBaseResponse);
                        }
                    }
                });

    }

    //广告
    public void getAdvert(int type) {
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
                            mRootView.onAdvert(listBaseResponse.getResult());
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
