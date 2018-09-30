package au.edu.rmit.mckerrow.sofia.mad_assignment_2.database;

public class TrackingsTable {

    public static final String TABLE_TRACKINGS = "trackings";
    public static final String COLUMN_TRACKING_ID = "trackingID";
    public static final String COLUMN_TRACKABLE_ID = "trackableID";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_START_TIME = "startTime";
    public static final String COLUMN_FINISH_TIME = "finishTime";
    public static final String COLUMN_MEET_TIME = "meetTime";
    public static final String COLUMN_CURRENT_LOCATION = "currentLocation";
    public static final String COLUMN_MEET_LOCATION = "meetLocation";

    public static final String[] ALL_COLUMNS =
            {COLUMN_TRACKING_ID, COLUMN_TRACKABLE_ID, COLUMN_TITLE, COLUMN_START_TIME, COLUMN_FINISH_TIME, COLUMN_MEET_TIME,
                    COLUMN_CURRENT_LOCATION, COLUMN_MEET_LOCATION};

    // Create trackings table
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_TRACKINGS + "(" +
                    COLUMN_TRACKING_ID + " TEXT PRIMARY KEY," +
                    COLUMN_TRACKABLE_ID + " TEXT," +
                    COLUMN_TITLE + " TEXT," +
                    COLUMN_START_TIME + " TEXT," +
                    COLUMN_FINISH_TIME + " TEXT," +
                    COLUMN_MEET_TIME + " TEXT," +
                    COLUMN_CURRENT_LOCATION + " TEXT," +
                    COLUMN_MEET_LOCATION + " TEXT" + ");";

    // Delete trackings table
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_TRACKINGS;
}
