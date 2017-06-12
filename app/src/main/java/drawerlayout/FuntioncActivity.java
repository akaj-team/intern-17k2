package drawerlayout;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import vn.asiantech.internship.R;

/**
 * Created by sony on 12/06/2017.
 */

public class FuntioncActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }
}
