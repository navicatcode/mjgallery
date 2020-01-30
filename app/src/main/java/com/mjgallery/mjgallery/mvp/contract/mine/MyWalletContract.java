package com.mjgallery.mjgallery.mvp.contract.mine;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MeMyAccount;
import com.mjgallery.mjgallery.mvp.model.bean.WithdrawDepositBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/20/2019 19:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MyWalletContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onDoShare();
        RxPermissions getRxPermissions();
        void onMeWithdrawMsg(BaseResponse<WithdrawDepositBean> response);
        void onMeWithdraw(BaseResponse<WithdrawDepositBean> response);
        void onMeMyAccount(BaseResponse<MeMyAccount> response);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<WithdrawDepositBean>> getMeWithdrawMsg(@Field("token") String token);
        Observable<BaseResponse<WithdrawDepositBean>> getMeWithdraw(@FieldMap Map<String, Object> map);
        Observable<BaseResponse<MeMyAccount>> getMeMyAccount(@Field("token") String token);
    }
}
