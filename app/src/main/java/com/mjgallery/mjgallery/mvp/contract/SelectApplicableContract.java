package com.mjgallery.mjgallery.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.mjgallery.mjgallery.app.BaseResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/21/2019 18:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface SelectApplicableContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        //期数列表
        void getTermsByYear(List<String> stringList);

        void getPictureMenu(List<String> stringList);

        void onSelectByYery(List<String> stringList);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<String>>> getTermsByYear(@FieldMap Map<String, Object> map);

        Observable<BaseResponse<List<String>>> getPictureMenu();

        Observable<BaseResponse<List<String>>> getSelectByYery(@FieldMap Map<String, Object> map);
    }
}
