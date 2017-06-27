package vn.asiantech.internship.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by ducle on 27/06/2017.
 */

public class DogAdapter extends PagerAdapter {
    private List<String> mDogImages;

    public DogAdapter(List<String> dogImages) {
        this.mDogImages = dogImages;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_item_animal, container, false);
        ImageView imgDog = (ImageView) view.findViewById(R.id.imgAnimal);
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(container.getContext()));
        imageLoader.displayImage(mDogImages.get(position), imgDog);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mDogImages.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
