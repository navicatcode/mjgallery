package com.mjgallery.mjgallery.app.api;

import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.AboutUsBean;
import com.mjgallery.mjgallery.mvp.model.bean.AndroidGetAccountXBean;
import com.mjgallery.mjgallery.mvp.model.bean.CollectionBean;
import com.mjgallery.mjgallery.mvp.model.bean.HomeBanner;
import com.mjgallery.mjgallery.mvp.model.bean.InvitedPlayersBean;
import com.mjgallery.mjgallery.mvp.model.bean.LoginResponse;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryDataListBean;
import com.mjgallery.mjgallery.mvp.model.bean.LotteryRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.MyShowBean;
import com.mjgallery.mjgallery.mvp.model.bean.NoticeBean;
import com.mjgallery.mjgallery.mvp.model.bean.UserInformation;
import com.mjgallery.mjgallery.mvp.model.bean.VIPWithdrawListBean;
import com.mjgallery.mjgallery.mvp.model.bean.VersionUpdateBean;
import com.mjgallery.mjgallery.mvp.model.bean.WithdrawDepositBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoverSubCommentDetailDosBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDataBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.DiscoveryDetailBean;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.discoverdetailsdata.PetElvesInFoViewBean;
import com.mjgallery.mjgallery.mvp.model.bean.hisinformation.HisInformationBean;
import com.mjgallery.mjgallery.mvp.model.bean.hisinformation.HisInformationLikeBean;
import com.mjgallery.mjgallery.mvp.model.bean.hisinformation.HisInformationWorksBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeDetailsCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeSubCommentDetailDosBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.HomeViewBean;
import com.mjgallery.mjgallery.mvp.model.bean.home.LotteryPlan;
import com.mjgallery.mjgallery.mvp.model.bean.mine.GetUserInfoBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MeMyAccount;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MineNoticeBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyAccountUserBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyAttentionItemBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyAttentionnNumberBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyCommentsBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.myvip.MyVIPMessageBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.RewardBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.RewardViewBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.SignRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.personal.PersonalBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.SignRewardBean;
import com.mjgallery.mjgallery.mvp.model.bean.search.SearchDrawingsBean;
import com.mjgallery.mjgallery.mvp.model.bean.search.SearchUserBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

public interface CommonService {


    /**
     * 首页banner
     *
     * @return
     */
    @FormUrlEncoded
    @POST("recommend/getAdvert")
    Observable<BaseResponse<List<HomeBanner>>> getAdvert(@Field("type") int type);

    /**
     * 获取首页公告
     *
     * @return
     */
    @Streaming
    @POST("recommend/getNotice")
    Observable<BaseResponse<List<NoticeBean>>> getNotice();

    /**
     * 获取年份
     *
     * @return
     */
    @Streaming
    @POST("recommend/getPictureMenu")
    Observable<BaseResponse<List<String>>> getPictureMenu();


    /**
     * 登录
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("auth/wx/login")
    Observable<LoginResponse> login(@FieldMap Map<String, Object> map);


    /**
     * app首页查看图片视图
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("recommend/getSmallPictrues")
    Observable<BaseResponse<List<HomeBean>>> getSmallPictrues(@FieldMap Map<String, Object> map);

    /**
     * 获取期数
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("recommend/getTermsByYear")
    Observable<BaseResponse<List<String>>> getTermsByYear(@FieldMap Map<String, Object> map);


    /**
     * 首页图片详情
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("recommend/getPictureDetail")
    Observable<BaseResponse<HomeDetailsBean>> getPictureDetail(@FieldMap Map<String, Object> map);

    /**
     * 投注记录
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("lottery/lottery/record")
    Observable<BaseResponse<List<LotteryRecordBean>>> getRecord(@FieldMap Map<String, Object> map);

    /**
     * app首页查看图片视图
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("discovery/getDiscoveryView")
    Observable<BaseResponse<List<HomeViewBean>>> getDiscoveryView(@FieldMap Map<String, Object> map);


    /**
     * 发送验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST("auth/sendCode")
    Observable<BaseResponse> sendCode(@FieldMap Map<String, Object> map);


    /**
     * 完善注册信息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("auth/register")
    Observable<BaseResponse> register(@FieldMap Map<String, Object> map);

    /**
     * 账号密码注册
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("auth/passwordLogin")
    Observable<LoginResponse> getPasswordLogin(@FieldMap Map<String, Object> map);


    /**
     * 忘记密码
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/forgetPassword")
    Observable<BaseResponse> getForgetPassword(@FieldMap Map<String, Object> map);


    /**
     * 获取收藏列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/myCollection")
    Observable<BaseResponse<List<CollectionBean>>> getMyCollection(@FieldMap Map<String, Object> map);


    /**
     * 获取个人中心的信息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/myIndex")
    Observable<BaseResponse<UserInformation>> getUserInformation(@FieldMap Map<String, Object> map);

    /**
     * 删除收藏
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/cancelCollection")
    Observable<BaseResponse> getCollectionDelete(@FieldMap Map<String, Object> map);

    /**
     * 完善个人信息
     *
     * @param map
     * @param file
     * @return
     */
    @Multipart
    @POST("me/updatePerfect")
    Observable<BaseResponse> getUpdatePerfect(@QueryMap Map<String, Object> map,
                                              @Part MultipartBody.Part file);


    /**
     * 通知列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("discovery/myShowList")
    Observable<BaseResponse<List<MyShowBean>>> getMyShowList(@FieldMap Map<String, Object> map);


    /**
     * 发现列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/queryNotifyList")
    Observable<BaseResponse<List<MineNoticeBean>>> getQueryNotifyList(@FieldMap Map<String, Object> map);


    /**
     * 发现图文
     *
     * @param map
     * @return
     */
    @Multipart
    @POST("discovery/publishDiscovery")
    Observable<BaseResponse> getPublishDiscovery(@QueryMap Map<String, Object> map,
                                                 @Part() List<MultipartBody.Part> parts);


    /**
     * 退出登陆
     *
     * @return
     */
    @FormUrlEncoded
    @POST("auth/loginOut?")
    Observable<BaseResponse> getLoginOut(@Field("token") String token);


    /**
     * 我的评论列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/myComment")
    Observable<BaseResponse<List<MyCommentsBean>>> getMyComment(@FieldMap Map<String, Object> map);

    /**
     * 我的评论列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/androidGetAccount")
    Observable<BaseResponse<AndroidGetAccountXBean>> getAndroidGetAccount(@FieldMap Map<String, Object> map);


    /**
     * 删除发现
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("discovery/show/delete")
    Observable<BaseResponse> getDiscoveryDelete(@FieldMap Map<String, Object> map);

    /**
     * 删除发现
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/getConcernCount")
    Observable<BaseResponse<MyAttentionnNumberBean>> getConcernCount(@FieldMap Map<String, Object> map);


    /**
     * 我的关注列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/getMyConcern")
    Observable<BaseResponse<List<MyAttentionItemBean>>> getMyConcern(@FieldMap Map<String, Object> map);


    /**
     * 我的关注列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/getMyFans")
    Observable<BaseResponse<List<MyAttentionItemBean>>> getMyFans(@FieldMap Map<String, Object> map);


    /**
     * app发现他人资料
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("herInformation/herMessage")
    Observable<BaseResponse<HisInformationBean>> getHerMessage(@FieldMap Map<String, Object> map);


    /**
     * app发现他人资料
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("herInformation/herlike")
    Observable<BaseResponse<List<HisInformationLikeBean>>> getHerlike(@FieldMap Map<String, Object> map);


    /**
     * app发现他人获取作品
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("herInformation/herInformation")
    Observable<BaseResponse<List<HisInformationWorksBean>>> getHisInformationWorks(@FieldMap Map<String, Object> map);


    /**
     * 账户信息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/androidGetUser")
    Observable<BaseResponse<MyAccountUserBean>> getMyAccountUser(@FieldMap Map<String, Object> map);

    /**
     * 取消关注
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/cancelUser")
    Observable<BaseResponse> getCancelUser(@FieldMap Map<String, Object> map);


    /**
     * 添加关注
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/concernUser")
    Observable<BaseResponse> getConcernUser(@FieldMap Map<String, Object> map);


    /**
     * 通知列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("herInformation/herDynamic")
    Observable<BaseResponse<List<MyShowBean>>> getHerInformationList(@FieldMap Map<String, Object> map);

    /**
     * 圈子资料列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("discovery/info/type")
    Observable<BaseResponse<List<DiscoveryDataBean>>> getDiscoveryDataList(@Field("type") int type);


    /**
     * 提现资料(带图片的)
     *
     * @param map
     * @return
     */
    @Multipart
    @POST("me/setWithdrawMsg")
    Observable<BaseResponse<String>> getWithdrawMsgImg(@QueryMap Map<String, Object> map, @Part MultipartBody.Part parts);

    /**
     * 提现资料
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/setWithdrawMsg")
    Observable<BaseResponse<String>> getWithdrawMsg(@FieldMap Map<String, Object> map);


    /**
     * app首页查看评论
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("recommend/getCommentDetail")
    Observable<BaseResponse<HomeDetailsCommentsBean>> getCommentDetail(@FieldMap Map<String, Object> map);

    /**
     * /**
     * app首页查看评论
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("recommend/getCommentDetail")
    Observable<BaseResponse<HomeSubCommentDetailDosBean>> getSubCommentDetail(@FieldMap Map<String, Object> map);

    /**
     * app首页查看评论
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("discovery/getCommentDetail")
    Observable<BaseResponse<DiscoveryCommentsBean>> getDiscoveryCommentDetail(@FieldMap Map<String, Object> map);

    /**
     * app首页查看评论
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("discovery/getCommentDetail")
    Observable<BaseResponse<DiscoverSubCommentDetailDosBean>> getDiscoverSubCommentDetailDosBean(@FieldMap Map<String, Object> map);


    /**
     * 删除评论
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("recommend/deleteComment")
    Observable<BaseResponse> getDeleteComment(@FieldMap Map<String, Object> map);


    /**
     * 点赞
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("recommend/picThumbUp")
    Observable<BaseResponse> getLike(@FieldMap Map<String, Object> map);


    /**
     * 添加收藏
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/addCollection")
    Observable<BaseResponse> getAddCollection(@FieldMap Map<String, Object> map);

    /**
     * 添加评论
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("recommend/commentRecommend")
    Observable<BaseResponse<HomeDetailsCommentsBean.DataBean>> getAddCommentRecommend(@FieldMap Map<String, Object> map);


    /**
     * 向阿里api请求获取银行卡号识别信息
     *
     * @return cardNo  银行卡号
     */
    @Headers({"Domain-Name: douban"}) // 加上 Domain-Name header
    @GET("/validateAndCacheCardInfo.json")
    Observable<ResponseBody> getBook(@Query("cardNo") String cardNo, @Query("cardBinCheck") boolean cardBinCheck);


    /**
     * 隐私设置
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/privacy")
    Observable<BaseResponse> getMePrivacy(@FieldMap Map<String, Object> map);

    /**
     * 修改手机号码
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("change/modifyhone")
    Observable<BaseResponse> getChangeModifyHone(@FieldMap Map<String, Object> map);

    /**
     * 发现点赞
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("discovery/thumbUpDiscovery")
    Observable<BaseResponse> getThumbUpDiscovery(@FieldMap Map<String, Object> map);

    /**
     * 发现删除评论
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("discovery/deleteComment")
    Observable<BaseResponse> getCircleDeleteComment(@FieldMap Map<String, Object> map);

    /**
     * 发现添加评论
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("discovery/commentDiscovery")
    Observable<BaseResponse<DiscoveryCommentsBean.DataBean>> getAddCommentDiscovery(@FieldMap Map<String, Object> map);

    /**
     * 圈子图片详情
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("discovery/getDiscoveryDetail")
    Observable<BaseResponse<DiscoveryDetailBean>> getDiscoveryDetail(@FieldMap Map<String, Object> map);

    /**
     * 修改支付密码
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("change/changePayPassword")
    Observable<BaseResponse> getChangePayPassword(@FieldMap Map<String, Object> map);

    /**
     * 修改登录密码
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("change/changeLoginPassword")
    Observable<BaseResponse> getChangeLoginPassword(@FieldMap Map<String, Object> map);

    /**
     * 意见反馈
     *
     * @param map
     * @return
     */
    @Multipart
    @POST("me/feedback")
    Observable<BaseResponse> getMeFeedback(@QueryMap Map<String, Object> map, @Part MultipartBody.Part parts);

    /**
     * 提现金额
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/withdraw")
    Observable<BaseResponse<WithdrawDepositBean>> getMeWithdraw(@FieldMap Map<String, Object> map);


    /**
     * 宠物精灵列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("discovery/info/view")
    Observable<BaseResponse<PetElvesInFoViewBean>> getPetElvesInFoViewList(@FieldMap Map<String, Object> map);

    /**
     * 提现信息读取
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("me/withdrawMsg")
    Observable<BaseResponse<WithdrawDepositBean>> getMeWithdrawMsg(@Field("token") String token);

    /**
     * 提现信息修改
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/withdrawUpMsg")
    Observable<BaseResponse> getMeWithdrawUpMsg(@FieldMap Map<String, Object> map);


    /**
     * 下一次开奖时间
     *
     * @return
     */
    @FormUrlEncoded
    @POST("lottery/lotteryPlan/nextTimePlan")
    Observable<BaseResponse<LotteryPlan>> getLotteryPlan(@FieldMap Map<String, Object> map);

    /**
     * app按月份查询开奖日期
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("lottery/lotteryPlan/listByMonth")
    Observable<BaseResponse<List<String>>> getPlanListByMonth(@Field("year") String year, @Field("month") String month);


    /**
     * app获取版本更新
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("common/version/update")
    Observable<BaseResponse<VersionUpdateBean>> getVersionUpdate(@FieldMap Map<String, Object> map);

    /**
     * 查看当前开奖结果
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("lottery/present/lotteryRecord")
    Observable<BaseResponse<LotteryRecordBean.LotteryRecordDtosBean>> getLotteryRecordDtosBean(@FieldMap Map<String, Object> map);

    /**
     * 获取图纸搜索数据
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("common/search")
    Observable<BaseResponse<List<SearchDrawingsBean>>> getSearchDrawings(@FieldMap Map<String, Object> map);

    /**
     * 获取搜索资料数据
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("common/search")
    Observable<BaseResponse<List<DiscoveryDataBean>>> getSearchData(@FieldMap Map<String, Object> map);

    /**
     * 获取搜索用户数据
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("common/search")
    Observable<BaseResponse<List<SearchUserBean>>> getSearchUser(@FieldMap Map<String, Object> map);

    /**
     * 指定日期查看期数
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("lottery/selectByYery")
    Observable<BaseResponse<List<String>>> getSelectByYery(@FieldMap Map<String, Object> map);

    /**
     * app踩
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("common/stepOn")
    Observable<BaseResponse> getStepOn(@FieldMap Map<String, Object> map);


    /**
     * app关于我们
     *
     * @return
     */
    @Streaming
    @POST("common/aboutMe")
    Observable<BaseResponse<List<AboutUsBean>>> getAboutMe();


    /**
     * 手机号注册
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("auth/phoneRegister")
    Observable<LoginResponse> phoneRegister(@FieldMap Map<String, Object> map);
    /**
     * 我的邀请
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/myInvite")
    Observable<BaseResponse<List<InvitedPlayersBean>>> getMyInvite(@FieldMap Map<String, Object> map);

    /**
     * 我的邀请
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("lottery/lottery/data")
    Observable<BaseResponse<List<LotteryDataListBean>>> getLotteryData(@FieldMap Map<String, Object> map);

    /**
     * 钱包信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST("me/myAccount")
    Observable<BaseResponse<MeMyAccount>> getMeMyAccount(@Field("token") String token);

    /**
     * 钱包收支列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/accountList")
    Observable<BaseResponse<List<VIPWithdrawListBean>>> getMeAccountList(@FieldMap Map<String, Object> map);


    /**
     * 任务列表
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/myTaskAll")
    Observable<BaseResponse<PersonalBean>> getMeMyTaskAll(@FieldMap Map<String, Object> map);

    /**
     * 领取任务奖励
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/getReceive")
    Observable<BaseResponse> getMeGetReceive(@FieldMap Map<String, Object> map);


    /**
     * 我的vip信息
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/myVip")
    Observable<BaseResponse<MyVIPMessageBean>> getMyVip(@Field("token") String token);

    /**
     * 领取晋升奖励
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("me/myVipMessage")
    Observable<BaseResponse<MyVIPMessageBean>> getMyVipMessage(@FieldMap Map<String, Object> map);




    /**
     * 签到记录
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("sign/signRecord")
    Observable<BaseResponse<SignRecordBean>> getSignRecord(@FieldMap Map<String, Object> map);

    /**
     * 累积签到整体展示
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("sign/getRewardView")
    Observable<BaseResponse<RewardViewBean>> getRewardView(@Field("token") String token);

    /**
     * 累积签到领取奖励+签到
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("sign/getReward")
    Observable<BaseResponse<RewardBean>> getReward(@FieldMap Map<String, Object> map);


    /**
     * 查看奖励
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("sign/signReward")
    Observable<BaseResponse<SignRewardBean>> getSignReward(@FieldMap Map<String, Object> map);

    /**
     * app他人信息获取他人评论记录
     *
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("herInformation/others/comment")
    Observable<BaseResponse<List<GetUserInfoBean>>> getHerInformation(@FieldMap Map<String, Object> map);

    /**
     * 分享文章成功后发送给后台做记录
     *
     * @return cardNo  银行卡号
     */
    @Headers({"Domain-Name: fenxiao"}) // 加上 Domain-Name header
    @POST("index/shareStatus")
    Observable<ResponseBody> getFenXiaoJiLu(@Query("token") String token);


    /**
     * app他人信息获取他人评论记录
     *
     * @param map
     * @return
     */
    @Streaming
    @POST("change/getIpList")
    Observable<BaseResponse<List<String>>> getChangeGetIpList();

//     * 添加收藏
//     * @param token
//     * @return
//     */
//    @FormUrlEncoded
//    @POST("auth/loginOut?")
//    Observable<BaseResponse> getLoginOut(@Field("token") String token);


//
//    /**
//     * 发现图文
//     *
//     * @param map
//     * @param file   /**
//     * @return
//     */
//    @Multipart
//    @POST("discovery/publishDiscovery")
//    Observable<BaseResponse> getPublishDiscovery(@QueryMap Map<String, Object> map,
//                                                 @Part() List<MultipartBody.Part> parts);



    /* *//**
     * 登录
     *
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/auth/login")
    Observable<BaseResponse<RegistBean>> login(@FieldMap Map<String, Object> map);

    *//**
     * 发送验证码
     *
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/auth/sendCode")
    Observable<BaseResponse> sendCode(@FieldMap Map<String, String> map);

    *//**        /App/api/auth/sendCode
     * 修改密码
     *
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/changePassword")
    Observable<BaseResponse> changePassworld(@FieldMap Map<String, String> map);

    *//**
     * 注册
     *
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/auth/register")
    Observable<BaseResponse<RegistBean>> sendRegister(@FieldMap Map<String, String> map);

    *//**
     * 忘记密码
     *
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/auth/forgetPassword")
    Observable<BaseResponse> forgetPassword(@FieldMap Map<String, String> map);

    *//**
     * 校验验证码
     *
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/auth/validateMobileCode")
    Observable<BaseResponse<ValidateBean>> validate(@FieldMap Map<String, String> map);


    *//**
     * 用户信息
     *
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/info")
    Observable<BaseResponse<UserBean>> userInfo(@Field("token") String map);

    @Multipart
    @POST("/yixin/app/user/update")
    Observable<BaseResponse<Object>> sendUpdateUserInfo(@Part List<MultipartBody.Part> partLis);

    *//**
     * 退出
     *
     * @param string
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/auth/logout")
    Observable<BaseResponse> logout(@Field("token") String string);

    *//**
     * 好友列表
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/listFriend")
    Observable<BaseResponse<AddressListBean>> getListFriend(@Field("token") String token);

    *//**
     * 好友列表
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/listFriend")
    Observable<BaseResponse<AddressListBean>> getListFriend(@FieldMap Map<String, Object> map);

    *//**
     * 用户查找
     *
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/search")
    Observable<BaseResponse<UserSearchBean>> getUserSearchDate(@FieldMap Map<String, Object> map);
    *//**
     * 用户查找
     *
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/searchForDispatch")
    Observable<BaseResponse<AddressListBean>> searchForDispatch(@FieldMap Map<String, Object> map);

    @Multipart
    @POST("/yixin/picture/uploadPicture")
    Observable<BaseResponse> uploadImage(@Part List<MultipartBody.Part> parts);//上传图片

    *//**
     * 获取图片列表
     *
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/picture/list")
    Observable<BaseResponse<FriendInfoBean>> getImages(@FieldMap Map<String, Object> map);


    *//**
     * 搜索群
     *
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/search")
    Observable<BaseResponse<SearchBean>> searchGroup(@FieldMap Map<String, Object> map);


    *//**
     * 同意添加好友
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/agree")
    Observable<BaseResponse<Object>> agreeInvitation(@FieldMap Map<String, String> map);


    *//**
     * 创建群
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/createGroup")
    Observable<BaseResponse<CreateGroupBean>> createGroup(@FieldMap Map<String, String> map);


    *//**
     * 群组
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/listGroup")
    Observable<BaseResponse<GroupListBean>> getListGroup(@Field("token") String string);

    *//**
     * 群组
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/groupInfo")
    Observable<BaseResponse<GroupData>> getData(@FieldMap Map<String, Object> map);

    *//**
     * 修改手機號
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/changeMobile")
    Observable<BaseResponse> updatePhone(@FieldMap Map<String, Object> map);

    @Multipart
    @POST("/yixin/app/chat/sendToGroup")
    Observable<BaseResponse> sendToGroup(@Part List<MultipartBody.Part> partLis);

    @Multipart
    @POST("/yixin/app/chat/sendToUser")
    Observable<BaseResponse> sendToUser(@Part List<MultipartBody.Part> partLis);

    @Multipart
    @POST("/yixin/app/picture/uploadPicture")
    Observable<BaseResponse<Object>> uploadPicture(@Part List<MultipartBody.Part> partLis);

    *//**
     * 用户图片
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/picture/list")
    Observable<BaseResponse<AddImageData>> getAddImageData(@FieldMap Map<String, Object> map);

    *//**
     * 好友信息
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/friendInfo")
    Observable<BaseResponse<FriendInfoDataBean>> getFriendInfo(@FieldMap Map<String, Object> map);


    *//**
     * 删除图片
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/picture/delete")
    Observable<BaseResponse<Object>> deleteImg(@FieldMap Map<String, Object> map);


    *//**
     * 微信登录
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/wechat/login")
    Observable<BaseResponse<WXLoginBean>> wxLogin(@Field("code") String code);

    *//**
     * 微信登录
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/deleteFriend")
    Observable<BaseResponse<Object>> deleteFriend(@FieldMap Map<String, Object> map);

    *//**
     * 群内禁言
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/disableUser")
    Observable<BaseResponse<Object>> disableUser(@FieldMap Map<String, Object> map);

    *//**
     * 取消群内禁言
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/enableUser")
    Observable<BaseResponse<Object>> undisableUser(@FieldMap Map<String, Object> map);

    *//**
     * 退群
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/leaveGroup")
    Observable<BaseResponse<Object>> leaveGroup(@FieldMap Map<String, Object> map);

    *//**
     * 踢人
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/removeUser")
    Observable<BaseResponse<Object>> removeUser(@FieldMap Map<String, Object> map);

    *//**
     * 修改群名称
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/setGroupName")
    Observable<BaseResponse<Object>> updateGroupName(@FieldMap Map<String, Object> map);

    *//**
     * 修改群昵称
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/setNickname")
    Observable<BaseResponse<Object>> updateGroupNickName(@FieldMap Map<String, Object> map);

    *//**
     * 绑定微信
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/wechat/binding")
    Observable<BaseResponse> bindWX(@FieldMap Map<String, Object> map);

    *//**
     * 解除绑定微信
     *
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/wechat/deBinding")
    Observable<BaseResponse> unBindWx(@Field("token") String string);

    *//**
     * 校验验证码
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/auth/validateMobileCode")
    Observable<BaseResponse<ValidateMobileBean>> validateMobileCode(@FieldMap Map<String, Object> map);

    *//**
     * 校验验证码
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/pullToGroup")
    Observable<BaseResponse<Object>> pullToGroupe(@FieldMap Map<String, Object> map);

    *//**
     * 绑定手机号
     * @param map
     * @return
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/bindingMobile")
    Observable<BaseResponse> bindPhone(@FieldMap Map<String, Object> map);

    *//**
     * 绑定手机号
     *//*
    @FormUrlEncoded
    @POST("/yixin/auth/exist")
    Observable<BaseResponse<Integer>> existUser(@Field("mobile") String string);

    *//**
     * 删除群组
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/group/deleteGroup")
    Observable<BaseResponse<Object>> deleteGroup(@FieldMap Map<String, Object> map);

    *//**
     * 删除群组
     *//*
    @FormUrlEncoded
    @POST("/yixin/app/user/chatStatus")
    Observable<BaseResponse<ChatJinYanBean>> chatStatus(@FieldMap Map<String, Object> map);

    @POST("/yixin/app/system/contactUs")
    Observable<BaseResponse<AboutBean>> getAboutData();*/
}
