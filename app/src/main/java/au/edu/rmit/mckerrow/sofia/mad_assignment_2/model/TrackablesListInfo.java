package au.edu.rmit.mckerrow.sofia.mad_assignment_2.model;

import android.content.Context;

import java.util.List;
import java.util.Map;

public class TrackablesListInfo {

    private static TrackablesListInfo trackablesListInfo;
    private static Context mContext;
    private List<BirdTrackable> trackableList;
    private Map<String, BirdTrackable> trackableMap;

    // Constructor for singleton
    private TrackablesListInfo() {
        // Read data from bird_data.txt
        ReadFile.readTrackableFile(mContext);
        trackableList = ReadFile.getTrackableList();
        trackableMap = ReadFile.getTrackableMap();
    }

    private static class TrackableLazyHolder {
        static final TrackablesListInfo INSTANCE = new TrackablesListInfo();
    }

    // Initialise singleton
    public static TrackablesListInfo getSingletonInstance(Context context) {
        mContext = context;
        return TrackableLazyHolder.INSTANCE;
    }

    public static TrackablesListInfo getTrackablesListInfo() {
        // Check if a singleton object has been created
        if (trackablesListInfo == null) {
            trackablesListInfo = new TrackablesListInfo();
        }
        return trackablesListInfo;
    }

    public List<BirdTrackable> getTrackableList() {
        return trackableList;
    }

    public void setTrackableList(List<BirdTrackable> trackableList) {
        this.trackableList = trackableList;
    }

    public Map<String, BirdTrackable> getTrackableMap() {
        return trackableMap;
    }

    public void setTrackableMap(Map<String, BirdTrackable> trackableMap) {
        this.trackableMap = trackableMap;
    }
}
