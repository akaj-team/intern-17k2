package vn.asiantech.internship.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;
import vn.asiantech.internship.R;
import vn.asiantech.internship.feed.adapters.PhotoListAdapter;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/15/2017
 */
public class ShowImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        ImageView imgShow = (ImageView) findViewById(R.id.imgPhoto);
        int imageId = getIntent().getIntExtra(PhotoListAdapter.KEY_IMAGE, -1);
        if (imageId == -1) {
            finish();
            return;
        }
        imgShow.setImageResource(imageId);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imgShow);
        photoViewAttacher.update();
    }
}
