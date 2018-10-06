package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateUtils;
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
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.SuggestTrackingDialog;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.TabWidgetActivity;

public class SuggestTrackingButtonController implements View.OnClickListener {
    private Context mContext;
    private DisplayTrackablesListActivity activity;
    private SuggestTrackingDialog dialog;

    public SuggestTrackingButtonController(Context mContext, DisplayTrackablesListActivity activity) {
        this.mContext = mContext;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        dialog = new SuggestTrackingDialog(mContext, activity);
        dialog.openDialog();
    }
}