package randevu.mproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class CustomerWait extends BaseActivity implements View.OnClickListener {

    private String customerId = "";
    private String uid = "";
    private String delivererFoundId = "";
    private String price="";
    Integer money = 0;

    private TextView mName, mPhone, mCustDet;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private GoogleMap mMap;
    private DatabaseReference delivererRef;

    private Button mReject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_wait);

        mName = (TextView) findViewById(R.id.nameField);
        mPhone = (TextView) findViewById(R.id.phoneField);
        mCustDet = (TextView) findViewById(R.id.custField);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid().toString();

        Intent myIntent = getIntent();
        delivererFoundId = myIntent.getStringExtra("delivererFoundId");
        price = myIntent.getStringExtra("price");

        delivererRef = FirebaseDatabase.getInstance().getReference().child("Users").child(delivererFoundId);

        DatabaseReference mNameRef = delivererRef.child("Name");
        mNameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                mName.setText("Name: " + s);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference mPhoneRef = delivererRef.child("Phone");
        mPhoneRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                mPhone.setText("Contact No: " + s);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference customerRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Wallet");
        customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                money = dataSnapshot.getValue(Integer.class);
                mCustDet.setText("Your Current Wallet ballence is " + Integer.toString(money) + " ₹.\nAfter Accepting " + price + " ₹ will be deduced as Item cost and service charge");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        findViewById(R.id.acceptBtn).setOnClickListener(this);
        findViewById(R.id.rejectBtn).setOnClickListener(this);
        findViewById(R.id.signOut).setOnClickListener(this);

        mReject = (Button) findViewById(R.id.rejectBtn);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.acceptBtn) {
            Toast.makeText(CustomerWait.this, "Accepted...", Toast.LENGTH_LONG).show();
            delivererRef.child("Matched").setValue(1);
            delivererRef.child("MatchedCustomer").setValue(uid);
            int min = 0;
            int max = 100000;
            FirebaseDatabase.getInstance().getReference("/Users/"+uid+"/Wallet").setValue(money-Integer.parseInt(price));
            int random = new Random().nextInt((max - min) + 1) + min;
            TextView tv = findViewById(R.id.random);
            tv.setText("Give this OTP to Deliverer After Complition of Delivery\n" + random);
            delivererRef.child("itemOTP").setValue(random);

            mReject.setVisibility(View.INVISIBLE);
        }
        if (i == R.id.rejectBtn) {
            Toast.makeText(CustomerWait.this, "Rejected...", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Profile.class));
            finish();
            return;
        }
        if (i == R.id.signOut) {
            mAuth.signOut();
            Toast.makeText(CustomerWait.this, "Sign Out...", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, start.class));
            finish();
            return;
        }
    }
}
