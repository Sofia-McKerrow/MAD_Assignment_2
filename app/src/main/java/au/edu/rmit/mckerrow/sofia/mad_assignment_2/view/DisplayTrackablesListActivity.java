package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.FilterController;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.database.DataSource;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.ReadFile;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackableInfo;

public class DisplayTrackablesListActivity extends AppCompatActivity {

    private static List<BirdTrackable> trackableList;
    private static Map<String, BirdTrackable> trackableMap;
    private TrackableInfo trackableInfo;
    private static TrackableAdapter adapter;
    private DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackables_list);

        mDataSource = new DataSource(this);
        mDataSource.open();

        if (trackableList != null) {
            trackableList.clear();
        }
        ReadFile.readTrackableFile(this);
        trackableList = ReadFile.getTrackableList();

        // Insert the data from the trackable list into the trackables table in the database
        mDataSource.seedDatabase(trackableList);

        // Sort list alphabetically
        Collections.sort(trackableList, new Comparator<BirdTrackable>() {
            @Override
            public int compare(BirdTrackable o1, BirdTrackable o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        adapter = new TrackableAdapter(this, trackableList);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvTrackables);
        recyclerView.setAdapter(adapter);

        setUpSpinner();

        // TestTrackingService.test(this);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

}