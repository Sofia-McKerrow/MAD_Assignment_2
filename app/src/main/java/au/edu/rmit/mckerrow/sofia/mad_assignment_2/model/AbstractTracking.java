package au.edu.rmit.mckerrow.sofia.mad_assignment_2.model;

import java.util.Date;

public abstract class AbstractTracking {

    private String trackingID;
    private String trackableID;
    private String title;
    private String startTime;
    private String finishTime;
    private String meetTime;
    private String currentLocation;
    private String meetLocation;

    public AbstractTracking(String trackingID, String trackableID, String title, String startTime, String finishTime,
                            String meetTime, String currentLocation, String meetLocation) {
        this.trackingID = trackingID;
        this.trackableID = trackableID;
        this.title = title;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.meetTime = meetTime;
        this.currentLocation = currentLocation;
        this.meetLocation = meetLocation;
    }

    public String getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(String trackingID) {
        this.trackingID = trackingID;
    }

    public String getTrackableID() {
        return trackableID;
    }

    public void setTrackableID(String trackableID) {
        this.trackableID = trackableID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(String meetTime) {
        this.meetTime = meetTime;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getMeetLocation() {
        return meetLocation;
    }

    public void setMeetLocation(String meetLocation) {
        this.meetLocation = meetLocation;
    }

    @Override
    public String toString() {
        return "AbstractTracking{" +
                "trackingID='" + trackingID + '\'' +
                ", trackableID='" + trackableID + '\'' +
                ", title='" + title + '\'' +
                ", startTime=" + startTime +
                ", finishTime=" + finishTime +
                ", meetTime=" + meetTime +
                ", currentLocation='" + currentLocation + '\'' +
                ", meetLocation='" + meetLocation + '\'' +
                '}';
    }

}
