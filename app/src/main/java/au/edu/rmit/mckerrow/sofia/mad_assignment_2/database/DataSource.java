package au.edu.rmit.mckerrow.sofia.mad_assignment_2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        // Check if there are already trackings in the trackings table
        if (numTrackings == 0) {
            // Add trackings from tracking list to trackings table in database
            for (BirdTracking tracking : trackingList) {
                try {
                    createTracking(tracking);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
