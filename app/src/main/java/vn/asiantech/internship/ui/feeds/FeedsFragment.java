package vn.asiantech.internship.ui.feeds;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.database.DatabaseHelper;
import vn.asiantech.internship.models.Feed;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedsFragment extends Fragment {

    private List<Feed> mFeeds = new ArrayList<>();
    private DatabaseHelper mDatabaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feeds, container, false);
        RecyclerView rvFeeds = (RecyclerView) v.findViewById(R.id.rvFeeds);
        rvFeeds.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabaseHelper = new DatabaseHelper(getContext());
        addData();
        FeedsAdapter adapter = new FeedsAdapter(mFeeds);
        rvFeeds.setAdapter(adapter);
        return v;
    }

    private void addData() {
        String s = saveToSdCard(R.drawable.bg_steve);
        String s2 = saveToSdCard(R.drawable.bg_stevi);
        String ic = saveToSdCard(R.drawable.ic_one);
        String ic2 = saveToSdCard(R.drawable.ic_two);
        String[] images = {s, s2, s, s2, s};
        //TODO move to sdcard
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                Feed feed = new Feed(ic, getString(R.string.app_author) + " " + i, images, getString(R.string.string_text));
                mFeeds.add(feed);
                mDatabaseHelper.createFeed(feed);
            } else {
                Feed feed = new Feed(ic2, getString(R.string.app_author) + " " + i, images, getString(R.string.string_text));
                mFeeds.add(feed);
                mDatabaseHelper.createFeed(feed);
            }
        }
    }

    private String saveToSdCard(int id) {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), id);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, id + ".thg");
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.toString();
    }
}
