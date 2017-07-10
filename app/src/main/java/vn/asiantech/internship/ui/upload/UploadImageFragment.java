package vn.asiantech.internship.ui.upload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import vn.asiantech.internship.R;

/**
 * UploadImageFragment
 */
public class UploadImageFragment extends Fragment {

    private static final String URL_HOST = "http://2.pik.vn/";
    private static final String TAG = "UploadImageFragment";
    private static final int KEY_LIBRARY = 2;

    private ImageView mImgShow;
    private String mBase64 = "";
    private ImageLoader mImageLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_image, container, false);
        mImgShow = (ImageView) v.findViewById(R.id.imgShow);
        Button btnUpload = (Button) v.findViewById(R.id.btnUpload);
        btnUpload.setOnClickListener(uploadImage());
        mImgShow.setOnClickListener(choiceImage());
        return v;
    }

    private View.OnClickListener uploadImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.equals(mBase64, "")) {
                    new UploadToNetwork().execute(URL_HOST);
                } else {
                    Toast.makeText(getContext(), getString(R.string.error_message_please_choice_image), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private View.OnClickListener choiceImage() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLibrary();
            }
        };
    }

    private void openLibrary() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, KEY_LIBRARY);
    }

    String post(String url, String base64) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
        RequestBody body = RequestBody.create(mediaType, base64);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case KEY_LIBRARY:
                    Uri uriSelectedImage = data.getData();

                    Display display = getActivity().getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int dw = size.x;
                    int dh = size.y;
                    try {
                        // Load up the image's dimensions not the image itself
                        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                        bmpFactoryOptions.inJustDecodeBounds = true;
                        int heightRatio = (int) Math
                                .ceil(bmpFactoryOptions.outHeight / (float) dh);
                        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
                                / (float) dw);
                        if (heightRatio > 1 && widthRatio > 1) {
                            if (heightRatio > widthRatio) {
                                bmpFactoryOptions.inSampleSize = heightRatio;
                            } else {
                                bmpFactoryOptions.inSampleSize = widthRatio;
                            }
                        }
                        bmpFactoryOptions.inJustDecodeBounds = false;
                        Bitmap bmp = BitmapFactory.decodeStream(getActivity().getContentResolver()
                                        .openInputStream(uriSelectedImage), null,
                                bmpFactoryOptions);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        mBase64 = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                        mBase64 = "image=data:image/png;base64," + mBase64;
                        mBase64 = URLEncoder.encode(mBase64, "UTF-8");
                    } catch (FileNotFoundException | UnsupportedEncodingException e) {
                        Log.e("onActivityResult ", e.toString());
                    }
                    break;
            }
        }
    }

    /**
     * UploadToNetwork using AsyncTask
     */
    private class UploadToNetwork extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String s = "";
            try {
                s = post(params[0], mBase64);
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObj = new JSONObject(s);
                if (jsonObj.getString("saved") != null) {
                    String urlImage = jsonObj.getString("saved");
                    if (!TextUtils.equals(urlImage, "") && !TextUtils.equals(urlImage, null)) {
                        pushOnToImages(urlImage);
                    } else {
                        Toast.makeText(getContext(), getString(R.string.error_message_load_fail), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.error_message_load_fail), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.e("JSONException", e.toString());
            }
            super.onPostExecute(s);
        }
    }

    private void pushOnToImages(String urlImage) {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageOnFail(R.drawable.ic_avatar)
                .build();
        getImageLoader();
        mImageLoader.displayImage(URL_HOST + urlImage, mImgShow, displayImageOptions);
    }

    private ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = ImageLoader.getInstance();
            mImageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));
        }
        return this.mImageLoader;
    }
}
