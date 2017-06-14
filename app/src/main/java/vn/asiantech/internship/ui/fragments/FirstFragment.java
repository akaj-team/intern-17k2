package vn.asiantech.internship.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import vn.asiantech.internship.R;

/**
 * First Fragment
 *
 * @author at-cuongcao
 * @version 1.0
 * @since 06/13/2017
 */
public class FirstFragment extends Fragment {
    private EditText mEdtInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        mEdtInput = (EditText) view.findViewById(R.id.edtInput);
        return view;
    }

    public String getText() {
        return mEdtInput.getText().toString();
    }

}
