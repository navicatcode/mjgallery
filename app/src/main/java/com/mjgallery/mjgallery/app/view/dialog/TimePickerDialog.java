package com.mjgallery.mjgallery.app.view.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mjgallery.mjgallery.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间选择器弹窗
 */
public class TimePickerDialog extends BaseDialog {
    LinearLayout llTimePicker;
    private TimePickerView pvTime;

    public TimePickerDialog(Activity activity) {
        super(activity);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_time_picker);
        llTimePicker = findViewById(R.id.llTimePicker);
        initTimePicker();
    }

    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();

        Calendar startDate = Calendar.getInstance();
        startDate.set(1970, 1, 1);

        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                Button btn = (Button) v;
                btn.setText(getTime(date));
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
//                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
//                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
//                        tvSubmit.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                pvTime.returnData();
//                                /*pvTime.dismiss();*/
//                            }
//                        });
//                        ivCancel.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                /*pvTime.dismiss();*/
//                            }
//                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentTextSize(20)
                .setDate(selectedDate)
                .setRangDate(startDate, selectedDate)
                .setDecorView(llTimePicker)//非dialog模式下,设置ViewGroup, pickerView将会添加到这个ViewGroup中
                .setOutSideColor(0x00000000)
                .setOutSideCancelable(false)
                .build();

        pvTime.setKeyBackCancelable(false);//系统返回键监听屏蔽掉
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


    @Override
    public void show() {
        super.show();
        pvTime.show();
    }
}
