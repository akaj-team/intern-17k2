package vn.asiantech.internship.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;

/**
 * ResultAdapter is adapter for result list
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {
    private List<Question> mQuestions;

    public ResultAdapter(List<Question> questions) {
        this.mQuestions = questions;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        holder.mTvQuestion.setText(holder.mTvQuestion.getContext().getString(R.string.question, position + 1));
        if (mQuestions.get(position).isCheckedRight()) {
            holder.mImgResult.setImageResource(R.drawable.ic_check_circle_green_500_24dp);
        } else {
            holder.mImgResult.setImageResource(R.drawable.ic_highlight_off_red_700_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ResultViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvQuestion;
        private ImageView mImgResult;

        public ResultViewHolder(View itemView) {
            super(itemView);
            mTvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
            mImgResult = (ImageView) itemView.findViewById(R.id.imgResult);
        }
    }
}
