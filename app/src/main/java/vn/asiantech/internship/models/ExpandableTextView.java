package vn.asiantech.internship.models;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import vn.asiantech.internship.R;

/**
 * custom show more in textview
 * <p>
 * Created by Hai on 6/16/2017.
 */

public class ExpandableTextView extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener {
    private static final int MAX_LINES = 3;
    private int currentMaxLines = Integer.MAX_VALUE;

    public ExpandableTextView(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(this);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        // If text longer than MAX_LINES set DrawableBottom
        post(new Runnable() {
            public void run() {
                if (getLineCount() > MAX_LINES) {
                    setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_more);
                }
                setMaxLines(MAX_LINES);
            }
        });
    }

    @Override
    public void setMaxLines(int maxLines) {
        currentMaxLines = maxLines;
        super.setMaxLines(maxLines);
    }

    public int getTextViewMaxLines() {
        return currentMaxLines;
    }

    @Override
    public void onClick(View v) {
        if (getTextViewMaxLines() == Integer.MAX_VALUE)
            setMaxLines(MAX_LINES);
        else
            setMaxLines(Integer.MAX_VALUE);
    }
}
