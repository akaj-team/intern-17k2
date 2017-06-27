package vn.asiantech.internship.note;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 25-06-2017.
 */

public class DetailNoteFragment extends Fragment implements View.OnClickListener {
    private EditText mEdtTitle;
    private EditText mEdtNote;
    private ImageView mImgDelete;
    private ImageView mImgEdit;
    private ImageView mImgPhoto;
    private NoteFragment mNoteFragment;
    private TextView mTvTime;
    private List<ItemNote> mItemNotes = new ArrayList<>();
    private NoteDatabase mNoteDatabase;
    private ItemNote mNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        initView(view);
        try {
            mNoteDatabase.open();
            mItemNotes = mNoteDatabase.getList();
            mNoteDatabase.close();
        } catch (IOException e) {
            Log.d("tag", "ERROR");
        }
        Bundle bundle = getArguments();
        int position = bundle.getInt("position");
        mNote = mItemNotes.get(position);
        setData();
        mImgDelete.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        try {
            mNoteDatabase.open();
            mNoteDatabase.delete(mNote);
            mNoteDatabase.close();
            ((OnReplaceFragmentListener) getActivity()).onReplaceFragmentMain();
        } catch (IOException e) {
            Log.d("tag1", "ERROR");
        }
    }

    private void setData() {
        if (mNote.getImage() != null) {
            File file = new File(mNote.getImage());
            if (file.exists()) {
                mImgPhoto.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            }
        }
        mEdtTitle.setText(mNote.getTitle());
        mEdtNote.setText(mNote.getNote());
        mTvTime.setText(mNote.getTime());
    }

    private void initView(View view) {
        mImgDelete = (ImageView) view.findViewById(R.id.imgDelete);
        mImgEdit = (ImageView) view.findViewById(R.id.imgEdit);
        mImgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
        mEdtNote = (EditText) view.findViewById(R.id.edtNote);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitle);
        mTvTime = (TextView) view.findViewById(R.id.tvTime);
    }

//    public void replaceFragment(Fragment fragment, boolean backStack) {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragmentNote, fragment);
//        if (backStack) {
//            fragmentTransaction.addToBackStack(fragment.getTag());
//        }
//        fragmentTransaction.commit();
//    }
}
