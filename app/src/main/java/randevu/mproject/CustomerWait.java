package randevu.mproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerWait extends BaseActivity {


    private String customerId = "";
    private String uid = "";
    private String delivererFoundId = "";

    private TextView mTextField;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private LatLng delivererLatLng;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_wait);

        mTextField = findViewById(R.id.tfCWait);
        mTextField = (TextView) findViewById(R.id.tfCWait);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid().toString();

        Intent myIntent = getIntent();
        delivererFoundId = myIntent.getStringExtra("delivererFoundId");
        mTextField.setText(delivererFoundId);
//        getDelivererLocation();
    }
    /*
    private void getDelivererLocation () {
        DatabaseReference delivererLocationRef = FirebaseDatabase.getInstance().getReference().child("StartDeliverer").child(delivererFoundId).child("l");
        delivererLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Object > map = (List <Object>) dataSnapshot.getValue();
                    double locationLat = 0;
                    double locationLng = 0;
                    if(map.get(0) != null)
                        locationLat = Double.parseDouble(map.get(0).toString());
                    if(map.get(1) != null)
                        locationLng = Double.parseDouble(map.get(1).toString());
                    delivererLatLng = new LatLng(locationLat, locationLng);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    */
}
