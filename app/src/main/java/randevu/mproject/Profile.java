package randevu.mproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends BaseActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.signOut).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signOut) {
            startSignOut();
        }
    }

    private void startSignOut() {
        mAuth.signOut();
        updateUI(null);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser == null)
            startActivity(new Intent(Profile.this, start.class));
        else
            Toast.makeText(Profile.this, "Allready Signed In\n Please Sign Out", Toast.LENGTH_LONG).show();
    }
}
