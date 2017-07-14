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
    private Button mBtnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        setListener();
    }

    private void initViews() {
        mEdtUserName = (EditText) findViewById(R.id.edtUserName);
        mEdtPassword = (EditText) findViewById(R.id.edtPassword);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
    }

    private void setListener() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mEdtUserName.getText().toString();
                String password = mEdtPassword.getText().toString();
                if (TextUtils.equals(userName, "") || TextUtils.equals(password, "")) {
                    showToast("Not enough data");
                } else if (!UserNameValidation.checkUserNameCharacter(userName)) {
                    showToast("UserName does not contain special characters");
                } else if (!UserNameValidation.checkUserNameLength(userName)) {
                    showToast("Length of userName is higher than 5 and lower than 25");
                } else if (!UserNameValidation.checkUserNameSpace(userName)) {
                    showToast("UserName does not have space");
                } else if (!UserNameValidation.checkUserNameIgnoreUpperCase(userName)) {
                    showToast("UserName does not have upper case character");
                } else if (!PasswordValidation.checkPasswordLength(password)) {
                    showToast("Length of password is higher than 3");
                } else if (!PasswordValidation.checkPasswordSpace(password)) {
                    showToast("Password does not have space");
                } else if (!PasswordValidation.checkPasswordRequirement(password)) {
                    showToast("Passwords must be at least 1 digit,1 special character and 1 upper case character");
                } else if (!PasswordValidation.checkPasswordDifferentWithUserName(password, userName)) {
                    showToast("Password is not same UserName");
                } else {
                    startActivity(new Intent(LoginActivity.this, EquationActivity.class));
                }
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
