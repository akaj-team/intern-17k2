package vn.asiantech.internship.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import vn.asiantech.internship.R;

/**
 * @author at-cuongcao
 * @version 1.0
 * @since 06/26/2017
 */
public class FootballStarFragment extends Fragment {

    private static final String KEY_ID = "keyId";
    private View mView;
    private ImageView mImgSpainStar;
    private int mId;
    private boolean dataLoaded = false;
    private boolean isVisible = false;

    public static FootballStarFragment getNewInstance(int imageId) {
        FootballStarFragment footballStarFragment = new FootballStarFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, imageId);
        footballStarFragment.setArguments(bundle);
        return footballStarFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getInt(KEY_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_football_star, container, false);
        mImgSpainStar = (ImageView) mView.findViewById(R.id.imgChampion);
        if (!dataLoaded && getUserVisibleHint()) {
            loadData();
        }
        return mView;
    }

    @Override
    public boolean getUserVisibleHint() {
        return isVisible;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
        if (isVisibleToUser) {
            loadData();
        }
    }

    private void loadData() {
        if (mView != null) {
            mImgSpainStar.setImageResource(mId);
            dataLoaded = true;
        } else {
            dataLoaded = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataLoaded = false;
    }
}
