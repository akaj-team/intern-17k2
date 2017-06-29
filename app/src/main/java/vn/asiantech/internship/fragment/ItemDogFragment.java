package vn.asiantech.internship.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by ducle on 29/06/2017.
 */

public class ItemDogFragment extends Fragment {
    public static final String KEY_IMAGE = "dog_image";
    private ImageView mImgAnimal;
    private String mUrlImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_animal, container, false);
        mUrlImage = getArguments().getString(KEY_IMAGE);
        mImgAnimal = (ImageView) view.findViewById(R.id.imgAnimal);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        imageLoader.displayImage(mUrlImage, mImgAnimal);
        return view;
    }

    public static ItemDogFragment newInstance(List<String> dogImages, int position) {
        Bundle args = new Bundle();
        args.putString(KEY_IMAGE, dogImages.get(position));
        ItemDogFragment fragment = new ItemDogFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
