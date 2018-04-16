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
        findViewById(R.id.userBtn).setOnClickListener(this);
        findViewById(R.id.delivererBtn).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
            startActivity(new Intent(Profile.this, start.class));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signOut) {
            startSignOut();
        } else if (i == R.id.userBtn) {
            startUser();
        } else if (i == R.id.delivererBtn) {
            startDeliverer();
        }
    }

    private void startDeliverer() {
        startActivity(new Intent(Profile.this, Deliverer.class));
    }

    private void startUser() {
        Toast.makeText(Profile.this, "Button to be linked", Toast.LENGTH_LONG).show();
    }

    private void startSignOut() {
        mAuth.signOut();
        startActivity(new Intent(Profile.this, start.class));
    }

}
