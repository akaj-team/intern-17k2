package vn.asiantech.internship.ui.uploadimage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import vn.asiantech.internship.R;

/**
 *
 * Created by quanghai on 05/07/2017.
 */
@EActivity(R.layout.activity_upload_image)
public class UploadImageActivity extends AppCompatActivity {
    private static final String URL = "http://2.pik.vn/";
    private static final int REQUEST_CODE_PERMISSION_GALLERY = 101;
    private static final int REQUEST_CODE_GALLERY = 1000;
    @ViewById(R.id.imgView)
    ImageView mImgUpload;

    @ViewById(R.id.btnUpload)
    Button mBtnUpload;

    private Uri mUri;

    @Click
    void imgView() {
        checkPermissionGallery();
    }

    @Click
    void btnUpload() {
        postImage();
    }

    @Background
    void postImage() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("xxx", "onResponse: " + response);
                try {
                    if (!TextUtils.isEmpty(response)) {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.has("saved")) {
                            String path = jsonObject.getString("saved");
                            setImageView(URL.concat(path));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                params.put("image", encodeImage());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @UiThread
    void setImageView(String uri) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        imageLoader.displayImage(uri, mImgUpload);
    }

    private String encodeImage() {
        Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(mUri));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        Log.d("xxx", "encodeImage: " + Base64.encodeToString(b, Base64.DEFAULT));
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void intentGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK && requestCode == REQUEST_CODE_GALLERY) {
            mUri = data.getData();
        }
    }
}
