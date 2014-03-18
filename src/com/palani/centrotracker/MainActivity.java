package com.palani.centrotracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.palani.centrotracker.database.DatabaseHelper;
import com.palani.centrotracker.util.Utility;
import com.palani.dataModel.Bus;

public class MainActivity extends Activity {

	private static final Logger logger = Logger.getLogger(MainActivity.class.getName());
	public DatabaseHelper dbh = new DatabaseHelper(this);

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		logger.log(Level.INFO,dbh.getWritableDatabase().getPath());
		
		// Get a handle to the Map Fragment
		final GoogleMap map = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		
		
		map.setBuildingsEnabled(true);
		map.setMyLocationEnabled(true);
		
		UiSettings uiSettings = map.getUiSettings();
		uiSettings.setMyLocationButtonEnabled(true);
		uiSettings.setCompassEnabled(true);
		uiSettings.setAllGesturesEnabled(true);
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, false);
		Location currentLocation = locationManager
				.getLastKnownLocation(provider);
		LatLng myLocation = new LatLng(43.131380, -76.185197);
		if (currentLocation != null) {
			myLocation = new LatLng(currentLocation.getLatitude(),
					currentLocation.getLongitude());
		}

		map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));

		final Map<LatLng, Bus> busStop = new HashMap<LatLng, Bus>();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
		String currentSchedule = sdf.format(new Date());
		boolean taftRoute = false;
		boolean stoneDaleRoute = false;
		try {

			if (sdf.parse(currentSchedule).after(sdf.parse("15:35"))
					&& sdf.parse(currentSchedule).before(sdf.parse("18:30"))) {
				taftRoute = true;
			}

			if ((sdf.parse(currentSchedule).after(sdf.parse("05:00")) && sdf
					.parse(currentSchedule).before(sdf.parse("05:35")))
					|| (sdf.parse(currentSchedule).after(sdf.parse("06:10")) && sdf
							.parse(currentSchedule).before(sdf.parse("07:00")))
					|| (sdf.parse(currentSchedule).after(sdf.parse("08:45")) && sdf
							.parse(currentSchedule).before(sdf.parse("09:30")))
					|| (sdf.parse(currentSchedule).after(sdf.parse("15:00")) && sdf
							.parse(currentSchedule).before(sdf.parse("18:30")))) {
				stoneDaleRoute = true;
			}
		} catch (ParseException cx) {

		}

		Bus a = new Bus();
		a.setName("NorStar Apartments");
		List<Date> stopTime = new ArrayList<Date>();
		SimpleDateFormat tformat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

		try {
			stopTime.add(tformat.parse("05:40"));
			stopTime.add(tformat.parse("06:11"));
			stopTime.add(tformat.parse("06:43"));
			stopTime.add(tformat.parse("07:26"));
			stopTime.add(tformat.parse("09:13"));
			stopTime.add(tformat.parse("12:18"));
			stopTime.add(tformat.parse("15:28"));
			stopTime.add(tformat.parse("16:24"));
			stopTime.add(tformat.parse("17:19"));
			stopTime.add(tformat.parse("18:09"));
		} catch (ParseException e1) {

		}

		a.setSchedules(stopTime);
		busStop.put(new LatLng(43.13137, -76.18511), a);

		Bus b = new Bus();
		b.setName("Stonedale Dr & New Hope East");
		stopTime = new ArrayList<Date>();
		;

		try {
			stopTime.add(tformat.parse("05:31"));
			stopTime.add(tformat.parse("06:46"));
			stopTime.add(tformat.parse("09:16"));
			stopTime.add(tformat.parse("15:31"));
			stopTime.add(tformat.parse("16:27"));
			stopTime.add(tformat.parse("17:22"));
			stopTime.add(tformat.parse("18:12"));
		} catch (ParseException e) {

		}
		b.setSchedules(stopTime);
		if (stoneDaleRoute)
			busStop.put(new LatLng(43.14736, -76.18372), b);

		Bus c = new Bus();
		c.setName("Maltlage & Wetzel");
		stopTime = new ArrayList<Date>();

		try {
			stopTime.add(tformat.parse("05:35"));
			stopTime.add(tformat.parse("06:18"));
			stopTime.add(tformat.parse("06:50"));
			stopTime.add(tformat.parse("07:33"));
			stopTime.add(tformat.parse("09:20"));
			stopTime.add(tformat.parse("12:25"));
			stopTime.add(tformat.parse("15:35"));
			stopTime.add(tformat.parse("16:31"));
			stopTime.add(tformat.parse("17:26"));
			stopTime.add(tformat.parse("18:16"));
		} catch (ParseException e) {

		}
		c.setSchedules(stopTime);
		busStop.put(new LatLng(43.14878, -76.19342), c);

		Bus d = new Bus();
		d.setName("Merril Farms (Harvest & Cedarpost)");
		stopTime = new ArrayList<Date>();

		try {
			stopTime.add(tformat.parse("16:12"));
			stopTime.add(tformat.parse("17:07"));
			stopTime.add(tformat.parse("17:57"));
		} catch (ParseException e) {

		}
		d.setSchedules(stopTime);
		if (taftRoute)
			busStop.put(new LatLng(43.12685, -76.16996), d);

		Bus e = new Bus();
		e.setName("North Medical Center");
		stopTime = new ArrayList<Date>();

		try {
			stopTime.add(tformat.parse("05:22"));
			stopTime.add(tformat.parse("06:02"));
			stopTime.add(tformat.parse("06:34"));
			stopTime.add(tformat.parse("07:17"));
			stopTime.add(tformat.parse("09:04"));
			stopTime.add(tformat.parse("12:09"));
			stopTime.add(tformat.parse("15:19"));
			stopTime.add(tformat.parse("16:09"));
			stopTime.add(tformat.parse("17:04"));
			stopTime.add(tformat.parse("17:54"));
		} catch (ParseException e3) {

		}
		e.setSchedules(stopTime);
		busStop.put(new LatLng(43.11959, -76.16141), e);

		Bus f = new Bus();
		f.setName("7th North & Buckley Rd");
		stopTime = new ArrayList<Date>();

		try {
			stopTime.add(tformat.parse("05:14"));
			stopTime.add(tformat.parse("05:54"));
			stopTime.add(tformat.parse("06:26"));
			stopTime.add(tformat.parse("07:09"));
			stopTime.add(tformat.parse("08:56"));
			stopTime.add(tformat.parse("12:01"));
			stopTime.add(tformat.parse("15:11"));
			stopTime.add(tformat.parse("16:01"));
			stopTime.add(tformat.parse("16:56"));
			stopTime.add(tformat.parse("17:46"));
		} catch (ParseException e4) {

		}
		f.setSchedules(stopTime);
		busStop.put(new LatLng(43.09338, -76.17107), f);

		Bus g = new Bus();
		g.setName("Washington & Franklin St");
		stopTime = new ArrayList<Date>();

		try {
			stopTime.add(tformat.parse("05:08"));
			stopTime.add(tformat.parse("05:48"));
			stopTime.add(tformat.parse("06:20"));
			stopTime.add(tformat.parse("07:03"));
			stopTime.add(tformat.parse("08:50"));
			stopTime.add(tformat.parse("11:55"));
			stopTime.add(tformat.parse("15:05"));
			stopTime.add(tformat.parse("15:55"));
			stopTime.add(tformat.parse("16:50"));
			stopTime.add(tformat.parse("17:40"));
		} catch (ParseException e5) {

		}
		g.setSchedules(stopTime);
		busStop.put(new LatLng(43.04963, -76.15552), g);
		Bus h = new Bus();
		h.setName("S State & Madison St");
		stopTime = new ArrayList<Date>();

		try {
			stopTime.add(tformat.parse("05:05"));
			stopTime.add(tformat.parse("05:45"));
			stopTime.add(tformat.parse("06:17"));
			stopTime.add(tformat.parse("07:00"));
			stopTime.add(tformat.parse("08:47"));
			stopTime.add(tformat.parse("11:52"));
			stopTime.add(tformat.parse("15:02"));
			stopTime.add(tformat.parse("15:52"));
			stopTime.add(tformat.parse("16:47"));
			stopTime.add(tformat.parse("17:37"));
		} catch (ParseException e6) {

		}
		h.setSchedules(stopTime);
		busStop.put(new LatLng(43.04547, -76.14750), h);

		Bus i = new Bus();
		i.setName("Salina & Adams St");
		List<Date> istopTime = new ArrayList<Date>();

		try {
			istopTime.add(tformat.parse("05:03"));
			istopTime.add(tformat.parse("05:43"));
			istopTime.add(tformat.parse("06:15"));
			istopTime.add(tformat.parse("06:58"));
			istopTime.add(tformat.parse("08:45"));
			istopTime.add(tformat.parse("11:50"));
			istopTime.add(tformat.parse("15:00"));
			istopTime.add(tformat.parse("15:50"));
			istopTime.add(tformat.parse("16:45"));
			istopTime.add(tformat.parse("17:35"));
		} catch (ParseException e7) {

		}
		i.setSchedules(istopTime);
		busStop.put(new LatLng(43.04331, -76.15086), i);

		Iterator<Entry<LatLng, Bus>> iterator = busStop.entrySet().iterator();

		while (iterator.hasNext()) {
			Map.Entry<LatLng, Bus> stop = (Map.Entry<LatLng, Bus>) iterator
					.next();
			map.addMarker(new MarkerOptions()

					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.bus_green_icon))
					.anchor(0.0f, 1.0f)
					// Anchors the marker on the bottom left
					.position(stop.getKey()));

		}

		map.setInfoWindowAdapter(new InfoWindowAdapter() {
			Map<LatLng, Bus> localStop = busStop;

			@Override
			public View getInfoWindow(Marker marker) {
				return null;
			}

			@Override
			public View getInfoContents(Marker marker) {

				View infoWindow = getLayoutInflater().inflate(
						R.layout.info_window_layout, null);

				try {
					TextView stopInfo = (TextView) infoWindow.findViewById(R.id.stopInfo);
					TextView scheduleInfo = (TextView) infoWindow.findViewById(R.id.scheduleInfo);
					SimpleDateFormat sdf = new SimpleDateFormat("h:mm a",
							Locale.ENGLISH);

					String stopName = "";
					String formattedTime = "";
					
					LatLng position = marker.getPosition();
					double latitude = Math.round(position.latitude*100000.0)/100000.0;
					double longitude = Math.round(position.longitude*100000.0)/100000.0;
					
					LatLng roundedPosition = new LatLng(latitude,longitude);
					
					
					Bus bus = localStop.get(roundedPosition);					
					
					if (bus != null) {
						stopName += "Stop Name : \t" + bus.getName();

						int count = 0;
						for (Date schedule : bus.getSchedules()) {
							SimpleDateFormat cdf = new SimpleDateFormat(
									"HH:mm", Locale.ENGLISH);

							if (schedule.after(cdf.parse(cdf.format(new Date())))) {
								if (count == 0)
									formattedTime = sdf.format(schedule);
								count = 1;
							}

						}
					} else
						stopName += "Location : \t" + marker.getPosition();

					stopInfo.setText(stopName);
					stopInfo.setTextColor(Color.BLUE);

					String info = "";
					info += "\n Current Time      : " + sdf.format(new Date());
					if (formattedTime.length() > 1)
						info += "\n Next Arrival Time : " + formattedTime;

					scheduleInfo.setText(info);

				} catch (Exception ex) {
					logger.log(Level.SEVERE,"Exception Occured",ex);
				}
				return infoWindow;
			}
		});

		map.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.centro))
				.anchor(0.0f, 1.0f).title("Centro Transit Hub")
				.position(new LatLng(43.04320, -76.15116)));


		PolylineOptions options = new PolylineOptions();
		options.geodesic(true);
		options.color(Color.BLUE);	

		try{
			
			
			Cursor cur;
			logger.log(Level.INFO,"DATABASE NAME = "+dbh.getDatabaseName());
			
			String routeNumber;
			if(taftRoute)
				routeNumber = "186B";
			else if(stoneDaleRoute)
				routeNumber = "186A";
			else
				routeNumber ="286";
			
			cur = dbh.getReadableDatabase().query("route", new String[]{"encodedmap"}, "routenumber=?", new String[]{routeNumber}, null, null, null);
			logger.log(Level.INFO,cur.getCount()+" rows returned  ");
			
			cur.moveToFirst();
			do{
				String encodedMap = cur.getString(0);
				List<LatLng> points  = Utility.decode(encodedMap);
				options.addAll(points);
			}while(cur.moveToNext());
		}catch(Exception ex){
			logger.log(Level.SEVERE,"Exception occured while trying to fetch from database",ex);
		}
	
		
		map.addPolyline(options);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onRestart() {		
		super.onRestart();
		dbh.getReadableDatabase();
	}

	@Override
	protected void onResume() {		
		super.onResume();
		dbh.getReadableDatabase();
	}

	@Override
	protected void onDestroy() {		
		super.onDestroy();
		dbh.close();
	}
	
	
	 

}
