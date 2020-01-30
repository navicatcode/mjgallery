package com.mjgallery.mjgallery.mvp.contract.discovery;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoverSubCommentDetailDosBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDetailBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/01/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface CirclePictureDetailsContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onDiscoveryDetail(DiscoveryDetailBean discoveryDetailBean);

        void onThumbUpDiscovery(BaseResponse baseResponse);
        //评论列表
        void onCommentDetail(DiscoveryCommentsBean homeDetailsCommentsBean);        //评论列表

        void onDiscoverSubCommentDetailDosBean(DiscoverSubCommentDetailDosBean discoverSubCommentDetailDosBean);

        //添加收藏
        void onAddCollection(BaseResponse baseResponse);

        //取消收藏
        void onCancelCollection(BaseResponse baseResponse);

        //添加评论
        void onAddCommentDiscovery(BaseResponse baseResponse);


        //删除评论
        void onCircleDeleteComment(BaseResponse baseResponse);



        RxPermissions getRxPermissions();

        void onDoShare();

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<DiscoveryDetailBean>> getDiscoveryDetail(@FieldMap Map<String, Object> map);

        //点赞
        Observable<BaseResponse> getThumbUpDiscovery(@FieldMap Map<String, Object> map);

        //评论数据
        Observable<BaseResponse<DiscoveryCommentsBean>> getCommentDetail(@FieldMap Map<String, Object> map);

        //评论数据
        Observable<BaseResponse<DiscoverSubCommentDetailDosBean>> getDiscoverSubCommentDetailDosBean(@FieldMap Map<String, Object> map);

        //添加收藏
        Observable<BaseResponse> getAddCollection(@FieldMap Map<String, Object> map);

        //取消收藏
        Observable<BaseResponse> getCancelCollection(Map<String, Object> map);

        //添加评论
        Observable<BaseResponse> getAddCommentDiscovery(@FieldMap Map<String, Object> map);

        //删除评论
        Observable<BaseResponse> getCircleDeleteComment(@FieldMap Map<String, Object> map);

    }
}
