package vn.asiantech.internship.ui.note.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.NoteDatabase;
import vn.asiantech.internship.models.Note;

/**
 * class add note
 * <p>
 * Created by Hai on 6/20/2017.
 */
public class NewNoteFragment extends Fragment implements OnClickListener {
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "notes";
    public static final String KEY_SEND_NOTE = "send_note";
    public static final int REQUEST_CODE_GALLERY = 1000;

    private ImageView mImgAddImage;
    private ImageView mImgEdit;
    private ImageView mImgDelete;
    private EditText mEdtTitle;
    private EditText mEdtInputContent;

    private Uri mUri;
    private String mFileName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_new_note, container, false);
        initView(view);
        setToolbar();
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

    public Bitmap decreaseSizeImage(Context context, Activity activity, Uri imageFileUri) {
        Bitmap bitmap;
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int dw = size.x;
        int dh = size.y;
        try {
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = true;
            int heightRatio = (int) Math
                    .ceil(bmpFactoryOptions.outHeight / (float) dh);
            int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                    / (float) dw);
            if (heightRatio > 1 && widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    bmpFactoryOptions.inSampleSize = heightRatio;
                } else {
                    bmpFactoryOptions.inSampleSize = widthRatio;
                }
            }
            bmpFactoryOptions.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(context.getContentResolver()
                            .openInputStream(imageFileUri), null,
                    bmpFactoryOptions);
            return bitmap;
        } catch (FileNotFoundException e) {
            Log.v("ERROR", e.toString());
            return null;
        }
    }

    public boolean copyImageToSDCard(Bitmap image, String path, String fileName) {
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

    private void saveNote() {
        String title = mEdtTitle.getText().toString();
        String content = mEdtInputContent.getText().toString();
        if (!title.isEmpty()) {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            String dayOfWeek = dateFormat.format(now);
            String dateTime = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
            String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().getTime());
            Note note;
            if (mUri != null) {
                copyImageToSDCard(decreaseSizeImage(getContext(), getActivity(), mUri), PATH, mFileName);
                note = new Note(dayOfWeek, dateTime, time, title, content, PATH.concat(mFileName));
            } else {
                note = new Note(dayOfWeek, dateTime, time, title, content, null);
            }
            NoteDatabase noteDatabase = new NoteDatabase(getContext());
            noteDatabase.open();
            noteDatabase.insertNote(note);
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

    private void initView(View view) {
        mImgAddImage = (ImageView) view.findViewById(R.id.imgAddImage);
        ImageView imgSave = (ImageView) view.findViewById(R.id.imgSaveNote);
        ImageView imgAttach = (ImageView) view.findViewById(R.id.imgAttachImage);
        mImgEdit = (ImageView) view.findViewById(R.id.imgEditNote);
        mImgDelete = (ImageView) view.findViewById(R.id.imgDeleteNote);
        mEdtTitle = (EditText) view.findViewById(R.id.edtNoteTitle);
        mEdtInputContent = (EditText) view.findViewById(R.id.edtInputContent);
        imgSave.setOnClickListener(this);
        imgAttach.setOnClickListener(this);
    }

    private void setToolbar() {
        mImgEdit.setVisibility(View.GONE);
        mImgDelete.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_CODE_GALLERY) {
            mUri = data.getData();
            Log.d("xxxx", mUri + "");
            mFileName = mUri.toString().substring(mUri.toString().lastIndexOf("/"));
            mImgAddImage.setVisibility(View.VISIBLE);
            mImgAddImage.setImageBitmap(decreaseSizeImage(getContext(), getActivity(), mUri));
        }
    }
}
