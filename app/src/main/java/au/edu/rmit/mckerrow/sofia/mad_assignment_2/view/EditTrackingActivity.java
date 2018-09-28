package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
import java.util.Map;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackableInfo;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingInfo;

public class EditTrackingActivity extends AppCompatActivity {
    private Context mContext;
    private EditText editTitle;
    private String title;
    private int position;
    private static List<BirdTracking> trackingList;
    private TrackingInfo trackingInfo;
    private BirdTracking birdTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tracking);

        setUpSpinners();

        String trackingID = getIntent().getExtras().getString(TrackingAdapter.TRACKING_ID_KEY);
        trackingInfo = TrackingInfo.getSingletonInstance(mContext);
        trackingList = trackingInfo.getTrackingList();

        for (int i = 0; i < trackingList.size(); i++) {
            if (trackingList.get(i).getTrackingID().equals(trackingID)) {
                birdTracking = trackingList.get(i);
                break;
            }
        }

        editTitle = (EditText) findViewById(R.id.editTitleEntry);
        title = birdTracking.getTitle();
        editTitle.setText(title);
    }

    // Set category names in spinner
    private void setUpSpinners() {
        Spinner trackableSpinner = (Spinner) findViewById(R.id.editTrackableNameSpinner);
        ArrayAdapter<CharSequence> arrayAdapter1 = ArrayAdapter.createFromResource(this, R.array.spinner_trackables, android.R.layout.simple_spinner_item);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trackableSpinner.setAdapter(arrayAdapter1);

        Spinner meetDateSpinner = (Spinner) findViewById(R.id.editMeetDateSpinner);
        ArrayAdapter<CharSequence> arrayAdapter2 = ArrayAdapter.createFromResource(this, R.array.meet_dates, android.R.layout.simple_spinner_item);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetDateSpinner.setAdapter(arrayAdapter2);
    }
}
