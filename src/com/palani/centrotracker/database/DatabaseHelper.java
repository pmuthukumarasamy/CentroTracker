package com.palani.centrotracker.database;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.palani.centrotracker.maputil.MapUtil;
import com.palani.centrotracker.util.Utility;

public class DatabaseHelper extends SQLiteOpenHelper implements
		DatabaseChangeListener {
	private static final Logger logger = Logger.getLogger(DatabaseHelper.class
			.getName());;
	private static final String DBNAME = "centrotrackerdb";
	private static final int VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DBNAME, null, VERSION);
		logger.log(Level.SEVERE, "Database Name =" + DBNAME + " Version = "
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

			db.beginTransaction();
			db.execSQL("DROP TABLE IF EXISTS ROUTE;");
			db.execSQL("CREATE TABLE IF NOT EXISTS ROUTE (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ROUTENUMBER INTEGER NOT NULL,DIRECTION TEXT,ENCODEDMAP TEXT);");
			
			ContentValues values = new ContentValues();		
			values.put("routenumber",286);
			values.put("direction", "From Downtown To HenryClay");
			values.put("encodedmap", Utility.encode(MapUtil.DownTownToHenryClay()));
			
			logger.log(Level.SEVERE,"Inserting record");
			db.insert("ROUTE", null, values);	
			logger.log(Level.SEVERE,db.rawQuery("SELECT * FROM ROUTE;",null).getCount()+" results added.");
			
			
			
		} catch (Exception dbException) {
			logger.log(Level.SEVERE,
					"Exception occured during database creation");
			logger.log(Level.SEVERE, dbException.getMessage());
			;
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        

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
