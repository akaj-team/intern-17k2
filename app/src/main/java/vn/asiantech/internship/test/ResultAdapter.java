package vn.asiantech.internship.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * As a adapter for recyclerView of result list.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-23
 */
class ResultAdapter extends RecyclerView.Adapter {
    private List<Result> mResults = new ArrayList<>();

    ResultAdapter(List<Result> questions) {
        this.mResults = questions;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_result_list, parent, false);
        return new ResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ResultViewHolder resultViewHolder = (ResultViewHolder) holder;
        resultViewHolder.mTvQuestion.setText(mResults.get(position).getQuestion());
        resultViewHolder.mTvState.setImageResource(mResults.get(position).isRight() ? R.drawable.ic_check_circle_yellow_800_24dp : R.drawable.ic_highlight_off_yellow_800_24dp);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    /**
     * Used to register for result.
     */
    private class ResultViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTvQuestion;
        private final ImageView mTvState;

        ResultViewHolder(View itemView) {
            super(itemView);
            mTvQuestion = (TextView) itemView.findViewById(R.id.tvResultQuestion);
            mTvState = (ImageView) itemView.findViewById(R.id.imgState);
        }
    }
}
