package au.edu.rmit.mckerrow.sofia.mad_assignment_2.model;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;

public class ReadFile {

    public static ArrayList<BirdTrackable> trackableList = new ArrayList<>();
    public static Map<String, BirdTrackable> trackableMap = new HashMap<>();
    private static final String LOG_TAG = BirdTrackable.class.getName();

    // Read file with trackable data e.g. bird_data.txt
    public static void readTrackableFile(Context context) {

        if (trackableMap != null) {
            trackableMap.clear();
        }

        try (Scanner scanner = new Scanner(context.getResources().openRawResource(R.raw.bird_data))) {
            String[] values;

            while (scanner.hasNextLine()) {
                // values = scanner.nextLine().split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                int trackableID = 0;
                String id = null;
                String name = null;
                String description = null;
                String url = null;
                String category = null;
                String image = null;

                values = scanner.nextLine().split("\"(,\")*");
                String testID = values[0];
                String[] splitID = testID.split(",");
                id = splitID[0];
                trackableID = Integer.parseInt(id);
                name = values[1];
                description = values[2];
                url = values[3];
                category = values[4];

                if (values.length < 6) {
                        image = "magpie.jpg";
                }
                else {
                    image = values[5];
                }

                BirdTrackable trackableInfo = new BirdTrackable(trackableID, name, description, url, category, image);
                trackableList.add(trackableInfo);
                trackableMap.put(id, trackableInfo);
            }
        } catch (Resources.NotFoundException e) {
            Log.i(LOG_TAG, "File Not Found Exception Caught");
        }
    }

    public static List<BirdTrackable> getTrackableList() {
        return trackableList;
    }

    public static void setTrackableList(ArrayList<BirdTrackable> trackableList) {
        ReadFile.trackableList = trackableList;
    }

    public static Map<String, BirdTrackable> getTrackableMap() {
        return trackableMap;
    }

    public static void setTrackableMap(Map<String, BirdTrackable> trackableMap) {
        ReadFile.trackableMap = trackableMap;
    }
}
