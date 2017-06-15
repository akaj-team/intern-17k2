package vn.asiantech.internship.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.main.Ex05Activity;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentSelectFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_content_select, container, false);
        init(v);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEx05:
                openActivity(Ex05Activity.class);
                break;
            case R.id.btnEx06:
                openActivity(MainActivity.class);
                break;
            case R.id.btnEx07_1:
                openActivity(MainActivity.class);
                break;
            case R.id.btnEx07_2:
                showToast("I did not add to this activity"); // i'll change when i add this Ex
                break;
            case R.id.btnEx08:
                showToast("Nothing to show");// i'll change when i add this Ex
                break;
        }

    }

    private void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void openActivity(Class<?> className) {
        Intent intent = new Intent(getContext(), className);
        getContext().startActivity(intent);
    }

    private void init(View v) {
        Button btnEx05 = (Button) v.findViewById(R.id.btnEx05);
        Button btnEx06 = (Button) v.findViewById(R.id.btnEx06);
        Button btnEx07_1 = (Button) v.findViewById(R.id.btnEx07_1);
        Button btnEx07_2 = (Button) v.findViewById(R.id.btnEx07_2);
        Button btnEx08 = (Button) v.findViewById(R.id.btnEx08);

        btnEx05.setOnClickListener(this);
        btnEx06.setOnClickListener(this);
        btnEx07_1.setOnClickListener(this);
        btnEx07_2.setOnClickListener(this);
        btnEx08.setOnClickListener(this);
    }
}
