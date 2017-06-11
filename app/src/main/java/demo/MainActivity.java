package demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.R;

/**
 * Used to display the recyclerview.
 *
 * @author at-HangTran
 * @version 1.0
 * @since 2017-6-9
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        List<String> names = new ArrayList<>();
        names.add("Nguyễn Văn A");
        names.add("Nguyễn Văn B");
        names.add("Nguyễn Văn C");
        names.add("Nguyễn Văn D");
        names.add("Nguyễn Văn E");
        names.add("Nguyễn Văn F");
        names.add("Nguyễn Văn G");
        names.add("Nguyễn Văn K");
        names.add("Nguyễn Văn J");
        names.add("Nguyễn Văn D");
        AdapterRecyclerView adapter = new AdapterRecyclerView(names);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
