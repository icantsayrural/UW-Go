package com.uwaterloo.go;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.HashMap;
import java.util.Date;
import java.sql.Timestamp;

public class Weather {
	static String json;

	static public boolean fetchData() {
		boolean success = true;
		try {
			//7 day forecast
			json = Connection.makeConnection("http://api.openweathermap.org/data/2.5/forecast/daily?q=Waterloo&mode=json&units=metric&cnt=7");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			success = false;
		}
		return success;
	}

	static public HashMap<String, Double> getTemp(Date date) {
		HashMap<String, Double> allTemp = new HashMap<String, Double>();

		try  {
			//Get root JSONObject
			JSONObject root = new JSONObject(json);
			//Get the temp object of current date from list array
			//By default we get the earliest temp data we can get (today)

			JSONArray list = root.getJSONArray("list");
			JSONObject dday = list.getJSONObject(0);
			for(int i=0; i<list.length(); i++) {
				Timestamp dtime = new Timestamp(date.getTime());
				Timestamp time = (Timestamp) new Timestamp(list.getJSONObject(i).getLong("dt"));
				if (time.equals(dtime) || time.before(dtime)) {
					//We want the latest temp
					dday = list.getJSONObject(i);
					break;
				}
			}

			JSONObject temp = dday.getJSONObject("temp");
			//Get temperatures
			//Store them in hashmaps
			allTemp.put("morn", temp.getDouble("morn"));
			allTemp.put("day", temp.getDouble("day"));
			allTemp.put("night", temp.getDouble("night"));
			allTemp.put("eve", temp.getDouble("eve"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return allTemp;
	}

	static public boolean isRainyDay(Date date) {
		boolean rainyDay = false;
		try  {
			//Get root JSONObject
			JSONObject root = new JSONObject(json);

			//Get the weather object of current date from list array
			//By default we get the earliest weather data we can get (today)
			JSONArray list = root.getJSONArray("list");
			JSONObject dday = list.getJSONObject(0);
			for(int i=0; i<list.length(); i++) {
				Timestamp dtime = new Timestamp(date.getTime());
				Timestamp time = (Timestamp) new Timestamp(list.getJSONObject(i).getLong("dt"));
				if (time.equals(dtime) || time.after(dtime)) {
					//We want the latest weather
					dday = list.getJSONObject(i);
					break;
				}
			}

			JSONArray weather = dday.getJSONArray("weather");
			//Get weather info on rain
			for(int j=0;j<weather.length();j++) {
				JSONObject weatherInfo = weather.getJSONObject(0);
				String main = weatherInfo.getString("main");
				if(main.equals("Rain")) {
					//Weather info contains the word 'Rain'
					//It is confirmed that it will rain so exit loop
					rainyDay=true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return rainyDay;
	}
}
