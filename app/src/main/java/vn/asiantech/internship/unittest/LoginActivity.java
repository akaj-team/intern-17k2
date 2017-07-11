package vn.asiantech.internship.unittest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.asiantech.internship.R;

/**
 * Author AsianTech Inc.
 * Created by sony on 11/07/2017.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText mEdtUserName;
    private EditText mEdtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEdtUserName = (EditText) findViewById(R.id.edtUserName);
        mEdtPassword = (EditText) findViewById(R.id.edtPassword);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mEdtUserName.getText().toString();
                String password = mEdtPassword.getText().toString();
                if (TextUtils.equals(userName, "") || TextUtils.equals(password, "")) {
                    Toast.makeText(LoginActivity.this, "Not enough data", Toast.LENGTH_SHORT).show();
                } else if (!UserNameValidation.checkUserNameCharacter(userName)) {
                    Toast.makeText(LoginActivity.this, "UserName don't contain special characters", Toast.LENGTH_SHORT).show();
                } else if (!UserNameValidation.checkUserNameLength(userName)) {
                    Toast.makeText(LoginActivity.this, "Length of userName is higher than 5 and louder than 25 ", Toast.LENGTH_SHORT).show();
                } else if (!UserNameValidation.checkUserNameSpace(userName)) {
                    Toast.makeText(LoginActivity.this, "UserName don't have space", Toast.LENGTH_SHORT).show();
                } else if (!UserNameValidation.checkUserNameIgnoreUpperCase(userName)) {
                    Toast.makeText(LoginActivity.this, "UserName don't have upper case character", Toast.LENGTH_SHORT).show();
                }else if (!PasswordValidation.checkPasswordHaveOneUpperCase_OneNumber_OneSpecialCharacter(password)) {
                    Toast.makeText(LoginActivity.this, "Password is not correct", Toast.LENGTH_SHORT).show();
                }
                else if (!PasswordValidation.checkPasswordDifferentWithUserName(password,userName)) {
                    Toast.makeText(LoginActivity.this, "Password is not same UserName", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(LoginActivity.this, EquationActivity.class));
                }
            }
        });
    }
}
