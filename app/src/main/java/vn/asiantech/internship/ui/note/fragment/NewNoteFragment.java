package vn.asiantech.internship.ui.note.fragment;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.databases.DatabaseHelper;

/**
 * class add note
 * <p>
 * Created by Hai on 6/20/2017.
 */
public class NewNoteFragment extends Fragment implements OnClickListener {
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "notes";
    public static final String KEY_SEND_NOTE = "send_note";
    private static final int REQUEST_CODE_GALLERY = 1000;

    private ImageView mImgAddImage;
    private EditText mEdtTitle;
    private EditText mEdtInputContent;

    private Uri mUri;
    private String mFileName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_new_note, container, false);
        mImgAddImage = (ImageView) view.findViewById(R.id.imgAddImage);
        ImageView imgSaveNote = (ImageView) view.findViewById(R.id.imgSaveNote);
        ImageView imgAttach = (ImageView) view.findViewById(R.id.imgAttachImage);
        mEdtTitle = (EditText) view.findViewById(R.id.edtNoteTitle);
        mEdtInputContent = (EditText) view.findViewById(R.id.edtInputContent);

        imgSaveNote.setOnClickListener(this);
        imgAttach.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSaveNote:
                saveNote();
                break;
            case R.id.imgAttachImage:
                intentGallery();
                break;
        }
    }

    private void intentGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    private boolean copyImageToSDCard(Bitmap image, String path, String fileName) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(path, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 70, fOut);
            fOut.flush();
            fOut.close();
            return true;
        } catch (IOException e) {
            e.getMessage();
            return false;
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        return MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
    }

    private void saveNote() {
        String title = mEdtTitle.getText().toString();
        String content = mEdtInputContent.getText().toString();
        if (!title.isEmpty()) {
            DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", java.util.Locale.getDefault());
            String dayOfWeek = dateFormat.format(now);
            String dateTime = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
            String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
            Note note;
            if (mUri != null) {
                try {
                    copyImageToSDCard(getBitmapFromUri(mUri), PATH, mFileName);
                } catch (IOException e) {
                    e.getMessage();
                }
                Log.d("xxxx", PATH.concat(mFileName));
                note = new Note(dayOfWeek, dateTime, time, title, content, PATH.concat(mFileName));
            } else {
                Log.d("xxxx", "Null");
                note = new Note(dayOfWeek, dateTime, time, title, content, null);
            }
            databaseHelper.insertNote(note);
            NoteFragment fragment = new NoteFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_SEND_NOTE, note);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.fmContent, fragment, null);
            fragmentTransaction.commit();
            getFragmentManager().popBackStack();
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_CODE_GALLERY) {
            mUri = data.getData();
            Log.d("xxxx", mUri + "");
            mFileName = mUri.toString().substring(mUri.toString().lastIndexOf("/"));
            mImgAddImage.setVisibility(View.VISIBLE);
            mImgAddImage.setImageURI(mUri);
        }
    }
}
