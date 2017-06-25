package vn.asiantech.internship.exday15;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import vn.asiantech.internship.R;

/**
 * Created by datbu on 23-06-2017.
 */
public class JsonFragment extends Fragment {
    private RadioButton mRbAnsA;
    private RadioButton mRbAnsB;
    private RadioButton mRbAnsC;
    private RadioButton mRbAnsD;
    private RadioGroup mRgAnswer;
    private TextView mTvQuestion;

    private ItemQuestion mItemQuesion;
    private int mPosition;
    private OnListener mOnListener;

    public static JsonFragment newInstance(ItemQuestion itemQuestion, int position) {
        JsonFragment jsonFragment = new JsonFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("itemQuestion", itemQuestion);
        bundle.putInt("position", position);
        jsonFragment.setArguments(bundle);
        return jsonFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemQuesion = getArguments().getParcelable("itemQuestion");
            mPosition = getArguments().getInt("position");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_json, container, false);
        mRbAnsA = (RadioButton) view.findViewById(R.id.rbAnswerA);
        mRbAnsB = (RadioButton) view.findViewById(R.id.rbAnswerB);
        mRbAnsC = (RadioButton) view.findViewById(R.id.rbAnswerC);
        mRbAnsD = (RadioButton) view.findViewById(R.id.rbAnswerD);
        mRgAnswer = (RadioGroup) view.findViewById(R.id.rgAnswer);
        mTvQuestion = (TextView) view.findViewById(R.id.tvQuestion);

        mTvQuestion.setText(mItemQuesion.getQuestion());
        mRbAnsA.setText(mItemQuesion.getAnswerA());
        mRbAnsB.setText(mItemQuesion.getAnswerB());
        mRbAnsC.setText(mItemQuesion.getAnswerC());
        mRbAnsD.setText(mItemQuesion.getAnswerD());
        mRgAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rbChoose = (RadioButton) group.findViewById(checkedId);
                mOnListener.onChoose(mPosition, mRgAnswer.indexOfChild(rbChoose));
            }
        });
        return view;
    }

    public void setListener(OnListener onListener) {
        mOnListener = onListener;
    }

    public interface OnListener {
        void onChoose(int question, int answer);
    }
}
