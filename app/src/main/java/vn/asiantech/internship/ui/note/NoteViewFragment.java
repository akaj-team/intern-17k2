package vn.asiantech.internship.ui.note;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Note;

/**
 * View Note Fragment
 */
public class NoteViewFragment extends Fragment {
    private static final String KEY_NOTE = "KEY_NOTE";

    private Note mNote;
    private TextView mTvHead;
    private TextView mTvDescription;
    private TextView mTvDate;
    private ImageView mImgThumb;

    public static NoteViewFragment newInstance(Note note) {
        NoteViewFragment fragment = new NoteViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNote = getArguments().getParcelable(KEY_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note_view, container, false);
        initView(v);
        setData();
        return v;
    }

    private void setData() {
        mTvDate.setText(mNote.getDate());
        mTvHead.setText(mNote.getTitle());
        mTvDescription.setText(mNote.getDescription());
        mImgThumb.setImageURI(Uri.parse(mNote.getImagesThumb()));
    }

    private void initView(View v) {
        mImgThumb = (ImageView) v.findViewById(R.id.imgThumb);
        mTvDate = (TextView) v.findViewById(R.id.tvDate);
        mTvDescription = (TextView) v.findViewById(R.id.tvDescription);
        mTvHead = (TextView) v.findViewById(R.id.tvHead);
    }
}
