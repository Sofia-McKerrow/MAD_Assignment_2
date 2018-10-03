package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.ReadFile;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingsListInfo;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.DisplayTrackingsListActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.EditTrackingActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TabWidgetActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TrackingAdapter;

public class UpdateTrackingButtonController implements View.OnClickListener{
    private Context mContext;
    private static List<BirdTracking> trackingList;
    private TrackingsListInfo trackingsListInfo;
    private BirdTracking tracking;
    private TrackingAdapter adapter;
    private EditTrackingActivity activity;
    private List<BirdTrackable> trackableList;
    private int position;

    public UpdateTrackingButtonController(Context mContext, EditTrackingActivity activity, int position) {
        this.mContext = mContext;
        this.activity = activity;
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        updateTracking(activity, position);

        adapter = DisplayTrackingsListActivity.getAdapter();
        adapter.notifyDataSetChanged();

        mContext.startActivity(new Intent(mContext, TabWidgetActivity.class));
    }

    public void updateTracking(EditTrackingActivity activity, int position) {
        trackingsListInfo = TrackingsListInfo.getSingletonInstance(mContext);
        trackingList = trackingsListInfo.getTrackingList();

        trackingList.remove(position);

        // Get the trackingID of the selected tracking
        String trackingID = activity.getIntent().getExtras().getString("tracking_id_key");

        String trackableID = null;
        Spinner trackableSpinner = (Spinner) activity.findViewById(R.id.editTrackableNameSpinner);
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

        EditText titleValue = (EditText) activity.findViewById(R.id.editTitleEntry);
        String title = titleValue.getText().toString();
        Spinner meetDateSpinner = (Spinner) activity.findViewById(R.id.editMeetDateSpinner);
        String meetTime = meetDateSpinner.getSelectedItem().toString();
        String startTime = meetDateSpinner.getSelectedItem().toString();
        String finishTime = "05/07/2018 1:10:00 PM";
        String currentLocation = "-37.820666, 144.958277";
        String meetLocation = "-37.820666, 144.958277";

        tracking = new BirdTracking(trackingID, trackableID, title, startTime, finishTime, meetTime, currentLocation, meetLocation);

        currentLocation = tracking.getCurrentLocation(mContext, Integer.parseInt(trackableID));
        tracking.setCurrentLocation(currentLocation);
        finishTime = tracking.returnFinishTime(trackableID, meetTime);
        tracking.setFinishTime(finishTime);
        meetLocation = tracking.returnMeetLocation(trackableID, meetTime);
        tracking.setMeetLocation(meetLocation);

        trackingList.add(position, tracking);
        trackingsListInfo.setTrackingList(trackingList);
    }
}
