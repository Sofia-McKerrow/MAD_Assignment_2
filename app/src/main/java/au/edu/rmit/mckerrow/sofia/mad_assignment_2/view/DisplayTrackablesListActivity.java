package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.FilterController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.SuggestTrackingButtonController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.DataSource;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.ReadFile;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackablesListInfo;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.service.SuggestTrackingService;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.service.TestTrackingService;

public class DisplayTrackablesListActivity extends AppCompatActivity {

    private static List<BirdTrackable> trackableList;
    private static Map<String, BirdTrackable> trackableMap;
    private TrackablesListInfo trackablesListInfo;
    private static TrackableAdapter adapter;
    private DataSource mDataSource;
    private RecyclerView recyclerView;
    private Button suggestTracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackables_list);

        updateTrackablesDB();

        if (trackableList != null) {
            trackableList.clear();
        }
        ReadFile.readTrackableFile(this);
        trackableList = ReadFile.getTrackableList();

        // Sort list alphabetically
        Collections.sort(trackableList, new Comparator<BirdTrackable>() {
            @Override
            public int compare(BirdTrackable o1, BirdTrackable o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        adapter = new TrackableAdapter(this, trackableList);

        suggestTracking = (Button) findViewById(R.id.suggestTracking);
        suggestTracking.setOnClickListener(new SuggestTrackingButtonController(this, this));

        recyclerView = (RecyclerView) findViewById(R.id.rvTrackables);
        recyclerView.setAdapter(adapter);

        setUpSpinner();

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(this, SuggestTrackingService.class);

        PendingIntent pending = PendingIntent.getService(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        long trigger = calendar.getTimeInMillis() + (30 * 1000);
        alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, pending);
    }

    // Set category names in spinner
    private void setUpSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.filterSpinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.categories,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        // Stop onItemSelected() in FilterController from being initialised when the app is first started
        spinner.setSelection(0, false);

        spinner.setOnItemSelectedListener(new FilterController(this));
    }

    public static TrackableAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();

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

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    @Override
    public void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras != null) {
            if (extras.containsKey("show_dialog")) {
                Log.i("MyTag", "Boolean " + extras.getString("show_dialog"));
            }
        }
    }

    public void updateTrackablesDB() {
        mDataSource = new DataSource(this);
        mDataSource.open();

        if (trackableList != null) {
            trackableList.clear();
        }
        ReadFile.readTrackableFile(this);
        trackableList = ReadFile.getTrackableList();

        // Insert the data from the trackable list into the trackables table in the database
        mDataSource.seedDatabaseWithTrackables(trackableList);
    }

}