package vn.asiantech.internship;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class AFragment extends Fragment{

    private EditText mEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_a, container, false);
        mEditText = (EditText) v.findViewById(R.id.edtName);
        return v;
    }

    /**
     *
     * @return editText of Fragment A
     */
    public String getText() {
        return mEditText.getText().toString();
    }
}
