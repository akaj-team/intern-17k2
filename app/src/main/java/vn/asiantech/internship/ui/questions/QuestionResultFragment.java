package vn.asiantech.internship.ui.questions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionResultFragment extends Fragment {

    private static final String KEY_BOOL = "KEY_BOOL";
    private static final String KEY_INT = "KEY_INT";

    private boolean[] mBooleans;
    private int mRightCorrect;

    public static QuestionResultFragment newInstance(boolean[] booleans, int rightCorrect) {
        QuestionResultFragment fragment = new QuestionResultFragment();
        Bundle args = new Bundle();
        args.putBooleanArray(KEY_BOOL, booleans);
        args.putInt(KEY_INT, rightCorrect);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBooleans = getArguments().getBooleanArray(KEY_BOOL);
            mRightCorrect = getArguments().getInt(KEY_INT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_result, container, false);

        ImageView imgTryAgain = (ImageView) v.findViewById(R.id.imgTryAgain);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new QuestionResultAdapter(mBooleans, mRightCorrect));

        imgTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof QuestionActivity) {
                    ((QuestionActivity) getActivity()).replaceFragmentAddContent();
                }
            }
        });
        if (getActivity() instanceof QuestionActivity) {
            ((QuestionActivity) getActivity()).setToolbar(toolbar);
        }
        return v;
    }

}
