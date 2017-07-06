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

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import vn.asiantech.internship.R;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 06/07/2017.
 */
public class UploadActivity extends AppCompatActivity {
    private static final String TAG = UploadActivity.class.getName();
    private static final int TYPE_GALLERY = 100;
    private ProgressDialog mProgressDialog;
    private ImageView mImgShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mImgShow = (ImageView) findViewById(R.id.imgShowImage);
        Button btnUpload = (Button) findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, UploadActivity.TYPE_GALLERY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            new UploadImageToServer(this).execute(filePathToEncoded64ImageString(getPath(data.getData())));
        }
    }

    public void initDialogProgress() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.message_progress_dialog));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void dismissDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showImage(String url) {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        imageLoader.displayImage(url, mImgShow);
    }

    private String getPath(Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    private String filePathToEncoded64ImageString(String path) {
        File imageFile = new File(path);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(imageFile);
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File Not Found");
        }
        Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StringBuilder extension = new StringBuilder();
        if (path.substring(path.lastIndexOf("."), path.length()).equals(".png")) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
            extension.append("data:image/png;base64,");
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            extension.append("data:image/jpeg;base64,");
        }
        byte[] b = baos.toByteArray();
        return extension.append(Base64.encodeToString(b, Base64.DEFAULT)).toString();
    }
}
