package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.CRUD;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.ReadFile;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingInfo;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.AddTrackingActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.DisplayTrackingsListActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TabWidgetActivity;

public class SaveTrackingButtonController implements View.OnClickListener{

    private Context mContext;
    private static List<BirdTracking> trackingList;
    private TrackingInfo trackingInfo;
    private BirdTracking tracking;
    private TrackingAdapter adapter;
    private AddTrackingActivity activity;
    private List<BirdTrackable> trackableList;
    private int position;
    private CRUD crud;

    public SaveTrackingButtonController(Context mContext, AddTrackingActivity activity) {
        this.mContext = mContext;
        this.activity = activity;
    }

    // Add tracking to the trackings list when the Save button is clicked
    @Override
    public void onClick(View v) {
        updateTrackingList(activity);

        adapter = DisplayTrackingsListActivity.getAdapter();
        adapter.notifyDataSetChanged();

        mContext.startActivity(new Intent(mContext, TabWidgetActivity.class));
    }

    public void updateTrackingList(AddTrackingActivity activity) {
        trackingInfo = TrackingInfo.getSingletonInstance(mContext);
        trackingList = trackingInfo.getTrackingList();

        if (trackingList == null) {
            trackingList = new ArrayList<BirdTracking>();
        }

        String trackingID = "tr" + trackingList.size() + 1;

        String trackableID = null;
        Spinner trackableSpinner = (Spinner) activity.findViewById(R.id.trackableNameSpinner);
        String trackableName = trackableSpinner.getSelectedItem().toString();

        if (trackableList != null) {
            trackableList.clear();
        }
        ReadFile.readTrackableFile(mContext);
        trackableList = ReadFile.getTrackableList();

        // Get trackable ID from the selected trackable name
        for (int i = 0; i < trackableList.size(); i++) {
            if (trackableName.equals(trackableList.get(i).getName())) {
                trackableID = Integer.toString(trackableList.get(i).getTrackableID());
                break;
            }
        }

        EditText titleValue = (EditText) activity.findViewById(R.id.titleEntry);
        String title = titleValue.getText().toString();
        String startTime = "05/07/2018 1:05:00 PM";
        String finishTime = "05/07/2018 1:10:00 PM";
        Spinner meetDateSpinner = (Spinner) activity.findViewById(R.id.meetDateSpinner);
        String meetTime = meetDateSpinner.getSelectedItem().toString();
        String currentLocation = "-37.820666, 144.958277";
        String meetLocation = "-37.820666, 144.958277";

        tracking = new BirdTracking(trackingID, trackableID, title, startTime, finishTime, meetTime, currentLocation, meetLocation);

        trackingList.add(tracking);
        trackingInfo.setTrackingList(trackingList);
    }

    public void editTracking(AddTrackingActivity activity) {
        String trackingID = activity.getIntent().getExtras().getString(TrackingAdapter.TRACKING_ID_KEY);
    }
}
