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

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.DurationRetrieval;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.DisplayTrackablesListActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TabWidgetActivity;

public class SuggestTrackingButtonController implements View.OnClickListener, DurationRetrieval.DataRetrieval{
    private Context mContext;
    private DisplayTrackablesListActivity activity;
    private Button addTracking;
    private Button nextTracking;
    private Button cancel;

    public SuggestTrackingButtonController(Context mContext, DisplayTrackablesListActivity activity) {
        this.mContext = mContext;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        openDialog();

        DurationRetrieval dr = new DurationRetrieval(mContext);
        dr.delegate = this;
        String from = "-37.807425,144.963814";
        String to = "-37.810045,144.964220";
        String key = "AIzaSyCIlpb8-g9K3ogJ0ptkp6_p45DkwuWAd28";
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + from + "&destinations=" + to +
                "&mode=walking&key=" + key;
        dr.execute(url);

//        Log.i("JSON", "Duration " + duration);
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
                alertDialog.dismiss();
            }
        });

        // Display next available tracking in dialog
        nextTracking = (Button) view.findViewById(R.id.dialog_next_tracking);
        nextTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"You clicked next tracking button",Toast.LENGTH_LONG).show();
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

    // Get duration values from distance matrix api request in DurationRetrieval class
    @Override
    public void setDuration(String result) {
        String res[]=result.split(",");
        String durationInMins = res[0];
        String durationInSeconds = res[1];
        Log.i("JSON", "Duration in minutes " + durationInMins);
        Log.i("JSON", "Duration in seconds " + durationInSeconds);
    }
}
