package com.uwaterloo.go;

import android.app.ListActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.text.TextUtils;
import android.widget.ArrayAdapter;

public class BusActivity extends ListActivity {

	private EditText stopid;
	private EditText routeid;
	private Button button;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus);

        //Get views
        stopid = (EditText) findViewById(R.id.stopid);
        routeid = (EditText) findViewById(R.id.routeid);
        button = (Button) findViewById(R.id.button);

        //Current BusActivity
        final BusActivity busActivity = this;

		button.setOnClickListener(
	        new View.OnClickListener()
	        {
	            public void onClick(View view)
	            {
	            	ArrayList<String> busTimes = new ArrayList();
	    	        Bus bus = new Bus();
					
					//stop and route id from input
	    	        int stopIdInteger = 0;
	    	        int routeIdInteger = 0;
	    	        
	    	        if ( !TextUtils.isEmpty(stopid.getText().toString()) ) {
	    	        	//input for stopid isn't empty
	    	        	stopIdInteger = Integer.parseInt(stopid.getText().toString());
	    	        } else {
	    	        	//input for stopid is empty
	    	        	//print error message when cursor hovers over
	    	        	stopid.setError("The stop id cannot be empty.");
	    	        }

	    	        if ( !TextUtils.isEmpty(routeid.getText().toString()) ) {
	    	        	//input for routeid isn't empty
	    	        	routeIdInteger = Integer.parseInt(routeid.getText().toString());
	    	        } else {
	    	        	//input for routeid is empty
	    	        	//print error message when cursor hovers over
	    	        	routeid.setError("The route id cannot be empty.");
	    	        }

			        boolean success = bus.fetchData(stopIdInteger, routeIdInteger);

			        if ( success ) {
			        	//Fetching data was successful
			        	//Add data to listview
			        	ArrayList<Long> times = bus.getTimes();
			        	for( int i = 0; i < times.size(); i++ ) {
			        		//concatenate
			        		busTimes.add(times.get(i).toString() + " minutes");
			        		
			        	}
			        }

        		    //Define a new adapter
			        //The adapter will display data in array as individiual views to listView
			        //Takes context ( currenc activity ), xml item layout and array
			       	ArrayAdapter<String> adapter = new ArrayAdapter<String>(busActivity, R.layout.value, R.id.text, busTimes);

			        //Set adapter
					setListAdapter(adapter);
	            }
        });
	}
}