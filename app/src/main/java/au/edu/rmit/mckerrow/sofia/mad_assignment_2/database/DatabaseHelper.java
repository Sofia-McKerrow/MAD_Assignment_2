package au.edu.rmit.mckerrow.sofia.mad_assignment_2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_FILE_NAME = "database.db";
    public static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TrackablesTable.SQL_CREATE);
        db.execSQL(TrackingsTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TrackablesTable.SQL_DELETE);
        db.execSQL(TrackingsTable.SQL_DELETE);
        onCreate(db);
    }
}
