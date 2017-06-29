package vn.asiantech.internship.day7.ex2.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import vn.asiantech.internship.R;

/**
 * Created by rimoka on 13/06/2017.
 */
public class FirstFragment extends Fragment {
    private EditText mEdtInput;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_1, container, false);
        mEdtInput = (EditText) v.findViewById(R.id.edtInput);
        return v;
    }

    public String getInput() {
        return mEdtInput.getText().toString();
    }
}
