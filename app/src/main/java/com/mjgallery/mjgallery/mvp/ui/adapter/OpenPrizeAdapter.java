package com.mjgallery.mjgallery.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.utils.Utils;

import java.util.List;

public class OpenPrizeAdapter extends RecyclerView.Adapter<OpenPrizeAdapter.ViewHolder> {

    private Context mContext;
    private List<Integer> chipList;
    private OnItemClickListener onItemClickListener;

    public OpenPrizeAdapter(Context context, List<Integer> chipList) {
        this.mContext = context;
        this.chipList = chipList;
    }

    public void updateData(List<Integer> data) {
        this.chipList = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_open_prize, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Integer integer = chipList.get(position);
        if (integer < 13) {
            holder.tv_open_prize_year.setText(Utils.getMonthByInt(integer));
        } else
            holder.tv_open_prize_year.setText(String.valueOf(integer));

        if (onItemClickListener != null) {
            holder.tv_open_prize_year.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(view, position, integer);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return chipList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, int integer);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_open_prize_year;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_open_prize_year = (TextView) itemView.findViewById(R.id.tv_open_prize_year);
        }
    }
}