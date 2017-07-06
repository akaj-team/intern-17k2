package vn.asiantech.internship.ui.upload;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import vn.asiantech.internship.R;

/**
 * Upload and Get Image with Volley.
 * Created by AnhHuy on 06-Jul-17.
 */
public class UploadGetImageActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 111;

    private ImageView mImgLoad;
    private Button mBtnChooseUpload;
    private String mURL = "http://2.pik.vn/";
    private Bitmap mBitmap;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_get_image);

        initView();
        chooseImage();
    }

    private void initView() {
        mImgLoad = (ImageView) findViewById(R.id.imgResponse);
        mBtnChooseUpload = (Button) findViewById(R.id.btnChooseUpload);
    }

    private void chooseImage() {
        mBtnChooseUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), PICK_IMAGE_REQUEST);
            }
        });
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(UploadGetImageActivity.this);
        mProgressDialog.setTitle(getString(R.string.progress_dialog_activity_title));
        mProgressDialog.setMessage(getString(R.string.progress_dialog_activity_wait));
        mProgressDialog.show();
    }

    private String convertBase64() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return "data:image/png;base64," + imageString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            } catch (Exception e) {
                Log.e("TAG", "Exception " + e.getMessage());
            }

            initProgressDialog();

            StringRequest request = new StringRequest(Request.Method.POST, mURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        if (!TextUtils.isEmpty(response)) {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("saved") && !TextUtils.isEmpty(jsonObject.getString("saved"))) {
                                String path = jsonObject.getString("saved");
                                setImage(mURL.concat(path));
                            }
                        }
                    } catch (JSONException e) {
                        Log.e("TAG1", "JSONException " + e.getMessage());
                    }

                    mProgressDialog.dismiss();
                    if (!TextUtils.isEmpty(response)) {
                        Toast.makeText(UploadGetImageActivity.this, "Uploaded Successful" + response, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(UploadGetImageActivity.this, "Some error occurred!" + response, Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(UploadGetImageActivity.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("image", convertBase64());
                    return parameters;
                }
            };
            request.setShouldCache(false);

            RequestQueue rQueue = Volley.newRequestQueue(UploadGetImageActivity.this);
            rQueue.add(request);
        }
    }

    private void setImage(String uri) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        imageLoader.displayImage(uri, mImgLoad, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}
