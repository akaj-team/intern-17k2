package vn.asiantech.internship.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * Created by PC on 6/20/2017.
 */

public class AddNoteFragment extends Fragment {
    private ImageView mImagePicture;
    private String mPicturePath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        mImagePicture = (ImageView) view.findViewById(R.id.imgNotePhoto);
        return view;
    }

}
