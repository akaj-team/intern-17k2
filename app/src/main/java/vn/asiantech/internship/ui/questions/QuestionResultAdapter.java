package vn.asiantech.internship.ui.questions;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by Thanh Thien on 6/23/17.
 * QuestionResultAdapter
 */
public class QuestionResultAdapter extends RecyclerView.Adapter<QuestionResultAdapter.ResultViewHolder> {

    private boolean[] mBooleans;
    private int mNumRight;

    QuestionResultAdapter(boolean[] booleans, int numRight) {
        mBooleans = booleans;
        mNumRight = numRight;
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_question_result, viewGroup, false);
        return new QuestionResultAdapter.ResultViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder resultViewHolder, int position) {

        if (position == 10) {
            String string = resultViewHolder.itemView.getResources().getString(R.string.correct) + " : " + mNumRight + "/10";
            resultViewHolder.mTvNumQuestion.setText(string);
            resultViewHolder.mTvNumQuestion.setTypeface(null, Typeface.BOLD);
            resultViewHolder.mImgCheck.setVisibility(View.GONE);
            return;
        }

        String s = resultViewHolder.itemView.getResources().getString(R.string.question) + " " + (position + 1);
        resultViewHolder.mTvNumQuestion.setText(s);
        if (mBooleans[position]) {
            resultViewHolder.mImgCheck.setImageResource(R.drawable.vector_check);
            resultViewHolder.mRlParent.setBackgroundColor(0x7d29ff42);
        } else {
            resultViewHolder.mImgCheck.setImageResource(R.drawable.vector_false);
            resultViewHolder.mRlParent.setBackgroundColor(0x7dff0000);
        }
    }

    @Override
    public int getItemCount() {
        return mBooleans.length + 1;
    }

    /**
     * Viewhoder class
     */
    class ResultViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvNumQuestion;
        private ImageView mImgCheck;
        private RelativeLayout mRlParent;

        ResultViewHolder(View itemView) {
            super(itemView);
            mTvNumQuestion = (TextView) itemView.findViewById(R.id.tvNumQuestion);
            mImgCheck = (ImageView) itemView.findViewById(R.id.imgCheck);
            mRlParent = (RelativeLayout) itemView.findViewById(R.id.rlParent);
        }
    }
}
