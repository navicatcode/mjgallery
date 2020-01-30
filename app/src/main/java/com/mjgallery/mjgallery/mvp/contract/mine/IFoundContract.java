package com.mjgallery.mjgallery.mvp.contract.mine;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.MyShowBean;
import com.mjgallery.mjgallery.mvp.model.bean.UserInformation;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 16:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface IFoundContract {
    interface View extends IView {
        void onPublishDiscovery(BaseResponse baseResponse);

        void onMyShowList(List<MyShowBean> myShowBeanList);
        
        void onDiscoveryDelete(BaseResponse baseResponse);

        void onUserInformation(UserInformation userInformation);

        void onAddCollection(BaseResponse baseResponse);

        RxPermissions getRxPermissions();
        void onDeleteCacheDirFile();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse> getPublishDiscovery(@QueryMap Map<String, Object> map,
                                                     @Part() List<MultipartBody.Part> parts);

        Observable<BaseResponse<List<MyShowBean>>> getMyShowList(@FieldMap Map<String, Object> map);

        Observable<BaseResponse> getDiscoveryDelete(@FieldMap Map<String, Object> map);

        Observable<BaseResponse<UserInformation>> getUserInformation(Map<String, Object> map);


        //添加收藏
        Observable<BaseResponse> getAddCollection(@FieldMap Map<String, Object> map);
    }
}
