package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.di.component.DaggerAboutUsActivityComponent;
import com.mjgallery.mjgallery.language.CommSharedUtil;
import com.mjgallery.mjgallery.language.LanguageType;
import com.mjgallery.mjgallery.language.MultiLanguageUtil;
import com.mjgallery.mjgallery.mvp.contract.mine.AboutUsActivityContract;
import com.mjgallery.mjgallery.mvp.model.bean.AboutUsBean;
import com.mjgallery.mjgallery.mvp.presenter.mine.AboutUsActivityPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.mine.AboutUsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/12/2019 15:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AboutUsActivityActivity extends BaseActivity<AboutUsActivityPresenter>
        implements AboutUsActivityContract.View, AboutUsAdapter.IAboutUsAdapterClickListener {

    @BindView(R.id.ivTopReturn)
    ImageView ivTopReturn;
    @BindView(R.id.tvTopTitle)
    TextView tvTopTitle;
    @BindView(R.id.ivTopTitle)
    ImageView ivTopTitle;
    @BindView(R.id.rlTop)
    LinearLayout rlTop;
    @BindView(R.id.ivTopRight)
    TextView ivTopRight;
    @BindView(R.id.ivTopRightImg)
    ImageView ivTopRightImg;
    @BindView(R.id.btnQueDing)
    TextView btnQueDing;
    @BindView(R.id.rlQueDing)
    RelativeLayout rlQueDing;
    @BindView(R.id.ivRightHomeSearch)
    ImageView ivRightHomeSearch;
    @BindView(R.id.ivMaJinToKuImg)
    ImageView ivMaJinToKuImg;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    AboutUsAdapter aboutUsAdapter;
    List<AboutUsBean> aboutUsBeanList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAboutUsActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about_us; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        init();
    }
//
//    /**
//     * 点击事件监听
//     */
//    private void initClickListener() {
//        aboutUsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                if (aboutUsBeanList.size() > position) {
//                    AboutUsBean aboutUsBean = aboutUsBeanList.get(position);
//                    if (aboutUsBean.isUrl()) {
//                        Uri uri = Uri.parse(aboutUsBean.getValue());
//                        if (uri.isAbsolute()) {
//                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                            startActivity(intent);
//                        }
//                    }
//                }
//            }
//        });
//    }


    @OnClick({R.id.ivTopReturn, R.id.ivTopTitle, R.id.rlTop, R.id.ivTopRight, R.id.ivTopRightImg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    private void init() {
        aboutUsBeanList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        aboutUsAdapter = new AboutUsAdapter(R.layout.about_us_item, aboutUsBeanList,this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(aboutUsAdapter);
        mPresenter.getAboutMe();
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.about_us));
        int languageType = CommSharedUtil.getInstance(this).getInt(MultiLanguageUtil.SAVE_LANGUAGE, LanguageType.LANGUAGE_RW);
        //判断繁简体
        if (languageType == LanguageType.LANGUAGE_CHINESE_SIMPLIFIED)
            ivMaJinToKuImg.setImageResource(R.drawable.a_bout_bg_img);
        else
            ivMaJinToKuImg.setImageResource(R.drawable.a_bout_bg_img_fenti);
    }

    @Override
    public void onAboutMe(List<AboutUsBean> list) {
        aboutUsBeanList.clear();
        if (list != null && list.size() > 0) {
            aboutUsBeanList.addAll(list);
        }
        if (aboutUsAdapter != null) {
            aboutUsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onAboutUsAdapterClickListener(AboutUsBean aboutUsBean) {
        if (aboutUsBean.isUrl()) {
            Uri uri = Uri.parse(aboutUsBean.getValue());
            if (uri.isAbsolute()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        }
    }
}
