package au.edu.rmit.mckerrow.sofia.mad_assignment_2.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.TrackablesTable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.TrackingsTable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.service.TrackingService;

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

    public AbstractTracking() {

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

    public String getCurrentLocation(Context context, int trackableID) {
        List<TrackingService.TrackingInfo> matched = getMatchedTrackingsFromTrackingService(context);

        // Remove trackings from matched list which don't match trackable ID of selected tracking
        for (int i = 0; i < matched.size(); i++) {
            String match = matched.get(i).toString();
            match = match.replace(" trackableId=", "");
            String tempID = match.split(",")[1];
            int id = Integer.parseInt(tempID);

            if (id != trackableID) {
                matched.remove(i);
            }
        }

        List<Date> dates = getDatesFromTrackingService(context, matched);
//        Date currentDate = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm:ss a", Locale.ENGLISH);
//        String strCurrentDate = sdf.format(currentDate);
//        Log.i("MyTag", "strCurrentDate " + strCurrentDate);
        Date targetDate = null;
//        try {
//            targetDate = sdf.parse(strCurrentDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        // Get current time
        try {
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            String searchDate = "05/07/2018 1:05:00 PM";
            targetDate = dateFormat.parse(searchDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("MyTag", "targetDate " + targetDate);

        // Get date closest to current time
        Date nearestDate = null;
        int index = 0;
        long prevDiff = -1;
        long targetTS = targetDate.getTime();
        for (int i = 0; i < dates.size(); i++) {
            Date temp = dates.get(i);
            long currDiff = Math.abs(temp.getTime() - targetTS);
            if (prevDiff == -1 || currDiff < prevDiff) {
                prevDiff = currDiff;
                nearestDate = temp;
            }
        }
        Log.i("MyTag", "nearestDate " + nearestDate);

        // Get location of tracking with date closest to current time
        for (int i = 0; i < matched.size(); i++) {
            String match = matched.get(i).toString();
            match = match.replace("Date/Time=", "");
            String strDate = match.split(",")[0];

            Date tempDate = null;

            try {
                tempDate = new SimpleDateFormat("M/d/yy h:mm:ss a", Locale.ENGLISH).parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Get latitude and longitude for tracking with date closest to current time
            if (tempDate.compareTo(nearestDate) == 0) {
                String latitude = match.split(",")[3];
                latitude = latitude.replace("lat=", "");
                String longitude = match.split(",")[4];
                longitude = longitude.replace("long=", "");
                currentLocation = latitude + ", " + longitude;
                break;
            }
        }

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

    public ContentValues toValues() {
        ContentValues values = new ContentValues(8);

        values.put(TrackingsTable.COLUMN_TRACKING_ID, trackingID);
        values.put(TrackingsTable.COLUMN_TRACKABLE_ID, trackableID);
        values.put(TrackingsTable.COLUMN_TITLE, title);
        values.put(TrackingsTable.COLUMN_START_TIME, startTime);
        values.put(TrackingsTable.COLUMN_FINISH_TIME, finishTime);
        values.put(TrackingsTable.COLUMN_MEET_TIME, meetTime);
        values.put(TrackingsTable.COLUMN_CURRENT_LOCATION, currentLocation);
        values.put(TrackingsTable.COLUMN_MEET_LOCATION, meetLocation);

        return values;
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

    // Get tracking items from TrackingService which are within 5 minutes of current time
    public List<TrackingService.TrackingInfo> getMatchedTrackingsFromTrackingService(Context context) {
        TrackingService trackingService = TrackingService.getSingletonInstance(context);
        List<TrackingService.TrackingInfo> matched = null;

        try
        {
            // 5 mins either side of current time
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            String searchDate = "05/07/2018 1:05:00 PM";
            int searchWindow = 5; // +/- 5 mins
            Date date = dateFormat.parse(searchDate);
            matched = trackingService.getTrackingInfoForTimeRange(date, searchWindow, 0);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        // 5 mins either side of current time
//        Date date = new Date();
//        Log.i("MyTag", "date " + date);
//        int searchWindow = 5; // +/- 5 mins
//        matched = trackingService.getTrackingInfoForTimeRange(date, searchWindow, 0);

        for (int i = 0; i < matched.size(); i++) {
            Log.i("MyTag", "matched " + matched.get(i).toString());
        }

        return matched;
    }

    // Get dates for matched trackings from TrackingService
    public List<Date> getDatesFromTrackingService(Context context, List<TrackingService.TrackingInfo> matchedTrackings) {
        List<Date> dates = new ArrayList<Date>();
        Date date = null;
        matchedTrackings = getMatchedTrackingsFromTrackingService(context);

        for (int i = 0; i < matchedTrackings.size(); i++) {
            String match = matchedTrackings.get(i).toString();
            match = match.replace("Date/Time=", "");
            match = match.split(",")[0];

            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm:ss a", Locale.ENGLISH);

            try {
                date = sdf.parse(match);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dates.add(date);
        }

        return dates;
    }

    // Get latitudes for matched trackings from TrackingService
    public List<String> getLatitudesFromTrackingService(Context context, List<TrackingService.TrackingInfo> matchedTrackings) {
        List<String> latitudes = new ArrayList<String>();
        String latitude = null;
        matchedTrackings = getMatchedTrackingsFromTrackingService(context);

        for (int i = 0; i < matchedTrackings.size(); i++) {
            String match = matchedTrackings.get(i).toString();
            latitude = match.split(",")[3];
            latitudes.add(latitude);
        }

        return latitudes;
    }

    // Get longitudes for matched trackings from TrackingService
    public List<String> getLongitudesFromTrackingService(Context context, List<TrackingService.TrackingInfo> matchedTrackings) {
        List<String> longitudes = new ArrayList<String>();
        String longitude = null;
        matchedTrackings = getMatchedTrackingsFromTrackingService(context);

        for (int i = 0; i < matchedTrackings.size(); i++) {
            String match = matchedTrackings.get(i).toString();
            longitude = match.split(",")[4];
            longitudes.add(longitude);
        }

        return longitudes;
    }


}
