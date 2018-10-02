package au.edu.rmit.mckerrow.sofia.mad_assignment_2.model;

import android.content.Context;

import java.util.List;

public class TrackingsListInfo {

    private static TrackingsListInfo trackingsListInfo;
    private static Context mContext;
    private List<BirdTracking> trackingList;

    // Constructor for singleton
    private TrackingsListInfo() {

    }

    private static class TrackingLazyHolder {
        static final TrackingsListInfo INSTANCE = new TrackingsListInfo();
    }

    // Initialise singleton
    public static TrackingsListInfo getSingletonInstance(Context context) {
        mContext = context;
        return TrackingLazyHolder.INSTANCE;
    }

    public static TrackingsListInfo getTrackableInfo() {
        // Check if a singleton object has been created
        if (trackingsListInfo == null) {
            trackingsListInfo = new TrackingsListInfo();
        }
        return trackingsListInfo;
    }

    public List<BirdTracking> getTrackingList() {
        return trackingList;
    }

    public void setTrackingList(List<BirdTracking> trackingList) {
        this.trackingList = trackingList;
    }

}
