package com.palani.centrotracker;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.palani.centrotracker.database.DatabaseHelper;

public class SplashActivity extends Activity {

	private static final Logger logger = Logger.getLogger(SplashActivity.class
			.getName());
	private static int SPLASH_TIME_OUT = 3000;
	public static DatabaseHelper dbh;
	public ProgressBar progressBar;
	public int progressStatus = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		dbh = new DatabaseHelper(getApplicationContext());
		logger.log(Level.INFO, dbh.getWritableDatabase().getPath());

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		new Thread(new Runnable() {
			public void run() {
				while(progressStatus < progressBar.getMax()){	
					progressStatus+=1;
				try{
					Thread.sleep(30);
				}
				catch(InterruptedException ie){
					logger.log(Level.SEVERE,ie.getLocalizedMessage());
				}
				progressBar.setProgress(progressStatus);
				}
			}
		}).start();

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				Intent i = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(i);
				
					
				
				
				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
		
		
	}

}
