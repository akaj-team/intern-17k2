package vn.asiantech.internship.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import vn.asiantech.internship.R;
import vn.asiantech.internship.backgrounds.UpImageAsyncTask;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 7/6/2017.
 */
public class UpLoadImageActivity extends AppCompatActivity {

    private static final int REQUEST_PICK_IMAGE = 22;

    private ImageView mImgPicture;
    private ProgressDialog mProgressDialog;
    private UpImageAsyncTask mUpImageAsyncTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_image);

        mImgPicture = (ImageView) findViewById(R.id.imgPicture);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setCancelable(false);
        Button button = (Button) findViewById(R.id.btnUp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_PICK_IMAGE);
            }
        });
        mUpImageAsyncTask = new UpImageAsyncTask(new UpImageAsyncTask.TaskStatusListener() {
            @Override
            public void onPrepareExecute() {
                mProgressDialog.show();
            }

            @Override
            public void onComplete(String result) {
                mProgressDialog.dismiss();
                if (result == null || result.equals("false")) {
                    Toast.makeText(UpLoadImageActivity.this, getString(R.string.up_fail), Toast.LENGTH_LONG).show();
                } else {
                    ImageLoaderConfiguration imageLoaderConfiguration = ImageLoaderConfiguration.createDefault(UpLoadImageActivity.this);
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    imageLoader.init(imageLoaderConfiguration);
                    imageLoader.displayImage(UpImageAsyncTask.URL + result, mImgPicture);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            String encodeImage = encodeImage(uri);
            if (encodeImage == null) {
                Toast.makeText(UpLoadImageActivity.this, getString(R.string.encode_error), Toast.LENGTH_LONG).show();
            } else {
                mUpImageAsyncTask.execute(encodeImage);
            }
        }
    }

    public String encodeImage(Uri uri) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = false;
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(uri),
                    null, bmpFactoryOptions);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            String encodeString = Base64.encodeToString(bytes, Base64.NO_WRAP);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("image=");
            try {
                stringBuilder.append(URLEncoder.encode("data:image/jpg;base64," + encodeString, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                Log.e("tag11", e.getMessage());
            }
            stringBuilder.append("==");
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            Log.i("tag11", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUpImageAsyncTask != null) {
            mUpImageAsyncTask.cancel(true);
        }
    }
}
