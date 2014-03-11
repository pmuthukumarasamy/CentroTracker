package com.palani.centrotracker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
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

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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

		map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 10));

		Map<LatLng, String> busStop = new HashMap<LatLng, String>();

		busStop.put(new LatLng(43.131380, -76.185197), "NorStar Apartments");
		busStop.put(new LatLng(43.147363, -76.183728),
				"Stonedale Dr & New Hope East");
		busStop.put(new LatLng(43.148784, -76.193417), "Maltlage & Wetzel");
		busStop.put(new LatLng(43.131380, -76.185197), "Norstar Apartments");
		busStop.put(new LatLng(43.126856, -76.169964),
				"Merril Farms (Harvest & CedarPost)");
		busStop.put(new LatLng(43.121675, -76.163188), "North Medical Center");
		busStop.put(new LatLng(43.093386, -76.171071), "7th North & Buckley Rd");
		busStop.put(new LatLng(43.049457, -76.153897),
				"Washington & Clinton/Franklin St");
		busStop.put(new LatLng(43.045469, -76.148538), "S State & Madison St");
		busStop.put(new LatLng(43.043306, -76.150863), "Salina & Adams St");

		Iterator<Entry<LatLng, String>> iterator = busStop.entrySet()
				.iterator();

		while (iterator.hasNext()) {
			Map.Entry<LatLng, String> stop = (Map.Entry<LatLng, String>) iterator
					.next();
			map.addMarker(new MarkerOptions()

					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.bus_green_icon))
					.anchor(0.0f, 1.0f)
					// Anchors the marker on the bottom left
					.title(stop.getValue()).snippet("").position(stop.getKey()));

		}

		map.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoWindow(Marker marker) {
				// TODO Auto-generated method stub
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

					lat.setText("Current Time :" + sdf.format(new Date()));

					lon.setText("Next Trip: Coming soon..");
				} catch (Exception ex) {
					Logger.getLogger(MainActivity.class.getName()).log(
							Level.SEVERE,
							"Error Occured during setting of Text Field", ex);
				}
				return infoWindow;
			}
		});

		map.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.centro))
				.anchor(0.0f, 1.0f).title("Centro Transit Hub")
				.position(new LatLng(43.0432043, -76.1511651)));

		/* Stonedale */
		map.addPolyline(new PolylineOptions().geodesic(true).color(Color.GREEN)
				.add(new LatLng(43.147021, -76.187909))
				.add(new LatLng(43.147147, -76.184696))
				.add(new LatLng(43.147166, -76.184014))
				.add(new LatLng(43.147342, -76.183295))
				.add(new LatLng(43.145710, -76.182330))
				.add(new LatLng(43.146090, -76.181547))
				.add(new LatLng(43.146411, -76.180302))
				.add(new LatLng(43.146630, -76.180088))
				.add(new LatLng(43.148473, -76.180259))
				.add(new LatLng(43.148712, -76.180334))
				.add(new LatLng(43.149029, -76.180506))
				.add(new LatLng(43.149464, -76.180576))
				.add(new LatLng(43.149209, -76.185473))
				.add(new LatLng(43.149147, -76.187185))
				.add(new LatLng(43.149240, -76.188317)));
		/* West Taft */
		map.addPolyline(new PolylineOptions().geodesic(true).color(Color.GREEN)
				.add(new LatLng(43.121544, -76.166953))
				.add(new LatLng(43.121293, -76.179013))
				.add(new LatLng(43.121309, -76.180837))
				.add(new LatLng(43.121246, -76.181459))
				.add(new LatLng(43.120714, -76.182832)));
		/* Full Map */
		map.addPolyline(new PolylineOptions()
				.geodesic(true)
				.color(Color.BLUE)
				.add(new LatLng(43.043306, -76.150863))//Centro Transit Hub
				.add(new LatLng(43.042769, -76.150873))//S Warren St & E Adams St
				.add(new LatLng(43.042740, -76.147542))//E Adams St & S State St
				.add(new LatLng(43.049514, -76.147401))//S State St & E Washington St
				.add(new LatLng(43.049545, -76.149324))//E Washington & Montgomery st				
				.add(new LatLng(43.049574, -76.149753))//E Washington & E Gennese St
				.add(new LatLng(43.049582, -76.150810))//E Washington & S Warren St
				.add(new LatLng(43.049590, -76.151498))//E Washington & Bank Alley
				.add(new LatLng(43.049590, -76.152213))//E Washington & S Salina St
				.add(new LatLng(43.049619, -76.153586))//W Washington & S Clinton St
				.add(new LatLng(43.049631, -76.155523))//W Washington & S Franklin St
				.add(new LatLng(43.050564, -76.155485))//S Franklin St & W Water St
				.add(new LatLng(43.050897, -76.155478))//S Franklin St & Erie Blvd W
				.add(new LatLng(43.050971, -76.155474))//Erie Blvd W & N Franklin St
				.add(new LatLng(43.051316, -76.155465))//N Franklin St;
				.add(new LatLng(43.051416, -76.155421))//N Franklin St;
				.add(new LatLng(43.051955, -76.155069))//N Franklin St & W Genessee St
				.add(new LatLng(43.051967, -76.155064))//W Genessee St & N Franklin St				
				.add(new LatLng(43.052400, -76.154901))//N Franklin St & W Willow St
				.add(new LatLng(43.053360, -76.154835))//N Franklin St & Herald PI
				.add(new LatLng(43.054113, -76.154838))//N Franklin St & 690
				.add(new LatLng(43.054124, -76.154838))//690 & N Franklin St
				.add(new LatLng(43.054294, -76.154831))//690 & N Franklin St
				.add(new LatLng(43.054307, -76.154830))//690 & N Franklin St
				.add(new LatLng(43.054402, -76.154825))//N Franklin St
				.add(new LatLng(43.054569, -76.154806))//N Franklin St
				.add(new LatLng(43.054749, -76.154756))//N Franklin St & Websters Landing
				.add(new LatLng(43.055034, -76.154493))//Butternut St
				.add(new LatLng(43.056398, -76.153623))//Butternut St
				.add(new LatLng(43.056618, -76.153485))//Butternut St
				.add(new LatLng(43.056859, -76.153339))//Butternut St
				.add(new LatLng(43.056971, -76.153257))//Butternut St
				.add(new LatLng(43.057117, -76.153142))//Butternut St
				.add(new LatLng(43.057194, -76.153049))//Butternut St
				.add(new LatLng(43.057283, -76.152910))//Butternut St & Salt St
				.add(new LatLng(43.057376, -76.152794))//Butternut St
				.add(new LatLng(43.057499, -76.152466))//Butternut St & N State St
				.add(new LatLng(43.058440, -76.153211))//N State St & I81 Ramp
				.add(new LatLng(43.058348, -76.153316))//I81 Ramp
				.add(new LatLng(43.058323, -76.153355))//I81 Ramp
				.add(new LatLng(43.058307, -76.153388))//I81 Ramp
				.add(new LatLng(43.058294, -76.153425))//I81 Ramp
				.add(new LatLng(43.058280, -76.153484))//I81 Ramp
				.add(new LatLng(43.058279, -76.153545))//I81 Ramp
				.add(new LatLng(43.058285, -76.153609))//I81 Ramp
				.add(new LatLng(43.058350, -76.153792))//Ramp
				.add(new LatLng(43.058470, -76.153966))//Ramp
				.add(new LatLng(43.058615, -76.154120))//Ramp
				.add(new LatLng(43.058748, -76.154240))//Merge on to I81
				.add(new LatLng(43.058762, -76.154265))//Merge on to I81
				.add(new LatLng(43.058801, -76.154360))//I81
				
				
				
				
				.add(new LatLng(43.058810, -76.154370))
				.add(new LatLng(43.061146, -76.156124))
				.add(new LatLng(43.062780, -76.158323))
				.add(new LatLng(43.064352, -76.159691))
				.add(new LatLng(43.064944, -76.160416))

				.add(new LatLng(43.066382, -76.163715))
				.add(new LatLng(43.066766, -76.164648))
				.add(new LatLng(43.066390, -76.163733))
				.add(new LatLng(43.066586, -76.164203))
				.add(new LatLng(43.067078, -76.165227))
				.add(new LatLng(43.067546, -76.165868))
				.add(new LatLng(43.068788, -76.167207))
				.add(new LatLng(43.069717, -76.168189))
				.add(new LatLng(43.070119, -76.168561))
				.add(new LatLng(43.070804, -76.169066))

				.add(new LatLng(43.072015, -76.169728))
				.add(new LatLng(43.074670, -76.170946))
				.add(new LatLng(43.074872, -76.171040))
				.add(new LatLng(43.075509, -76.171314))
				.add(new LatLng(43.075934, -76.171500))
				.add(new LatLng(43.076478, -76.171739))
				.add(new LatLng(43.076903, -76.171925))
				.add(new LatLng(43.077608, -76.172213))
				.add(new LatLng(43.079650, -76.172766))
				.add(new LatLng(43.081405, -76.172713))

				.add(new LatLng(43.082381, -76.172412))
				.add(new LatLng(43.084046, -76.171468))
				.add(new LatLng(43.087173, -76.168861))
				.add(new LatLng(43.089468, -76.166984))
				.add(new LatLng(43.089727, -76.166729))
				.add(new LatLng(43.089784, -76.166573))
				.add(new LatLng(43.090095, -76.166249))
				.add(new LatLng(43.090299, -76.165860))
				.add(new LatLng(43.090350, -76.165592))
				.add(new LatLng(43.090352, -76.165356))

				.add(new LatLng(43.090315, -76.165138))
				.add(new LatLng(43.090230, -76.164908))
				.add(new LatLng(43.090128, -76.164731))
				.add(new LatLng(43.089819, -76.164478))
				.add(new LatLng(43.089643, -76.164427))
				.add(new LatLng(43.089466, -76.164436))
				.add(new LatLng(43.089298, -76.164500))
				.add(new LatLng(43.089141, -76.164618))
				.add(new LatLng(43.088840, -76.164996))
				.add(new LatLng(43.088795, -76.165205))

				.add(new LatLng(43.088834, -76.165500))
				.add(new LatLng(43.089668, -76.166766))
				.add(new LatLng(43.090700, -76.168700))
				.add(new LatLng(43.090702, -76.168853))
				.add(new LatLng(43.091895, -76.170849))
				.add(new LatLng(43.092422, -76.171605))
				.add(new LatLng(43.093276, -76.171066))
				.add(new LatLng(43.093327, -76.170975))
				.add(new LatLng(43.095760, -76.169360))
				.add(new LatLng(43.096210, -76.169092))

				.add(new LatLng(43.097518, -76.168148))
				.add(new LatLng(43.100182, -76.166034))
				.add(new LatLng(43.100981, -76.165305))
				.add(new LatLng(43.104968, -76.161807))
				.add(new LatLng(43.106167, -76.160874))
				.add(new LatLng(43.107275, -76.160444))
				.add(new LatLng(43.111309, -76.159983))
				.add(new LatLng(43.113711, -76.159637))
				.add(new LatLng(43.113770, -76.159645))
				.add(new LatLng(43.116161, -76.159245))

				.add(new LatLng(43.116966, -76.159122))
				.add(new LatLng(43.117561, -76.159114))
				.add(new LatLng(43.119133, -76.159240))
				.add(new LatLng(43.119695, -76.159275))
				.add(new LatLng(43.119591, -76.161405))
				.add(new LatLng(43.119569, -76.162349))
				.add(new LatLng(43.119681, -76.162400))
				.add(new LatLng(43.120364, -76.162630))
				.add(new LatLng(43.120777, -76.162913))
				.add(new LatLng(43.120986, -76.163112))

				.add(new LatLng(43.121056, -76.163101))
				.add(new LatLng(43.121337, -76.163202))
				.add(new LatLng(43.121505, -76.163228))
				.add(new LatLng(43.121678, -76.163219))
				.add(new LatLng(43.121735, -76.163228))
				.add(new LatLng(43.121575, -76.166849))
				.add(new LatLng(43.125241, -76.167187))
				.add(new LatLng(43.125696, -76.167448))
				.add(new LatLng(43.125997, -76.167796))
				.add(new LatLng(43.126163, -76.168126))

				.add(new LatLng(43.126608, -76.169352))
				.add(new LatLng(43.126849, -76.169679))
				.add(new LatLng(43.126962, -76.169770))
				.add(new LatLng(43.125934, -76.171865))
				.add(new LatLng(43.125619, -76.172152))
				.add(new LatLng(43.125764, -76.172504))
				.add(new LatLng(43.126504, -76.173306))
				.add(new LatLng(43.126650, -76.173625))
				.add(new LatLng(43.126664, -76.173751))
				.add(new LatLng(43.126633, -76.174448))

				.add(new LatLng(43.126594, -76.174582))
				.add(new LatLng(43.126380, -76.175132))
				.add(new LatLng(43.126343, -76.175366))
				.add(new LatLng(43.126468, -76.176031))
				.add(new LatLng(43.126492, -76.176336))
				.add(new LatLng(43.126433, -76.176814))
				.add(new LatLng(43.126310, -76.177211))
				.add(new LatLng(43.126261, -76.178262))
				.add(new LatLng(43.126421, -76.178869))
				.add(new LatLng(43.121864, -76.182238))

				.add(new LatLng(43.121378, -76.182431))
				.add(new LatLng(43.120955, -76.182431))
				.add(new LatLng(43.120148, -76.183665))
				.add(new LatLng(43.119256, -76.184523))
				.add(new LatLng(43.118802, -76.185221))
				.add(new LatLng(43.118903, -76.185510))
				.add(new LatLng(43.129498, -76.186444))
				.add(new LatLng(43.131323, -76.186626))
				.add(new LatLng(43.131554, -76.182131))
				.add(new LatLng(43.131409, -76.186621))

				.add(new LatLng(43.138831, -76.187270))
				.add(new LatLng(43.141700, -76.187511))
				.add(new LatLng(43.144037, -76.187683))
				.add(new LatLng(43.144154, -76.187758))
				.add(new LatLng(43.146945, -76.187946))
				.add(new LatLng(43.147062, -76.187914))
				.add(new LatLng(43.149172, -76.188091))
				.add(new LatLng(43.149908, -76.190403))
				.add(new LatLng(43.150710, -76.192666))
				.add(new LatLng(43.149567, -76.193450))

				.add(new LatLng(43.148784, -76.193417))

		);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
