package vn.asiantech.internship.ui.ninepatch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Message;

/**
 * Main Chat Here
 * Create by Thanh Thien
 */
public class ChatFragment extends Fragment {

    private EditText mEdtInput;
    private RecyclerView mSmsRecyclerView;
    private ChatAdapter mChatAdapter;
    private List<Message> mMessages;
    private int[] mResourceAvatar = {R.drawable.ic_three, R.drawable.ic_two, R.drawable.ic_one};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        ImageView imgSend = (ImageView) v.findViewById(R.id.imgSend);
        init(v);
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEdtInput.getText().toString().trim().equals(getString(R.string.edittext_hint_type)) || !mEdtInput.getText().toString().trim().equals("")) {
                    sendAction();
                } else {
                    Toast.makeText(getContext(), getString(R.string.error_message_please_enter_text), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    private void init(View v) {
        mEdtInput = (EditText) v.findViewById(R.id.edtInput);
        mSmsRecyclerView = (RecyclerView) v.findViewById(R.id.smsRecyclerView);
        mMessages = new ArrayList<>();
        mChatAdapter = new ChatAdapter(mMessages);
        mSmsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSmsRecyclerView.setAdapter(mChatAdapter);
    }

    private void sendAction() {
        Random random = new Random();
        int i = random.nextInt(3);
        mMessages.add(new Message(mEdtInput.getText().toString(), mResourceAvatar[i]));
        mChatAdapter.notifyDataSetChanged();
        mSmsRecyclerView.setAdapter(mChatAdapter);
        mSmsRecyclerView.scrollToPosition(mMessages.size() - 1);
        mEdtInput.setText(null);
    }
}
