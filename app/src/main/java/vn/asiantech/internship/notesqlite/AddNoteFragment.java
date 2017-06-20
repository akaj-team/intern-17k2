package vn.asiantech.internship.notesqlite;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vn.asiantech.internship.R;

import static android.app.Activity.RESULT_OK;

/**
 * Used to enter data for item of recyclerView.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-20
 */
public class AddNoteFragment extends Fragment {
    private static final int REQUEST_CODE = 3;
    private ImageView mImgNote;
    private EditText mEdtTitle;
    private EditText mEdtContent;
    private NoteSqlite mDatabase;
    private Uri mUriImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        mImgNote = (ImageView) view.findViewById(R.id.imgImageNote);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitle);
        mEdtContent = (EditText) view.findViewById(R.id.edtContent);
        ImageView imgChooseImage = (ImageView) view.findViewById(R.id.imgAddImage);
        ImageView imgAdd = (ImageView) view.findViewById(R.id.btnAdd);

        mDatabase = new NoteSqlite(getActivity());
        imgChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                SimpleDateFormat dayOfWeekFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
                SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
                if (mEdtTitle.getText().toString().equals("") || mEdtContent.getText().toString().equals("") || mUriImage == null) {
                    Toast.makeText(getActivity(), "Inquire enough data entry!", Toast.LENGTH_LONG).show();
                } else {
                    mDatabase.open();
                    mDatabase.createData(new Note(dayOfWeekFormat.format(date), String.valueOf(DateFormat.format("dd", date)), monthFormat.format(date), String.valueOf(DateFormat.format("hh:mm:ss", date)), mEdtTitle.getText().toString(), mEdtContent.getText().toString(), getRealPathFromUri(mUriImage)));
                    mDatabase.close();
                    ((NoteActivity) (getActivity())).showList();
                }
            }
        });
        return view;
    }

    private String getRealPathFromUri(Uri tempUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(tempUri, proj, null, null, null);
            int column_index = cursor != null ? cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) : 0;
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE) {
                mUriImage = data.getData();
                mImgNote.setVisibility(View.VISIBLE);
                mImgNote.setImageBitmap(BitmapFactory.decodeFile(getRealPathFromUri(mUriImage)));
            }
        }
    }
}
