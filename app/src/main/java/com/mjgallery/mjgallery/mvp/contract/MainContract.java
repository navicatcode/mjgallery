package com.mjgallery.mjgallery.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.VersionUpdateBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/13/2019 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MainContract {
    interface View extends IView {
        void onPublishDiscovery(BaseResponse baseResponse);

        RxPermissions getRxPermissions();

        void onDeleteCacheDirFile();

        void onVersionUpdate(VersionUpdateBean versionUpdateBean);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse> getPublishDiscovery(@QueryMap Map<String, Object> map,
                                                     @Part() List<MultipartBody.Part> parts);

        Observable<BaseResponse<VersionUpdateBean>> getVersionUpdate(@QueryMap Map<String, Object> map);

    }
}
