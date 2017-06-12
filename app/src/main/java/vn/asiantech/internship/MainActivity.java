package vn.asiantech.internship;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Dummy Data
        List<DrawerItem> DrawerItemList = new ArrayList<>();
        DrawerItemList.add(new DrawerItem(R.mipmap.ic_launcher_round, "dsadasdasdsasdasd", "dsadasa"));
        DrawerItemList.add(new DrawerItem(R.mipmap.ic_launcher, "Inbox"));
        DrawerItemList.add(new DrawerItem(R.mipmap.ic_launcher, "Send"));


        DrawerRecyclerAdapter adapter = new DrawerRecyclerAdapter(DrawerItemList);
        RecyclerView RecyclerView = (RecyclerView) findViewById(R.id.drawerRecyclerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(adapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

}
