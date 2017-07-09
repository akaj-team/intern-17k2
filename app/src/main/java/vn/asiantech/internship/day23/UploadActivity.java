package vn.asiantech.internship.day23;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import vn.asiantech.internship.R;

public class UploadActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private static final String TAG = "error";
    private ImageView mImgShowImage;
    private ProgressDialog mProgressDialog;
    private final FileUpload mUploadFileToServe = new FileUpload(new FileUpload.OnAsyncResponseListener() {
        @Override
        public void onProcessFinish(String link) {
            if (link != null) {
                ImageLoader.getInstance().displayImage(link, mImgShowImage);
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        mImgShowImage = (ImageView) findViewById(R.id.imgShowImage);
        Button btnUpload = (Button) findViewById(R.id.btnUpload);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private String getRealPathFromUri(Uri tempUri) {
        Cursor cursor;
        try {
            String[] images = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(tempUri, images, null, null, null);
            int column_index;
            String path;
            if (cursor != null) {
                column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                path = cursor.getString(column_index);
                cursor.close();
            } else {
                path = null;
            }
            return path;
        } catch (NullPointerException e) {
            Log.e(TAG, "NullPointerException" + e.toString());
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE) {
                Uri uriImage = data.getData();
                mUploadFileToServe.execute(getFileToByte(getRealPathFromUri(uriImage)));
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
            }
        }
    }

    private String getFileToByte(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String encodeString = Base64.encodeToString(bytes, Base64.NO_WRAP);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("image=");
        try {
            stringBuilder.append(URLEncoder.encode("data:image/jpg;base64," + encodeString, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "UnsupportedEncodingException" + e.toString());
        }
        stringBuilder.append("==");
        return stringBuilder.toString();
    }
}
