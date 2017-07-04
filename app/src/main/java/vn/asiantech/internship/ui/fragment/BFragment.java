package vn.asiantech.internship.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vn.asiantech.internship.R;
import vn.asiantech.internship.ui.main.Ex072Activity;

/**
 * Fragment B created by Thien 13/06
 */
public class BFragment extends Fragment {

    private TextView mTvShow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_b, container, false);
        mTvShow = (TextView) v.findViewById(R.id.tvShow);
        Button btnSend2 = (Button) v.findViewById(R.id.btnSend2);
        btnSend2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof Ex072Activity) {
                    ((Ex072Activity) getActivity()).setText();
                }
            }
        });
        return v;
    }

    /**
     * Set Text Show
     *
     * @param s is a text of fragment A
     */
    public void setText(String s) {
        mTvShow.setText(s);
    }

    /**
     * interface
     */
    public interface OnBFragmentListener {
        void setText();
    }
}
