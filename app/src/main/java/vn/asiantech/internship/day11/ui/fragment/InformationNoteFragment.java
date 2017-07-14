package vn.asiantech.internship.day11.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.database.NoteModify;
import vn.asiantech.internship.day11.model.Note;
import vn.asiantech.internship.day11.ui.activity.NoteActivity;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 24/06/2017.
 */
public class InformationNoteFragment extends Fragment {
    public static final String TYPE_ID = "Id";
    public static final String TYPE_TIME = "Time";
    public static final String TYPE_TITLE = "Title";
    public static final String TYPE_DESCRIPTION = "Description";
    public static final String TYPE_URI_IMAGE = "UriImage";

    private int mId;
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
        String error = "Error";
        mId = (int) (getArguments() != null ? getArguments().get(TYPE_ID) : error);
        mTime = getArguments() != null ? (String) getArguments().get(TYPE_TIME) : error;
        mTitle = getArguments() != null ? (String) getArguments().get(TYPE_TITLE) : error;
        mDescription = getArguments() != null ? (String) getArguments().get(TYPE_DESCRIPTION) : error;
        mUriImage = getArguments() != null ? (String) getArguments().get(TYPE_URI_IMAGE) : error;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_information_note, container, false);
        TextView tvTime = (TextView) v.findViewById(R.id.tvTimeInformationNote);
        final EditText edtTitle = (EditText) v.findViewById(R.id.edtTitleInformation);
        final EditText edtDescription = (EditText) v.findViewById(R.id.edtDescriptionInforNote);
        ImageView imgNote = (ImageView) v.findViewById(R.id.imgInformationNote);
        ImageView imgBack = (ImageView) v.findViewById(R.id.imgBack);
        ImageView imgDelete = (ImageView) v.findViewById(R.id.imgDeleteNote);
        ImageView imgEdit = (ImageView) v.findViewById(R.id.imgEditNote);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(edtTitle.getText())) {
                    Toast.makeText(getContext(), R.string.toast_input_title, Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(edtDescription.getText())) {
                        Toast.makeText(getContext(), R.string.toast_input_description, Toast.LENGTH_SHORT).show();
                    } else {
                        updateNote(new Note(mId, edtTitle.getText().toString(), edtDescription.getText().toString(), "", "", "", ""));
                        mNoteActivity.changeFragment(new NoteFragment());
                    }
                }

            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNote(mId);
                mNoteActivity.changeFragment(new NoteFragment());
                Toast.makeText(getContext(), R.string.toast_delete_note, Toast.LENGTH_SHORT).show();
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTitle.setEnabled(true);
                edtDescription.setEnabled(true);
            }
        });
        tvTime.setText(mTime);
        edtTitle.setText(mTitle);
        edtDescription.setText(mDescription);
        imgNote.setImageURI(Uri.parse(mUriImage));
        return v;
    }

    private void deleteNote(int id) {
        NoteModify noteModify = new NoteModify(getContext());
        noteModify.deleteNote(id);
    }

    private void updateNote(Note note) {
        NoteModify noteModify = new NoteModify(getContext());
        noteModify.updateNote(note);
    }
}
