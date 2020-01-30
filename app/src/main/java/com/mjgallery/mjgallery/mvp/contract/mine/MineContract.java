package com.mjgallery.mjgallery.mvp.contract.mine;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.UserInformation;
import com.mjgallery.mjgallery.mvp.model.bean.WithdrawDepositBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/15/2019 08:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MineContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onUserInformation(UserInformation userInformation);
        void onMeWithdraw(BaseResponse<WithdrawDepositBean> response);
        void onMeWithdrawMsg(BaseResponse<WithdrawDepositBean> response);

        RxPermissions getRxPermissions();

        void onSaveQRImg();
        void onUpdatePerfect(BaseResponse baseResponse);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<UserInformation>> getUserInformation(Map<String, Object> map);
        Observable<BaseResponse<WithdrawDepositBean>> getMeWithdraw(@FieldMap Map<String, Object> map);
        Observable<BaseResponse<WithdrawDepositBean>> getMeWithdrawMsg(@Field("token") String token);

        default Observable<BaseResponse> getUpdatePerfect(@PartMap Map<String, Object> map, @Part MultipartBody.Part file) {
            return null;
        }
    }

}
