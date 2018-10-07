package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.SuggestTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.DataSource;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.service.SuggestTrackingService;

public class TabWidgetActivity extends android.app.TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        TabHost.TabSpec spec;
        Intent intent;

        // Create a new TabSpec using tab host
        spec = tabHost.newTabSpec("trackables");
        // Set "Bird Trackables" as the indicator
        spec.setIndicator("Trackables");
        // Create an intent to launch an activity for the tab
        intent = new Intent(this, DisplayTrackablesListActivity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        // Create a new TabSpec using tab host
        spec = tabHost.newTabSpec("trackings");
        // Set "Bird Trackables" as the indicator
        spec.setIndicator("Trackings");
        // Create an intent to launch an activity for the tab
        intent = new Intent(this, DisplayTrackingsListActivity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        // Set trackables tab to open when app first opens
        tabHost.setCurrentTab(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
