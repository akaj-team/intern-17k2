package vn.asiantech.internship.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by ducle on 26/06/2017.
 */
public class ItemAnimalFragment extends android.support.v4.app.Fragment {
    private boolean hasLoadedOnce = true;
    public static final String KEY_IMAGE = "image";
    private ImageView mImgAnimal;
    private String mImageUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_item_animal, container, false);
        mImageUrl = getArguments().getString(KEY_IMAGE);
        mImgAnimal = (ImageView) view.findViewById(R.id.imgAnimal);
        loadData();
        return view;
    }

    public static ItemAnimalFragment newInstance(List<String> animalImages, int position) {
        ItemAnimalFragment itemAnimalFragment = new ItemAnimalFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMAGE, animalImages.get(position));
        itemAnimalFragment.setArguments(bundle);
        return itemAnimalFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            if (!isVisibleToUser && !hasLoadedOnce) {
                loadData();
                hasLoadedOnce = true;
            }
        }
    }

    public void loadData() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        imageLoader.displayImage(mImageUrl, mImgAnimal);
    }
}
