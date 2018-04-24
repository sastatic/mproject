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
    }
}
