package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;

import java.util.Calendar;
import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.DataSource;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.service.SuggestTrackingService;

public class TabWidgetActivity extends android.app.TabActivity {

    private DataSource mDataSource;
    private static List<BirdTrackable> trackableList;

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

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, SuggestTrackingService.class);

        PendingIntent pending = PendingIntent.getService(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        long trigger = calendar.getTimeInMillis() + (30 * 1000);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pending);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Boolean displayDialog = intent.getExtras().getBoolean("show_dialog");

            // Display suggest tracking dialog
            if (displayDialog == true) {
                SuggestTrackingDialog dialog = new SuggestTrackingDialog(this, this);
                dialog.openDialog();
            }
        }
    }
}
