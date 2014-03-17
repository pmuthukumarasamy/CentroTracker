package com.palani.centrotracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
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

	private static final Logger LOGGER = Logger.getLogger("Centro Tracker"
			+ MainActivity.class.getName());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DatabaseHelper dbh = new DatabaseHelper(this);
		LOGGER.log(Level.SEVERE,dbh.getWritableDatabase().getPath());
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
					TextView lat = (TextView) infoWindow.findViewById(R.id.lat);
					TextView lon = (TextView) infoWindow.findViewById(R.id.lon);
					SimpleDateFormat sdf = new SimpleDateFormat("h:mm a",
							Locale.ENGLISH);

					String name = "";
					String t = "";
					
					LatLng position = marker.getPosition();
					double latitude = Math.round(position.latitude*100000.0)/100000.0;
					double longitude = Math.round(position.longitude*100000.0)/100000.0;
					
					LatLng roundedPosition = new LatLng(latitude,longitude);
					
					
					Bus bus = localStop.get(roundedPosition);					
					
					if (bus != null) {
						name += "Stop Name : \t" + bus.getName();

						int count = 0;
						for (Date d : bus.getSchedules()) {
							SimpleDateFormat cdf = new SimpleDateFormat(
									"HH:mm", Locale.ENGLISH);

							if (d.after(cdf.parse(cdf.format(new Date())))) {
								if (count == 0)
									t = sdf.format(d);
								count = 1;
							}

						}
					} else
						name += "Location : \t" + marker.getPosition();

					lat.setText(name);
					lat.setTextColor(Color.BLUE);

					String info = "";
					info += "\n Current Time      : " + sdf.format(new Date());
					if (t.length() > 1)
						info += "\n Next Arrival Time : " + t;

					lon.setText(info);

				} catch (Exception ex) {
					LOGGER.log(Level.SEVERE,"Exception Occured",ex);
				}
				return infoWindow;
			}
		});

		map.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.centro))
				.anchor(0.0f, 1.0f).title("Centro Transit Hub")
				.position(new LatLng(43.04320, -76.15116)));

//		 Full Map 
		PolylineOptions options = new PolylineOptions();
		options.geodesic(true);
		options.color(Color.BLUE);

		map.addPolyline(options
				.add(new LatLng(43.04331, -76.15086))
				.add(new LatLng(43.04432, -76.15086))
				.add(new LatLng(43.04433, -76.15222))
				.add(new LatLng(43.04383, -76.15201))
				.add(new LatLng(43.04276, -76.15155))
				.add(new LatLng(43.04277, -76.15087))
				.add(new LatLng(43.04275, -76.14887))
				.add(new LatLng(43.04274, -76.14754))
				.add(new LatLng(43.0436, -76.14753))
				.add(new LatLng(43.04429, -76.14752))
				.add(new LatLng(43.0458, -76.1475))
				.add(new LatLng(43.04753, -76.14748))
				.add(new LatLng(43.04896, -76.1474))
				.add(new LatLng(43.04951, -76.14739))
				.add(new LatLng(43.04954, -76.14933))
				.add(new LatLng(43.04957, -76.14975))
				.add(new LatLng(43.04958, -76.15081))
				.add(new LatLng(43.04959, -76.15221))
				.add(new LatLng(43.04963, -76.15482))
				.add(new LatLng(43.04963, -76.15552))
				.add(new LatLng(43.05013, -76.1555))
				.add(new LatLng(43.0509, -76.15548))
				.add(new LatLng(43.05132, -76.15546))
				.add(new LatLng(43.05141, -76.15542))
				.add(new LatLng(43.05196, -76.15507))
				.add(new LatLng(43.0524, -76.1549))
				.add(new LatLng(43.05299, -76.15486))
				.add(new LatLng(43.05336, -76.15483))
				.add(new LatLng(43.05388, -76.15484))
				.add(new LatLng(43.05425, -76.15483))
				.add(new LatLng(43.05448, -76.15482))
				.add(new LatLng(43.05475, -76.15476))
				.add(new LatLng(43.05503, -76.1545))
				.add(new LatLng(43.05629, -76.15368))
				.add(new LatLng(43.05686, -76.15334))
				.add(new LatLng(43.05711, -76.15314))
				.add(new LatLng(43.05728, -76.15291))
				.add(new LatLng(43.05738, -76.15279))
				.add(new LatLng(43.05754, -76.15292))
				.add(new LatLng(43.05784, -76.15314))
				.add(new LatLng(43.05798, -76.15327))
				.add(new LatLng(43.05808, -76.15338))
				.add(new LatLng(43.05835, -76.15379))
				.add(new LatLng(43.05861, -76.15411))
				.add(new LatLng(43.05874, -76.15424))
				.add(new LatLng(43.05875, -76.15425))
				.add(new LatLng(43.0588, -76.15436))
				.add(new LatLng(43.05951, -76.15485))
				.add(new LatLng(43.06022, -76.15537))
				.add(new LatLng(43.06104, -76.15603))
				.add(new LatLng(43.06118, -76.15617))
				.add(new LatLng(43.06143, -76.15649))
				.add(new LatLng(43.06218, -76.15758))
				.add(new LatLng(43.06261, -76.15813))
				.add(new LatLng(43.06285, -76.15838))
				.add(new LatLng(43.06415, -76.1595))
				.add(new LatLng(43.06457, -76.15992))
				.add(new LatLng(43.06495, -76.16044))
				.add(new LatLng(43.06513, -76.16074))
				.add(new LatLng(43.06537, -76.16121))
				.add(new LatLng(43.06599, -76.16275))
				.add(new LatLng(43.0668, -76.16474))
				.add(new LatLng(43.06698, -76.16509))
				.add(new LatLng(43.06719, -76.16542))
				.add(new LatLng(43.06751, -76.16583))
				.add(new LatLng(43.06875, -76.16716))
				.add(new LatLng(43.06919, -76.16765))
				.add(new LatLng(43.06982, -76.16829))
				.add(new LatLng(43.07012, -76.16856))
				.add(new LatLng(43.07052, -76.16886))
				.add(new LatLng(43.07095, -76.16916))
				.add(new LatLng(43.07131, -76.16937))
				.add(new LatLng(43.07176, -76.16961))
				.add(new LatLng(43.07226, -76.16984))
				.add(new LatLng(43.07321, -76.17029))
				.add(new LatLng(43.07498, -76.17109))
				.add(new LatLng(43.07685, -76.17191))
				.add(new LatLng(43.07812, -76.17242))
				.add(new LatLng(43.07873, -76.17261))
				.add(new LatLng(43.07928, -76.17273))
				.add(new LatLng(43.07972, -76.17279))
				.add(new LatLng(43.08029, -76.17282))
				.add(new LatLng(43.08083, -76.17279))
				.add(new LatLng(43.08138, -76.17271))
				.add(new LatLng(43.08193, -76.17257))
				.add(new LatLng(43.08257, -76.17236))
				.add(new LatLng(43.08299, -76.17216))
				.add(new LatLng(43.08339, -76.17194))
				.add(new LatLng(43.08414, -76.17143))
				.add(new LatLng(43.08553, -76.17028))
				.add(new LatLng(43.08939, -76.16702))
				.add(new LatLng(43.08972, -76.16674))
				.add(new LatLng(43.08975, -76.16665))
				.add(new LatLng(43.08978, -76.16659))
				.add(new LatLng(43.08998, -76.16637))
				.add(new LatLng(43.0902, -76.1661))
				.add(new LatLng(43.09029, -76.16589))
				.add(new LatLng(43.09034, -76.16569))
				.add(new LatLng(43.09035, -76.16535))
				.add(new LatLng(43.09033, -76.16519))
				.add(new LatLng(43.09027, -76.165))
				.add(new LatLng(43.09019, -76.16483))
				.add(new LatLng(43.0901, -76.1647))
				.add(new LatLng(43.08996, -76.16456))
				.add(new LatLng(43.08984, -76.16449))
				.add(new LatLng(43.08963, -76.16443))
				.add(new LatLng(43.08944, -76.16444))
				.add(new LatLng(43.0893, -76.1645))
				.add(new LatLng(43.08913, -76.16464))
				.add(new LatLng(43.08885, -76.16498))
				.add(new LatLng(43.0888, -76.1652))
				.add(new LatLng(43.08881, -76.16537))
				.add(new LatLng(43.08884, -76.16554))
				.add(new LatLng(43.08904, -76.16579))
				.add(new LatLng(43.0894, -76.16632))
				.add(new LatLng(43.08967, -76.16677))
				.add(new LatLng(43.08997, -76.16731))
				.add(new LatLng(43.0907, -76.16871))
				.add(new LatLng(43.0907, -76.16885))
				.add(new LatLng(43.09113, -76.16964))
				.add(new LatLng(43.09201, -76.17102))
				.add(new LatLng(43.09241, -76.17161))
				.add(new LatLng(43.09282, -76.17136))
				.add(new LatLng(43.09326, -76.17109))
				.add(new LatLng(43.0933, -76.17101))
				.add(new LatLng(43.09408, -76.17053))
				.add(new LatLng(43.09446, -76.17029))
				.add(new LatLng(43.09495, -76.16995))
				.add(new LatLng(43.09588, -76.16926))
				.add(new LatLng(43.09604, -76.16916))
				.add(new LatLng(43.09618, -76.16911))
				.add(new LatLng(43.09714, -76.16841))
				.add(new LatLng(43.09959, -76.16653))
				.add(new LatLng(43.10045, -76.16582))
				.add(new LatLng(43.10122, -76.16511))
				.add(new LatLng(43.10287, -76.16356))
				.add(new LatLng(43.10564, -76.16126))
				.add(new LatLng(43.10604, -76.16097))
				.add(new LatLng(43.10647, -76.16073))
				.add(new LatLng(43.10684, -76.16057))
				.add(new LatLng(43.10727, -76.16045))
				.add(new LatLng(43.10928, -76.16022))
				.add(new LatLng(43.11118, -76.16003))
				.add(new LatLng(43.11127, -76.15999))
				.add(new LatLng(43.11161, -76.15996))
				.add(new LatLng(43.11227, -76.15988))
				.add(new LatLng(43.11297, -76.15977))
				.add(new LatLng(43.11344, -76.15969))
				.add(new LatLng(43.11371, -76.15964))
				.add(new LatLng(43.11376, -76.15966))
				.add(new LatLng(43.11392, -76.15963))
				.add(new LatLng(43.11496, -76.15945))
				.add(new LatLng(43.11689, -76.15913))
				.add(new LatLng(43.11717, -76.1591))
				.add(new LatLng(43.11746, -76.1591))
				.add(new LatLng(43.11786, -76.15914))
				.add(new LatLng(43.11905, -76.15926))
				.add(new LatLng(43.11905, -76.15926))
				.add(new LatLng(43.11918, -76.15931))
				.add(new LatLng(43.1197, -76.15938))
				.add(new LatLng(43.11959, -76.16142))
				// Hospital
				.add(new LatLng(43.11959, -76.16142))
				.add(new LatLng(43.11955, -76.16215))
				.add(new LatLng(43.11956, -76.16233))
				.add(new LatLng(43.11959, -76.16238))
				.add(new LatLng(43.11983, -76.16242))
				.add(new LatLng(43.12012, -76.1625))
				.add(new LatLng(43.12035, -76.16263))
				.add(new LatLng(43.12078, -76.16292))
				.add(new LatLng(43.12099, -76.16311))
				.add(new LatLng(43.12106, -76.1631))
				.add(new LatLng(43.12133, -76.1632))
				.add(new LatLng(43.1215, -76.16323))
				.add(new LatLng(43.12173, -76.16323))
				.add(new LatLng(43.12157, -76.16685))
		// WestTaft & cedarPost

		);

		if (taftRoute) {
			options

			.add(new LatLng(43.12157, -76.16685))
					.add(new LatLng(43.12431, -76.16708))
					.add(new LatLng(43.12522, -76.16718))
					.add(new LatLng(43.1254, -76.16724))
					.add(new LatLng(43.12555, -76.16734))
					.add(new LatLng(43.12579, -76.16753))
					.add(new LatLng(43.12604, -76.16786))
					.add(new LatLng(43.1263, -76.16848))
					.add(new LatLng(43.1266, -76.16935))
					.add(new LatLng(43.12666, -76.16946))
					.add(new LatLng(43.12688, -76.16972))
					.add(new LatLng(43.12696, -76.16978))
					.add(new LatLng(43.12681, -76.17014))
					.add(new LatLng(43.12651, -76.17074))
					.add(new LatLng(43.12627, -76.17123))
					.add(new LatLng(43.12601, -76.17176))
					.add(new LatLng(43.12589, -76.17193))
					.add(new LatLng(43.12577, -76.17205))
					.add(new LatLng(43.12561, -76.17216))
					.add(new LatLng(43.12573, -76.17244))
					.add(new LatLng(43.12577, -76.1725))
					.add(new LatLng(43.12636, -76.17314))
					.add(new LatLng(43.1265, -76.17331))
					.add(new LatLng(43.12659, -76.17346))
					.add(new LatLng(43.12667, -76.17373))
					.add(new LatLng(43.12666, -76.17423))
					.add(new LatLng(43.12659, -76.17458))
					.add(new LatLng(43.12645, -76.17488))
					.add(new LatLng(43.12638, -76.17514))
					.add(new LatLng(43.12634, -76.17537))
					.add(new LatLng(43.12635, -76.17556))
					.add(new LatLng(43.12647, -76.17605))
					.add(new LatLng(43.12648, -76.17652))
					.add(new LatLng(43.12644, -76.1768))
					.add(new LatLng(43.12633, -76.17713))
					.add(new LatLng(43.12628, -76.17758))
					.add(new LatLng(43.12626, -76.17825))
					.add(new LatLng(43.12628, -76.17846))
					.add(new LatLng(43.12639, -76.17882))
					.add(new LatLng(43.12642, -76.1789))
					.add(new LatLng(43.1257, -76.17942))
					.add(new LatLng(43.12356, -76.18098))
					.add(new LatLng(43.12239, -76.18184))
					.add(new LatLng(43.12177, -76.18227))
					.add(new LatLng(43.12162, -76.18234))
					.add(new LatLng(43.12143, -76.1824))
					.add(new LatLng(43.12108, -76.18244))
					.add(new LatLng(43.12097, -76.18241));
			// Bear Rd & W.Taft

		} else {
			options
			// Without cedarPost in W Taft
			.add(new LatLng(43.12157, -76.16685))
					.add(new LatLng(43.12156, -76.16708))
					.add(new LatLng(43.12159, -76.16725))
					.add(new LatLng(43.12154, -76.16898))
					.add(new LatLng(43.12142, -76.17123))
					.add(new LatLng(43.12138, -76.17133))
					.add(new LatLng(43.12137, -76.17246))
					.add(new LatLng(43.12131, -76.17679))
					.add(new LatLng(43.12129, -76.17899))
					.add(new LatLng(43.12128, -76.17954))
					.add(new LatLng(43.12132, -76.17969))
					.add(new LatLng(43.12132, -76.17978))
					.add(new LatLng(43.12131, -76.18068))
					.add(new LatLng(43.12129, -76.18112))
					.add(new LatLng(43.12124, -76.18144))
					.add(new LatLng(43.12109, -76.18209))
					.add(new LatLng(43.12097, -76.18241));
			// Bear Rd & W.Taft

		}

		options.add(new LatLng(43.12097, -76.18241))
				.add(new LatLng(43.12072, -76.18292))
				.add(new LatLng(43.12053, -76.18322))
				.add(new LatLng(43.12034, -76.18344))
				.add(new LatLng(43.11998, -76.1838))
				.add(new LatLng(43.11961, -76.18412))
				.add(new LatLng(43.11924, -76.18453))
				.add(new LatLng(43.11895, -76.18493))
				.add(new LatLng(43.11878, -76.1852))
				.add(new LatLng(43.11881, -76.18532))
				.add(new LatLng(43.11882, -76.18538))
				.add(new LatLng(43.11889, -76.18552))
				.add(new LatLng(43.12106, -76.18573))
				.add(new LatLng(43.12459, -76.18605))
				.add(new LatLng(43.13059, -76.18656))
				.add(new LatLng(43.13137, -76.18663))
				.add(new LatLng(43.13137, -76.18655))
				.add(new LatLng(43.13143, -76.1851))
				.add(new LatLng(43.1315, -76.18346))
				.add(new LatLng(43.13157, -76.18213))
				.add(new LatLng(43.13157, -76.18208));
		// Norstar Apts
		// Direct to Moltlage

		if (stoneDaleRoute) {
			options
			// With Stonedale
			.add(new LatLng(43.13157, -76.18208))
					.add(new LatLng(43.13144, -76.18483))
					.add(new LatLng(43.13137, -76.18663))
					.add(new LatLng(43.13398, -76.18685))
					.add(new LatLng(43.13842, -76.18724))
					.add(new LatLng(43.14102, -76.18746))
					.add(new LatLng(43.14393, -76.18768))
					.add(new LatLng(43.14406, -76.1877))
					.add(new LatLng(43.14413, -76.18776))
					.add(new LatLng(43.1442, -76.18776))
					.add(new LatLng(43.14573, -76.18788))
					.add(new LatLng(43.14689, -76.18797))
					.add(new LatLng(43.14702, -76.18791))
					.add(new LatLng(43.14705, -76.18706))
					.add(new LatLng(43.14717, -76.184))
					.add(new LatLng(43.14727, -76.18349))
					.add(new LatLng(43.14735, -76.18329))
					.add(new LatLng(43.14662, -76.18287))
					.add(new LatLng(43.14572, -76.18234))
					.add(new LatLng(43.14591, -76.18182))
					.add(new LatLng(43.1461, -76.18149))
					.add(new LatLng(43.14622, -76.18109))
					.add(new LatLng(43.14639, -76.18037))
					.add(new LatLng(43.14644, -76.18024))
					.add(new LatLng(43.14651, -76.18016))
					.add(new LatLng(43.14659, -76.18011))
					.add(new LatLng(43.14668, -76.1801))
					.add(new LatLng(43.14848, -76.18027))
					.add(new LatLng(43.14869, -76.18033))
					.add(new LatLng(43.14902, -76.18051))
					.add(new LatLng(43.14911, -76.18054))
					.add(new LatLng(43.14946, -76.18058))
					.add(new LatLng(43.14936, -76.18261))
					.add(new LatLng(43.14921, -76.18555))
					.add(new LatLng(43.14917, -76.18671))
					.add(new LatLng(43.14915, -76.18749))
					.add(new LatLng(43.1492, -76.18809))
					.add(new LatLng(43.14932, -76.18868))
					.add(new LatLng(43.1496, -76.1895))
					.add(new LatLng(43.1499, -76.19038))
					.add(new LatLng(43.14992, -76.19049))
					.add(new LatLng(43.15071, -76.19267))
					.add(new LatLng(43.14966, -76.19339))
					.add(new LatLng(43.14956, -76.19345))
					.add(new LatLng(43.14944, -76.19347))
					.add(new LatLng(43.14876, -76.19342));
		} else {
			options.add(new LatLng(43.13157, -76.18208))
					.add(new LatLng(43.13144, -76.18483))
					.add(new LatLng(43.13137, -76.18663))
					.add(new LatLng(43.13398, -76.18685))
					.add(new LatLng(43.13842, -76.18724))
					.add(new LatLng(43.14102, -76.18746))
					.add(new LatLng(43.14393, -76.18768))
					.add(new LatLng(43.14406, -76.1877))
					.add(new LatLng(43.14413, -76.18776))
					.add(new LatLng(43.1442, -76.18776))
					.add(new LatLng(43.14573, -76.18788))
					.add(new LatLng(43.14689, -76.18797))
					.add(new LatLng(43.14702, -76.18791))
					.add(new LatLng(43.1477, -76.18797))
					.add(new LatLng(43.14911, -76.18808))
					.add(new LatLng(43.1492, -76.18809))
					.add(new LatLng(43.14922, -76.18823))
					.add(new LatLng(43.14928, -76.18855))
					.add(new LatLng(43.14937, -76.18885))
					.add(new LatLng(43.14953, -76.1893))
					.add(new LatLng(43.14985, -76.19025))
					.add(new LatLng(43.14991, -76.19042))
					.add(new LatLng(43.15026, -76.19143))
					.add(new LatLng(43.15071, -76.19267))
					.add(new LatLng(43.15013, -76.19305))
					.add(new LatLng(43.14956, -76.19345))
					.add(new LatLng(43.14944, -76.19347))
					.add(new LatLng(43.14919, -76.19346))
					.add(new LatLng(43.14876, -76.19342))
			
			  // Wetzel & Moltlage
			 ;
		}

		map.addPolyline(options);

		dbh.getWritableDatabase().beginTransaction();
		try{
			String sql = "INSERT INTO ROUTE (id,routenumber,direction,map) values (1,286,'From Downtown To HenryClay','"+Utility.encode(options.getPoints())+"');";
			
			String select = "SELECT * FROM ROUTE;";
			
			
			
			LOGGER.log(Level.SEVERE,"TRYING "+sql);
			
			dbh.getWritableDatabase().execSQL(sql);
			
			Cursor cursor = dbh.getReadableDatabase().rawQuery(select, null);
			
			cursor.moveToFirst();
			do{
				LOGGER.log(Level.SEVERE,Arrays.toString(cursor.getColumnNames())+"Columns");
			}while(cursor.moveToNext());
			LOGGER.log(Level.SEVERE,cursor.getCount()+"results");
			
			dbh.getWritableDatabase().endTransaction();
		dbh.close();
		}catch(Exception ex){
			LOGGER.log(Level.SEVERE,ex.getMessage());
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	


}
