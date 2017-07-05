package vn.asiantech.internship.day15.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day15.model.Result;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 25/06/2017.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    private List<Result> mResults;

    public ResultAdapter(List<Result> results) {
        mResults = results;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ResultViewHolder(inflater.inflate(R.layout.item_list_result, parent, false));
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        holder.mTvQuestion.setText(mResults.get(position).getQuestion());
        if (mResults.get(position).isResultQuestion()) {
            holder.mImgFalse.setVisibility(View.INVISIBLE);
            holder.mImgTrue.setVisibility(View.VISIBLE);
        } else {
            holder.mImgTrue.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    /**
     * Create ResultViewHolder
     */
    class ResultViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvQuestion;
        private ImageView mImgFalse;
        private ImageView mImgTrue;

        ResultViewHolder(View itemView) {
            super(itemView);
            mTvQuestion = (TextView) itemView.findViewById(R.id.tvQuestionItem);
            mImgFalse = (ImageView) itemView.findViewById(R.id.imgFalse);
            mImgTrue = (ImageView) itemView.findViewById(R.id.imgTrue);
        }
    }
}
