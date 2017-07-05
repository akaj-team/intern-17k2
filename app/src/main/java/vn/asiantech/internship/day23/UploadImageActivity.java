package vn.asiantech.internship.day23;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import vn.asiantech.internship.R;

public class UploadImageActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    private ImageView mImgUpload;
    private ProgressDialog mDialog;
    private String responseString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        mImgUpload = (ImageView) findViewById(R.id.imgUpload);
        findViewById(R.id.btnUpload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                mImgUpload.setImageURI(selectedImage);
                final String filePath = getPath(selectedImage);
                String file_extn = filePath.substring(filePath.lastIndexOf(".") + 1);
                Log.e("AAAA", "onActivityResult: " + filePath);
                /*if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                    mDialog = ProgressDialog.show(UploadImageActivity.this, "", "Uploading file...", true);
                    new Thread(new Runnable() {
                        public void run() {
                            HandlerUploadFile handler = new HandlerUploadFile();
                            handler.uploadFile(filePath);
                        }
                    }).start();
                }*/
                if (file_extn.equals("img") || file_extn.equals("jpg") || file_extn.equals("jpeg") || file_extn.equals("gif") || file_extn.equals("png")) {
                    mDialog = ProgressDialog.show(UploadImageActivity.this, "", "Uploading file...", true);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                HandlerUploadFile handler = new HandlerUploadFile("http://2.pik.vn", "UTF-8");
                                handler.addFilePart("at-dinhvo",
                                        new File(filePath));
                                List<String> response = handler.finish();
                                Log.e("AAAA", "SERVER REPLIED: " + filePath);
                                for (String line : response) {
                                    Log.e("AAAAA", "Upload Files Response:::" + line);
                                    responseString = line;
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            } else {
                //NOT IN REQUIRED FORMAT
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}