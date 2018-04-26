package randevu.mproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends BaseActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private String uid="";
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
        uid = currentUser.getUid().toString().trim();

        FirebaseDatabase.getInstance().getReference("StartCustomer/"+uid).removeValue();
        FirebaseDatabase.getInstance().getReference("StartDeliverer/"+uid).removeValue();
        FirebaseDatabase.getInstance().getReference("EndtCustomer/"+uid).removeValue();
        FirebaseDatabase.getInstance().getReference("EndDeliverer/"+uid).removeValue();
        FirebaseDatabase.getInstance().getReference("Users/"+uid+"/Item").removeValue();

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
