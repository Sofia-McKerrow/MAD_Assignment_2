package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackingsListInfo;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.DisplayTrackingsListActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TabWidgetActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TrackingAdapter;

public class RemoveTrackingButtonController implements View.OnClickListener{

    private Context mContext;
    private static List<BirdTracking> trackingList;
    private TrackingsListInfo trackingsListInfo;
    private TrackingAdapter adapter;
    private int position;

    public RemoveTrackingButtonController(Context mContext, int position) {
        this.mContext = mContext;
        this.position = position;
    }

    // Remove selected tracking from trackings list when the Remove button is clicked
    @Override
    public void onClick(View v) {
        removeTracking(position);

        adapter = DisplayTrackingsListActivity.getAdapter();
        adapter.notifyDataSetChanged();

        mContext.startActivity(new Intent(mContext, TabWidgetActivity.class));
    }

    // Remove tracking from trackingList
    public void removeTracking(int position) {
        trackingsListInfo = TrackingsListInfo.getSingletonInstance(mContext);
        trackingList = trackingsListInfo.getTrackingList();

        trackingList.remove(position);
        trackingsListInfo.setTrackingList(trackingList);
    }
}
