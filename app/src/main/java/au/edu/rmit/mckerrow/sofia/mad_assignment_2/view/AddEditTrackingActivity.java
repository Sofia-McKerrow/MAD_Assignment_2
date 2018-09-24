package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.SaveTrackingButtonController;

public class AddEditTrackingActivity extends AppCompatActivity {
    private Button saveTracking;
    private EditText editTitle;
    private String title;
    private static int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_tracking);

        setUpSpinners();

        editTitle = (EditText) findViewById(R.id.titleEntry);
        Intent intent = getIntent();
        if (intent.hasExtra("title")) {
            title = getIntent().getExtras().getString("title");
            editTitle.setText(title);
        }

        saveTracking = (Button) findViewById(R.id.saveTracking);
        saveTracking.setOnClickListener(new SaveTrackingButtonController(this, this));
    }

    // Set category names in spinner
    private void setUpSpinners() {
        Spinner trackableSpinner = (Spinner) findViewById(R.id.trackableNameSpinner);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.spinner_trackables, android.R.layout.simple_spinner_item);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trackableSpinner.setAdapter(arrayAdapter1);

        Spinner meetDateSpinner = (Spinner) findViewById(R.id.meetDateSpinner);
        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.meet_dates, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetDateSpinner.setAdapter(arrayAdapter2);
    }

}
