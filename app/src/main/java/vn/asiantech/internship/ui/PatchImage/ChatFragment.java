package vn.asiantech.internship.ui.PatchImage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapter.PatchImageAdapter.ChatAdapter;

/**
 * Chat Fragment
 * Created by anhhuy on 21/06/2017.
 */
public class ChatFragment extends Fragment {
    private EditText mEdtMessage;
    private ImageView mImgSend;
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private List<String> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        mEdtMessage = (EditText) view.findViewById(R.id.edtMessage);
        mImgSend = (ImageView) view.findViewById(R.id.imgSend);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewAllMessage);

        initChatAdapter();
        return view;
    }

    private void initChatAdapter() {
        mImgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mList.add(getMessage());
                mAdapter.notifyDataSetChanged();
            }
        });

        mAdapter = new ChatAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    private String getMessage() {
        return mEdtMessage.getText().toString().trim();
    }
}
