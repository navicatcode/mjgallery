package com.mjgallery.mjgallery.mvp.contract.login;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.LoginResponse;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 15:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface LoginTransparentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void onSendCode(BaseResponse baseResponse);

        void onPasswordLogin(LoginResponse baseResponse);

        void onForgetPassword(BaseResponse baseResponse);

        void onPhoneRegister(LoginResponse baseResponse);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseResponse> getSendCode(Map<String, Object> map);

        Observable<LoginResponse> getPasswordLogin(Map<String, Object> map);

        Observable<BaseResponse> getForgetPassword(Map<String, Object> map);

        Observable<LoginResponse> getPhoneRegister(Map<String, Object> map);


    }
}
