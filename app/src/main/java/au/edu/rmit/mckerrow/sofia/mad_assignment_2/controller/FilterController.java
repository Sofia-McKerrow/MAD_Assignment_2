package au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.ReadFile;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.view.DisplayTrackablesListActivity;

public class FilterController implements AdapterView.OnItemSelectedListener {

    private Context mContext;
    private List<BirdTrackable> trackableList;
    private static List<BirdTrackable> filteredList;
    private String category;
    private TrackableAdapter adapter;

    private static final String LOG_TAG = "MyTag";

    public FilterController(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        adapter = DisplayTrackablesListActivity.getAdapter();

        filteredList = new ArrayList<>();

        if (trackableList != null) {
            trackableList.clear();
        }

        ReadFile.readTrackableFile(mContext);
        trackableList = ReadFile.getTrackableList();

        // Remove duplicate entries from trackableList so only entries with an unique trackableID are in the list
        // Prevents bug where entries display twice when one of the categories in the filter spinner is first selected
        Map<Integer, BirdTrackable> map = new LinkedHashMap<Integer, BirdTrackable>();
        for (BirdTrackable trackable : trackableList) {
            map.put(trackable.getTrackableID(), trackable);
        }
        trackableList.clear();
        trackableList.addAll(map.values());

        // Sort list alphabetically
        Collections.sort(trackableList, new Comparator<BirdTrackable>() {
            @Override
            public int compare(BirdTrackable o1, BirdTrackable o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        switch (position) {
            // If All is selected
            case 0:
                filteredList.addAll(trackableList);
                adapter.notifyDataSetChanged();

                break;
            case 1:
                // If Bird of Prey category is selected
                category = "Bird of Prey";
                filteredList = filterTrackableList(trackableList, category);
                adapter.notifyDataSetChanged();

                Log.i("MyTag", "Bird of prey has been selected");
//                for (int i = 0; i < trackableList.size(); i++) {
//                    Log.i("MyTag", "TrackableList " + trackableList.get(i).getName());
//                }
                break;

            case 2:
                // If Bush Bird category is selected
                category = "Bush Bird";
                filteredList = filterTrackableList(trackableList, category);
                adapter.notifyDataSetChanged();
                break;

            case 3:
                // If Parrot category is selected
                category = "Parrot";
                filteredList = filterTrackableList(trackableList, category);
                adapter.notifyDataSetChanged();
                break;

            case 4:
                // If Sea Bird category is selected
                category = "Sea Bird";
                filteredList = filterTrackableList(trackableList, category);
                adapter.notifyDataSetChanged();
                break;

            case 5:
                // If Water Bird category is selected
                category = "Water Bird";
                filteredList = filterTrackableList(trackableList, category);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Filter trackable list by category
    public List<BirdTrackable> filterTrackableList(List<BirdTrackable> list, String category) {
        List<BirdTrackable> toRemove = new ArrayList<BirdTrackable>();

        // Remove trackables in the list which don't match the category selected
        for (BirdTrackable trackable : list) {
            if (!trackable.getCategory().equals(category)) {
                toRemove.add(trackable);
            }
        }

        list.removeAll(toRemove);

        return list;
    }

}