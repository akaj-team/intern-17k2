package vn.asiantech.internship.exday13;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 21-06-2017.
 */
public class ChatActivity extends AppCompatActivity {
    private EditText mditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mditText = (EditText) findViewById(R.id.edtChat);
        final Button button = (Button) findViewById(R.id.btnChat);

        final List<ItemChat> itemChats = new ArrayList<>();
        itemChats.add(new ItemChat(getString(R.string.chat_hello), true));
        itemChats.add(new ItemChat(getString(R.string.chat_meet), false));
        itemChats.add(new ItemChat(getString(R.string.chat_goodbye), true));
        itemChats.add(new ItemChat(getString(R.string.chat_kaka), false));
        itemChats.add(new ItemChat(getString(R.string.chat_bla), true));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        final ChatAdapter adapter = new ChatAdapter(itemChats);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s.toString())) {
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemChats.add(new ItemChat(mditText.getText().toString().trim(), false));
                mditText.setText("");
                adapter.notifyItemInserted(itemChats.size());
                itemChats.add(rep());
                adapter.notifyItemInserted(itemChats.size() - 1);
                layoutManager.scrollToPosition(itemChats.size() - 1);
            }
        });
    }

    public ItemChat rep() {
        ItemChat itemChat = new ItemChat();
        Random random = new Random();
        int m = random.nextInt();
        if (m % 2 == 0) {
            itemChat.setText(getString(R.string.chat_d));
            itemChat.setCheck();
        } else if (m % 3 == 0) {
            itemChat.setText(getString(R.string.chat_c));
            itemChat.setCheck();
        } else {
            itemChat.setText(getString(R.string.chat_a));
            itemChat.setCheck();
        }
        return itemChat;
    }
}
