package vn.asiantech.internship.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Question;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/23/2017
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ItemHolder> {

    private Context mContext;
    private ArrayList<Question> mQuestionSet;

    public ResultAdapter(Context context, ArrayList<Question> questionSet) {
        this.mQuestionSet = questionSet;
        this.mContext = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_result, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.mTvQuestionNumber.setText(String.format(mContext.getString(R.string.question_number), position + 1));
        if (mQuestionSet.get(position).isRight()) {
            holder.mImgStatus.setImageResource(R.drawable.ic_check_circle_red_500_24dp);
        } else {
            holder.mImgStatus.setImageResource(R.drawable.ic_not_interested_red_500_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return mQuestionSet.size();
    }

    /**
     * Item for RecyclerView
     */
    final class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mTvQuestionNumber;
        private ImageView mImgStatus;

        ItemHolder(View itemView) {
            super(itemView);
            mTvQuestionNumber = (TextView) itemView.findViewById(R.id.tvQuestionNumber);
            mImgStatus = (ImageView) itemView.findViewById(R.id.imgStatus);
        }
    }
}
