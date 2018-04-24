package randevu.mproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DelivererWait extends BaseActivity implements View.OnClickListener {


    private String customerId = "";
    private String uid = "";
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private GoogleMap mMap;
    private Boolean matched = false;

    private Layout layout;
    private Button mAcceptBtn;

    private Integer cm=0;
    private Integer cItemOTP=0;
    private Integer cItemPrice=0;

    private String cid="";
    private String cName="";
    private String cPhone="";
    private String cItem="";
    private String cItemDescription="";

    private EditText mOTP;

    private TextView mCustField, mItemField, mIntroField, mDescrField;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverer_wait);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mAcceptBtn = (Button) findViewById(R.id.acceptBtn);
        mAcceptBtn.setVisibility(View.INVISIBLE);

        mCustField = (TextView) findViewById(R.id.custField);
        mItemField = (TextView) findViewById(R.id.itemField);
        mIntroField = (TextView) findViewById(R.id.introField);
        mDescrField = (TextView) findViewById(R.id.itemDescriptionField);

        mOTP = findViewById(R.id.otpField);

        uid = currentUser.getUid().toString();
        findViewById(R.id.refreshBtn).setOnClickListener(this);
        findViewById(R.id.acceptBtn).setOnClickListener(this);
        findViewById(R.id.signOut).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.refreshBtn) {
            if (cm == 0) {
                cmGetter("/Users/"+uid+"/Matched");
                if (cm == 1) {
                    mAcceptBtn.setVisibility(View.VISIBLE);

//                    while (cid == null)
                        cidGetter("/Users/"+uid+"/MatchedCustomer");
//                    while (cItemOTP == null)
                        cItemOTPGetter("/Users/"+uid+"/itemOTP");
//                    while (cItemPrice == null)
                        cItemPriceGetter("/Users/"+cid+"/Item/Price");
//                    while (cName == null)
                        cNameGetter("/Users/"+cid+"Name");
//                    while (cPhone == null)
                        cPhoneGetter("/Users/"+cid+"/Phone");
//                    while (cItem == null)
                        cItemGetter("/Users/"+cid+"/Item/ItemName");
//                    while (cItemDescription == null)
                        cItemDescriptionGetter("/Users/"+cid+"/Item/Description");

                    String strC = "Customer Name: "+cName+"\nCustomer Phone No. "+cPhone;
                    String strI = "Item Name: "+cItem+"\nItem Price: "+cItemPrice;
                    String strDI = "Item Description\n"+cItemDescription;

                    mCustField.setText(strC);
                    mItemField.setText(strI);
                    mIntroField.setText("Item Details");
                    mDescrField.setText(strDI);
                }
            }
        }
        if (i == R.id.acceptBtn) {
            Toast.makeText(DelivererWait.this, "Accepted...", Toast.LENGTH_LONG).show();
//            startActivity(new Intent(this, Profile.class));
//            finish();
//            return;
        }
        if (i == R.id.signOut) {
            mAuth.signOut();
            Toast.makeText(DelivererWait.this, "Sign Out...", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, start.class));
            finish();
            return;
        }
    }

    private void cItemGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { cItem = dataSnapshot.getValue(String.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cPhoneGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { cPhone = dataSnapshot.getValue(String.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cNameGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { cName = dataSnapshot.getValue(String.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cItemPriceGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { cItemPrice = dataSnapshot.getValue(Integer.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cItemOTPGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { cItemOTP = dataSnapshot.getValue(Integer.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cidGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { cid = dataSnapshot.getValue(String.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cItemDescriptionGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { cItemDescription = dataSnapshot.getValue(String.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }
    private void cmGetter (String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { cm = dataSnapshot.getValue(Integer.class); }
            @Override public void onCancelled(DatabaseError databaseError) { }
        });
    }
}
