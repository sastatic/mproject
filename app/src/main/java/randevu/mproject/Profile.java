package randevu.mproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends BaseActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.signOut).setOnClickListener(this);
        findViewById(R.id.customerBtn).setOnClickListener(this);
        findViewById(R.id.delivererBtn).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(Profile.this, start.class));
            finish();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signOut) {
            mAuth.signOut();
            startActivity(new Intent(Profile.this, start.class));
            finish();
            return;
        }
        else if (i == R.id.customerBtn) {
            startActivity(new Intent(Profile.this, Customer.class));
            finish();
            return;
        }
        else if (i == R.id.delivererBtn) {
            startActivity(new Intent(Profile.this, DelivererMap.class));
            finish();
            return;
        }
    }
}
