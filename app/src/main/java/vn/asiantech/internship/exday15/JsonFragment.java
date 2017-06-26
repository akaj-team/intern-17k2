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
    public static final String TYPE_QUESTION = "question";
    public static final String TYPE_POSITION = "position";
    private RadioGroup mRgAnswer;
    private ItemQuestion mItemQuestion;
    private int mPosition;
    private OnListener mOnListener;

    public static JsonFragment newInstance(ItemQuestion itemQuestion, int position) {
        JsonFragment jsonFragment = new JsonFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TYPE_QUESTION, itemQuestion);
        bundle.putInt(TYPE_POSITION, position);
        jsonFragment.setArguments(bundle);
        return jsonFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemQuestion = getArguments().getParcelable(TYPE_QUESTION);
            mPosition = getArguments().getInt(TYPE_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_json, container, false);
        RadioButton rbAnsA = (RadioButton) view.findViewById(R.id.rbAnswerA);
        RadioButton rbAnsB = (RadioButton) view.findViewById(R.id.rbAnswerB);
        RadioButton rbAnsC = (RadioButton) view.findViewById(R.id.rbAnswerC);
        RadioButton rbAnsD = (RadioButton) view.findViewById(R.id.rbAnswerD);
        mRgAnswer = (RadioGroup) view.findViewById(R.id.rgAnswer);
        TextView tvQuestion = (TextView) view.findViewById(R.id.tvQuestion);

        tvQuestion.setText(mItemQuestion.getQuestion());
        rbAnsA.setText(mItemQuestion.getAnswers().get(0));
        rbAnsB.setText(mItemQuestion.getAnswers().get(1));
        rbAnsC.setText(mItemQuestion.getAnswers().get(2));
        rbAnsD.setText(mItemQuestion.getAnswers().get(3));
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

    interface OnListener {
        void onChoose(int question, int answer);
    }
}
