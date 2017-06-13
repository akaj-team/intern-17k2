package vn.asiantech.internship;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 *
 * Created by datbu on 14-06-2017.
 */

public class TopFragment extends Fragment {
    private EditText mEdtInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        mEdtInput = (EditText) view.findViewById(R.id.edtInput);
        return view;
    }

    public String getText() {
        return mEdtInput.getText().toString();
    }
}
