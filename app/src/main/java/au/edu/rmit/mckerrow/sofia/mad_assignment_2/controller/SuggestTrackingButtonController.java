package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.DurationRetrieval;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackablesListInfo;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.service.TrackingService;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.DisplayTrackablesListActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TabWidgetActivity;

public class SuggestTrackingButtonController implements View.OnClickListener {
    private Context mContext;
    private DisplayTrackablesListActivity activity;
    private TrackablesListInfo trackablesListInfo;
    private List<BirdTrackable> filteredList;
    private Button addTracking;
    private Button nextTracking;
    private Button cancel;
    private String duration;
    private String durationInMins;
    private String durationInSeconds;

    public SuggestTrackingButtonController(Context mContext, DisplayTrackablesListActivity activity) {
        this.mContext = mContext;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        openDialog();

        getDurationValues();

        Log.i("JSON", "Duration " + duration);
//        Log.i("JSON", "Minutes " + durationInMins);
//        Log.i("JSON", "Seconds " + durationInSeconds);
    }

    private void openDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        View view = activity.getLayoutInflater().inflate(R.layout.dialog, null);
        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // Add available tracking to tracking list
        addTracking = (Button) view.findViewById(R.id.dialog_add_tracking);
        addTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"You clicked add tracking button",Toast.LENGTH_LONG).show();
                getAvailableTracking(mContext);
                alertDialog.dismiss();
            }
        });

        // Display next available tracking in dialog
        nextTracking = (Button) view.findViewById(R.id.dialog_next_tracking);
        nextTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"You clicked next tracking button",Toast.LENGTH_LONG).show();
                getNextTracking();
                alertDialog.dismiss();
            }
        });

        // Return to trackables list activity
        cancel = (Button) view.findViewById(R.id.dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"You clicked cancel button",Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    public void getAvailableTracking(Context context) {
        trackablesListInfo = TrackablesListInfo.getSingletonInstance(mContext);
        filteredList = trackablesListInfo.getTrackableList();

        List<TrackingService.TrackingInfo> matched = getMatchedTrackingsFromTrackingService(context);
        for (int i = 0; i < matched.size(); i++) {
            Log.i("MyTag", "Matched " + matched.get(i).toString());
        }


    }

    public void getNextTracking() {

    }

    // Get duration values from distance matrix api request in DurationRetrieval class
    public void getDurationValues() {
        DurationRetrieval dr = new DurationRetrieval(mContext);
        String from = "-37.807425,144.963814";
        String to = "-37.810045,144.964220";
        String key = "AIzaSyCIlpb8-g9K3ogJ0ptkp6_p45DkwuWAd28";
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + from + "&destinations=" + to +
                "&mode=walking&key=" + key;
//        dr.execute(url);
        try {
            duration = dr.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        duration = duration.replace(" mins", "").trim();

//        String res[] = duration.split(",");
//        durationInMins = res[0];
//        durationInSeconds = res[1];
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

    public String getDurationInMins() {
        return durationInMins;
    }

    public void setDurationInMins(String durationInMins) {
        this.durationInMins = durationInMins;
    }

    public String getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(String durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }
}
