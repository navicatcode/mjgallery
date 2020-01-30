package com.mjgallery.mjgallery.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.MJBaseActivity;
import com.mjgallery.mjgallery.di.component.DaggerSelectApplicableComponent;
import com.mjgallery.mjgallery.event.SelectApplicableEvent;
import com.mjgallery.mjgallery.mvp.contract.SelectApplicableContract;
import com.mjgallery.mjgallery.mvp.model.bean.YearsBean;
import com.mjgallery.mjgallery.mvp.presenter.SelectApplicablePresenter;
import com.mjgallery.mjgallery.mvp.ui.adapter.SelectApplicableYearAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.SelectApplicableYearItemAdapter;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:选择期数的透明的activity
 * <p>
 * Created by MVPArmsTemplate on 09/21/2019 18:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SelectApplicableActivity extends MJBaseActivity<SelectApplicablePresenter> implements SelectApplicableContract.View {
    @BindView(R.id.mRecyclerViewYear)
    RecyclerView mRecyclerViewYear;
    @BindView(R.id.mRecyclerViewQiShu)
    RecyclerView mRecyclerViewQiShu;
    @BindView(R.id.llAllYearDialog)
    LinearLayout llAllYearDialog;
    List<String> yearStringList;
    int pictureTypeId;
    String year;
    List<YearsBean> yearsBeans;
    SelectApplicableYearAdapter mSelectApplicableYearAdapter;
    SelectApplicableYearItemAdapter mSelectApplicableYearItemAdapter;
    @BindView(R.id.llAll)
    RelativeLayout llAll;
    @BindView(R.id.llAllOne)
    LinearLayout llAllOne;
    List<String> itemStringList;
    boolean isHome = true;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSelectApplicableComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_select_applicable; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        //年份数据集合
        itemStringList=new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearItemLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearItemLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        yearsBeans = new ArrayList<>();
        yearStringList = new ArrayList<>();
        if (getIntent() != null) {
            if (getIntent().getStringArrayListExtra("yearString") != null) {
                yearStringList.addAll(getIntent().getStringArrayListExtra("yearString"));
            }
            pictureTypeId = getIntent().getIntExtra("pictureTypeId", 0);
            isHome = getIntent().getBooleanExtra("isHome", false);
        }
        if (yearStringList.size() > 0) {
            setYearsBeans();
        } else {
            mPresenter.getPictureMenu();
        }
        mSelectApplicableYearAdapter = new SelectApplicableYearAdapter(R.layout.select_applicable_item, yearsBeans);
        mSelectApplicableYearItemAdapter = new SelectApplicableYearItemAdapter(R.layout.adapter_years_item_item, itemStringList);
        mRecyclerViewYear.setLayoutManager(linearLayoutManager);
        mRecyclerViewQiShu.setLayoutManager(linearItemLayoutManager);
        mRecyclerViewYear.setAdapter(mSelectApplicableYearAdapter);
        mRecyclerViewQiShu.setAdapter(mSelectApplicableYearItemAdapter);
        mSelectApplicableYearAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (yearsBeans.size() > position) {
                    for (int i = 0; i < yearsBeans.size(); i++) {
                        YearsBean yearsBean = yearsBeans.get(i);
                        if (i == position) {
                            year=yearsBean.getYear();
                            yearsBean.setSelect(true);
                        } else {
                            yearsBean.setSelect(false);
                        }
                    }
                    getTermsByYear();
                    if (mSelectApplicableYearAdapter != null) {
                        mSelectApplicableYearAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        mSelectApplicableYearItemAdapter.setEmptyView(R.layout.adapter_years_item_item_error,llAllYearDialog);
        mSelectApplicableYearItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(itemStringList.size()>position){
                    EventBus.getDefault().post(new SelectApplicableEvent(year,itemStringList.get(position)));
                    killMyself();
                }
            }
        });

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    //设置开奖记录年份
    private void setYearsBeans() {
        if (yearStringList.size() > 0) {
            year = yearStringList.get(0);
            for (int i = 0; i < yearStringList.size(); i++) {
                YearsBean yearsBean = new YearsBean();
                if (i == 0) {
                    this.year = yearStringList.get(i);
                    yearsBean.setSelect(true);
                }
                yearsBean.setYear(yearStringList.get(i));
                yearsBeans.add(yearsBean);
            }
            if(isHome){
                getTermsByYear();
            }else {
                Map<String, Object> map=new HashMap<>();
                map.put("year",year);
                mPresenter.getSelectByYery(map);
            }

        }
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
        overridePendingTransition(0, R.anim.dialog_exit_anim);
    }


    /**
     * 得到期数列表
     */
    private void getTermsByYear() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(year)) {
            map.put("year", year);
            if(pictureTypeId != 0){
        /*        map.put("pictureTypeId", pictureTypeId);*/
                mPresenter.getTermsByYear(map);
            }else {
                mPresenter.getSelectByYery(map);
            }

        }

    }


    @Override
    public void getTermsByYear(List<String> stringList) {
        itemStringList.clear();
        if(stringList.size()>0){
            itemStringList.addAll(stringList);
        }
        if(mSelectApplicableYearItemAdapter!=null){
            mSelectApplicableYearItemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getPictureMenu(List<String> stringList) {
        yearStringList.clear();
        yearStringList.addAll(stringList);
        setYearsBeans();
        if (mSelectApplicableYearAdapter != null) {
            mSelectApplicableYearAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSelectByYery(List<String> stringList) {
        itemStringList.clear();
        if(stringList.size()>0){
            itemStringList.addAll(stringList);
        }
        if(mSelectApplicableYearItemAdapter!=null){
            mSelectApplicableYearItemAdapter.notifyDataSetChanged();
        }
    }


    @OnClick({R.id.llAllYearDialog, R.id.llAll,R.id.llAllOne})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llAllYearDialog:
                break;
            case R.id.llAllOne:
            case R.id.llAll:
                EventBus.getDefault().post(new SelectApplicableEvent("",""));
                killMyself();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            EventBus.getDefault().post(new SelectApplicableEvent("",""));
                killMyself();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void requestData() {

    }
}
