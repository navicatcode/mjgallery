package com.mjgallery.mjgallery.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.CollectionBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 20:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface CollectionContract {
    interface View extends IView {
        void onMyCollection(List<CollectionBean> collectionBeanList, int count);

        void getCollectionDelete(BaseResponse baseResponse);

        RxPermissions getRxPermissions();

        void onDoShare();


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<CollectionBean>>> getMyCollection(Map<String, Object> map);

        Observable<BaseResponse> getCollectionDelete(Map<String, Object> map);
    }
}
