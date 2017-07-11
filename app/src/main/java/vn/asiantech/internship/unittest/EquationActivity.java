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
        setContentView(R.layout.activity_equation);
        mEdtFactorA = (EditText) findViewById(R.id.edtFactorA);
        mEdtFactorB = (EditText) findViewById(R.id.edtFactorB);
        mEdtFactorC = (EditText) findViewById(R.id.edtFactorC);
        Button btnCalculate = (Button) findViewById(R.id.btnCalculate);
        mTvResult = (TextView) findViewById(R.id.tvResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = mEdtFactorA.getText().toString();
                String b = mEdtFactorB.getText().toString();
                String c = mEdtFactorC.getText().toString();
                if (TextUtils.equals(a, " ") || TextUtils.equals(b, " ") || TextUtils.equals(c, " ")) {
                    Toast.makeText(EquationActivity.this, "Not enough data", Toast.LENGTH_SHORT).show();
                } else {
                    calculate(Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(c));
                }
            }
        });
    }

    private void calculate(int a, int b, int c) {
        EquationValidation.checkCondition(a, b, c);
        if (TextUtils.equals(EquationValidation.checkCondition(a, b, c), "VoSoNghiem")) {
            mTvResult.setText(R.string.text_vosonghiem);
        } else if (TextUtils.equals(EquationValidation.checkCondition(a, b, c), "0")) {
            mTvResult.setText(R.string.text_vonghiem);
        } else if (TextUtils.equals(EquationValidation.checkCondition(a, b, c), "1")) {
            mTvResult.setText(String.valueOf(R.string.text_1nghiem + (float) -c / b));

        } else if (TextUtils.equals(EquationValidation.checkCondition(a, b, c), "TinhDenta")) {
            int denta = b * b - 4 * a * c;
            if (EquationValidation.checkDenta(denta) == 1) {
                mTvResult.setText(String.valueOf(R.string.text_nghiemkep + EquationValidation.checkOneRoot(a, b)));
            } else if (EquationValidation.checkDenta(denta) == 2) {
                mTvResult.setText(String.valueOf("Phuong trinh co 2 nghiem phan biet:\n x1 = " + EquationValidation.checkTwoRoot(a, b, denta).get(0) + "  x2 = " + EquationValidation.checkTwoRoot(a, b, denta).get(1)));
            } else if (EquationValidation.checkDenta(denta) == 0) {
                mTvResult.setText(R.string.text_vonghiem);
            }
        }
    }
}
