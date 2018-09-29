package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.RemoveTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.UpdateTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingInfo;

public class EditTrackingActivity extends AppCompatActivity {
    private Context mContext;
    private EditText editTitle;
    private String title;
    private int position;
    private static List<BirdTracking> trackingList;
    private TrackingInfo trackingInfo;
    private BirdTracking birdTracking;
    private Button updateTracking;
    private Button removeTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tracking);

        setUpSpinners();

        String trackingID = getCurrentTrackingID();
        birdTracking = getCurrentTracking(trackingID);

        title = birdTracking.getTitle();
        editTitle = (EditText) findViewById(R.id.editTitleEntry);
        editTitle.setText(title);

        position = getIntent().getExtras().getInt("position_key");

        updateTracking = (Button) findViewById(R.id.updateTracking);
        updateTracking.setOnClickListener(new UpdateTrackingButtonController(this, this, position));
        removeTracking = (Button) findViewById(R.id.removeTracking);
        removeTracking.setOnClickListener(new RemoveTrackingButtonController(this, position));
    }

    // Set category names in spinner
    public void setUpSpinners() {
        Spinner trackableSpinner = (Spinner) findViewById(R.id.editTrackableNameSpinner);
        ArrayAdapter<CharSequence> arrayAdapterName = ArrayAdapter.createFromResource(this, R.array.spinner_trackables, android.R.layout.simple_spinner_item);
        arrayAdapterName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trackableSpinner.setAdapter(arrayAdapterName);

        String trackingID = getCurrentTrackingID();
        birdTracking = getCurrentTracking(trackingID);

        // If the selected tracking's trackable ID is 1, display "Australian Magpie" in the trackable name spinner
        if (birdTracking.getTrackableID().equals("1")) {
            trackableSpinner.setSelection(0);
        }
        // If the selected tracking's trackable ID is 2, display "Kookaburra" in the trackable name spinner
        else if (birdTracking.getTrackableID().equals("2")) {
            trackableSpinner.setSelection(1);
        }
        // If the selected tracking's trackable ID is 3, display "Sulphur-Crested Cockatoo" in the trackable name spinner
        else if (birdTracking.getTrackableID().equals("3")) {
            trackableSpinner.setSelection(2);
        }

        Spinner meetDateSpinner = (Spinner) findViewById(R.id.editMeetDateSpinner);
        ArrayAdapter<CharSequence> arrayAdapterDate = ArrayAdapter.createFromResource(this, R.array.meet_dates, android.R.layout.simple_spinner_item);
        arrayAdapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        meetDateSpinner.setAdapter(arrayAdapterDate);
    }

    // Get the selected tracking
    public BirdTracking getCurrentTracking(String trackingID) {
        BirdTracking currentTracking = null;

        trackingInfo = TrackingInfo.getSingletonInstance(mContext);
        trackingList = trackingInfo.getTrackingList();

        for (int i = 0; i < trackingList.size(); i++) {
            if (trackingList.get(i).getTrackingID().equals(trackingID)) {
                currentTracking = trackingList.get(i);
                break;
            }
        }

        return currentTracking;
    }

    // Get the tracking ID of the selected tracking
    public String getCurrentTrackingID () {
        String id = getIntent().getExtras().getString(TrackingAdapter.TRACKING_ID_KEY);;

        return id;
    }


}
