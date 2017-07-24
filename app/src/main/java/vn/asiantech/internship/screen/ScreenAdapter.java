package vn.asiantech.internship.screen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 10/07/2017.
 */
class ScreenAdapter extends RecyclerView.Adapter<ScreenAdapter.ScreenViewHolder> {
    private List<String> mDays;
    private OnDayChooseListener mOnDayChooseListener;

    ScreenAdapter(List<String> days, OnDayChooseListener onDayChooseListener) {
        mDays = days;
        mOnDayChooseListener = onDayChooseListener;
    }

    @Override
    public ScreenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScreenViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_day_screen, parent, false));
    }

    @Override
    public void onBindViewHolder(ScreenViewHolder holder, int position) {
        holder.mBtnDay.setText(mDays.get(position));
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    /**
     * Create ScreenViewHolder
     */
    class ScreenViewHolder extends RecyclerView.ViewHolder {
        private Button mBtnDay;

        ScreenViewHolder(View itemView) {
            super(itemView);
            mBtnDay = (Button) itemView.findViewById(R.id.btnDayScreen);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnDayChooseListener.chooseDay(getAdapterPosition());
                }
            });
        }
    }
}
