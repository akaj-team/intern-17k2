package vn.asiantech.internship.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.adapters.ChatAdapter;
import vn.asiantech.internship.models.Chat;

/**
 * Chat Fragment
 * Created by huypham on 21/06/2017.
 */
public class ChatFragment extends Fragment {
    private EditText mEdtMessage;
    private ImageView mImgSend;
    private RecyclerView mRecyclerView;
    private ChatAdapter mAdapter;
    private List<Chat> mChatList = new ArrayList<>();

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
        final LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());

        mImgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mEdtMessage.getText())) {
                    randomMessage();
                    mEdtMessage.setText("");
                    mAdapter.notifyDataSetChanged();
                    linearLayout.scrollToPosition(mChatList.size() - 1);
                }
            }
        });

        mAdapter = new ChatAdapter(mChatList);
        mRecyclerView.setLayoutManager(linearLayout);
        mRecyclerView.setAdapter(mAdapter);
    }

    private Chat randomMessage() {
        Chat chat = new Chat();
        Random random = new Random();
        if (random.nextInt(6) % 2 == 0) {
            mChatList.add(new Chat(mEdtMessage.getText().toString().trim(), false));
        } else {
            mChatList.add(new Chat(mEdtMessage.getText().toString().trim(), true));
        }
        return chat;
    }
}
