package com.uwaterloo.go;

import org.json.JSONObject;
import org.json.JSONArray;
import java.util.ArrayList;

public class Bus {
	String json;

	boolean fetchData(int stopId, int routeId) {
		boolean success = true;
		try {
			json = Connection.makeConnection(String.format("http://realtimemap.grt.ca/Stop/GetStopInfo?stopId=%s&routeId=%s", String.valueOf(stopId), String.valueOf(routeId)));
			JSONObject root = new JSONObject(json);
			String status = root.getString("status");
			if(!status.equals("success")) {
				//Api failed to fetch required data
				success = false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			success = false;
		}
		return success;
	}

	ArrayList<Long> getTimes() {
		//Times
		ArrayList<Long> times = new ArrayList<Long>();

		try {
			//Get root json object
			JSONObject root = new JSONObject(json);

			JSONArray stopTimes = root.getJSONArray("stopTimes");
			for(int i=0;i<stopTimes.length();i++) {
				JSONObject stopTime = stopTimes.getJSONObject(i);
				Long time = stopTime.getLong("Minutes");
				times.add(time);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return times;
	}
}
