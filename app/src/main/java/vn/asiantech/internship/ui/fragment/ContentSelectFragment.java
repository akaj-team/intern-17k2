package vn.asiantech.internship.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.feeds.FeedsActivity;
import vn.asiantech.internship.ui.main.Ex05Activity;
import vn.asiantech.internship.ui.main.Ex072Activity;
import vn.asiantech.internship.ui.main.MainActivity;
import vn.asiantech.internship.ui.music.MusicActivity;
import vn.asiantech.internship.ui.questions.QuestionActivity;
import vn.asiantech.internship.ui.note.NoteActivity;

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
            case R.id.btnEx07:
                openActivity(MainActivity.class);
                break;
            case R.id.btnEx07_2:
                openActivity(Ex072Activity.class);
                break;
            case R.id.btnEx08:
                //TODO watiing to add Ex
                break;
            case R.id.btnEx11:
                openActivity(FeedsActivity.class);
                break;
            case R.id.btnEx11_2:
                openActivity(NoteActivity.class);
                break;
            case R.id.btnEx15:
                openActivity(QuestionActivity.class);
                break;
            case R.id.btnEx19:
                openActivity(MusicActivity.class);
                break;
        }
    }

    private void openActivity(Class<?> className) {
        Intent intent = new Intent(getContext(), className);
        getContext().startActivity(intent);
    }

    private void init(View v) {
        Button btnEx05 = (Button) v.findViewById(R.id.btnEx05);
        Button btnEx06 = (Button) v.findViewById(R.id.btnEx06);
        Button btnEx07_1 = (Button) v.findViewById(R.id.btnEx07);
        Button btnEx07_2 = (Button) v.findViewById(R.id.btnEx07_2);
        Button btnEx08 = (Button) v.findViewById(R.id.btnEx08);
        Button btnEx11 = (Button) v.findViewById(R.id.btnEx11);
        Button btnEx11_2 = (Button) v.findViewById(R.id.btnEx11_2);
        Button btnEx15 = (Button) v.findViewById(R.id.btnEx15);
        Button btnEx19 = (Button) v.findViewById(R.id.btnEx19);

        btnEx05.setOnClickListener(this);
        btnEx06.setOnClickListener(this);
        btnEx07_1.setOnClickListener(this);
        btnEx07_2.setOnClickListener(this);
        btnEx08.setOnClickListener(this);
        btnEx11.setOnClickListener(this);
        btnEx11_2.setOnClickListener(this);
        btnEx15.setOnClickListener(this);
        btnEx19.setOnClickListener(this);
    }
}
