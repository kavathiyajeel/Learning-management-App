package com.example.lma_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {
    private Button loginbtn;
    private TextInputLayout username_var, password_var;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // NavigationBar
        Objects.requireNonNull(getSupportActionBar()).hide();

        //NavigationBar_color
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splashScreen.setKeepOnScreenCondition(new SplashScreen.KeepOnScreenCondition() {
                    @Override
                    public boolean shouldKeepOnScreen() {
                        return false;
                    }
                });
            }
        }, 3000);

        // getting id from components
        loginbtn = findViewById(R.id.sigin_btn);
        username_var = findViewById(R.id.username_field);
        password_var = findViewById(R.id.password_filed);
        mAuth = FirebaseAuth.getInstance();

        // login button
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO login validation
                String username_ = username_var.getEditText().getText().toString();
                String password_ = password_var.getEditText().getText().toString();
                String user_pttn = "^[a-z0-9._%+-]+@(rku)+\\.+(ac)+\\.+(in)$";
                if (!username_.isEmpty() && username_.matches(user_pttn)) {
                    username_var.setError(null);
                    username_var.setErrorEnabled(false);
                    if (!password_.isEmpty()) {
                        password_var.setError(null);
                        password_var.setErrorEnabled(false);

                        //TODO firebase login_authentication
                        mAuth.signInWithEmailAndPassword(username_,password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        password_var.setError("Please Enter The Password");
                    }
                } else {
                    username_var.setError("Please Enter The Username");
                }
            }
        });
    }
}