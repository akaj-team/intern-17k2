package vn.asiantech.internship.ui.feed;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Created by ducle on 15/06/2017.
 */
public class FeedFragment extends Fragment {
    private RecyclerView mRecyclerViewPost;
    private PostAdapter mPostAdapter;
    private List<Post> mPosts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        mPosts = new ArrayList<>();
        List<Bitmap> images = new ArrayList<>();
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.lu2));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.lu2));
        images.add(BitmapFactory.decodeResource(getResources(), R.drawable.lu2));
        mPosts.add(new Post(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), "LeDuc", images, "Who is he?"));
        mPosts.add(new Post(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), "LeA", images, "He is Mr. Luom"));
        mPosts.add(new Post(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), "LeB", images, "he is good man"));
        mPosts.add(new Post(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher), "LeC", images, "i trusted him"));
        mRecyclerViewPost = (RecyclerView) view.findViewById(R.id.recyclerViewPost);
        mPostAdapter = new PostAdapter(mPosts);
        mRecyclerViewPost.setHasFixedSize(true);
        mRecyclerViewPost.setAdapter(mPostAdapter);
        mRecyclerViewPost.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return view;
    }
}
