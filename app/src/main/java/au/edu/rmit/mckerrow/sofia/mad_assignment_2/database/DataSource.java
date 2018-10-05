package au.edu.rmit.mckerrow.sofia.mad_assignment_2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTrackable;
import au.edu.rmit.mckerrow.sofia.mad_assignment_2.model.BirdTracking;

public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private SQLiteOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public BirdTrackable createTrackable(BirdTrackable trackable) {
        ContentValues values = trackable.toValues();
        mDatabase.insert(TrackablesTable.TABLE_TRACKABLES, null, values);

        return trackable;
    }

    public long getTrackablesCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, TrackablesTable.TABLE_TRACKABLES);
    }

    // Insert trackable items into trackables table
    public void seedDatabaseWithTrackables(List<BirdTrackable> trackableList) {
        long numTrackables = getTrackablesCount();
        // Check if there are already trackables in the trackables table
        if (numTrackables == 0) {
            // Add trackables from trackable list to trackables table in database
            for (BirdTrackable trackable : trackableList) {
                try {
                    createTrackable(trackable);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public BirdTracking createTracking(BirdTracking tracking) {
        ContentValues values = tracking.toValues();
        mDatabase.insert(TrackingsTable.TABLE_TRACKINGS, null, values);

        return tracking;
    }

    public long getTrackingsCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, TrackingsTable.TABLE_TRACKINGS);
    }

    // Insert tracking items into trackings table
    public void seedDatabaseWithTrackings(List<BirdTracking> trackingList) {
        long numTrackings = getTrackingsCount();
        // Add trackings from tracking list to trackings table in database
        for (BirdTracking tracking : trackingList) {
            try {
                createTracking(tracking);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    // Get all trackable items from the trackables table
    public List<BirdTrackable> getAllTrackables(String category) {
        List<BirdTrackable> trackables = new ArrayList<>();
        Cursor cursor = null;

        if (category == null) {
            cursor = mDatabase.query(TrackablesTable.TABLE_TRACKABLES, TrackablesTable.ALL_COLUMNS, null,
                    null, null, null, TrackablesTable.COLUMN_NAME);
        }

        while (cursor.moveToNext()) {
            BirdTrackable trackable = new BirdTrackable();
            trackable.setTrackableID(cursor.getInt(cursor.getColumnIndex(TrackablesTable.COLUMN_TRACKABLE_ID)));
            trackable.setName(cursor.getString(cursor.getColumnIndex(TrackablesTable.COLUMN_NAME)));
            trackable.setDescription(cursor.getString(cursor.getColumnIndex(TrackablesTable.COLUMN_DESCRIPTION)));
            trackable.setUrl(cursor.getString(cursor.getColumnIndex(TrackablesTable.COLUMN_URL)));
            trackable.setCategory(cursor.getString(cursor.getColumnIndex(TrackablesTable.COLUMN_CATEGORY)));
            trackable.setImage(cursor.getString(cursor.getColumnIndex(TrackablesTable.COLUMN_IMAGE)));

            trackables.add(trackable);
        }

        cursor.close();

        return trackables;
    }

    // Get tracking items from trackings table
    public List<BirdTracking> getAllTrackings() {
        List<BirdTracking> trackings = new ArrayList<>();
        Cursor cursor = mDatabase.query(TrackingsTable.TABLE_TRACKINGS, TrackingsTable.ALL_COLUMNS, null,
                null, null, null, null);

        while (cursor.moveToNext()) {
            BirdTracking tracking = new BirdTracking();
            tracking.setTrackingID(cursor.getString(cursor.getColumnIndex(TrackingsTable.COLUMN_TRACKING_ID)));
            tracking.setTrackableID(cursor.getString(cursor.getColumnIndex(TrackingsTable.COLUMN_TRACKABLE_ID)));
            tracking.setTitle(cursor.getString(cursor.getColumnIndex(TrackingsTable.COLUMN_TITLE)));
            tracking.setStartTime(cursor.getString(cursor.getColumnIndex(TrackingsTable.COLUMN_START_TIME)));
            tracking.setFinishTime(cursor.getString(cursor.getColumnIndex(TrackingsTable.COLUMN_FINISH_TIME)));
            tracking.setMeetTime(cursor.getString(cursor.getColumnIndex(TrackingsTable.COLUMN_MEET_TIME)));
            tracking.setCurrentLocation(cursor.getString(cursor.getColumnIndex(TrackingsTable.COLUMN_CURRENT_LOCATION)));
            tracking.setMeetLocation(cursor.getString(cursor.getColumnIndex(TrackingsTable.COLUMN_MEET_LOCATION)));

            trackings.add(tracking);
        }

        return trackings;
    }
}
