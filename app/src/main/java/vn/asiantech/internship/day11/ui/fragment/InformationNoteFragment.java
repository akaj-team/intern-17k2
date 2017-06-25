package vn.asiantech.internship.day11.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.ui.activity.NoteActivity;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by rimoka on 24/06/2017.
 */
public class InformationNoteFragment extends Fragment {

    private String mTime;
    private String mTitle;
    private String mDescription;
    private String mUriImage;
    private NoteActivity mNoteActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNoteActivity = (NoteActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTime = getArguments() != null ? (String) getArguments().get("Time") : "Error";
        mTitle = getArguments() != null ? (String) getArguments().get("Title") : "Error";
        mDescription = getArguments() != null ? (String) getArguments().get("Description") : "Error";
        mUriImage = getArguments() != null ? (String) getArguments().get("UriImage") : "Error";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_information_note, container, false);
        TextView tvTime = (TextView) v.findViewById(R.id.tvTimeInformationNote);
        TextView tvTitle = (TextView) v.findViewById(R.id.tvTitleInformation);
        TextView tvDescription = (TextView) v.findViewById(R.id.tvDescriptionInforNote);
        ImageView imgNote = (ImageView) v.findViewById(R.id.imgInformationNote);
        ImageView imgBack = (ImageView) v.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNoteActivity.changeFragment(new NoteFragment());
            }
        });
        tvTime.setText(mTime);
        tvTitle.setText(mTitle);
        tvDescription.setText(mDescription);
        imgNote.setImageURI(Uri.parse(mUriImage));
        return v;
    }
}
