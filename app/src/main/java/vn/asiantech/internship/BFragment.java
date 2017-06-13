package vn.asiantech.internship;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Fragment B created by Thien 13/06
 */
public class BFragment extends Fragment {

    private TextView mTVShow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_b, container, false);
        mTVShow = (TextView) v.findViewById(R.id.tvShow);
        Button btnSend = (Button) v.findViewById(R.id.btnSend2);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).setText();
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
        mTVShow.setText(s);
    }

    public interface BFragmentInterface {
        void setText();
    }
}
