package vn.asiantech.internship.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.models.Message;
import vn.asiantech.internship.ui.adapters.MessageAdapter;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/21/2017
 */
public class ChatActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    EditText mEdtMessage;
    private Button mBtnSend;
    private List<Message> mMessages;
    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewMessages);
        mEdtMessage = (EditText) findViewById(R.id.edtMessage);
        mBtnSend = (Button) findViewById(R.id.btnSend);

        mMessages = new ArrayList<>();
        mMessages.add(new Message(getString(R.string.message1), false));
        mMessages.add(new Message(getString(R.string.message2), true));
        mAdapter = new MessageAdapter(mMessages);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mEdtMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mBtnSend.setVisibility(View.VISIBLE);
                } else {
                    mBtnSend.setVisibility(View.GONE);
                }
            }
        });

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mEdtMessage.getText())) {
                    mMessages.add(new Message(mEdtMessage.getText().toString().trim(), false));
                    mEdtMessage.setText("");
                    mAdapter.notifyItemInserted(mMessages.size() - 1);
                    mMessages.add(reply());
                    mAdapter.notifyItemInserted(mMessages.size() - 1);
                    mRecyclerView.scrollToPosition(mMessages.size() - 1);
                }
            }
        });
    }

    private static Message reply() {
        Message message = new Message();
        Random random = new Random();
        if (random.nextInt(2) % 2 == 0) {
            message.setText("Đã xem và không trả lời");
        } else {
            message.setText("Đây là câu trả lời.");
        }
        return message;
    }
}
