package com.devs.jeric.qrcas;

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
import com.google.firebase.database.FirebaseDatabase;

public class RegTeachActivity extends AppCompatActivity {
    private EditText Name, Email, Password, ConfPass;
    private Button RegBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_teach);

        Name = (EditText)findViewById(R.id.regTeachName);
        Email = (EditText)findViewById(R.id.regTeachEmail);
        Password = (EditText)findViewById(R.id.regTeachPass);
        ConfPass = (EditText)findViewById(R.id.regTeachConfPass);
        RegBtn = (Button)findViewById(R.id.regTeachRegBtn);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        RegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                final String name = Name.getText().toString();
                final String email = Email.getText().toString();
                String password = Password.getText().toString();
                String confPass = ConfPass.getText().toString();

                if(name.isEmpty() || email.isEmpty() || password.isEmpty() || confPass.isEmpty()){
                    Toast.makeText(RegTeachActivity.this, "Please complete given fields!", Toast.LENGTH_LONG).show();
                }else if(!password.equals(confPass)){
                    Toast.makeText(RegTeachActivity.this, "Passwords not match", Toast.LENGTH_LONG).show();
                }else{
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Teacher teacher = new Teacher(name, email);

                                FirebaseDatabase.getInstance().getReference("Teachers")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(teacher).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Name.getText().clear();
                                            Email.getText().clear();
                                            Password.getText().clear();
                                            ConfPass.getText().clear();
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegTeachActivity.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                                        }else{
                                            Toast.makeText(RegTeachActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(RegTeachActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG);
                            }
                        }
                    });
                }
            }
        });
    }
}
