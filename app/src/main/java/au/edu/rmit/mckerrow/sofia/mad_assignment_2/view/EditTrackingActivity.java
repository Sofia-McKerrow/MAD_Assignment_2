package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.RemoveTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.UpdateTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.DataSource;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.DatabaseHelper;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.TrackingsTable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingsListInfo;

public class EditTrackingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;
    private DataSource mDataSource;
    private EditText editTitle;
    private String title;
    private int position;
    private static List<BirdTracking> trackingList;
    private TrackingsListInfo trackingsListInfo;
    private BirdTracking birdTracking;
    private Button updateTracking;
    private Button removeTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_tracking);

        mDataSource = new DataSource(this);
        mDataSource.open();

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
        // Show default trackable name in trackable name spinner
        else {
            trackableSpinner.setSelection(0);
        }

        trackableSpinner.setOnItemSelectedListener(this);
    }

    // Get the selected tracking
    public BirdTracking getCurrentTracking(String trackingID) {
        BirdTracking currentTracking = null;

        trackingsListInfo = TrackingsListInfo.getSingletonInstance(mContext);
        trackingList = trackingsListInfo.getTrackingList();

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner trackableSpinner = (Spinner) findViewById(R.id.editTrackableNameSpinner);
        String trackable = trackableSpinner.getSelectedItem().toString();

        Spinner meetDateSpinner = (Spinner) findViewById(R.id.editMeetDateSpinner);

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

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }

    @Override
    protected void onStop() {
        super.onStop();

        trackingsListInfo = TrackingsListInfo.getSingletonInstance(this);
        trackingList = trackingsListInfo.getTrackingList();

        mDbHelper = new DatabaseHelper(this);
        mDatabase = mDbHelper.getWritableDatabase();

        // Clear data in trackings table
        mDatabase.delete(TrackingsTable.TABLE_TRACKINGS, null, null);

        // Add items from trackings list to trackings table
        mDataSource.seedDatabaseWithTrackings(trackingList);

        mDataSource.close();
    }
}
