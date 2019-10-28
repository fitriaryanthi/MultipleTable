package com.example.dosen.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.dosen.Util.Config;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper databaseHelper;

    static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (databaseHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (databaseHelper == null) {
                    databaseHelper = new DatabaseHelper(context);
                }
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String CREATE_PRODI_TABLE = "CREATE TABLE " + Config.TABLE_PRODI + "("
                + Config.COLUMN_PRODI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_PRODI_FULL_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_PRODI_NAME + " INTEGER NOT NULL "
                + ")";

        String CREATE_DOSEN_TABLE = "CREATE TABLE " + Config.TABLE_DOSEN + "("
                + Config.COLUMN_DOSEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_REGISTRATION_NUMBER + " INTEGER NOT NULL, "
                + Config.COLUMN_DOSEN_NIP + " TEXT NOT NULL UNIQUE, "
                + Config.COLUMN_DOSEN_NAME + " TEXT, "
                + Config.COLUMN_DOSEN_PHONE + " TEXT, " //nullable
                + Config.COLUMN_DOSEN_EMAIL + " TEXT, " //nullable
                + "FOREIGN KEY (" + Config.COLUMN_REGISTRATION_NUMBER + ") REFERENCES " + Config.TABLE_PRODI + "(" + Config.COLUMN_PRODI_ID + ") ON UPDATE CASCADE ON DELETE CASCADE "
//                + "CONSTRAINT " + Config.PRODI_SUB_CONSTRAINT + " UNIQUE (" + Config.COLUMN_REGISTRATION_NUMBER + "," + Config.COLUMN_STUDENT_NIM + ")"
                + ")";

        db.execSQL(CREATE_PRODI_TABLE);
        db.execSQL(CREATE_DOSEN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_DOSEN);
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_PRODI);

        // Create tables again
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }
}
