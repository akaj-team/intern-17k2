package vn.asiantech.internship.feed;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to display feed fragment.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-9
 */
public class FeedFragment extends Fragment {
    FeedSqlite mdata;
    List<Image> images = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.feedRecyclerView);
        List<Feed> feeds = new ArrayList<>();

        try {
            mdata = new FeedSqlite(getActivity());
            try {
                mdata.opendatabase();
                images.addAll(mdata.getList());
                Log.i("aaaaaaaa2", "onCreateView: "+images.size());
                mdata.close();
            } catch (SQLException e) {
                Log.i("aaaaaaaa", "Can't open");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
       // List<Integer> images = Arrays.asList(R.mipmap.ic_one, R.mipmap.ic_two, R.mipmap.ic_three, R.mipmap.ic_four, R.mipmap.ic_five, R.mipmap.ic_six, R.mipmap.ic_seven, R.mipmap.ic_eight, R.mipmap.ic_nine, R.mipmap.ic_ten);
       String[] names = getResources().getStringArray(R.array.names);
       String[] descriptions = getResources().getStringArray(R.array.descriptions);
        for (int i = 0; i < names.length; i++) {
           feeds.add(new Feed(names[i], descriptions[i], images));
      }
        FeedAdapter adapter = new FeedAdapter(getActivity(), feeds);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
