package vn.asiantech.internship.unittest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.asiantech.internship.R;

/**
 * Author AsianTech Inc.
 * Created by sony on 11/07/2017.
 */
public class EquationActivity extends AppCompatActivity {
    private EditText mEdtFactorA;
    private EditText mEdtFactorB;
    private EditText mEdtFactorC;
    private TextView mTvResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quadratic_equation);

        mEdtFactorA = (EditText) findViewById(R.id.edtFactorA);
        mEdtFactorB = (EditText) findViewById(R.id.edtFactorB);
        mEdtFactorC = (EditText) findViewById(R.id.edtFactorC);
        mTvResult = (TextView) findViewById(R.id.tvResultCalculate);
        Button btnCalculate = (Button) findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = mEdtFactorA.getText().toString();
                String b = mEdtFactorB.getText().toString();
                String c = mEdtFactorC.getText().toString();
                if (TextUtils.equals(a, "") || TextUtils.equals(b, "") || TextUtils.equals(c, "")) {
                    Toast.makeText(EquationActivity.this, "Not enough data", Toast.LENGTH_SHORT).show();
                } else {
                    calculate(Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(c));
                }
            }
        });
    }

    private void calculate(int a, int b, int c) {
        EquationValidation.checkDataInput(a, b, c);
        if (TextUtils.equals(EquationValidation.checkDataInput(a, b, c), "CountlessRoots")) {
            mTvResult.setText(R.string.text_unlessRoots);
        } else if (TextUtils.equals(EquationValidation.checkDataInput(a, b, c), "NoRoot")) {
            mTvResult.setText(R.string.text_noRoot);
        } else if (TextUtils.equals(EquationValidation.checkDataInput(a, b, c), "OneRoot")) {
            mTvResult.setText(String.valueOf("Quadratic equation has one root:\n x = " + (float) -c / b));
        } else if (TextUtils.equals(EquationValidation.checkDataInput(a, b, c), "CalculateDelta")) {
            int delta = b * b - 4 * a * c;
            if (EquationValidation.checkDelta(delta) == 1) {
                mTvResult.setText(String.valueOf("Quadratic equation has one root:\n x = " + EquationValidation.checkOneRoot(a, b)));
            } else if (EquationValidation.checkDelta(delta) == 2) {
                mTvResult.setText(String.valueOf("Quadratic equation has two root:\n x1 = " + EquationValidation.checkTwoRoots(a, b, delta).get(0) + "; x2 = " + EquationValidation.checkTwoRoots(a, b, delta).get(1)));
            } else if (EquationValidation.checkDelta(delta) == 0) {
                mTvResult.setText(R.string.text_noRoot);
            }
        }
    }
}
