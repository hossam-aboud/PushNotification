package com.iug.pushnotification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iug.pushnotification.Api.ApiConfig;
import com.iug.pushnotification.Api.DCallBack;
import com.iug.pushnotification.Api.Model.GlobalModel;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFName;
    private EditText etLName;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView txtAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFName = findViewById(R.id.et_fName);
        etLName = findViewById(R.id.et_lName);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        txtAccount = findViewById(R.id.txt_account);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = etFName.getText().toString();
                String lName = etLName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (fName.isEmpty()) {
                    GlobalData.Toast(SignUpActivity.this, "ادخل الاسم الاول");
                    return;
                }
                if (lName.isEmpty()) {
                    GlobalData.Toast(SignUpActivity.this, "ادخل الاسم الاخير");
                    return;
                }
                if (email.isEmpty()) {
                    GlobalData.Toast(SignUpActivity.this, "ادخل البريد الالكتروني");
                    return;
                }
                if (password.isEmpty()) {
                    GlobalData.Toast(SignUpActivity.this, "ادخل كلمة المرور");
                    return;
                }

                GlobalData.progressDialogKH(SignUpActivity.this, true);
                new ApiConfig(SignUpActivity.this, false, new DCallBack() {
                    @Override
                    public void Result(Object obj, String fun, boolean IsSuccess) {
                        GlobalData.progressDialogKH(SignUpActivity.this, false);
                        if (IsSuccess) {
                            GlobalModel model = (GlobalModel) obj;
                            GlobalData.Toast(SignUpActivity.this, model.msg);
                            Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            String msg = (String) obj;
                            GlobalData.Toast(SignUpActivity.this, msg);
                        }

                    }
                }).addNewUserApi(fName, lName, email, password);

            }
        });

        txtAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}