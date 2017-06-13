package vn.asiantech.internship.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

import vn.asiantech.internship.DrawerAdapter;
import vn.asiantech.internship.MainActivity;
import vn.asiantech.internship.R;
import vn.asiantech.internship.RecyclerItemClickListener;

/**
 * DrawerFragment create on 12/06 by Thien Nguyen
 */
public class DrawerFragment extends Fragment implements MainActivity.MainActivityInterface {

    private String[] mListDrawer;
    private DrawerAdapter mDrawerAdapter;
    private Uri mUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rvDrawer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mListDrawer = getResources().getStringArray(R.array.listDrawer);
        mDrawerAdapter = new DrawerAdapter(getContext(), mListDrawer, DrawerFragment.this);
        recyclerView.setAdapter(mDrawerAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position != 0) {
                    mDrawerAdapter.setPositionSelected(position);
                    if (getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).setMainText(mListDrawer[position]);
                    }
                }
            }
        }));

        return v;
    }

    public void showDialogChoice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.Dialog_Text_Choice_Select)
                .setItems(R.array.Choice, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        if (position == 0) {
                            OpenCamera();
                        } else {
                            OpenLibrary();
                        }
                    }
                });
        builder.create().show();
    }

    @Override
    public void setImage(Bitmap bitmap) {
        mDrawerAdapter.setImageAvatar(bitmap);
    }

    @Override
    public void OpenCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File photo = new File(Environment.getExternalStorageDirectory(), new Date().toString() + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo));
        mUri = Uri.fromFile(photo);
        startActivityForResult(intent, MainActivity.KEY_CAMERA);
    }

    @Override
    public void OpenLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, MainActivity.KEY_LIBRARY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case MainActivity.KEY_CAMERA:
                    if (resultCode == Activity.RESULT_OK) {
                        Uri selectedImage = mUri;
                        getActivity().getContentResolver().notifyChange(selectedImage, null);
                        ContentResolver contentResolver = getActivity().getContentResolver();
                        Bitmap bitmap;
                        try {
                            bitmap = android.provider.MediaStore.Images.Media
                                    .getBitmap(contentResolver, selectedImage);
                            String pathFile = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null);
                            performCrop(Uri.parse(pathFile));
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), getActivity().getString(R.string.Error_Message_Error), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    break;
                case MainActivity.KEY_LIBRARY:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    @SuppressLint("Recycle") Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                    String pathFile = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null);
                    performCrop(Uri.parse(pathFile));
                    break;
                case MainActivity.KEY_CROP:
                    try {
                        Bundle extras = data.getExtras();
                        Bitmap bitmapImage = extras.getParcelable("data");
                        setImage(bitmapImage);
                    } catch (NullPointerException e) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.Error_Message_Error) + "Ssdsd", Toast.LENGTH_SHORT)
                                .show();
                    }
                    break;
            }
        }
    }

    private void performCrop(Uri picUri) {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, MainActivity.KEY_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.Error_Message_Error) + "Sdsdd", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
