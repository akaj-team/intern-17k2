package vn.asiantech.internship.ui.note.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Note;
import vn.asiantech.internship.ui.note.activity.NoteActivity;
import vn.asiantech.internship.ui.note.databases.DatabaseHelper;

/**
 * class add note
 * <p>
 * Created by Hai on 6/20/2017.
 */
public class NewNoteFragment extends Fragment implements OnClickListener {
    private static final int REQUEST_CODE_GALLERY = 100000;

    private ImageView mImgAddImage;
    private EditText mEdtInputContent;

    private Uri mUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_new_note, container, false);
        mImgAddImage = (ImageView) view.findViewById(R.id.imgAddImage);
        ImageView imgSaveNote = (ImageView) view.findViewById(R.id.imgSaveNote);
        mEdtInputContent = (EditText) view.findViewById(R.id.edtInputContent);

        mImgAddImage.setOnClickListener(this);
        imgSaveNote.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imgAddImage) {
            intentGallery();
        }
        if (v.getId() == R.id.imgSaveNote) {
            String content = mEdtInputContent.getText().toString();
            if (!content.isEmpty() && !mUri.toString().isEmpty()) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.US);
                String dayOfWeek = dateFormat.format(now);
                String dateTime = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
                String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                Note note = new Note(dayOfWeek, dateTime, time, mEdtInputContent.getText().toString(), mUri.toString());
                databaseHelper.insertDb(note);
                Intent intent = new Intent(getActivity(), NoteActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }
    }

    private void intentGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_CODE_GALLERY) {
            mUri = data.getData();
            mImgAddImage.setImageURI(mUri);
        }
    }
}
