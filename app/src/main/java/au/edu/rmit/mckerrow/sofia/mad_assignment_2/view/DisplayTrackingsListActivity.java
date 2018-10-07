package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.AddTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.DataSource;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.DurationRetrieval;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingsListInfo;

public class DisplayTrackingsListActivity extends AppCompatActivity {
    private static List<BirdTracking> trackingList;
    private TrackingsListInfo trackingsListInfo;
    private static TrackingAdapter adapter;
    private Button addTracking;
    private DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackings_list);

        mDataSource = new DataSource(this);
        mDataSource.open();

        updateTrackingsDB();
        trackingList = mDataSource.getAllTrackings();
        updateTrackingInfoList(trackingList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvTrackings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new TrackingAdapter(this, trackingList);
        recyclerView.setAdapter(adapter);

        addTracking = (Button) findViewById(R.id.addTracking);
        addTracking.setOnClickListener(new AddTrackingButtonController(this));
    }

    public static TrackingAdapter getAdapter() {
        return adapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // Show the settings screen
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
