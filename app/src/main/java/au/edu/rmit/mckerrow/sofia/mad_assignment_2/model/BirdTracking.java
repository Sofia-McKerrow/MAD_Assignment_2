package au.edu.rmit.mckerrow.sofia.mad_assignment_2.model;

import java.util.Date;

public class BirdTracking extends AbstractTracking {


    public BirdTracking(String trackingID, String trackableID, String title, String startTime, String finishTime,
                        String meetTime, String currentLocation, String meetLocation) {
        super(trackingID, trackableID, title, startTime, finishTime, meetTime, currentLocation, meetLocation);
    }
}
