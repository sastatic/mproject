package randevu.mproject;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DelivererWait extends BaseActivity {


    private String customerId = "";
    private String uid = "";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverer_wait);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        uid = currentUser.getUid().toString();
//        getAssignedCustomer();
    }

    /*
    private void getAssignedCustomer() {
        DatabaseReference assignedCustomerRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        assignedCustomerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if (map.get("CustomerItemRequestId") != null) {
                        customerId = map.get("CustomerItemRequestId").toString();
                        getAssignedCustomerItemPickupLocation();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getAssignedCustomerItemPickupLocation() {
        DatabaseReference itemPickupLocationRef = FirebaseDatabase.getInstance().getReference().child("StartCustomer").child(customerId).child("l");
        itemPickupLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<Object> map = (List<Object>) dataSnapshot.getValue();
                    double locationLat = 0;
                    double locationLng = 0;
                    if(map.get(0) != null)
                        locationLat = Double.parseDouble(map.get(0).toString());
                    if(map.get(1) != null)
                        locationLng = Double.parseDouble(map.get(1).toString());
                    LatLng driverLatLng = new LatLng(locationLat, locationLng);
                    mMap.addMarker(new MarkerOptions().position(driverLatLng).title("PickUpLocation"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    */
}
