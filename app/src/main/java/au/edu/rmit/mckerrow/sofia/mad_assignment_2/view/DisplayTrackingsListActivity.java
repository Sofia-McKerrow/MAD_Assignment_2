package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.AddTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingInfo;

public class DisplayTrackingsListActivity extends AppCompatActivity {
    private static List<BirdTracking> trackingList;
    private TrackingInfo trackingInfo;
    private static TrackingAdapter adapter;
    private Button addTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackings_list);

        // Check if a trackableInfo singleton has been created
        if (trackingInfo == null) {
            trackingInfo = TrackingInfo.getSingletonInstance(this);
        }

        trackingList = trackingInfo.getTrackingList();

        if (trackingList == null) {
            trackingList = new ArrayList<BirdTracking>();
        }

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
}
