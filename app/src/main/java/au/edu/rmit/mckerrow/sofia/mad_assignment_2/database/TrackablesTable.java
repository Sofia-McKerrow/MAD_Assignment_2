package au.edu.rmit.mckerrow.sofia.mad_assignment_2.database;

public class TrackablesTable {

    public static final String TABLE_TRACKABLES = "trackables";
    public static final String COLUMN_TRACKABLE_ID = "trackableID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_IMAGE = "image";

    // Create trackables table
    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_TRACKABLES + "(" +
                    COLUMN_TRACKABLE_ID + " TEXT PRIMARY KEY," +
                    COLUMN_NAME + " TEXT," +
                    COLUMN_DESCRIPTION + " TEXT," +
                    COLUMN_URL + " TEXT," +
                    COLUMN_CATEGORY + " TEXT," +
                    COLUMN_IMAGE + " TEXT" + ");";

    // Delete trackables table
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_TRACKABLES;
}
