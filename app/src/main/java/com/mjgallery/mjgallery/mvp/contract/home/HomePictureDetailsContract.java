package com.mjgallery.mjgallery.mvp.contract.home;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeSubCommentDetailDosBean;
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
 * Created by MVPArmsTemplate on 09/19/2019 18:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface HomePictureDetailsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        //首页详情
        void getPictureDetail(HomeDetailsBean homeDetailsBean);

        //获取权限
        RxPermissions getRxPermissions();

        //分享
        void onDoShare();


        //添加收藏
        void onAddCollection(BaseResponse baseResponse);

        //取消收藏
        void onCancelCollection(BaseResponse baseResponse);        //添加收藏

        //添加关注人
        void onCancelUser(BaseResponse baseResponse);

        //取消关注人
        void onConcernUser(BaseResponse baseResponse);


        //期数列表
        void getTermsByYear(List<String> stringList);

        //评论列表
        void onCommentDetail(HomeDetailsCommentsBean homeDetailsCommentsBean);

        //删除评论
        void onDeleteComment(BaseResponse baseResponse);

        //点赞
        void onLike(BaseResponse baseResponse);

        //添加评论
        void onAddCommentRecommend(BaseResponse<HomeDetailsCommentsBean.DataBean> dataBeanBaseResponse);

        //评论数据
        void onSubCommentDetail(HomeSubCommentDetailDosBean subCommentDetailDosBeans);


        void onLotteryRecordDtosBean(LotteryRecordBean.LotteryRecordDtosBean lotteryRecordDtosBean);

        //app踩
        void onStepOn(BaseResponse baseResponse);

        //图片下载
        void onImgDownload();

        //广告
        void onAdvert(List<HomeBanner> resultBeanList);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<HomeDetailsBean>> getPictureDetail(@FieldMap Map<String, Object> map);


        Observable<BaseResponse<LotteryRecordBean.LotteryRecordDtosBean>> getLotteryRecordDtosBean(@FieldMap Map<String, Object> map);

        //评论数据
        Observable<BaseResponse<HomeDetailsCommentsBean>> getCommentDetail(@FieldMap Map<String, Object> map);

        //评论数据
        Observable<BaseResponse<HomeSubCommentDetailDosBean>> getSubCommentDetail(@FieldMap Map<String, Object> map);

        //删除评论
        Observable<BaseResponse> getDeleteComment(@FieldMap Map<String, Object> map);


        //点赞
        Observable<BaseResponse> getLike(@FieldMap Map<String, Object> map);


        //添加评论
        Observable<BaseResponse<HomeDetailsCommentsBean.DataBean>> getAddCommentRecommend(@FieldMap Map<String, Object> map);

        //添加收藏
        Observable<BaseResponse> getAddCollection(@FieldMap Map<String, Object> map);

        //取消收藏
        Observable<BaseResponse> getCancelCollection(Map<String, Object> map);

        //添加关注人
        Observable<BaseResponse> getConcernUser(@FieldMap Map<String, Object> map);

        //取消关注人
        Observable<BaseResponse> getCancelUser(@FieldMap Map<String, Object> map);

        Observable<BaseResponse<List<String>>> getTermsByYear(@FieldMap Map<String, Object> map);

        Observable<BaseResponse> getStepOn(@FieldMap Map<String, Object> map);

        //广告
        Observable<BaseResponse<List<HomeBanner>>> getAdvert(@Field("type") int type);

    }
}
