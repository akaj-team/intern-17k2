package vn.asiantech.internship.day13;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * ChatActivity
 */
public class ChatActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private EditText mEdtMessage;
    private Button mBtnSendMessage;

    private ChatAdapter mAdapter;
    private List<String> mMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initUI();
        createRecyclerView();
        addEvents();
    }

    private void addEvents() {
        mBtnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMessages.add(mEdtMessage.getText().toString());
                mAdapter.notifyItemChanged(mMessages.size() - 1);
                mEdtMessage.setText("");
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }

    private void initUI() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolBarChat);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewChat);
        mEdtMessage = (EditText) findViewById(R.id.edtMessage);
        mBtnSendMessage = (Button) findViewById(R.id.btnMessage);
        setSupportActionBar(mToolbar);
    }

    private void createRecyclerView() {
        mAdapter = new ChatAdapter(mMessages);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }
}
