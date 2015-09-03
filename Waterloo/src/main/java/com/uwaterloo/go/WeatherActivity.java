package com.uwaterloo.go;

import android.app.ListActivity;
import android.os.Bundle;
import java.util.HashMap;
import java.util.Date;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class WeatherActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);

        //Define array of temperatures in list
        ArrayList<String> list = new ArrayList();

        //Get weather
        if(Weather.fetchData()) {
        	//Data was fetched successfully

        	//Current date
        	Date now = new Date();

        	//Temperature
        	HashMap<String, Double> temperature = new HashMap<String, Double>();
        	temperature = Weather.getTemp(now);

	        String morn = Double.toString(temperature.get("morn"));
	        String day = Double.toString(temperature.get("day"));
	        String night = Double.toString(temperature.get("night"));
	        String eve = Double.toString(temperature.get("eve"));

	        if ( Weather.isRainyDay(now) ) {
	        	//Today you need an umbrella
	        	list.add("** Warning: Bring your umbrella!");
	        }

	        if ( morn != null ) {
	        	list.add("Morning: " + morn);
	        }

	        if ( day != null ) {
	        	list.add("Afternoon: " + day);
	        }

	        if ( night != null ) {
	        	list.add("Night: " + night);
	        }

	        if ( eve != null ) {
	        	list.add("Evening: " + eve);
	        }

		    //Define a new adapter
	        //The adapter will display data in array as individiual views to listView
	        //Takes context ( currenc activity ), xml item layout and array
	       	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.value, R.id.text, list);

	        //Set adapter
			setListAdapter(adapter);
        }
	}
}