package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by ducle on 26/06/2017.
 */

public class ItemAnimalFragment extends android.support.v4.app.Fragment {
    public static final String KEY_IMAGE="image";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_item_animal, container, false);
        String url_image=getArguments().getString(KEY_IMAGE);
        return view;
    }

    public static ItemAnimalFragment newInstance(List<String> animalImages,int position){
        ItemAnimalFragment itemAnimalFragment=new ItemAnimalFragment();
        Bundle bundle=new Bundle();
        bundle.putString(KEY_IMAGE,animalImages.get(position));
        itemAnimalFragment.setArguments(bundle);
        return itemAnimalFragment;
    }
}
