package vn.asiantech.internship.splash;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Author AsianTech Inc.
 * Created by sony on 17/07/2017.
 */
class SplashAdapter extends RecyclerView.Adapter {
    private final List<String> mDays;
    private final OnClickListener mListener;

    SplashAdapter(List<String> days, OnClickListener listener) {
        mDays = days;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_splash_recyclerview, parent, false);
        return new SplashViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SplashViewHolder splashViewHolder = (SplashViewHolder) holder;
        splashViewHolder.mBtnDay.setText(mDays.get(position));
    }

    @Override
    public int getItemCount() {
        return mDays.size();
    }

    /**
     * Use to register for day buttons
     */
    private class SplashViewHolder extends RecyclerView.ViewHolder {
        private final Button mBtnDay;

        SplashViewHolder(View itemView) {
            super(itemView);
            mBtnDay = (Button) itemView.findViewById(R.id.btnDay);
            mBtnDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onClick(getAdapterPosition());
                }
            });
        }
    }

    /**
     * Used to get position of recyclerView
     */
    public interface OnClickListener {
        void onClick(int position);
    }
}
