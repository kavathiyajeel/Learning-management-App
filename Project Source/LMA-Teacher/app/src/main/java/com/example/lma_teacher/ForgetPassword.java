package com.example.lma_teacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {
    public MaterialButton forget;
    private TextInputEditText fp_email;
    private TextInputLayout email_field;
    private FirebaseAuth mAuth;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Forget Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3E5D7C")));
        getWindow().setNavigationBarColor(ContextCompat.getColor(this,R.color.bg));
        fp_email =findViewById(R.id.fp_emailid);
        forget = findViewById(R.id.fp_send);
        email_field = findViewById(R.id.email_field);
        mAuth = FirebaseAuth.getInstance();
        builder = new AlertDialog.Builder(this);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = fp_email.getText().toString();
                String email_pttn = "^[a-z0-9._%+-]+@(rku)+\\.+(ac)+\\.+(in)$";
                if(!TextUtils.isEmpty(email) && email.matches(email_pttn)){
                    email_field.setError(null);
                    email_field.setErrorEnabled(false);
                    resetPassword();

                }else{
                    email_field.setError("Enter email address");
                }
            }

        });
    }
    private void resetPassword() {
        String email = fp_email.getText().toString();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ForgetPassword.this, "Sent sucessfully", Toast.LENGTH_SHORT).show();
                showdialog();
            }


        });
    }
    private void showdialog() {
        builder.setTitle("Forget Password").setIcon(R.drawable.ic_nav_mp);
        builder.setMessage("Email sent Successfully. \nplese check your Spambox.");
        builder.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        startActivity(new Intent(ForgetPassword.this,Login.class));
                        finish();

                    }
                });
        builder.show();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}