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
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        mItem = findViewById(R.id.itemField);
        mItemDescription = findViewById(R.id.itemDescriptionField);

        findViewById(R.id.itemSubmitBtn).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        String item = mItem.getText().toString();
        String description = mItemDescription.getText().toString();
        String uid = mAuth.getCurrentUser().getUid().toString();
        int i = view.getId();
        if (i == R.id.itemSubmitBtn) {
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Item").child(uid);
            dbRef.child(item).setValue(description);

            startActivity(new Intent(Customer.this, CustomerMap.class));
            finish();
            return;
        }
    }
}
