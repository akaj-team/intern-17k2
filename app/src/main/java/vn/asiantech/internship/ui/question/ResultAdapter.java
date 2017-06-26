package vn.asiantech.internship.ui.question;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Result;

/**
 * Adapter of result recyclerView
 * <p>
 * Created by Hai on 6/25/2017.
 */

class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ItemViewHolder> {
    private List<Result> mResults = new ArrayList<>();

    ResultAdapter(List<Result> results) {
        mResults = results;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_question_result, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int position) {
        itemViewHolder.mTvQuestionId.setText(itemViewHolder.itemView.getContext().getResources().getString(R.string.question_text, mResults.get(position).getQuestionId() + 1));
        boolean isCorrect = mResults.get(position).isCorrect();
        itemViewHolder.mImgResult.setImageResource(isCorrect ? R.drawable.ic_check_circle_red_400_24dp : R.drawable.ic_not_interested_red_400_24dp);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvQuestionId;
        private ImageView mImgResult;

        ItemViewHolder(View itemView) {
            super(itemView);
            mTvQuestionId = (TextView) itemView.findViewById(R.id.tvQuestionId);
            mImgResult = (ImageView) itemView.findViewById(R.id.imgQuestionResult);
        }
    }
}
