package vn.asiantech.internship.ninepath;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;

/**
 * Used to display message recyclerView.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-15
 */
public class ChatActivity extends AppCompatActivity {
    private EditText mEdtInput;
    private List<Message> mMessages;
    private ChatAdapter mAdapter;
    private RecyclerView mChatRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mChatRecyclerView = (RecyclerView) findViewById(R.id.smsRecyclerView);
        mEdtInput = (EditText) findViewById(R.id.edtInputSms);
        ImageView imgSend = (ImageView) findViewById(R.id.imgBtnSend);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mChatRecyclerView.setLayoutManager(linearLayoutManager);
        mMessages = new ArrayList<>();
        mAdapter = new ChatAdapter(mMessages);
        mChatRecyclerView.setAdapter(mAdapter);
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mEdtInput.getText().toString().equals("")) {
                    Random random = new Random();
                    int i = random.nextInt(2);
                    if (i == 1) {
                        mMessages.add(new Message(mEdtInput.getText().toString(), true));
                    } else if (i == 0) {
                        mMessages.add(new Message(mEdtInput.getText().toString(), false));
                    }
                    mEdtInput.setText("");
                    mAdapter.notifyDataSetChanged();
                    mChatRecyclerView.setAdapter(mAdapter);
                    mChatRecyclerView.scrollToPosition(mMessages.size() - 1);
                }
            }
        });
    }
}
