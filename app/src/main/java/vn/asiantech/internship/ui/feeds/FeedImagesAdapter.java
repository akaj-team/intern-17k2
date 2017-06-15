package vn.asiantech.internship.ui.feeds;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created Thien root on 6/15/17.
 * Adapter of list images in view pager
 */
public class FeedImagesAdapter extends PagerAdapter {
    private List<Integer> mImages;
    private Activity mActivity;

    FeedImagesAdapter(List<Integer> images, Activity activity) {
        mImages = images;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return this.mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = LayoutInflater.from(container.getContext()).inflate(R.layout.item_image, container, false);
        ImageView imgThumb = (ImageView) v.findViewById(R.id.imgThumb);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        options.inDither = false;
        Bitmap bitmap = BitmapFactory.decodeResource(container.getResources(), mImages.get(position), options );
        imgThumb.setImageBitmap(bitmap);
        container.addView(imgThumb,0);

        return imgThumb;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        (container).removeView((ImageView) object);
    }
}
