package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.SaveTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.DataSource;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.DatabaseHelper;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.TrackingsTable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingsListInfo;

public class AddTrackingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;
    private DataSource mDataSource;
    private TrackingsListInfo trackingsListInfo;
    private List<BirdTracking> trackingList;
    private Button saveTracking;
    private String trackableName;
    private String trackingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tracking);

        mDataSource = new DataSource(this);
        mDataSource.open();

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

        if (getIntent().getExtras() != null) {
            trackableName = getIntent().getExtras().getString("trackable_name_key");

            // Display the name of the selected tracking in the spinner
            if (trackableName.equals("Australian Magpie")) {
                trackableSpinner.setSelection(0);
            }
            else if (trackableName.equals("Kookaburra")) {
                trackableSpinner.setSelection(1);
            }
            else if (trackableName.equals("Sulphur-Crested Cockatoo")) {
                trackableSpinner.setSelection(2);
            }
            else {
                trackableSpinner.setSelection(0);
            }
        }

        trackableSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner trackableSpinner = (Spinner) findViewById(R.id.trackableNameSpinner);
        String trackable = trackableSpinner.getSelectedItem().toString();

        Spinner meetDateSpinner = (Spinner) findViewById(R.id.meetDateSpinner);
        trackingTime = null;

        // If trackable Australian Magpie is selected, display the meet dates/times for this trackable in the meetDateSpinner
        if (trackable.contentEquals("Australian Magpie")) {
            ArrayAdapter<CharSequence> arrayAdapterDate = ArrayAdapter.createFromResource(this, R.array.meet_dates_magpie,
                    android.R.layout.simple_spinner_item);
            arrayAdapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapterDate.notifyDataSetChanged();
            meetDateSpinner.setAdapter(arrayAdapterDate);

            if (getIntent().getExtras() != null) {
                trackingTime = getIntent().getExtras().getString("tracking_time_key");
                String time = trackingTime.split(" ")[1];

                if (trackingTime.equals("1:10:00")) {
                    meetDateSpinner.setSelection(0, true);
                } else if (trackingTime != null && trackingTime.equals("1:30:00")) {
                    meetDateSpinner.setSelection(1, true);
                } else {
                    meetDateSpinner.setSelection(0, true);
                }
            }
        }
        // If trackable Kookaburra is selected, display the meet dates/times for this trackable in the meetDateSpinner
        else if (trackable.contentEquals("Kookaburra")) {
            ArrayAdapter<CharSequence> arrayAdapterDate = ArrayAdapter.createFromResource(this, R.array.meet_dates_kookaburra,
                    android.R.layout.simple_spinner_item);
            arrayAdapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapterDate.notifyDataSetChanged();
            meetDateSpinner.setAdapter(arrayAdapterDate);

            if (getIntent().getExtras() != null) {
                trackingTime = getIntent().getExtras().getString("tracking_time_key");
                String time = trackingTime.split(" ")[1];

                if (time.equals("1:10:00")) {
                    meetDateSpinner.setSelection(0, true);
                }
                else if (time.equals("1:35:00")) {
                    meetDateSpinner.setSelection(1, true);
                }
                else {
                    meetDateSpinner.setSelection(0, true);
                }
            }
        }

        // If trackable Sulphur-Crested Cockatoo is selected, display the meet dates/times for this trackable in the meetDateSpinner
        else if (trackable.contentEquals("Sulphur-Crested Cockatoo")) {
            ArrayAdapter<CharSequence> arrayAdapterDate = ArrayAdapter.createFromResource(this, R.array.meet_dates_cockatoo,
                    android.R.layout.simple_spinner_item);
            arrayAdapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapterDate.notifyDataSetChanged();
            meetDateSpinner.setAdapter(arrayAdapterDate);

            if (getIntent().getExtras() != null) {
                trackingTime = getIntent().getExtras().getString("tracking_time_key");
                String time = trackingTime.split(" ")[1];

                if (time.equals("1:30:00")) {
                    meetDateSpinner.setSelection(0, true);
                }
                else if (time.equals("1:55:00")) {
                    meetDateSpinner.setSelection(1, true);
                }
                else {
                    meetDateSpinner.setSelection(0, true);
                }
            }
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
