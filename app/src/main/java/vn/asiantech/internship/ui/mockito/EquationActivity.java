package vn.asiantech.internship.ui.mockito;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.asiantech.internship.R;
import vn.asiantech.internship.others.EquationValidation;

/**
 * Quadratic Equation Activity.
 * Created by huypham on 16-Jul-17.
 */
public class EquationActivity extends AppCompatActivity {

    private EditText mEdtNumberA;
    private EditText mEdtNumberB;
    private EditText mEdtNumberC;
    private Button mBtnCalculate;
    private TextView mTvResult;
    private Toolbar mToolbar;
    private TextView mTvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equation);
        initViews();
        initToolbar();
        setButtonCalculate();
    }

    private void initViews() {
        mEdtNumberA = (EditText) findViewById(R.id.edtNumberA);
        mEdtNumberB = (EditText) findViewById(R.id.edtNumberB);
        mEdtNumberC = (EditText) findViewById(R.id.edtNumberC);
        mBtnCalculate = (Button) findViewById(R.id.btnCalculate);
        mTvResult = (TextView) findViewById(R.id.tvResult);
        mToolbar = (Toolbar) findViewById(R.id.toolbarMockito);
        mTvTitle = (TextView) findViewById(R.id.tvToolbarMockito);
    }

    private void setButtonCalculate() {
        mBtnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String a = mEdtNumberA.getText().toString().trim();
                final String b = mEdtNumberB.getText().toString().trim();
                final String c = mEdtNumberC.getText().toString().trim();
                if (TextUtils.isEmpty(a) || TextUtils.isEmpty(b) || TextUtils.isEmpty(c)) {
                    Toast.makeText(EquationActivity.this, "Data is not enough, fill data!", Toast.LENGTH_SHORT).show();
                } else {
                    calculateEquation(Double.parseDouble(a), Double.parseDouble(b), Double.parseDouble(c));
                }
            }
        });
    }

    private void calculateEquation(double a, double b, double c) {
        EquationValidation.checkInput(a, b, c);
        if (TextUtils.equals(EquationValidation.checkInput(a, b, c), "Surd Root")) {
            mTvResult.setText(R.string.equation_text_view_surd);
        } else if (TextUtils.equals(EquationValidation.checkInput(a, b, c), "No Root")) {
            mTvResult.setText(R.string.equation_text_view_no);
        } else if (TextUtils.equals(EquationValidation.checkInput(a, b, c), "One Root")) {
            mTvResult.setText(String.valueOf("One Root \n\nx = " + (float) -c / b));
        } else if (TextUtils.equals(EquationValidation.checkInput(a, b, c), "Double Root")) {
            double delta = (Math.pow(b, 2) - (4 * a * c));
            if (EquationValidation.checkDelta(delta) == 0) {
                mTvResult.setText(R.string.equation_text_view_no);
            } else if (EquationValidation.checkDelta(delta) == 1) {
                mTvResult.setText(String.valueOf("One Root \n\nx = " + EquationValidation.checkOneRoot(a, b)));
            } else if (EquationValidation.checkDelta(delta) == 2) {
                mTvResult.setText(String.valueOf("Double Root \n\nx1 = "
                        + EquationValidation.checkDoubleRoot(a, b, delta).get(0)
                        + "\n\nx2 = " + EquationValidation.checkDoubleRoot(a, b, delta).get(1)));
            }
        }
    }

    private void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        mTvTitle.setText(R.string.equation_toolbar_title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
