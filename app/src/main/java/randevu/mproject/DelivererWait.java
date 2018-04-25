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
    private Integer myMoney=0;

    private String cid="";
    private String cName="";
    private String cPhone="";
    private String cItem="";
    private String cItemDescription="";

    private EditText mOTP;

    private TextView mCustField, mItemField, mIntroField, mDescrField, mMyWalletField;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverer_wait);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        mAcceptBtn = (Button) findViewById(R.id.acceptBtn);
        mAcceptBtn.setVisibility(View.INVISIBLE);
        mOTP = findViewById(R.id.otpField);
        mOTP.setVisibility(View.INVISIBLE);

        mCustField = (TextView) findViewById(R.id.custField);
        mItemField = (TextView) findViewById(R.id.itemField);
        mIntroField = (TextView) findViewById(R.id.introField);
        mDescrField = (TextView) findViewById(R.id.itemDescriptionField);
        mMyWalletField = (TextView) findViewById(R.id.myWalletField);

        myMoneyGetter("/Users/"+uid+"/Wallet");

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
            } else {
                mAcceptBtn.setVisibility(View.VISIBLE);
                mOTP.setVisibility(View.VISIBLE);

                if (cid == null || cid == "")
                    cidGetter("/Users/"+uid+"/MatchedCustomer");
                if (cItemOTP == null || cItemOTP == 0)
                    cItemOTPGetter("/Users/"+uid+"/itemOTP");
                if (cItemPrice == null || cItemPrice == 0)
                    cItemPriceGetter("/Users/"+cid+"/Item/Price");
                if (cName == null || cName == "")
                    cNameGetter("/Users/"+cid+"Name");
                if (cPhone == null || cPhone == "")
                    cPhoneGetter("/Users/"+cid+"/Phone");
                if (cItem == null || cItem == "")
                    cItemGetter("/Users/"+cid+"/Item/ItemName");
                if (cItemDescription == null || cItemDescription == "")
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
        if (i == R.id.acceptBtn) {
            int otp = Integer.parseInt(mOTP.getText().toString().trim());
            if (cItemOTP == otp)
                while (myMoney == null || myMoney == 0)
                    myMoneyGetter("/Users/"+uid+"Wallet");
                FirebaseDatabase.getInstance().getReference("/Users/"+uid+"/Wallet").setValue(myMoney + cItemPrice);
                FirebaseDatabase.getInstance().getReference("/Users/"+uid+"/Matched").setValue(0);
                FirebaseDatabase.getInstance().getReference("/Users/"+uid+"/MatchedCustomer").removeValue();
                FirebaseDatabase.getInstance().getReference("/Users/"+uid+"/itemOTP").removeValue();

                FirebaseDatabase.getInstance().getReference("/Users/"+cid+"/Item").removeValue();

                mAcceptBtn.setVisibility(View.INVISIBLE);
                mOTP.setVisibility(View.INVISIBLE);

                mMyWalletField.setText("Your Wallet Amount updated to Rs. "+Integer.toString(myMoney));
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
            @Override public void onDataChange(DataSnapshot dataSnapshot) { if (dataSnapshot.exists()) cItem = dataSnapshot.getValue(String.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cPhoneGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { if (dataSnapshot.exists()) cPhone = dataSnapshot.getValue(String.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cNameGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { if (dataSnapshot.exists()) cName = dataSnapshot.getValue(String.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cItemPriceGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { if (dataSnapshot.exists()) cItemPrice = dataSnapshot.getValue(Integer.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void myMoneyGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { if (dataSnapshot.exists()) myMoney = dataSnapshot.getValue(Integer.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cItemOTPGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { if (dataSnapshot.exists()) cItemOTP = dataSnapshot.getValue(Integer.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cidGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { if (dataSnapshot.exists()) cid = dataSnapshot.getValue(String.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void cItemDescriptionGetter(String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { if (dataSnapshot.exists()) cItemDescription = dataSnapshot.getValue(String.class); }
            @Override public void onCancelled(DatabaseError databaseError) {}
        });
    }
    private void cmGetter (String ref) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(ref);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) { if (dataSnapshot.exists()) cm = dataSnapshot.getValue(Integer.class); }
            @Override public void onCancelled(DatabaseError databaseError) { }
        });
    }
}
