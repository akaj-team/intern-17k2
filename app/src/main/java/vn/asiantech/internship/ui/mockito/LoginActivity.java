package vn.asiantech.internship.ui.mockito;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import vn.asiantech.internship.R;
import vn.asiantech.internship.others.LoginValidation;

/**
 * Login Activity.
 * Created by huypham on 16-Jul-17.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText mEdtUser;
    private EditText mEdtPassword;
    private Button mBtnLogin;
    private Toolbar mToolbar;
    private TextView mTvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initToolbar();
        setButtonLogin();
    }

    private void initViews() {
        mEdtUser = (EditText) findViewById(R.id.edtUserName);
        mEdtPassword = (EditText) findViewById(R.id.edtPassword);
        mBtnLogin = (Button) findViewById(R.id.btnLogin);
        mToolbar = (Toolbar) findViewById(R.id.toolbarMockito);
        mTvTitle = (TextView) findViewById(R.id.tvToolbarMockito);
    }

    private void setButtonLogin() {
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mEdtUser.getText().toString().trim();
                String password = mEdtPassword.getText().toString().trim();

                if (TextUtils.equals(userName, "") && TextUtils.equals(password, "")) {
                    Toast.makeText(LoginActivity.this, "Data is empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.equals(userName, "")) {
                    mEdtUser.setError("Fill user name");
                } else if (TextUtils.equals(password, "")) {
                    mEdtPassword.setError("Fill password");
                } else if (!LoginValidation.checkUserCharacter(userName)) {
                    mEdtUser.setError("User name does not contain special character and space");
                } else if (!LoginValidation.checkUserLength(userName)) {
                    mEdtUser.setError("Length of user name from 6 to 24 character");
                } else if (!LoginValidation.checkPasswordCharacter(password)) {
                    mEdtPassword.setError("Password must be have 1 upper case, 1 number, 1 special character");
                } else if (!LoginValidation.checkPasswordLength(password)) {
                    mEdtPassword.setError("Length of password must be higher 4 charater");
                } else if (!LoginValidation.checkDifference(password, userName)) {
                    mEdtPassword.setError("Password must be different than user name");
                } else if (!LoginValidation.checkPasswordNoSpace(password)) {
                    mEdtPassword.setError("Password does not contain space");
                } else {
                    startActivity(new Intent(LoginActivity.this, EquationActivity.class));
                }
            }
        });
    }

    private void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
        }
        mTvTitle.setText(R.string.login_toolbar_tittle);
    }
}
