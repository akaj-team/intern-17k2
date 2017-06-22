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

public class NinePathActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private EditText mEdtChat;
    private Button mBtnSend;
    private List<Chat> mChats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nine_path);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewChat);
        mEdtChat = (EditText) findViewById(R.id.edtChat);
        mBtnSend = (Button) findViewById(R.id.btnSend);
        final NinePathAdapter ninePathAdapter = new NinePathAdapter(mChats);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(ninePathAdapter);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mEdtChat.getText())) {
                    String text = mEdtChat.getText().toString();
                    Random random = new Random();
                    mChats.add(new Chat(text, random.nextBoolean()));
                    ninePathAdapter.notifyDataSetChanged();
                    mEdtChat.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "input content", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
