package au.edu.rmit.mckerrow.sofia.mad_assignment_2.model;

import java.util.List;

// Class for add, remove and update tracking methods
public class CRUD {
    private List<BirdTracking> trackingList;

    public CRUD(List<BirdTracking> trackingList) {
        this.trackingList = trackingList;
    }

    // Add tracking to trackingList
    public boolean addTracking(BirdTracking birdTracking) {
        try {
            trackingList.add(birdTracking);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update selected tracking
    public boolean updateTracking(int position, BirdTracking selectedTracking) {
        try {
            trackingList.remove(position);
            trackingList.add(position, selectedTracking);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Remove selected tracking
    public boolean removeTracking(int pos) {
        try {
            trackingList.remove(pos);

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<BirdTracking> getTrackingList() {
        return trackingList;
    }
}
