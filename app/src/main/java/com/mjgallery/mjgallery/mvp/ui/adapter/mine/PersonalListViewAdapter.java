package com.mjgallery.mjgallery.mvp.ui.adapter.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.utils.ArmsUtils;
import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;
import com.mjgallery.mjgallery.app.glide.GlideUtil;
import com.mjgallery.mjgallery.app.utils.PersonalGetTask;
import com.mjgallery.mjgallery.app.utils.TW2CN;
import com.mjgallery.mjgallery.mvp.model.bean.mine.personal.NoviceTaskBean;
import com.mjgallery.mjgallery.mvp.ui.activity.CustomerServiceActivity;
import com.mjgallery.mjgallery.widget.UIImageView;

import java.util.List;


public class PersonalListViewAdapter extends BaseAdapter {
    private Context context;
    private List<NoviceTaskBean> list;
    private LayoutInflater mInflater = null;
    PersonalGetTask personalGetTask;
    public PersonalListViewAdapter(Context context,List<NoviceTaskBean> list,PersonalGetTask personalGetTask){
        this.list=list;
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.personalGetTask=personalGetTask;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getId();
    }

    //在外面先定义，ViewHolder静态类
    static class ViewHolder
    {
        public ImageView ivStatusImg;
        public TextView tvTitle;
        public TextView tvDescribe;
        public TextView rlCancelAttentionText;
        public RelativeLayout rlCancelAttention;
        UIImageView ivPersonalDialog;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.adapter_personal_layout_item, null);
            holder.ivStatusImg = (ImageView)convertView.findViewById(R.id.ivStatusImg);
            holder.ivStatusImg = (ImageView)convertView.findViewById(R.id.ivStatusImg);
            holder.ivPersonalDialog =(UIImageView)convertView.findViewById(R.id.ivPersonalDialog);
            holder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            holder.tvDescribe = (TextView)convertView.findViewById(R.id.tvDescribe);
            holder.rlCancelAttentionText =(TextView)convertView.findViewById(R.id.rlCancelAttentionText);
            holder.rlCancelAttention = (RelativeLayout) convertView.findViewById(R.id.rlCancelAttention);
            holder.ivPersonalDialog.setVisibility(View.GONE);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        //后台内容只有简体，如果当前app为繁体，则换成繁体
        holder.tvDescribe.setText(TW2CN.getInstance(context).toLocalStringS2T(list.get(position).getContent()));
        String name=list.get(position).getRewardName();
        holder.tvTitle.setText(TW2CN.getInstance(context).toLocalStringS2T(name));

        //因为以文本做为判断显示对应图片，而后台转的文只有简体，所以直接写死文本在代码中判断
        if("分享好友".equals(name)){
            GlideUtil.loadCircleImage(holder.ivStatusImg, R.drawable.personal_type_03);
        }else if("补填邀请码".equals(name)){
            GlideUtil.loadCircleImage(holder.ivStatusImg, R.drawable.personal_type_02);
        }else{
            GlideUtil.loadCircleImage(holder.ivStatusImg, R.drawable.personal_type_01);
        }

        int idDo=list.get(position).getIsDo();
        if(idDo==2){//已领取
            holder.rlCancelAttention.setBackgroundResource(R.drawable.personal_type_btn);
            holder.rlCancelAttentionText.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.to_receive));
            holder.rlCancelAttentionText.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.color_0EB4FE));
        }else {//立即领取
            holder.rlCancelAttentionText.setTextColor(ArmsUtils.getColor(BaseApplication.getInstance(),R.color.white));

            if(idDo==1 || "分享好友".equals(name)){//立即领取
                holder.rlCancelAttention.setBackgroundResource(R.drawable.item_oaaffa_bg);
            }else{//还没达到领取要求
                holder.rlCancelAttention.setBackgroundResource(R.drawable.item_aeaeae_bg_click);
            }

            if (!"分享好友".equals(name)) {
                holder.rlCancelAttentionText.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.personal_immediately_receive));
            }else{
                holder.rlCancelAttentionText.setText(ArmsUtils.getString(BaseApplication.getInstance(), R.string.contact_service));
            }
        }

        holder.rlCancelAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("分享好友".equals(name)){
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    intent.setClass(BaseApplication.getInstance(), CustomerServiceActivity.class);
                    context.startActivity(intent);
                }else if(idDo==1){
                    personalGetTask.getTaskOnClick(list.get(position).getId(),name,true,position);
                }

            }
        });

        return convertView;
    }


}
