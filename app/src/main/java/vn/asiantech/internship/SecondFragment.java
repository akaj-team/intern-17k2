package vn.asiantech.internship;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Used to get data from first fragment
 */
public class SecondFragment extends Fragment {

    private TextView mTvResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        Button btnFirstSend = (Button) view.findViewById(R.id.btnFirstSend);
        mTvResult = (TextView) view.findViewById(R.id.tvResult);
        btnFirstSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).onClick();
            }
        });
        return view;
    }

    public void setResult(String result) {
        mTvResult.setText(result);
    }
}
