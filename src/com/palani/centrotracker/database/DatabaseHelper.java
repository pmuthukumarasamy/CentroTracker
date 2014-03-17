package com.palani.centrotracker.database;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.palani.centrotracker.maputil.MapUtil;
import com.palani.centrotracker.util.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper implements
		DatabaseChangeListener {
	private static final Logger logger = Logger.getLogger(DatabaseHelper.class
			.getName());;
	private static final String DBNAME = "centrotrackerdb";
	private static final int VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DBNAME, null, VERSION);
		logger.log(Level.SEVERE, "Database Name =" + DBNAME + "Version ="
				+ VERSION);
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			logger.log(Level.SEVERE, "Creating Database");

			db.execSQL("CREATE TABLE IF NOT EXISTS ROUTE (ID INTEGER PRIMARY KEY NOT NULL,ROUTENUMBER INTEGER NOT NULL,DIRECTION TEXT,ENCODEDMAP TEXT);");
			
			ContentValues values = new ContentValues();
			values.put("id", 1);
			values.put("routenumber",86);
			values.put("direction", "From Downtown To HenryClay");
			values.put("encodedmap", Utility.encode(MapUtil.DownTownToHenryClay()));
			
			db.insert("ROUTE", null, values);

			logger.log(Level.SEVERE, "Creating Database");
		} catch (Exception dbException) {
			logger.log(Level.SEVERE,
					"Exception occured during database creation");
			logger.log(Level.SEVERE, dbException.getMessage());
			;
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTableChanged(String tableName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTableRowChanged(String tableName, long rowId) {
		// TODO Auto-generated method stub

	}

	

}
