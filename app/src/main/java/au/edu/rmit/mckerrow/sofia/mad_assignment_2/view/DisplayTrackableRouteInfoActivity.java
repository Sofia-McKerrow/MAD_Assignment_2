package au.edu.rmit.mckerrow.sofia.mad_assignment_2.view;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.R;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.controller.TrackableAdapter;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.TrackableInfo;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.service.TrackingService;

public class DisplayTrackableRouteInfoActivity extends AppCompatActivity {

    private TextView trackableName;
    private ImageView trackableImage;

    private static List<BirdTrackable> trackableList;
    private static Map<String, BirdTrackable> trackableMap;
    private TrackableInfo trackableInfo;

    private static final String LOG_TAG = TrackingService.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackable_route_info);

        // Check if a trackableInfo singleton has been created
        if (trackableInfo == null) {
            trackableInfo = TrackableInfo.getSingletonInstance(this);
        }

        trackableList = trackableInfo.getTrackableList();
        trackableMap = trackableInfo.getTrackableMap();
        String trackableID = getIntent().getExtras().getString(TrackableAdapter.TRACKABLE_ID_KEY);
        BirdTrackable birdTrackable = trackableMap.get(trackableID);

        trackableName = (TextView) findViewById(R.id.trackableName);
        trackableImage = (ImageView) findViewById(R.id.trackableImage);
        trackableName.setText(birdTrackable.getName());

        InputStream inputStream = null;
        try {
            String imageFile = birdTrackable.getImage();
            inputStream = getAssets().open(imageFile);
            Drawable d = Drawable.createFromStream(inputStream, null);
            trackableImage.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        TrackingService trackingService = TrackingService.getSingletonInstance(this);
        Log.i(LOG_TAG, "Parsed File Contents:");
        trackingService.logAll();
    }
}
