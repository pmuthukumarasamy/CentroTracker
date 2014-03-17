package com.palani.centrotracker.database;

public interface DatabaseChangeListener {

	   public abstract void onTableChanged(String tableName);

       public abstract void onTableRowChanged(String tableName, long rowId);
}
