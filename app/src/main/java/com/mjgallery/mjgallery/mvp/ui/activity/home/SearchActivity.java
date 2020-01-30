package com.mjgallery.mjgallery.mvp.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.AppConstants;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.di.component.DaggerSearchComponent;
import com.mjgallery.mjgallery.event.SearchContentEvent;
import com.mjgallery.mjgallery.mvp.contract.search.SearchContract;
import com.mjgallery.mjgallery.mvp.presenter.search.SearchPresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.fragmentpager.FragmentPagerAdapter;
import com.mjgallery.mjgallery.mvp.ui.fragment.search.SearchDataFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.search.SearchDrawingsFragment;
import com.mjgallery.mjgallery.mvp.ui.fragment.search.SearchUserFragment;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:搜索界面
 * <p>
 * Created by MVPArmsTemplate on 08/23/2019 12:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchActivity extends MJBaseActivity<SearchPresenter> implements SearchContract.View {
    @BindView(R.id.ivTopReturn)
    ImageView ivTopReturn;
    @BindView(R.id.ivSearchImg)
    ImageView ivSearchImg;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.rlSearchBtn)
    RelativeLayout rlSearchBtn;
    @BindView(R.id.mSlidingTabLayoutSearch)
    SlidingTabLayout mSlidingTabLayoutSearch;
    @BindView(R.id.mViewPagerSearch)
    ViewPager mViewPagerSearch;
    private ArrayList<Fragment> mFragments;
    String[] mTitle = new String[3];
    FragmentPagerAdapter fragmentPagerAdapter;
    List<String> yearStringList;
    SearchDrawingsFragment searchDrawingsFragment;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        mTitle[0] = ArmsUtils.getString(BaseApplication.getInstance(),R.string.drawings);
        mTitle[1] = ArmsUtils.getString(BaseApplication.getInstance(),R.string.app_discovery_title_two);
        mTitle[2] = ArmsUtils.getString(BaseApplication.getInstance(),R.string.user);
        AppConstants.KEYWORD = "";
        yearStringList = new ArrayList<>();
        searchDrawingsFragment=SearchDrawingsFragment.newInstance();
        mFragments.add(searchDrawingsFragment);
        mFragments.add(SearchDataFragment.newInstance());
        mFragments.add(SearchUserFragment.newInstance());
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), mFragments, mTitle);
        mViewPagerSearch.setAdapter(fragmentPagerAdapter);
        mSlidingTabLayoutSearch.setViewPager(mViewPagerSearch);
        if (getIntent() != null && getIntent().getStringArrayListExtra("yearString")!=null) {
            //年份数据集合
            yearStringList.addAll(getIntent().getStringArrayListExtra("yearString"));
        }
        //如果没有年，就重新获取年份
        if (yearStringList.size() == 0) {
            mPresenter.getPictureMenu();
        } else {
            if (searchDrawingsFragment != null) {
                searchDrawingsFragment.setYearStringList(yearStringList);
            }
        }
        //设置ViewPager缓存数量，避免出现第三个的时候没有创建问题
        mViewPagerSearch.setOffscreenPageLimit(2);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        if(message==null)
            return;
        checkNotNull(message);
        ArmsUtils.makeText(getApplication(), message);
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


    @OnClick({R.id.ivTopReturn, R.id.rlSearchBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivTopReturn:
                killMyself();
                break;
            case R.id.rlSearchBtn:
                if (TextUtils.isEmpty(etSearch.getText().toString())) {
                    showMessage(ArmsUtils.getString(BaseApplication.getInstance(),R.string.search_title));
                    return;
                }
                closeKeybord(SearchActivity.this);
                AppConstants.KEYWORD = etSearch.getText().toString();
                EventBus.getDefault().post(new SearchContentEvent(etSearch.getText().toString()));
                break;
        }
    }


    @Override
    protected void requestData() {

    }

    @Override
    public void getPictureMenu(List<String> stringList) {
        yearStringList.clear();
        yearStringList.addAll(stringList);
        if (searchDrawingsFragment != null) {
           searchDrawingsFragment.setYearStringList(yearStringList);
        }
    }
}
