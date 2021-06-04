package com.iug.pushnotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.iug.pushnotification.Api.ApiConfig;
import com.iug.pushnotification.Api.DCallBack;
import com.iug.pushnotification.Api.Model.GlobalModel;

public class SignInActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView txtDonotAccount;
    String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getToken();
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        txtDonotAccount = findViewById(R.id.txt_donot_account);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (email.isEmpty()){
                    GlobalData.Toast(SignInActivity.this, "ادخل البريد الالكتروني");
                    return;
                }
                if (password.isEmpty()){
                    GlobalData.Toast(SignInActivity.this, "ادخل كلمة المرور");
                    return;
                }

                GlobalData.progressDialogKH(SignInActivity.this, true);
                new ApiConfig(SignInActivity.this, false, new DCallBack() {
                    @Override
                    public void Result(Object obj, String fun, boolean IsSuccess) {
                        if (IsSuccess) {
                            GlobalModel model = (GlobalModel) obj;
                            GlobalData.Toast(SignInActivity.this, model.msg);
                            new ApiConfig(SignInActivity.this, false, new DCallBack() {
                                @Override
                                public void Result(Object obj, String fun, boolean IsSuccess) {
                                    GlobalData.progressDialogKH(SignInActivity.this, false);
                                    if (IsSuccess) {
                                        GlobalModel model = (GlobalModel) obj;
                                        GlobalData.Toast(SignInActivity.this, model.msg);
                                        startActivity(new Intent(SignInActivity.this,MainActivity.class));

                                    } else {
                                        String msg = (String) obj;
                                        GlobalData.Toast(SignInActivity.this, msg);
                                    }

                                }
                            }).addRegTokenApi(email, password,token);


                        } else {
                            GlobalData.progressDialogKH(SignInActivity.this, false);
                            String msg = (String) obj;
                            GlobalData.Toast(SignInActivity.this, msg);
                        }

                    }
                }).loginApi(email, password);

            }
        });

        txtDonotAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

    }


    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(!task.isSuccessful()){
                    Log.e("tokenF","Failed Token");
                    return;
                }

                token = task.getResult();
                Log.e("token","Token : " + token );
            }
        });
    }
}