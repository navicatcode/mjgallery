package com.mjgallery.mjgallery.mvp.contract.home;

import android.app.Activity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.NoticeBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.LotteryPlan;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;


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
public interface HomeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Activity getActivity();


        void getHomeBanner(List<HomeBanner> resultBeanList);

        void getNoticeBanner(List<String> noticeStringList);

        void getPictureMenu(List<String> stringList);

        void getSmallPictrues(List<HomeBean> homeBeanList);

        RxPermissions getRxPermissions();

        void onDoShare();

        /**下一次开奖时间回调*/
        void onLotteryPlan(LotteryPlan lotteryPlan);

        //获取最新一次开奖的码号回调
        void onRecord(List<LotteryRecordBean> lotteryRecordBeans);

        void onLotteryRecordDtosBean(LotteryRecordBean.LotteryRecordDtosBean lotteryRecordDtosBean);

        void onChangeGetIpList(BaseResponse<List<String>> baseResponse);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<HomeBanner>>> getAdvert(@Field("type") int type);

        Observable<BaseResponse<List<NoticeBean>>> getNotice();

        Observable<BaseResponse<List<String>>> getPictureMenu();

        Observable<BaseResponse<List<HomeBean>>> getSmallPictrues(Map<String, Object> map);

        //下一次开奖时间
        Observable<BaseResponse<LotteryPlan>> getLotteryPlan(Map<String, Object> map);

        //获取最新一次开奖的码号
        Observable<BaseResponse<List<LotteryRecordBean>>> getRecord(@FieldMap Map<String, Object> map);

        Observable<BaseResponse<LotteryRecordBean.LotteryRecordDtosBean>> getLotteryRecordDtosBean(@FieldMap Map<String, Object> map);

        Observable<BaseResponse<List<String>>> getChangeGetIpList();
    }
}
