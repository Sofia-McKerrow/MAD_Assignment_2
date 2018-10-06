package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.DurationRetrieval;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackablesListInfo;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.service.TrackingService;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.AddTrackingActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.DisplayTrackablesListActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TabWidgetActivity;

public class SuggestTrackingButtonController implements View.OnClickListener {
    private Context mContext;
    private DisplayTrackablesListActivity activity;
    private TrackablesListInfo trackablesListInfo;
    private List<BirdTrackable> filteredList;
    private List<TrackingService.TrackingInfo> availableTrackings;
    private TextView nextTrackingDetails;
    private Button addTracking;
    private Button nextTracking;
    private Button cancel;
    private String duration;
    private int count;
    public static final String TRACKABLE_NAME_KEY = "trackable_name_key";
    public static final String TRACKING_TIME_KEY = "tracking_time_key";

    public SuggestTrackingButtonController(Context mContext, DisplayTrackablesListActivity activity) {
        this.mContext = mContext;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        openDialog();
    }

    private void openDialog() {
        count = 0;
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        View view = activity.getLayoutInflater().inflate(R.layout.dialog, null);
        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        String trackingDetails = getAvailableTrackings(mContext);

        if (trackingDetails != null) {
            final String trackableName = trackingDetails.split(",")[0];
            final String trackingTime = trackingDetails.split(",")[1];

            nextTrackingDetails = (TextView) view.findViewById(R.id.next_tracking_details);
            nextTrackingDetails.setText(trackableName + " " + trackingTime);

            // Add available tracking to tracking list
            addTracking = (Button) view.findViewById(R.id.dialog_add_tracking);
            addTracking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddTrackingActivity.class);
                    intent.putExtra(TRACKABLE_NAME_KEY, trackableName);
                    intent.putExtra(TRACKING_TIME_KEY, trackingTime);
                    mContext.startActivity(intent);

                    alertDialog.dismiss();
                }
            });

            // Display next available tracking in dialog
            nextTracking = (Button) view.findViewById(R.id.dialog_next_tracking);
            nextTracking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getNextTracking();
                    alertDialog.dismiss();
                }
            });

            // Return to trackables list activity
            cancel = (Button) view.findViewById(R.id.dialog_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        }
        else {
            Toast.makeText(mContext,"No suggested trackings available",Toast.LENGTH_LONG).show();
        }
    }

    public String getAvailableTrackings(Context context) {
        String details = null;
        availableTrackings = new ArrayList<>();
        trackablesListInfo = TrackablesListInfo.getSingletonInstance(mContext);
        filteredList = trackablesListInfo.getTrackableList();

        Map<Integer, BirdTrackable> map = new LinkedHashMap<Integer, BirdTrackable>();
        for (BirdTrackable trackable : filteredList) {
            map.put(trackable.getTrackableID(), trackable);
        }
        filteredList.clear();
        filteredList.addAll(map.values());

        List<TrackingService.TrackingInfo> matched = getMatchedTrackingsFromTrackingService(context);

        // Remove items from matched list where stop time = 0
        removeItemsFromMatchedList(matched);

        // Get trackable IDs of items in filtered list
        List<Integer> trackableIDs = getTrackableIDsFromFilteredList(filteredList);

        // Get items from matched list which match trackable ID of items in trackables list
        List<TrackingService.TrackingInfo> trackings = getItemsWithMatchingTrackableID(matched, trackableIDs);

        // Get trackings which can be reached during its stationary period from current location
        availableTrackings = getTrackingWhichCanBeReachedInTime(trackings);

        // Get first available tracking
        if (availableTrackings.size() != 0) {
            TrackingService.TrackingInfo tracking = availableTrackings.get(count);
            count++;

            String trackableID = tracking.toString();
            trackableID = trackableID.replace(" trackableId=", "");
            trackableID = trackableID.split(",")[1];
            String trackableName = null;
            if (trackableID.equals("1")) {
                trackableName = "Australian Magpie";
            }
            else if (trackableID.equals("2")) {
                trackableName = "Kookaburra";
            }
            else if (trackableID.equals("3")) {
                trackableName = "Sulphur-Crested Cockatoo";
            }

            String trackingTime = tracking.toString();
            trackingTime = trackingTime.replace("Date/Time=", "");
            trackingTime = trackingTime.split(",")[0];

            details = trackableName + "," + trackingTime;
        }

        return details;

    }

    public void getNextTracking() {

    }

    // Get duration values from distance matrix api request in DurationRetrieval class
    public String getDurationValues(String from, String to) {
        DurationRetrieval dr = new DurationRetrieval(mContext);
        String key = "AIzaSyCIlpb8-g9K3ogJ0ptkp6_p45DkwuWAd28";
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + from + "&destinations=" + to +
                "&mode=walking&key=" + key;

        try {
            duration = dr.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        duration = duration.replace(" mins", "").trim();
        return duration;
    }

    // Get trackable IDs of items in filtered list
    public List<Integer> getTrackableIDsFromFilteredList(List<BirdTrackable> filtered) {
        List<Integer> trackableIDs = new ArrayList<>();
        for (BirdTrackable trackable : filtered) {
            int id = trackable.getTrackableID();
            trackableIDs.add(id);
        }

        return trackableIDs;
    }

    // Remove items from matched list where stop time = 0
    public void removeItemsFromMatchedList(List<TrackingService.TrackingInfo> matchedList) {
        for (int i = 0; i < matchedList.size(); i++) {
            String match = matchedList.get(i).toString();
            match = match.replace(" stopTime=", "");
            String stopTime = match.split(",")[2];
            if (stopTime.equals("0")) {
                matchedList.remove(i);
                i--;
            }
        }
    }

    public List<TrackingService.TrackingInfo> getItemsWithMatchingTrackableID(List<TrackingService.TrackingInfo> matchedList, List<Integer> ids) {
        List<TrackingService.TrackingInfo> trackings = new ArrayList<>();

        for (int i = 0; i < matchedList.size(); i++) {
            String match = matchedList.get(i).toString();
            match = match.replace(" trackableId=", "");
            String tempID = match.split(",")[1];
            int id = Integer.parseInt(tempID);

            for (int tID : ids) {
                if (id == tID) {
                    trackings.add(matchedList.get(i));
                }
            }
        }

        return trackings;
    }

    // Get trackings can be reached during its stationary period from current location
    public List<TrackingService.TrackingInfo> getTrackingWhichCanBeReachedInTime(List<TrackingService.TrackingInfo> matchedList) {
        List<TrackingService.TrackingInfo> inTimeTrackings = new ArrayList<>();
        for (int i = 0; i < matchedList.size(); i++) {
            String match = matchedList.get(i).toString();
            String latitude = match.split(",")[3];
            latitude = latitude.replace("lat=", "");
            String longitude = match.split(",")[4];
            longitude = longitude.replace("long=", "");
            String trackingLocation = latitude + "," + longitude;
            trackingLocation = trackingLocation.replace(" ", "").trim();

            // Get current location
            String currentLocation = "-37.807425,144.963814";
            // Get duration value from current location to location for each tracking
            duration = getDurationValues(currentLocation, trackingLocation);
            int durationMins = Integer.parseInt(duration);

            // Get time of arrival at tracking location based on how long it will take to get from current location
            Calendar c1 = Calendar.getInstance();
            c1.add(Calendar.MINUTE, durationMins);
            Date arrivalTime = c1.getTime();

            // Get time when tracking arrives at its location
            Date startTime = null;
            match = match.replace("Date/Time=", "");
            match = match.split(",")[0];

            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm:ss a", Locale.ENGLISH);

            try {
                startTime = sdf.parse(match);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Get time when tracking leaves its location
            match = matchedList.get(i).toString();
            match = match.replace(" stopTime=", "");
            String stopTime = match.split(",")[2];
            int mins = Integer.parseInt(stopTime);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(startTime);
            c2.add(Calendar.MINUTE, mins);
            Date finishTime = c2.getTime();

            // If the tracking can be reached before it leaves, add it to the available trackings list
            if (arrivalTime.before(finishTime)) {
                inTimeTrackings.add(matchedList.get(i));
            }
        }

        return inTimeTrackings;
    }

    // Get tracking items from TrackingService which are within 60 minutes of current time
    public List<TrackingService.TrackingInfo> getMatchedTrackingsFromTrackingService(Context context) {
        TrackingService trackingService = TrackingService.getSingletonInstance(context);
        List<TrackingService.TrackingInfo> matched = null;

        try
        {
            // 5 mins either side of current time
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM);
            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm:ss a", Locale.ENGLISH);
            String searchDate = sdf.format(new Date());
            int searchWindow = 60; // +/- 60 mins
            Date date = dateFormat.parse(searchDate);
            matched = trackingService.getTrackingInfoForTimeRange(date, searchWindow, 0);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }


        return matched;
    }
}
