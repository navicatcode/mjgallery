package com.mjgallery.mjgallery.mvp.contract.discovery.discoverydata;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mjgallery.mjgallery.app.BaseResponse;
import com.mjgallery.mjgallery.mvp.model.bean.discovery.discoverdetailsdata.PetElvesInFoViewBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/03/2019 21:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface PetElvesContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onPetElvesInFoViewList(PetElvesInFoViewBean beans);


        //添加收藏
        void onAddCollection(BaseResponse baseResponse);

        //取消收藏
        void onCancelCollection(BaseResponse baseResponse);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<PetElvesInFoViewBean>> getPetElvesInFoViewList(@FieldMap Map<String, Object> map);


        //添加收藏
        Observable<BaseResponse> getAddCollection(@FieldMap Map<String, Object> map);

        //取消收藏
        Observable<BaseResponse> getCancelCollection(Map<String, Object> map);

    }
}
