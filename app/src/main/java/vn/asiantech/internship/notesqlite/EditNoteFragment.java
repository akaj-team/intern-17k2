package vn.asiantech.internship.notesqlite;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Used to enter data for item of recyclerView.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-20
 */
public class EditNoteFragment extends Fragment {
    private ImageView mImgNote;
    private EditText mEdtTitle;
    private EditText mEdtContent;
    private TextView mTvTime;
    private NoteSqlite mDatabase;
    private Note mNote;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_note, container, false);
        mImgNote = (ImageView) view.findViewById(R.id.imgNoteEdit);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitleEdit);
        mEdtContent = (EditText) view.findViewById(R.id.edtContentEdit);
        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        ImageView imgEdit = (ImageView) view.findViewById(R.id.imgEditNote);
        ImageView imgDelete = (ImageView) view.findViewById(R.id.imgDeleteNote);
        ImageView imgSave = (ImageView) view.findViewById(R.id.imgSave);
        mNote = getArguments().getParcelable("Note");
        setData(mNote);
        mDatabase = new NoteSqlite(getActivity());
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEdtTitle.setEnabled(true);
                mEdtContent.setEnabled(true);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.open();
                mDatabase.delete(mNote.getId());
                mDatabase.close();
                ((NoteActivity) (getActivity())).replaceNoteFragment();
            }
        });

        imgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.open();
                mDatabase.update(mEdtTitle.getText().toString(), mEdtContent.getText().toString(), mNote.getId());
                mDatabase.close();
                ((NoteActivity) (getActivity())).replaceNoteFragment();
            }
        });
        return view;
    }

    private void setData(Note note) {
        if (note != null) {
            if (!TextUtils.equals(note.getPathImage(), "")) {
                mImgNote.setVisibility(View.VISIBLE);
                mImgNote.setImageBitmap(BitmapFactory.decodeFile(note.getPathImage()));
            }
            mEdtTitle.setText(note.getTitle());
            mEdtContent.setText(note.getContent());
            mTvTime.setText(note.getDay() + "\n" + note.getDayOfWeek() + " " + note.getMonth() + "\n" + note.getHour());
        }
    }
}
