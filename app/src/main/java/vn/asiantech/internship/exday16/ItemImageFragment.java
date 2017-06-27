package vn.asiantech.internship.exday16;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 26-06-2017.
 */

public class ItemImageFragment extends Fragment {
    private static String image1 = "http://cdn.runescape.com/assets/img/external/news/2015/03/dark_lord_outfit.jpg";
    private static String image2 = "http://vignette2.wikia.nocookie.net/runescape2/images/3/36/Lord_Amlodd_concept_art.jpg/revision/latest?cb=20140811105559";
    private static String image3 = "https://dviw3bl0enbyw.cloudfront.net/uploads/forum_attachment/file/139844/Male_voodoo_armor_concept_art.jpg";
    private static String image4 = "https://cdna.artstation.com/p/assets/images/images/002/854/562/large/jonas-lopez-moreno-jonaslopezmoreno-saitan-web.jpg?1466498557";
    private static ArrayList<String> mImages;
    private ImageView mImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_viewpager, container, false);
        mImages = new ArrayList<>();
        mImages.add(image1);
        mImages.add(image2);
        mImages.add(image3);
        mImages.add(image4);
        mImageView = (ImageView) view.findViewById(R.id.imgView);
        for (int i = 0; i < mImages.size(); i++) {
            Glide.with(this).load(mImages.get(i)).into(mImageView);
        }
        Log.d("tag", "onCreateView: " + mImages);
        return view;
    }

    public static ViewPagerFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

