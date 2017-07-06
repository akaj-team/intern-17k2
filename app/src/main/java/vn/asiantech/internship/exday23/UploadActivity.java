package vn.asiantech.internship.exday23;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by DatBui on 06/07/2017.
 */
@EActivity(R.layout.activity_upload)
public class UploadActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_CROP = 11;
    public static final int REQUEST_CODE_GALERY = 22;
    public static String urlServer = "http://2.pik.vn/";
    private Bitmap mBitmap;
    private static String mLinkImage = null;

    @ViewById(R.id.btnUpload)
    Button mBtnUpload;

    @ViewById(R.id.imgApi)
    ImageView mImgApi;

    @Click(R.id.btnUpload)
    void uploadImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALERY);
    }

    @AfterViews
    void initView() {
        initBackGround();
    }

    @UiThread
    void initData() {

    }

    @Background
    void initBackGround() {
        initData();
    }

    @OnActivityResult(REQUEST_CODE_GALERY)
    void onResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            performCrop(data.getData());
            Log.d("tag", "onResult: " + data.getData());
        }
    }

    @OnActivityResult(REQUEST_CODE_CROP)
    void onCrop(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            mBitmap = data.getExtras().getParcelable("data");
            try {
                upLoadImage();
            } catch (IOException e) {
                Log.d("tag", "onCrop: false");
            }
        }
    }

    private void performCrop(Uri uri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(uri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, REQUEST_CODE_CROP);
        } catch (ActivityNotFoundException e) {
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return "data:image/png;base64," +
                imgString;
    }

    public String upLoadImage() throws IOException {
        String encodedImageData = getEncoded64ImageStringFromBitmap(mBitmap);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", encodedImageData)
                .build();
        Request request = new Request.Builder()
                .url(urlServer)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .addHeader("content-length", "36378")
                .method("POST", requestBody)
                .build();
        final String[] res = new String[1];
        final String[] url = {null};
        final String[] image = {null};
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("VVVV", "onFailure: " + e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                res[0] = response.body().string();
                JSONObject jsObject;
                try {
                    jsObject = new JSONObject(res[0]);
                    url[0] = jsObject.get("saved").toString();
                } catch (JSONException e) {
                    Log.d("tag", "onResponse: false");
                }
                image[0] = urlServer + url[0];
                mLinkImage = image[0];
                UploadActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getApplicationContext()).load(mLinkImage).into(mImgApi);
                    }
                });
            }
        });
        return mLinkImage;
    }
}
