package vn.asiantech.internship.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import vn.asiantech.internship.R;

/**
 * fragment content edittex
 */
public class FirstIntentFragment extends Fragment {
    private EditText mEdtInput;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intent_1, container, false);
        mEdtInput = (EditText) view.findViewById(R.id.edtInput);
        return view;
    }

    public String getData() {
        return mEdtInput.getText().toString();
    }
}
