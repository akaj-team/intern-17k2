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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMessages);
        final EditText edtMessage = (EditText) findViewById(R.id.edtMessage);
        final Button btnSend = (Button) findViewById(R.id.btnSend);

        final List<Message> messages = new ArrayList<>();
        messages.add(new Message(getString(R.string.message1), false));
        messages.add(new Message(getString(R.string.message2), true));
        final MessageAdapter adapter = new MessageAdapter(messages);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        edtMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    btnSend.setVisibility(View.VISIBLE);
                } else {
                    btnSend.setVisibility(View.GONE);
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtMessage.getText())) {
                    messages.add(new Message(edtMessage.getText().toString().trim(), false));
                    edtMessage.setText("");
                    adapter.notifyItemInserted(messages.size() - 1);
                    messages.add(reply());
                    adapter.notifyItemInserted(messages.size() - 1);
                    linearLayoutManager.scrollToPosition(messages.size() - 1);
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
