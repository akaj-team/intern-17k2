package vn.asiantech.internship.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Food;

/**
 * Created by anhhuy on 15/06/2017.
 */

public class PageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Food> mFoodLists;

    public PageAdapter(List<Food> foodLists) {
        mFoodLists = foodLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.item_list_page, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ((ViewHolder) holder).setData(position);
        }
    }

    @Override
    public int getItemCount() {
        return mFoodLists.size() == 0 ? 0 : mFoodLists.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgFood;
        public ViewHolder(View itemView) {
            super(itemView);
            mImgFood = (ImageView) itemView.findViewById(R.id.imgFood);
        }

        private void setData(int position){
            Food food = mFoodLists.get(position);
            mImgFood.setImageResource(food.getImage());
        }
    }
}
