package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.DisplayTrackablesListActivity;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TabWidgetActivity;

public class SuggestTrackingButtonController implements View.OnClickListener{
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
    }

    private void openDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        View view = activity.getLayoutInflater().inflate(R.layout.dialog, null);
        alertDialogBuilder.setView(view);
        final AlertDialog alertDialog = alertDialogBuilder.create();

        addTracking = (Button) view.findViewById(R.id.dialog_add_tracking);
        addTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"You clicked add tracking button",Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        });

        nextTracking = (Button) view.findViewById(R.id.dialog_next_tracking);
        nextTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"You clicked next tracking button",Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        });

        cancel = (Button) view.findViewById(R.id.dialog_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"You clicked cancel button",Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
            }
        });

//        alertDialogBuilder.setTitle("Next tracking available");
//        alertDialogBuilder.setMessage("Tracking goes here");
//
//        alertDialogBuilder.setPositiveButton("Add Tracking", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(mContext,"You clicked add tracking button",Toast.LENGTH_LONG).show();
//            }
//        });
//
//        alertDialogBuilder.setNeutralButton("Next available tracking", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(mContext,"You clicked next tracking button",Toast.LENGTH_LONG).show();
//            }
//        });
//
//        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(mContext,"You clicked cancel button",Toast.LENGTH_LONG).show();
//            }
//        });

        alertDialog.show();
    }
}
