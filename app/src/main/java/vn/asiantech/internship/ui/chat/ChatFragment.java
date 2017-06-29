package vn.asiantech.internship.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Message;

/**
 * fragment display and event send message
 * <p>
 * Created by Hai on 6/22/2017.
 */
public class ChatFragment extends Fragment implements View.OnClickListener {
    private EditText mEdtInput;
    private ChatAdapter mAdapter;
    private List<Message> mMessages = new ArrayList<>();
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        mEdtInput = (EditText) view.findViewById(R.id.edtInputMessage);
        Button btnSend = (Button) view.findViewById(R.id.btnSendMessage);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewChat);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        createMessage();
        mAdapter = new ChatAdapter(mMessages);
        mRecyclerView.setAdapter(mAdapter);
        btnSend.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        String input = mEdtInput.getText().toString();
        if (!input.isEmpty()) {
            mMessages.add(new Message(input, 1));
            mMessages.add(new Message("No message", 2));
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mMessages.size() - 1);
            mEdtInput.setText(null);
        }
    }

    private void createMessage() {
        mMessages.add(new Message("Hello", 2));
        mMessages.add(new Message("Alo alo", 2));
    }
}
