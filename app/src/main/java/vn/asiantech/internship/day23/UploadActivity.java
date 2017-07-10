package vn.asiantech.internship.day23;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import vn.asiantech.internship.R;

public class UploadActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 123;
    private static final String TAG = "at-dinhvo";

    private ImageView mImageUpload;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
        mImageUpload = (ImageView) findViewById(R.id.imgImageUpload);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                FileUpload asyncTaskUpload = new FileUpload(new FileUpload.OnResponseImageListener() {
                    @Override
                    public void onResponseImage(String link) {
                        if (link != null) {
                            ImageLoader.getInstance().displayImage(link, mImageUpload);
                            if (mProgressDialog.isShowing()) {
                                mProgressDialog.dismiss();
                            }
                        }
                    }
                });
                asyncTaskUpload.execute(handlerBitmap(bitmap));
                showProgressBar();
            } catch (IOException e) {
                Log.e(TAG, "IOException " + e.toString());
            }
        }
    }

    private void showProgressBar() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.progress_title));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @NonNull
    private String handlerBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String encodeString = Base64.encodeToString(bytes, Base64.NO_WRAP);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("image=");
        try {
            stringBuilder.append(URLEncoder.encode("data:image/jpg;base64," + encodeString, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "UnsupportedEncodingException" + e.toString());
        }
        stringBuilder.append("==");
        return stringBuilder.toString();
    }
}
