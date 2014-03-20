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

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final Logger logger = Logger.getLogger(DatabaseHelper.class.getName());;
	public static final String DBNAME = "centrotrackerdb";
	public static int VERSION = 1;

	public DatabaseHelper(Context context) {
		super(context, DBNAME, null, VERSION);
		logger.log(Level.INFO, "Database Name = "+DBNAME+" Version = "+VERSION);
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			
			logger.log(Level.INFO,"Beginning Transaction");
			db.execSQL("DROP TABLE IF EXISTS ROUTE;");
			db.execSQL("CREATE TABLE IF NOT EXISTS ROUTE (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ROUTENUMBER VARCHAR NOT NULL,DIRECTION TEXT,ENCODEDMAP TEXT);");
			
			ContentValues values = new ContentValues();		
			values.put("routenumber","286");
			values.put("direction", "OUTBOUND");
			values.put("encodedmap", Utility.encode(MapUtil.route286OutBound()));			
			logger.log(Level.INFO,"Inserting record");
			db.insert("ROUTE", null, values);		
			
			values = new ContentValues();
			values.put("routenumber", "186A");
			values.put("direction", "OUTBOUND");
			values.put("encodedMap", Utility.encode(MapUtil.route186AOutBound()));
			db.insert("ROUTE", null, values);
			
			
			values = new ContentValues();
			values.put("routenumber","186B");
			values.put("direction","OUTBOUND");
			values.put("encodedMap",Utility.encode(MapUtil.route186BOutBound()));
			db.insert("ROUTE", null, values);
			
			logger.log(Level.INFO,db.rawQuery("SELECT * FROM ROUTE;",null).getCount()+" records added.");
			
			
			
		} catch (Exception dbException) {
			logger.log(Level.SEVERE,"Exception occured during database creation");
			logger.log(Level.SEVERE, dbException.getMessage());			
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    if(oldVersion > 0 && oldVersion < newVersion && VERSION == oldVersion)    
	    {
	    	VERSION = newVersion;
	    	onCreate(db);
	    }

	}
	
	

}
