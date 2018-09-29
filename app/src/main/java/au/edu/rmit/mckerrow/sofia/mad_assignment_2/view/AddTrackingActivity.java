package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.SaveTrackingButtonController;

public class AddTrackingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button saveTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tracking);

        setUpSpinners();

        saveTracking = (Button) findViewById(R.id.saveTracking);
        saveTracking.setOnClickListener(new SaveTrackingButtonController(this, this));
    }

    // Set category names in spinner
    private void setUpSpinners() {
        Spinner trackableSpinner = (Spinner) findViewById(R.id.trackableNameSpinner);
        ArrayAdapter<CharSequence> arrayAdapterName = ArrayAdapter.createFromResource(this, R.array.spinner_trackables, android.R.layout.simple_spinner_item);
        arrayAdapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trackableSpinner.setAdapter(arrayAdapterName);
        trackableSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner trackableSpinner = (Spinner) findViewById(R.id.trackableNameSpinner);
        String trackable = trackableSpinner.getSelectedItem().toString();

        Spinner meetDateSpinner = (Spinner) findViewById(R.id.meetDateSpinner);

        // If trackable Australian Magpie is selected, display the meet dates/times for this trackable in the meetDateSpinner
        if (trackable.contentEquals("Australian Magpie")) {
            ArrayAdapter<CharSequence> arrayAdapterDate = ArrayAdapter.createFromResource(this, R.array.meet_dates_magpie,
                    android.R.layout.simple_spinner_item);
            arrayAdapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapterDate.notifyDataSetChanged();
            meetDateSpinner.setAdapter(arrayAdapterDate);
        }
        // If trackable Kookaburra is selected, display the meet dates/times for this trackable in the meetDateSpinner
        else if (trackable.contentEquals("Kookaburra")) {
            ArrayAdapter<CharSequence> arrayAdapterDate = ArrayAdapter.createFromResource(this, R.array.meet_dates_kookaburra,
                    android.R.layout.simple_spinner_item);
            arrayAdapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapterDate.notifyDataSetChanged();
            meetDateSpinner.setAdapter(arrayAdapterDate);
        }
        // If trackable Sulphur-Crested Cockatoo is selected, display the meet dates/times for this trackable in the meetDateSpinner
        else if (trackable.contentEquals("Sulphur-Crested Cockatoo")) {
            ArrayAdapter<CharSequence> arrayAdapterDate = ArrayAdapter.createFromResource(this, R.array.meet_dates_cockatoo,
                    android.R.layout.simple_spinner_item);
            arrayAdapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapterDate.notifyDataSetChanged();
            meetDateSpinner.setAdapter(arrayAdapterDate);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
