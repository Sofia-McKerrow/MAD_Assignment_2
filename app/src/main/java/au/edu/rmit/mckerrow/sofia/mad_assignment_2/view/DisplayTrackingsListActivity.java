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
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.EditTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.CRUD;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingInfo;

public class DisplayTrackingsListActivity extends AppCompatActivity {
    private static List<BirdTracking> trackingList;
    private TrackingInfo trackingInfo;
    private static TrackingAdapter adapter;
    private Button addTracking;
    private Button editTracking;
    private CRUD crud;

    private static final String LOG_TAG = "DisplayTag";

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
//        for (int i = 0; i < trackingList.size(); i++) {
//            Log.i(LOG_TAG, "Tracking List " + trackingList.get(i).toString());
//        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvTrackings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new TrackingAdapter(this, trackingList);
        crud = new CRUD(trackingList);

        recyclerView.setAdapter(adapter);

        addTracking = (Button) findViewById(R.id.addTracking);
        addTracking.setOnClickListener(new AddTrackingButtonController(this));

//        editTracking = (Button) findViewById(R.id.editTracking);
//        if (editTracking != null) {
//            editTracking.setOnClickListener(new EditTrackingButtonController(this));
//        }
    }

    public static TrackingAdapter getAdapter() {
        return adapter;
    }
}
