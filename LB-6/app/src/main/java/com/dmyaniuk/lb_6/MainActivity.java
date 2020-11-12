package com.dmyaniuk.lb_6;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    private EditText emailEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initializeElements();
    }

    public void onLogIn(View v) {
        final String email = this.emailEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();

        this.firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent viewContactsIntent = new Intent(MainActivity.this, ContactsView.class);
                    viewContactsIntent.putExtra("user", email);
                    startActivity(viewContactsIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Unable to authorize", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onRegistration(View v) {
        String email = this.emailEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();

        this.firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "User has been added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Unable to add user", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initializeElements() {
        this.emailEditText = findViewById(R.id.emailEditText);
        this.passwordEditText = findViewById(R.id.passwordEditText);

        this.firebaseAuth = FirebaseAuth.getInstance();
    }
}