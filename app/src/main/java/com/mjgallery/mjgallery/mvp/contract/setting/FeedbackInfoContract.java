package com.mjgallery.mjgallery.mvp.contract.setting;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.Part;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 20:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface FeedbackInfoContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onMeFeedback(BaseResponse baseResponse);
        RxPermissions getRxPermissions();
        void onDeleteCacheDirFile();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse> getMeFeedback(@FieldMap Map<String, Object> map, @Part MultipartBody.Part file);
    }
}
