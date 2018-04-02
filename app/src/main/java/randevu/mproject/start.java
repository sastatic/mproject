package randevu.mproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class start extends BaseActivity implements View.OnClickListener {

    private Button mFbSignUp;
    private Button mGglSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        findViewById(R.id.fbSignUp).setOnClickListener(this);
        findViewById(R.id.gglSignUp).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.fbSignUp) {

        } else if (i == R.id.gglSignUp) {

        }
    }
}
