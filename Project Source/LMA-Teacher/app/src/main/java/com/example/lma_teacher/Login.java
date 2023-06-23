package com.example.lma_teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
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
    private Button fp, loginbtn;
    private TextInputLayout username_var, password_var;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).setTitle(" Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));
        mAuth = FirebaseAuth.getInstance();
        fp = findViewById(R.id.forget_btn);
        loginbtn = findViewById(R.id.sigin_btn);
        username_var = findViewById(R.id.username_field);
        password_var = findViewById(R.id.password_filed);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_ = username_var.getEditText().getText().toString();
                String password_ = password_var.getEditText().getText().toString();
                String user_pttn = "^[a-z0-9._%+-]+@(rku)+\\.+(ac)+\\.+(in)$";

                if (!TextUtils.isEmpty(username_) && username_.matches(user_pttn)) {
                    username_var.setError(null);
                    username_var.setErrorEnabled(false);
                    if (!TextUtils.isEmpty(password_)) {
                        password_var.setError(null);
                        password_var.setErrorEnabled(false);
                        mAuth.signInWithEmailAndPassword(username_, password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                    Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();

                                } else {
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


        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, ForgetPassword.class);
                startActivity(i);
                finishAndRemoveTask();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}