package com.uwaterloo.go;

import android.app.ListActivity;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;
import android.text.Html;
import java.util.ArrayList;
import android.text.Spanned;

public class Main extends ListActivity
{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Define array options to show in list
        ArrayList<Spanned> options = new ArrayList();
        //Weather
        Spanned weather = Html.fromHtml("<html><body><font color=red>Weather </font><br> <font color=white>Today's Forecast </font> </body><html>");
        options.add(weather);
        //Bus
        Spanned bus = Html.fromHtml("<html><body><font color=red>Bus Schedule </font><br> <font color=white>GRT Real Time </font> </body><html>");
        options.add(bus);

        //Define a new adapter
        //The adapter will display data in array as individiual views to listView
        //Takes context ( currenc activity ), xml item layout and array
       	ArrayAdapter<Spanned> adapter = new ArrayAdapter<Spanned>(this, R.layout.item, R.id.option, options);

        //Set adapter
		setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {

    	Intent intent;

    	//Start activity depending on position
    	switch( position )
    	{
    		case 0:
    			intent = new Intent(this, WeatherActivity.class);
    			startActivity(intent);
    			break;
    		case 1:
    			intent = new Intent(this, BusActivity.class);
    			startActivity(intent);
    			break;
    	}

    }
}