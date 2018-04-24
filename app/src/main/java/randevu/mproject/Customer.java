package randevu.mproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Customer extends AppCompatActivity implements View.OnClickListener {

    private EditText mItem;
    private EditText mItemDescription;
    private EditText mPriceField;
    private FirebaseAuth mAuth;
    private int money, price;

    private String uid = "";

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
        uid = mAuth.getCurrentUser().getUid().toString();

        String item = mItem.getText().toString();
        String description = mItemDescription.getText().toString();
        price = Integer.parseInt(mPriceField.getText().toString());


        int i = view.getId();
        if (i == R.id.itemSubmitBtn) {
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Item").child(uid);
            dbRef.child(item).child("Description").setValue(description);
            dbRef.child(item).child("Price").setValue(Integer.toString(price));

            Intent mIntent = new Intent(Customer.this, CustomerMap.class);
            mIntent.putExtra("price", Integer.toString(price));

            startActivity(mIntent);
            finish();
            return;

        }
    }
}
