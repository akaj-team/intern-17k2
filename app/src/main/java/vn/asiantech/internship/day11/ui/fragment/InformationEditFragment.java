package vn.asiantech.internship.day11.ui.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day11.database.NoteModify;
import vn.asiantech.internship.day11.model.Note;
import vn.asiantech.internship.day11.ui.activity.NoteActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by at-hoavo on 19/06/2017.
 */
public class InformationEditFragment extends Fragment {
    private static final int TYPE_GALLERY = 0;

    private EditText mEdtTitle;
    private EditText mEdtDescription;
    private ImageView mImgNote;
    private ImageView mImgSave;
    private ImageView mImgPhoto;
    private Uri mUri;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_information, container, false);
        initView(v);
        mImgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, TYPE_GALLERY);
            }
        });
        mImgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteModify noteModify = new NoteModify(getContext());
                Date date = new Date();
                SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd hh:mm:ss ");
                Note note = new Note(0, mEdtTitle.getText().toString(), mEdtDescription.getText().toString(), mUri.toString(), ft.format(date));
                noteModify.insert(note);
                if (getActivity() instanceof NoteActivity) {
                    ((NoteActivity) getActivity()).changeFragment(new NoteFragment());
                }
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null && requestCode == TYPE_GALLERY) {
            mUri = data.getData();
            mImgNote.setVisibility(View.VISIBLE);
            mImgNote.setImageBitmap(BitmapFactory.decodeFile(fromUritoPath(mUri)));
        }
    }

    public void initView(View v) {
        mEdtTitle = (EditText) v.findViewById(R.id.edtTitle);
        mEdtDescription = (EditText) v.findViewById(R.id.edtDescription);
        mImgNote = (ImageView) v.findViewById(R.id.imgEditInformation);
        mImgSave = (ImageView) v.findViewById(R.id.imgSave);
        mImgPhoto = (ImageView) v.findViewById(R.id.imgPhoto);
    }

    public String fromUritoPath(Uri uri) {
        Cursor cursor;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(uri, proj, null, null, null);
            int column_index;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            } else {
                column_index = 0;
            }
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (NullPointerException e) {
            Log.e("Pointer is null", e.toString());
        }
        return "";
    }
}
