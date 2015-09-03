package com.uwaterloo.go;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.StringBuffer;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.MalformedURLException;

public class Connection {
	static public String makeConnection(String str) throws MalformedURLException, IOException, ProtocolException {
		//HttpURLConnection
		URL url = new URL(str);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		//Select request method
		con.setRequestMethod("GET");

		//Get buffered input
		StringBuffer response = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		while((inputLine=reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();

		return response.toString();
	}
}