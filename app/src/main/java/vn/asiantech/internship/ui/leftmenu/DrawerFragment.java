package vn.asiantech.internship.ui.leftmenu;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 * DrawerFragment create on 12/06 by  Nguyen
 */
public class DrawerFragment extends Fragment implements DrawerAdapter.OnItemsListener {

    private String[] mListDrawer;
    private DrawerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_drawer, container, false);

        initDrawer(v);

        return v;
    }

    private void initDrawer(View v) {
        mListDrawer = getResources().getStringArray(R.array.listdrawer);
        mAdapter = new DrawerAdapter(getContext(), mListDrawer, this);

        RecyclerView rvDrawer = (RecyclerView) v.findViewById(R.id.rvDrawer);

        rvDrawer.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDrawer.setAdapter(mAdapter);
    }

    @Override
    public void showDialogChoice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_text_choice_select)
                .setItems(R.array.choice, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        if (position == 0) {
                            openCamera();
                        } else {
                            openLibrary();
                        }
                    }
                });
        builder.create().show();
    }

    @Override
    public void onItemCLick(int position) {
        mAdapter.setPositionSelected(position);
        if (getActivity() instanceof MainActivity) {
            if (position == 1) {
                ((MainActivity) getActivity()).setMainText(mListDrawer[position - 1]);
            } else {
                ((MainActivity) getActivity()).setMainText(mListDrawer[position - 1]);
            }
        }
    }

    private void setImage(Bitmap bitmap) {
        mAdapter.setImageAvatar(bitmap);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, MainActivity.KEY_CAMERA);
    }

    private void openLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, MainActivity.KEY_LIBRARY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String pathFile;
        Bitmap bitmap;
        if (data != null) {
            switch (requestCode) {
                case MainActivity.KEY_CAMERA:
                    bitmap = (Bitmap) data.getExtras().get("data");
                    pathFile = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, getString(R.string.title), null);
                    performCrop(Uri.parse(pathFile));
                    break;
                case MainActivity.KEY_LIBRARY:
                    Uri uriSelectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    @SuppressLint("Recycle") Cursor cursor = getActivity().getContentResolver().query(uriSelectedImage,
                            filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    bitmap = BitmapFactory.decodeFile(picturePath);
                    pathFile = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, getString(R.string.title), null);
                    performCrop(Uri.parse(pathFile));
                    break;
                case MainActivity.KEY_CROP:
                    try {
                        Bundle extras = data.getExtras();
                        Bitmap bitmapImage = extras.getParcelable("data");
                        setImage(bitmapImage);
                    } catch (NullPointerException e) {
                        Toast.makeText(getActivity(), getActivity().getString(R.string.error_message_error), Toast.LENGTH_SHORT)
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
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.error_message_error), Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
