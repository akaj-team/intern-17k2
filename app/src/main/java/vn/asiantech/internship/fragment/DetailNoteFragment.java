package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.NoteDataBase;
import vn.asiantech.internship.interfaces.OnReplaceFragmentListener;
import vn.asiantech.internship.models.Note;

/**
 * Created by ducle on 20/06/2017.
 */
public class DetailNoteFragment extends Fragment implements View.OnClickListener {
    private ImageView mImgDelete;
    private ImageView mImgNote;
    private TextView mTvTitleNote;
    private TextView mTvContent;
    private TextView mTvDate;
    private NoteDataBase mNoteDataBase;
    private List<Note> mNotes;
    private Note mNote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_detail_note, container, false);
        initeViews(view);
        mNoteDataBase = new NoteDataBase(getActivity());
        try {
            mNoteDataBase.open();
            mNotes = mNoteDataBase.getList();
            mNoteDataBase.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
        mNote = mNotes.get(position);
        setData();
        mImgDelete.setOnClickListener(this);
        return view;
    }

    private void setData() {
        if (mNote.getUrlImage() != null) {
            File file = new File(mNote.getUrlImage());
            if (file.exists()) {
                mImgNote.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            }
        }
        mTvTitleNote.setText(mNote.getTitle());
        mTvContent.setText(mNote.getContent());
        mTvDate.setText(mNote.getDate());
    }

    private void initeViews(View view) {
        mImgDelete = (ImageView) view.findViewById(R.id.imgDelete);
        mImgNote = (ImageView) view.findViewById(R.id.imgNote);
        mTvTitleNote = (TextView) view.findViewById(R.id.tvTitleNote);
        mTvContent = (TextView) view.findViewById(R.id.tvContent);
        mTvDate = (TextView) view.findViewById(R.id.tvDate);
        mImgDelete = (ImageView) view.findViewById(R.id.imgDelete);
    }

    @Override
    public void onClick(View v) {
        try {
            mNoteDataBase.open();
            mNoteDataBase.delete(mNote);
            mNoteDataBase.close();
            ((OnReplaceFragmentListener) getActivity()).OnReplaceFragmentMain();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
