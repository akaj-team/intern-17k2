package vn.asiantech.internship.ui.feed;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.databases.ImageDatabase;

/**
 * Created by ducle on 15/06/2017.
 */
public class FeedFragment extends Fragment {
    private RecyclerView mRecyclerViewPost;
    private PostAdapter mPostAdapter;
    private List<Post> mPosts;
    private ImageDatabase mImageDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mPosts = new ArrayList<>();
        try {
            mImageDatabase = new ImageDatabase(container.getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mImageDatabase.opendatabase();
        mPosts = mImageDatabase.getList();
        for (Post post : mPosts) {
            post.setImageUSer(BitmapFactory.decodeResource(getResources(), R.drawable.ic_accessibility_green_700_24dp));
        }
        mRecyclerViewPost = (RecyclerView) view.findViewById(R.id.recyclerViewPost);
        mPostAdapter = new PostAdapter(mPosts);
        mRecyclerViewPost.setHasFixedSize(true);
        mRecyclerViewPost.setAdapter(mPostAdapter);
        mRecyclerViewPost.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return view;
    }
}
