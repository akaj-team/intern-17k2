package vn.asiantech.internship.ui.feeds;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class FeedsFragment extends Fragment implements FeedsAdapter.OnFeedsListener {

    private List<Feed> mFeeds;
    private DatabaseHelper mDatabaseHelper;
    private RecyclerView mRvFeeds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feeds, container, false);
        mRvFeeds = (RecyclerView) v.findViewById(R.id.rvFeeds);
        mRvFeeds.setLayoutManager(new LinearLayoutManager(getContext()));
        mDatabaseHelper = new DatabaseHelper(getContext());
        mFeeds = new ArrayList<>();
        mFeeds = getDataFromDatabase();
        FeedsAdapter adapter = new FeedsAdapter(mFeeds, this);
        mRvFeeds.setAdapter(adapter);
        return v;
    }

    private List<Feed> getDataFromDatabase() {
        return mDatabaseHelper.getAllFeeds();
    }

    //TODO for next ex
    private String saveImageToSdCard(int id) {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), id);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, id + ".thg");
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (FileNotFoundException e) {
            Log.d("FeedsFragment", "saveImageToSdCard: " + e.toString());
        } catch (IOException e) {
            Log.d("FeedsFragment", "saveImageToSdCard: " + e.toString());
        }
        return file.toString();
    }

    @Override
    public void onScrollToPosition(int position) {
        mRvFeeds.scrollToPosition(position);
    }
}
