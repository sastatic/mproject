package randevu.mproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CustomerWait extends BaseActivity {


    private String customerId = "";
    private String uid = "";
    private String delivererFoundId = "";

    private ListView mDetailList;

    private ArrayList<String> deliverer;

    private Button mSignOut, mAccept, mReject;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_wait);

        mDetailList = (ListView) findViewById(R.id.detailList);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid().toString();

        Intent myIntent = getIntent();
        delivererFoundId = myIntent.getStringExtra("delivererFoundId");
        DatabaseReference delivererRef = FirebaseDatabase.getInstance().getReference().child("Users").child(delivererFoundId);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, deliverer);

        mDetailList.setAdapter(arrayAdapter);

        delivererRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
