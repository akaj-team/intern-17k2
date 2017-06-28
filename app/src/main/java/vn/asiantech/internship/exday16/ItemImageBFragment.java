package vn.asiantech.internship.exday16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 26-06-2017.
 */
public class ItemImageBFragment extends Fragment {
    private ImageView mImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_viewpager_b, container, false);
        String image = getArguments().getString("image");
        mImageView = (ImageView) view.findViewById(R.id.imgView);
        Glide.with(getActivity()).load(image).into(mImageView);
        return view;
    }

    public static ItemImageFragment newInstance(List<String> mImages, int position) {
        Bundle args = new Bundle();
        args.putString("image", mImages.get(position));
        ItemImageFragment fragment = new ItemImageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
