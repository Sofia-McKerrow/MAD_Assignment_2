package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.ReadFile;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingsListInfo;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.AddTrackingActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.DisplayTrackingsListActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TabWidgetActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TrackingAdapter;

public class SaveTrackingButtonController implements View.OnClickListener{

    private Context mContext;
    private static List<BirdTracking> trackingList;
    private TrackingsListInfo trackingsListInfo;
    private BirdTracking tracking;
    private TrackingAdapter adapter;
    private AddTrackingActivity activity;
    private List<BirdTrackable> trackableList;

    public SaveTrackingButtonController(Context mContext, AddTrackingActivity activity) {
        this.mContext = mContext;
        this.activity = activity;
    }

    // Add tracking to the trackings list when the Save button is clicked
    @Override
    public void onClick(View v) {
        addTracking(activity);

        adapter = DisplayTrackingsListActivity.getAdapter();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        mContext.startActivity(new Intent(mContext, TabWidgetActivity.class));
    }

    public void addTracking(AddTrackingActivity activity) {
        trackingsListInfo = TrackingsListInfo.getSingletonInstance(mContext);
        trackingList = trackingsListInfo.getTrackingList();

        if (trackingList == null) {
            trackingList = new ArrayList<BirdTracking>();
        }

        // Give tracking a unique ID number
        String trackingID = UUID.randomUUID().toString();

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
        Spinner meetDateSpinner = (Spinner) activity.findViewById(R.id.meetDateSpinner);
        String meetTime = meetDateSpinner.getSelectedItem().toString();
        String startTime = meetDateSpinner.getSelectedItem().toString();
        String finishTime = meetDateSpinner.getSelectedItem().toString();
        String currentLocation = "-37.820666, 144.958277";
        String meetLocation = "-37.820666, 144.958277";

        tracking = new BirdTracking(trackingID, trackableID, title, startTime, finishTime, meetTime, currentLocation, meetLocation);

        currentLocation = tracking.getCurrentLocation(mContext, Integer.parseInt(trackableID));
        tracking.setCurrentLocation(currentLocation);
        finishTime = tracking.returnFinishTime(trackableID, meetTime);
        tracking.setFinishTime(finishTime);
        meetLocation = tracking.returnMeetLocation(trackableID, meetTime);
        tracking.setMeetLocation(meetLocation);

        trackingList.add(tracking);
        trackingsListInfo.setTrackingList(trackingList);
    }
}
