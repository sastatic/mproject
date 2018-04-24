package randevu.mproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Customer extends AppCompatActivity implements View.OnClickListener {

    private EditText mItem;
    private EditText mItemDescription;
    private EditText mPriceField;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        mItem = findViewById(R.id.itemField);
        mItemDescription = findViewById(R.id.itemDescriptionField);
        mPriceField = findViewById(R.id.priceField);

        findViewById(R.id.itemSubmitBtn).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        String uid = mAuth.getCurrentUser().getUid().toString();

        String item = mItem.getText().toString();
        String description = mItemDescription.getText().toString();
        final int price = Integer.parseInt(mPriceField.getText().toString());

        DatabaseReference walletRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Wallet");
        walletRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                int money = Integer.parseInt(value);
                money = money - price;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        int i = view.getId();
        if (i == R.id.itemSubmitBtn) {
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Item").child(uid);
            dbRef.child(item).child("Description").setValue(description);
            dbRef.child(item).child("Price").setValue(Integer.toString(price));
//            walletRef.setValue(Integer.toString(money));
            startActivity(new Intent(Customer.this, CustomerMap.class));
            finish();
            return;

        }
    }
}
