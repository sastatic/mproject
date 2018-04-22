package randevu.mproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Customer extends BaseActivity implements View.OnClickListener  {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        findViewById(R.id.signOut).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null)
            startActivity(new Intent(Customer.this, start.class));
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.signOut) {
            mAuth.signOut();
            startActivity(new Intent(Customer.this, start.class));
        }
    }

}
