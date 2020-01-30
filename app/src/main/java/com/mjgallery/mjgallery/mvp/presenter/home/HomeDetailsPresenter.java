package com.mjgallery.mjgallery.mvp.presenter.home;

import android.app.Application;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.contract.home.HomeDetailsContract;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeSubCommentDetailDosBean;

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
 * Created by MVPArmsTemplate on 08/09/2019 14:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class HomeDetailsPresenter extends BasePresenter<HomeDetailsContract.Model, HomeDetailsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HomeDetailsPresenter(HomeDetailsContract.Model model, HomeDetailsContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 使用 2017 Google IO 发布的 Architecture Components
     * 中的 Lifecycles 的新特性 (此特性已被加入 Support library)
     * 使 {@code Presenter} 可以与 {@link SupportActivity} 和
     * {@link Fragment} 的部分生命周期绑定
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {


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



    public void getSubCommentDetail(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getSubCommentDetail(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HomeSubCommentDetailDosBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HomeSubCommentDetailDosBean> listBaseResponse) {
                        if (listBaseResponse.getResult() != null) {
                            mRootView.onSubCommentDetail(listBaseResponse.getResult());

                        }
                    }
                });


    }



    public void getPictureDetail(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getPictureDetail(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HomeDetailsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HomeDetailsBean> listBaseResponse) {
                        if (listBaseResponse.getResult() != null && listBaseResponse.getCode() == 0) {
                            mRootView.getPictureDetail(listBaseResponse.getResult());

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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HomeDetailsCommentsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HomeDetailsCommentsBean> listBaseResponse) {
                        if (listBaseResponse.getResult() != null ) {
                            mRootView.onCommentDetail(listBaseResponse.getResult());

                        }
                    }
                });


    }


    //获取期数数据
    public void getTermsByYear(Map<String, Object> map) {
        mModel.getTermsByYear(map)
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
                            mRootView.getTermsByYear(listBaseResponse.getResult());

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
                        if (listBaseResponse!= null) {
                            mRootView.onAddCollection(listBaseResponse);

                        }
                    }
                });
    }


    //添加评论
    public void getAddCommentRecommend(Map<String, Object> map) {
        mModel.getAddCommentRecommend(map)
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
                            mRootView.onAddCommentRecommend(listBaseResponse);

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
                        if (listBaseResponse!= null) {
                            mRootView.onCancelCollection(listBaseResponse);

                        }
                    }
                });
    }

    //点赞
    public void getLike(Map<String, Object> map) {
        mModel.getLike(map)
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
                        if (listBaseResponse!= null) {
                            mRootView.onLike(listBaseResponse);
                        }
                    }
                });
    }

    //删除评论
    public void getDeleteComment(Map<String, Object> map) {
        mModel.getDeleteComment(map)
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
                            mRootView.onDeleteComment(listBaseResponse);

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
