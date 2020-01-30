package com.mjgallery.mjgallery.mvp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PreferenceUtil;
import com.mjgallery.mjgallery.di.component.DaggerMyAttentionComponent;
import com.mjgallery.mjgallery.event.MyAttentionUpDataTopEvent;
import com.mjgallery.mjgallery.mvp.contract.mine.myattention.MyAttentionContract;
import com.mjgallery.mjgallery.mvp.model.bean.mine.MyAttentionnNumberBean;
import com.mjgallery.mjgallery.mvp.presenter.MyAttentionPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.fragmentpager.FragmentPagerAdapter;
import com.mjgallery.mjgallery.mvp.ui.fragment.mine.myattention.MyAttentionOneFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.mine.myattention.MyAttentionTwoFragment;

import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:我的关注界面
 * <p>
 * Created by MVPArmsTemplate on 08/18/2019 10:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */



public class MyAttentionActivity extends MJBaseActivity<MyAttentionPresenter> implements MyAttentionContract.View {
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
    @BindView(R.id.slidingTabLayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = new String[2];
    private MyPagerAdapter mAdapter;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyAttentionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_attention;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        GlideUtil.loadNormalImage(ivTopReturn, R.drawable.activity_return_while_img);
        tvTopTitle.setText(ArmsUtils.getString(BaseApplication.getInstance(),R.string.my_attention));
        tvTopTitle.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));
        isLogin();

    }


    @Override
    public void showLoading() {
        showLoadingAnimationDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingAnimationDialog();
    }

    @Override
    public void showMessage(@NonNull String message) {
        if(message==null)
            return;
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


    @OnClick({R.id.ivTopReturn, R.id.ivRightHomeSearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.ivRightHomeSearch: //需要后台提供新的接口
                break;
        }
    }

    @Override
    public void onConcernCount(MyAttentionnNumberBean myAttentionnNumberBean) {
        if (myAttentionnNumberBean != null) {
            mFragments.clear();
            mTitles[0] = ArmsUtils.getString(BaseApplication.getInstance(),R.string.focus_on) + myAttentionnNumberBean.getMyConcernCount();
            mTitles[1] = ArmsUtils.getString(BaseApplication.getInstance(),R.string.fans) + myAttentionnNumberBean.getMyFansCount();
            mFragments.add(MyAttentionOneFragment.newInstance());
            mFragments.add(MyAttentionTwoFragment.newInstance());
            mAdapter = new MyPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(mAdapter);
            slidingTabLayout.setViewPager(viewPager);
        }
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

    }


    @Subscriber(mode = ThreadMode.MAIN)
    public void onMyAttentionUpDataTopEvent(MyAttentionUpDataTopEvent myAttentionUpDataTopEvent) {
        int index = myAttentionUpDataTopEvent.getIndex();
        int count = myAttentionUpDataTopEvent.getCount();
        if (mAdapter != null) {
            if (index == 1) {
                mTitles[0] = ArmsUtils.getString(BaseApplication.getInstance(),R.string.focus_on) + count + "";
            } else {
                mTitles[1] =ArmsUtils.getString(BaseApplication.getInstance(),R.string.fans) + count + "";
            }
        }
        slidingTabLayout.notifyDataSetChanged();
    }

    @Override
    protected void requestData() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", PreferenceUtil.getInstance(BaseApplication.getInstance()).getString("token", ""));
        mPresenter.requestData(map);
    }
}
