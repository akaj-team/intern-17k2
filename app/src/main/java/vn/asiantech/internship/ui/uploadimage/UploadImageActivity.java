package vn.asiantech.internship.ui.uploadimage;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import vn.asiantech.internship.R;

/**
 * Created by quanghai on 05/07/2017.
 */
@EActivity(R.layout.activity_upload_image)
public class UploadImageActivity extends AppCompatActivity {
    private static final String URL = "http://2.pik.vn/";
    private static final String HEADER_URL = "data:image/png;base64,";
    private static final int REQUEST_CODE_PERMISSION_GALLERY = 101;
    private static final int REQUEST_CODE_GALLERY = 1000;

    @ViewById(R.id.imgView)
    ImageView mImageView;

    @ViewById(R.id.btnPickImage)
    Button mBtnUpload;

    private ProgressDialog mProgressDialog;

    @Click
    void btnPickImage() {
        checkPermissionGallery();
    }

    private void postImage(final Uri uri) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("xxx", "onResponse: " + response);
                try {
                    if (!TextUtils.isEmpty(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("saved") && !TextUtils.isEmpty(jsonObject.getString("saved"))) {
                            String path = jsonObject.getString("saved");
                            setImageView(URL.concat(path));
                        }
                    }
                } catch (JSONException e) {
                    Log.e("JSONException", "JSONException: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xxx", "onErrorResponse: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("image", HEADER_URL.concat(encodeImage(uri)));
                return params;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    void setImageView(String uri) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        imageLoader.displayImage(uri, mImageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                dismissDialog();
                Toast.makeText(getBaseContext(), getString(R.string.message_failed), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                dismissDialog();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                dismissDialog();
                Toast.makeText(getBaseContext(), getString(R.string.message_failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String encodeImage(Uri uri) {
        String result = null;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            result = Base64.encodeToString(b, Base64.NO_WRAP);
        } catch (IOException e) {
            Log.e("IOException", "IOException: " + e.getMessage());
        }
        return result;
    }

    private void intentGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    private void showDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.upload_dialog_message));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    private void dismissDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void checkPermissionGallery() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_GALLERY);
            }
        } else {
            intentGallery();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                intentGallery();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnActivityResult(REQUEST_CODE_GALLERY)
    void onResult(int resultCode, Intent data) {
        if (data != null && resultCode == RESULT_OK) {
            showDialog();
            Uri uri = data.getData();
            postImage(uri);
        }
    }
}
