package vn.asiantech.internship.drawer.day15.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.drawer.day15.models.Result;

/**
 * Created by at-dinhvo on 26/06/2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private List<Result> mResults;

    public ResultAdapter(List<Result> results) {
        mResults = results;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        Result result = mResults.get(position);
        holder.mTvResult.setText(result.getQuestion());
        if (result.isCorrect()) {
            holder.mImgResult.setImageResource(R.drawable.ic_check_circle_black_36dp);
        }
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvResult;
        private ImageView mImgResult;

        public ResultViewHolder(final View itemView) {
            super(itemView);
            mTvResult = (TextView) itemView.findViewById(R.id.tvResult);
            mImgResult = (ImageView) itemView.findViewById(R.id.imgResult);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
}
