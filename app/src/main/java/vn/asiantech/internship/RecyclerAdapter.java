package vn.asiantech.internship;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {
    private List<Name> mNameList;

    RecyclerAdapter(List<Name> mNameList) {
        this.mNameList = mNameList;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_friend, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Name name = mNameList.get(position);
        holder.mTvName.setText(name.getName());

        Button btn = holder.mBtnAdd;
        if (name.isAddtt()) {
            btn.setText(R.string.btn_friend);
            btn.setBackgroundColor(Color.RED);
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.imgcheck, 0, 0, 0);
        } else {
            btn.setText(R.string.btn_add);
            btn.setBackgroundColor(Color.GRAY);
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.imgadd, 0, 0, 0);
        }

    }

    @Override
    public int getItemCount() {
        return mNameList.size();

    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTvName;
        Button mBtnAdd;

        MyHolder(View itemView) {
            super(itemView);
            mTvName = (TextView) itemView.findViewById(R.id.tvName);
            mBtnAdd = (Button) itemView.findViewById(R.id.btnAdd1);
            mBtnAdd.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnAdd1:
                    mNameList.get(getAdapterPosition()).setAddtt();
                    notifyDataSetChanged();
                    break;

            }
        }

    }
}



