package vn.asiantech.internship.exday15;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 26-06-2017.
 */
class PointAdapter extends RecyclerView.Adapter<PointAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<ItemQuestion> mItemQuestions;

    PointAdapter(Context mContext, ArrayList<ItemQuestion> mItemQuestions) {
        this.mContext = mContext;
        this.mItemQuestions = mItemQuestions;
    }

    @Override
    public PointAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_answer_point, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(PointAdapter.MyHolder holder, int position) {
        if (mItemQuestions.get(position).isRight()) {
            holder.mTvResult.setText(String.format(mContext.getString(R.string.qustion), position + 1));
            holder.mTvResult.setBackgroundColor(Color.GREEN);
        } else if (!mItemQuestions.get(position).isRight()) {
            holder.mTvResult.setText(String.format(mContext.getString(R.string.qustion), position + 1));
            holder.mTvResult.setBackgroundColor(Color.RED);
        }

    }

    @Override
    public int getItemCount() {
        return mItemQuestions.size();
    }

    /**
     * Created by datbu on 26-06-2017.
     */
    class MyHolder extends RecyclerView.ViewHolder {
        private TextView mTvResult;

        MyHolder(View itemView) {
            super(itemView);
            mTvResult = (TextView) itemView.findViewById(R.id.tvResult);
        }
    }
}
