package randevu.mproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup_form extends AppCompatActivity {


    private EditText mName;
    private EditText mDOB;
    private EditText mPhone;
    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mName = findViewById(R.id.nameField);
        mDOB = findViewById(R.id.dobField);
        mPhone = findViewById(R.id.phoneField);
        mEmailField = findViewById(R.id.emailField);
        mPasswordField = findViewById(R.id.passwordField);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mProgress = new ProgressDialog(this);

        findViewById(R.id.registerBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });
    }

    private void startRegister() {
        final String name = mName.getText().toString().trim();
        final String dob = mDOB.getText().toString().trim();
        final String phone = mPhone.getText().toString().trim();
        final String email = mEmailField.getText().toString().trim();
        final String password = mPasswordField.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(signup_form.this, "Fields are Empty", Toast.LENGTH_LONG).show();
        } else {
            mProgress.setMessage("Signing Up...");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String user_id = mAuth.getCurrentUser().getUid();

                    DatabaseReference curr_user_db = mDatabase.child(user_id);
                    curr_user_db.child("Name").setValue(name);
                    curr_user_db.child("DOB").setValue(dob);
                    curr_user_db.child("Phone").setValue(phone);
                    curr_user_db.child("Email").setValue(email);
//                    curr_user_db.child("Password").setValue(password);
                    curr_user_db.child("Wallet").setValue(5000);
                    curr_user_db.child("Matched").setValue(0);

                    mProgress.dismiss();

                    startActivity(new Intent(signup_form.this, Profile.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    return;
                }
            });
        }
    }
}
