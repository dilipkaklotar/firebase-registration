package com.mobitz.firebaseuserregistration;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "MainActivity";

    private EditText editTextEmail, editTextPassword;
    private Button buttonRegistration;
    private TextView txtMessage;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        txtMessage = (TextView) findViewById(R.id.txtMessage);


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonRegistration = (Button) findViewById(R.id.buttonRegistration);
        buttonRegistration.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == buttonRegistration) {

            if (TextUtils.isEmpty(editTextEmail.getText().toString()) || TextUtils.isEmpty(editTextPassword.getText().toString())) {
                Toast.makeText(this, "Fields are Empty", Toast.LENGTH_SHORT).show();
            } else {

                final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Registration", "Please wait while loading ....", true);

                mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                progressDialog.dismiss();

                                if (task.isSuccessful()) {

                                    FirebaseUser user = mAuth.getCurrentUser();

                                    StringBuffer stringBuffer = new StringBuffer();
                                    stringBuffer.append("Welcome ");
                                    stringBuffer.append(user.getEmail());

                                    txtMessage.setText(stringBuffer);

                                } else {

                                    txtMessage.setText("Authentication failed.");

                                }
                            }
                        });
            }

        }
    }
}
