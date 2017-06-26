package vn.asiantech.internship.ui.adapter;

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
 * Created by AnhHuy on 26-Jun-17.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Question> mQuestionList;

    public ResultAdapter(Context context, ArrayList<Question> questionList) {
        mContext = context;
        mQuestionList = questionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvQuestionNumber.setText(String.format(mContext.getString(R.string.question_number), position + 1));
        if (mQuestionList.get(position).isCorrect()) {
            holder.mImgStatus.setImageResource(R.drawable.ic_check);
        } else {
            holder.mImgStatus.setImageResource(R.drawable.ic_cancel);
        }
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    final class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvQuestionNumber;
        private ImageView mImgStatus;

        ViewHolder(View itemView) {
            super(itemView);
            mTvQuestionNumber = (TextView) itemView.findViewById(R.id.tvQuestionNumber);
            mImgStatus = (ImageView) itemView.findViewById(R.id.imgStatus);
        }
    }
}
