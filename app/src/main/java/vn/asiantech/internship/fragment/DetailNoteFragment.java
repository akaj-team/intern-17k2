package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by ducle on 20/06/2017.
 */
public class DetailNoteFragment extends Fragment {
    private ImageView mImgEdit;
    private ImageView mImgDelete;
    private ImageView mImgNote;
    private TextView mTvTitleNote;
    private TextView mTvContent;
    private TextView mTvDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_detail_note, container, false);
        initeViews(view);

        return view;
    }

    private void initeViews(View view) {
        mImgEdit=(ImageView) view.findViewById(R.id.imgEdit);
        mImgDelete=(ImageView) view.findViewById(R.id.imgDelete);
        mImgNote=(ImageView) view.findViewById(R.id.imgNote);
        mTvTitleNote=(TextView) view.findViewById(R.id.tvTitleNote);
        mTvContent=(TextView) view.findViewById(R.id.tvContent);
        mTvDate=(TextView) view.findViewById(R.id.tvDate);
    }
}
