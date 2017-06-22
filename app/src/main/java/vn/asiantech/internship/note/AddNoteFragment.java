package vn.asiantech.internship.note;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 20-06-2017.
 */
public class AddNoteFragment extends Fragment {
    private String mImagePath;

    private EditText mEdtTitle;
    private EditText mEdtNote;

    private ImageView mImgSave;
    private ImageView mImgPhoto;

    private NoteFragment mNoteFragment;
    private DatabaseHandler mDatabaseHandler;
    private List<ItemNote> mItemNotes = new ArrayList<>();

    private RecyclerViewNoteAdapter.OnItemClickListener mOnItemClickListener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addnote, container, false);
        Toolbar toolbarAddNote = (Toolbar) view.findViewById(R.id.toolbarAddNote);
        mImgSave = (ImageView) view.findViewById(R.id.imgSave);
        mImgPhoto = (ImageView) view.findViewById(R.id.imgEdit);
        mNoteFragment = new NoteFragment();
        mEdtNote = (EditText) view.findViewById(R.id.edtNote);
        mEdtTitle = (EditText) view.findViewById(R.id.edtTitle);

        mDatabaseHandler = new DatabaseHandler(getContext());
        mItemNotes = mDatabaseHandler.getAllContacts();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbarAddNote);
        ActionBar actionbar = activity.getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayShowTitleEnabled(true);
            actionbar.setDisplayShowHomeEnabled(true);
            actionbar.setDisplayShowCustomEnabled(true);
        }


        addNote();

        mImgSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(mNoteFragment, false);
                DatabaseHandler db = new DatabaseHandler(getContext());
                db.addContact(new ItemNote(mEdtTitle.getText().toString(), mEdtNote.getText().toString(), mImagePath));
            }
        });

        mImgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Choose").setItems(R.array.choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mOnItemClickListener.onAvatarClick(NoteActivity.REQUEST_CODE_GALERY);
                                break;
                            case 1:
                                mOnItemClickListener.onAvatarClick(NoteActivity.REQUEST_CODE_CAMERA);
                                break;
                            default:
                                dialog.dismiss();
                        }
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
        return view;
    }

    public void replaceFragment(Fragment fragment, boolean backStack) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentNote, fragment);
        if (backStack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        fragmentTransaction.commit();
    }

    public void addNote() {

        if (TextUtils.isEmpty(mEdtNote.getText()) || TextUtils.isEmpty(mEdtTitle.getText())) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
        } else {
            ItemNote noteItem;
            if (mImgSave != null) {
                noteItem = new ItemNote(mEdtTitle.getText().toString(), mEdtNote.getText().toString(), mImagePath);
            } else {
                noteItem = new ItemNote(mEdtTitle.getText().toString(), mEdtNote.getText().toString());
            }
            if (mDatabaseHandler.addContact(noteItem) > 0) {
                Toast.makeText(getContext(), "sussces", Toast.LENGTH_SHORT).show();
                mEdtNote.setText("");
                mEdtTitle.setText("");
            } else {
                Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addImage(String filePath) {
        if (filePath != null) {
            mImagePath = filePath;
            mImgPhoto.setVisibility(View.VISIBLE);
            mImgPhoto.setImageURI(Uri.parse(filePath));
        } else {
            mImgPhoto.setVisibility(View.GONE);
        }
    }
}
