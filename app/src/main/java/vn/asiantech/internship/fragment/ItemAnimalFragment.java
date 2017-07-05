package vn.asiantech.internship.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
 * ItemAnimalFragment is fragment contain an item of main viewpager
 */
public class ItemAnimalFragment extends android.support.v4.app.Fragment {
    public static final String KEY_IMAGE = "animal_image";
    private static final String TAG = ItemAnimalFragment.class.getSimpleName();
    private boolean mIsLoaded;
    private boolean mIsShowed;
    private ImageView mImgAnimal;
    private String mImageUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_animal, container, false);
        mImageUrl = getArguments().getString(KEY_IMAGE);
        mImgAnimal = (ImageView) view.findViewById(R.id.imgAnimal);
        if (mIsShowed && !mIsLoaded) {
            loadData();
        }
        return view;
    }

    /**
     * update status of fragment
     *
     * @param animalImages is url list about animal
     * @param position     is position of item
     * @return a item fragment
     */
    public static ItemAnimalFragment newInstance(List<String> animalImages, int position) {
        ItemAnimalFragment itemAnimalFragment = new ItemAnimalFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_IMAGE, animalImages.get(position));
        itemAnimalFragment.setArguments(bundle);
        return itemAnimalFragment;
    }

    /**
     * set image for ImageView in an item
     */
    private void loadData() {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
        imageLoader.displayImage(mImageUrl, mImgAnimal);
        Log.i(TAG, "load item thuong");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsShowed = isVisibleToUser;
        if (this.isVisible()) {
            if (mIsShowed) {
                loadData();
                mIsLoaded = true;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsLoaded = false;
        Log.i(TAG, "destroy item thuong");
    }
}
