package vn.asiantech.internship.Bai2.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import vn.asiantech.internship.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {

    private EditText mEdtInput;

    public InputFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_input, container, false);
        mEdtInput = (EditText) layout.findViewById(R.id.edtInput);
        return layout;
    }

    public String getText(){
        return mEdtInput.getText().toString();
    }
}
