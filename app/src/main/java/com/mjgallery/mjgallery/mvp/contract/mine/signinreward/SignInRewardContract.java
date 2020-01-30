package com.mjgallery.mjgallery.mvp.contract.mine.signinreward;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.RewardBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.RewardViewBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.SignRecordBean;
import com.mjgallery.mjgallery.mvp.model.bean.mine.signrecord.SignRewardBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;


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
public interface SignInRewardContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onSignRecord(SignRecordBean signRecordBean);

        void onRewardView(RewardViewBean rewardViewBean);

        void onReward(BaseResponse baseResponse);

        void onSignReward(SignRewardBean signRewardBean);

        void onSignRewardError(String errorMessage);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<SignRecordBean>> getSignRecord(@FieldMap Map<String, Object> map);

        Observable<BaseResponse<RewardViewBean>> getRewardView(@Field("token") String token);

        Observable<BaseResponse<RewardBean>> getReward(@FieldMap Map<String, Object> map);


        Observable<BaseResponse<SignRewardBean>> getSignReward(@FieldMap Map<String, Object> map);
    }
}
