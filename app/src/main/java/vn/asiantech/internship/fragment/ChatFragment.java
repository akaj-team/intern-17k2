package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import vn.asiantech.internship.adapter.ChatAdapter;
import vn.asiantech.internship.models.Message;

/**
 * Created by ducle on 22/06/2017.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRecyclerViewChat;
    private ChatAdapter mChatAdapter;
    private List<Message> mMessages;
    private EditText mEdtInput;
    private ImageView mImgSend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        initViews(view);
        mMessages = new ArrayList<>();
        mMessages.add(new Message("hello ", 1));
        mMessages.add(new Message("hello you", 2));
        mMessages.add(new Message("hello you how are you, i'm fine thank you, and you", 1));
        mChatAdapter = new ChatAdapter(mMessages);
        mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewChat.setAdapter(mChatAdapter);
        mImgSend.setOnClickListener(this);
        return view;
    }

    private void initViews(View view) {
        mRecyclerViewChat = (RecyclerView) view.findViewById(R.id.recyclerViewChat);
        mEdtInput = (EditText) view.findViewById(R.id.edtInput);
        mImgSend = (ImageView) view.findViewById(R.id.imgSend);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSend:
                if (!mEdtInput.getText().toString().isEmpty()) {
                    mMessages.add(new Message(mEdtInput.getText().toString(), 2));
                    mChatAdapter.notifyItemInserted(mMessages.size() - 1);
                    mMessages.add(new Message("nothing to show", 1));
                    mChatAdapter.notifyItemInserted(mMessages.size() - 1);
                    mRecyclerViewChat.scrollToPosition(mMessages.size() - 1);
                    mEdtInput.setText("");
                }
                break;
        }
    }
}
