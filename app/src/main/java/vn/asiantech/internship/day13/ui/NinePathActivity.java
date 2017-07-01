package vn.asiantech.internship.day13.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.asiantech.internship.R;
import vn.asiantech.internship.day13.adapter.NinePathAdapter;
import vn.asiantech.internship.day13.model.Chat;

/**
 * Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 22/06/2017.
 */
public class NinePathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewChat);
        final EditText edtChat = (EditText) findViewById(R.id.edtChat);
        Button btnSend = (Button) findViewById(R.id.btnSend);
        final List<Chat> chats = new ArrayList<>();
        final NinePathAdapter ninePathAdapter = new NinePathAdapter(chats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ninePathAdapter);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtChat.getText())) {
                    String text = edtChat.getText().toString();
                    Random random = new Random();
                    chats.add(new Chat(text, random.nextBoolean()));
                    ninePathAdapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(chats.size() - 1);
                    edtChat.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "input content", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
