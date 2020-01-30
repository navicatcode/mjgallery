package com.mjgallery.mjgallery.mvp.presenter.hisinformation;

import android.app.Application;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.event.RemoveTokenEvent;
import com.mjgallery.mjgallery.mvp.contract.hisinformation.HisInformationWorksContract;
import com.mjgallery.mjgallery.mvp.model.bean.hisinformation.HisInformationWorksBean;

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
 * Created by MVPArmsTemplate on 08/20/2019 12:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */

/**
 * 我的作品
 */
@FragmentScope
public class HisInformationWorksPresenter extends BasePresenter<HisInformationWorksContract.Model, HisInformationWorksContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HisInformationWorksPresenter(HisInformationWorksContract.Model model, HisInformationWorksContract.View rootView) {
        super(model, rootView);
    }


    public void requestHisInformationWorks(Map<String, Object> map) {
        mRootView.showLoading();
        mModel.getHisInformationWorks(map)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 15))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<HisInformationWorksBean>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<HisInformationWorksBean>> listBaseResponse) {
                        if (listBaseResponse!= null) {
                            EventBus.getDefault().post(new RemoveTokenEvent(listBaseResponse.getCode()));
                            mRootView.onHisInformationWorks(listBaseResponse.getResult());
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
