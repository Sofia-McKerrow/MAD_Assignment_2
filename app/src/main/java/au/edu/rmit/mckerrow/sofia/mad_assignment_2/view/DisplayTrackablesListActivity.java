package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.FilterController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.SuggestTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.DataSource;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.ReadFile;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackablesListInfo;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingsListInfo;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.service.TestTrackingService;

public class DisplayTrackablesListActivity extends AppCompatActivity {

    private static List<BirdTrackable> trackableList;
    private static List<BirdTracking> trackingList;
    private static Map<String, BirdTrackable> trackableMap;
    private TrackablesListInfo trackablesListInfo;
    private TrackingsListInfo trackingsListInfo;
    private static TrackableAdapter adapter;
    private DataSource mDataSource;
    private RecyclerView recyclerView;
    private Button suggestTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackables_list);

        mDataSource = new DataSource(this);
        mDataSource.open();

        updateTrackablesDB();

        if (trackableList != null) {
            trackableList.clear();
        }
        ReadFile.readTrackableFile(this);
        trackableList = ReadFile.getTrackableList();

        // Sort list alphabetically
        Collections.sort(trackableList, new Comparator<BirdTrackable>() {
            @Override
            public int compare(BirdTrackable o1, BirdTrackable o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        updateTrackingsDB();
        trackingList = mDataSource.getAllTrackings();
        updateTrackingInfoList(trackingList);

        adapter = new TrackableAdapter(this, trackableList);

        suggestTracking = (Button) findViewById(R.id.suggestTracking);
        suggestTracking.setOnClickListener(new SuggestTrackingButtonController(this, this));

        recyclerView = (RecyclerView) findViewById(R.id.rvTrackables);
        recyclerView.setAdapter(adapter);

        setUpSpinner();
    }

    // Set category names in spinner
    private void setUpSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.filterSpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.categories,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        // Stop onItemSelected() in FilterController from being initialised when the app is first started
        spinner.setSelection(0, false);

        spinner.setOnItemSelectedListener(new FilterController(this));
    }

    public static TrackableAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    public void updateTrackablesDB() {
        if (trackableList != null) {
            trackableList.clear();
        }
        ReadFile.readTrackableFile(this);
        trackableList = ReadFile.getTrackableList();

        // Insert the data from the trackable list into the trackables table in the database
        mDataSource.seedDatabaseWithTrackables(trackableList);
    }

    public void updateTrackingsDB() {
        // Check if a trackingsListInfo singleton has been created
        if (trackingsListInfo == null) {
            trackingsListInfo = TrackingsListInfo.getSingletonInstance(this);
        }

        trackingList = trackingsListInfo.getTrackingList();

        if (trackingList == null) {
            trackingList = new ArrayList<BirdTracking>();
        }

        // Insert the data from the tracking list into the trackings table in the database
        mDataSource.seedDatabaseWithTrackings(trackingList);
    }

    public void updateTrackingInfoList(List<BirdTracking> trackings) {
        trackings = mDataSource.getAllTrackings();

        // Check if a trackingsListInfo singleton has been created
        if (trackingsListInfo == null) {
            trackingsListInfo = TrackingsListInfo.getSingletonInstance(this);
        }
        // Set the tracking list to the trackingsListInfo singleton
        trackingsListInfo.setTrackingList(trackings);
    }

}