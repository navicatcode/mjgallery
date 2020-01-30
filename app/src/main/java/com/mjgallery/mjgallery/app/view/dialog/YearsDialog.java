package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.mvp.model.bean.YearsBean;
import com.mjgallery.mjgallery.mvp.ui.adapter.dialog.YearsAdapter;
import com.mjgallery.mjgallery.mvp.ui.adapter.dialog.YearsIemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 年月筛选弹窗
 */
public class YearsDialog extends BaseDialog implements View.OnClickListener, YearsAdapter.IBaseQuickAdapterClickListener {

    RecyclerView mRecyclerViewYear;
    RecyclerView mRecyclerViewQiShu;
    LinearLayout llAllYearDialog;
    YearsAdapter yearsAdapter;
    List<YearsBean> yearsBeans;
    List<String> stringsItem;
    YearsIemAdapter yearsIemAdapter;
    IYearsSelectDialog iYearsSelectDialog;
    String year;

    public YearsDialog(Activity activity, List<String> strings, IYearsSelectDialog iYearsSelectDialog) {
        super(activity,R.style.tran_dialog);
        setCanceledOnTouchOutside(true);
        this.iYearsSelectDialog = iYearsSelectDialog;
        init(strings);
    }

    private void init(List<String> strings) {
        stringsItem = new ArrayList<>();
        setContentView(R.layout.dialog_years);
        yearsBeans = new ArrayList<>();
        if (strings.size() > 0) {
            for (int i = 0; i < strings.size(); i++) {
                YearsBean yearsBean = new YearsBean();
                if (i == 0) {
                    this.year = strings.get(i);
                    yearsBean.setSelect(true);
                }
                yearsBean.setYear(strings.get(i));
                yearsBeans.add(yearsBean);

            }
        }
        LinearLayoutManager linearLayoutManagerYear = new LinearLayoutManager(getContext());
        linearLayoutManagerYear.setOrientation(LinearLayoutManager.VERTICAL);
        LinearLayoutManager linearLayoutManagerQishu = new LinearLayoutManager(getContext());
        linearLayoutManagerQishu.setOrientation(LinearLayoutManager.VERTICAL);
        yearsIemAdapter = new YearsIemAdapter(R.layout.adapter_years_item_item, stringsItem);
        mRecyclerViewYear = findViewById(R.id.mRecyclerViewYear);
        mRecyclerViewQiShu = findViewById(R.id.mRecyclerViewQiShu);
        yearsAdapter = new YearsAdapter(R.layout.adapter_years_item, yearsBeans, this);
        mRecyclerViewYear.setLayoutManager(linearLayoutManagerYear);
        mRecyclerViewYear.setAdapter(yearsAdapter);
        mRecyclerViewQiShu.setLayoutManager(linearLayoutManagerQishu);
        mRecyclerViewQiShu.setAdapter(yearsIemAdapter);
        yearsIemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (iYearsSelectDialog != null && stringsItem.size() > position) {
                    iYearsSelectDialog.onYearItemSelect(year, stringsItem.get(position));
                }
            }

        });

    }

    @Override
    public void onClickListener(YearsBean item) {
        for (int i = 0; i < yearsBeans.size(); i++) {
            YearsBean yearsBean = yearsBeans.get(i);
            if (item.getYear().equals(yearsBean.getYear())) {
                yearsBean.setSelect(true);
            } else {
                yearsBean.setSelect(false);
            }
        }
        this.year = item.getYear();
        if(iYearsSelectDialog!=null){
            iYearsSelectDialog.onYearsSelect(year);
        }
        if (yearsAdapter != null) {
            yearsAdapter.notifyDataSetChanged();
        }
    }


    public void setItemListData(List<String> strings) {
        stringsItem.clear();
        stringsItem.addAll(strings);
        if (yearsIemAdapter != null) {
            yearsIemAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {

    }

    public interface IYearsSelectDialog {
        void onYearsSelect(String year);

        void onYearItemSelect(String year, String itemYear);
    }
}
