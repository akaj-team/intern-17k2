package vn.asiantech.internship.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import vn.asiantech.internship.R;

/**
 * Created by Administrator on 6/13/2017.
 */
public class FirstFragment extends Fragment {
    private EditText mEdtInput;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, container);
        mEdtInput = (EditText) view.findViewById(R.id.edtInput);
        return view;
    }

    public String getData() {
        return mEdtInput.getText().toString();
    }
}
