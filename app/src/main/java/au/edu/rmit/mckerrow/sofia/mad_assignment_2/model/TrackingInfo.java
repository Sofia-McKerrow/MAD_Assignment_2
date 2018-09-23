package au.edu.rmit.mckerrow.sofia.mad_assignment_2.model;

import android.content.Context;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.util.Calendar.PM;

public class TrackingInfo {

    private static TrackingInfo trackingInfo;
    private static Context mContext;
    private List<BirdTracking> trackingList;

    // Constructor for singleton
    private TrackingInfo() {

    }

    private static class TrackingLazyHolder {
        static final TrackingInfo INSTANCE = new TrackingInfo();
    }

    // Initialise singleton
    public static TrackingInfo getSingletonInstance(Context context) {
        mContext = context;
        return TrackingLazyHolder.INSTANCE;
    }

    public static TrackingInfo getTrackableInfo() {
        // Check if a singleton object has been created
        if (trackingInfo == null) {
            trackingInfo = new TrackingInfo();
        }
        return trackingInfo;
    }

    public List<BirdTracking> getTrackingList() {
        return trackingList;
    }

    public void setTrackingList(List<BirdTracking> trackingList) {
        this.trackingList = trackingList;
    }

}
