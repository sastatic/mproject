package randevu.mproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class start extends BaseActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPasswordField;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mEmailField = findViewById(R.id.emailId);
        mPasswordField = findViewById(R.id.password);

        findViewById(R.id.signIn).setOnClickListener(this);
        findViewById(R.id.registerBtn).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(start.this, Profile.class));
            finish();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signIn) {
            startSignIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        }
        else if (i == R.id.registerBtn) {
            startActivity(new Intent(start.this, signup_form.class));
            finish();
            return;
        }
    }


    private void startSignIn(String email, String password) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(start.this, "Fields are Empty", Toast.LENGTH_LONG).show();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                        Toast.makeText(start.this, "Please Register First...", Toast.LENGTH_LONG).show();
                    else {
                        startActivity(new Intent(start.this, Profile.class));
                        finish();
                        return;
                    }
                }
            });
        }
    }
}
