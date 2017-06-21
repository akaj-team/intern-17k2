package vn.asiantech.internship.ui.note;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import vn.asiantech.internship.R;
import vn.asiantech.internship.database.DatabaseHelper;

/**
 * A simple Note class.
 * Create by Thanh Thien
 */
public class NoteFragment extends Fragment {
    private RelativeLayout mRlSecond;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note, container, false);
        mRlSecond = (RelativeLayout) v.findViewById(R.id.rlSecond);
        ImageView imgBtnAdd = (ImageView) v.findViewById(R.id.imgBtnAdd);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        imgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragmentAddContent(getActivity(), new NoteAddNewFragment());
                mRlSecond.setVisibility(View.VISIBLE);
            }
        });
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        checkContent(databaseHelper);
        if (getActivity() instanceof NoteActivity) {
            ((NoteActivity) getActivity()).setToolbar(toolbar);
        }
        return v;
    }

    private void checkContent(DatabaseHelper databaseHelper) {
        if (databaseHelper.getAllNotes().size() != 0) {
            setFragmentAddContent(getActivity(), new NoteShowListFragment());
            mRlSecond.setVisibility(View.VISIBLE);
            return;
        }
        mRlSecond.setVisibility(View.GONE);
    }

    /**
     * setFragmentAddContent in Note Fragment
     * @param fragmentActivity Activity Fragment
     * @param fragment to replace
     */
    public static void setFragmentAddContent(FragmentActivity fragmentActivity, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
        fragmentTransaction.replace(R.id.rlSecond, fragment);
        fragmentTransaction.commit();
    }
}

