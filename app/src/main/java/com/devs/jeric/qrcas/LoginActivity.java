package com.devs.jeric.qrcas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button login, regTeach;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.loginEmail);
        password = (EditText)findViewById(R.id.loginPassword);
        login = (Button)findViewById(R.id.loginBtn);
        regTeach = (Button)findViewById(R.id.loginRegAsTeachBtn);
        progressBar = (ProgressBar)findViewById(R.id.progressBarLogin);

        try {
            mAuth = FirebaseAuth.getInstance();
        }catch (Exception e){
            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if(Email.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please input email!", Toast.LENGTH_LONG).show();
                }else if(Password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please input password!", Toast.LENGTH_LONG).show();
                }else if(Email.isEmpty() || Password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please input email and password!", Toast.LENGTH_LONG).show();
                }else{
                    mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(LoginActivity.this, TeacherDashboardActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });

        regTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegTeachActivity.class);
                startActivity(intent);
            }
        });
    }
}
