package ca.kgb.okhttplogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textView = (TextView) findViewById(R.id.txtView);

        Student student = getIntent()
                .getExtras()
                .getParcelable(MainActivity.STUDENT_BUNDLE_KEY);

        textView.setText("Welcome! " + student.toString());

    }
}
