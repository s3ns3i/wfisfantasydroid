package com.s3ns3i.degejm.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.s3ns3i.degejm.Fragments.CharacterCreationFragment;
import com.s3ns3i.degejm.JSONParser;
import com.s3ns3i.degejm.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Tutaj trzeba
 * zrobić:
 * - wczytanie dwóch tablic do spinnerów,
 * 	-- klasy
 * 	-- rasy
 * - wczytanie danych do trzech zmiennych i wyśćwietlić je (początkowe staty)
 * 	--siła
 * 	-- zręczność
 * 	-- inteligencja
 * 
 * @author s3ns3i
 * 
 */
public class GetDefaultCharacterDataFromTheServer extends
		AsyncTask<String, String, String> {

	// ===============JSON related objects=================
	// Creating JSON Parser object.
	private JSONParser jParser = new JSONParser();
	//Link to the server.
	private static String url = "http://wfisfantasy.16mb.com/";
	//Arrays that will hold downloaded races and classes.
	private JSONArray racesTable = null, classesTable = null;
	//Tag checking if download from the server succeeded.
	private static final String TAG_SUCCESS = "success";

	// ===================Other objects====================
	//Names of the tables.
	private String []tableNames = {"race", "classes"}
		//Columns names.
			, raceTableColumnIDs = {"nazwa_rasy", "base_hp", "base_mana", "STR", "DEX", "INT"}
			, classTableColumnIDs = {"class_name", "magic_attack", "meele_attack", "magic_defense", "meele_defense", "hp_modifier", "mana_modifier"};
	//Progress dialog. It will cover the layout blocking any action from the user.
	private ProgressDialog pDialog;
	//
	private Boolean isGetSuccessful;
    private Integer numberofRacesTableColumns = 6;
    private Integer numberofclassesTableColumns = 7;

	// ====================References======================
	//These are references to the lists in the activity.
	private ArrayList<ArrayList<String>> racesList, classesList;

	// =====================Messages=======================
	private final String message1 = "Loading data from database.",
			message2 = "Getting data from the server...",
			message3 = "Tables from the server: ",
			message4 = "The mapping doesn't exist or is not a JSONArray.",
			message5 = "No such mapping exists.",
			message6 = "Connected with the main server",
			message7 = "Database is empty.",
			message8 = "Couldn't connect with the main server.",
			progress1 = "Attempting to connect with the main server...",
			progress2 = "Connected! Now making a tables...",
			progress3 = "Loading table with races names...",
			progress4 = "Loading table with classes names...",
			progress5 = "Getting layout elements references...";

	// ===================Constructor======================
	public GetDefaultCharacterDataFromTheServer(ArrayList<ArrayList<String>> racesList, ArrayList<ArrayList<String>> classesList){
		this.racesList = racesList;
		this.classesList = classesList;
		
		for(int i = 0; i < numberofRacesTableColumns; i++){
			this.racesList.add(new ArrayList<String>());
		}
		
		for(int j = 0; j < numberofclassesTableColumns; j++){
			this.classesList.add(new ArrayList<String>());
		}
	}

	/**
	 * getting All products from url
	 * */
	protected String doInBackground(String... phpURLs) {
		// Getting table/s from the server
		// I named this list 'dummyList', 'cause I can't figure out how it's
		// used in JSONParser.
		List<NameValuePair> dummyList = new ArrayList<NameValuePair>();
		// getting JSON string from URL
		// At this point we get all tables that php could send us.
		publishProgress(progress1);
		JSONObject jsonRaces = jParser.makeHttpRequest(url + phpURLs[0], "GET",
				dummyList);
		JSONObject jsonClasses = jParser.makeHttpRequest(url + phpURLs[1],
				"GET", dummyList);

		// Check your log cat for JSON reponse
		Log.d("GetDataFromDatabase", message3 + jsonRaces.toString());
		Log.d("GetDataFromDatabase", message3 + jsonClasses.toString());

		try {
			// Checking for SUCCESS TAG
			publishProgress(progress2);
			int successRaces = jsonRaces.getInt(TAG_SUCCESS);
			int successClasses = jsonClasses.getInt(TAG_SUCCESS);
			if (successRaces == 1 && successClasses == 1) {
				// Getting desired array (stated in 'tableName' variable passed
				// as an argument).
				// Now we'll have only this table that we want. In my case it'll
				// be class table and race table. //s3ns3i
				try {
					racesTable = jsonRaces.getJSONArray(tableNames[0]);
					classesTable = jsonClasses.getJSONArray(tableNames[1]);
				} catch (JSONException e) {
					Log.d("GetDataFromDatabase", message4);
				}

				// Had to make two loops with exact code, because tables may
				// have different sizes.
				publishProgress(progress3);
				// Looping through all elements.

				// There we have to get classes names and their stats.
				// It looks like this:
				// +------------+---------+-----------+-----+-----+-----+
				// | nazwa_rasy | base_hp | base_mana | STR | DEX | INT |
				// +------------+---------+-----------+-----+-----+-----+
				// | Łodzianin  |     100 |       200 |  10 |  10 |  10 |
				// +------------+---------+-----------+-----+-----+-----+
				// | LiManek    |     200 |         5 |   6 |  15 |   0 |
				// +------------+---------+-----------+-----+-----+-----+
				// | Wiesniak   |     300 |       100 |  20 |  10 |   5 |
				// +------------+---------+-----------+-----+-----+-----+
				for (int i = 0; i < racesTable.length(); i++) {
					//This gets a single row from a table
					// +------------+---------+-----------+-----+-----+-----+
					// | Łodzianin  |     100 |       200 |  10 |  10 |  10 |
					// +------------+---------+-----------+-----+-----+-----+
					JSONObject tableRow = racesTable.getJSONObject(i);
					// Storing each json item in List<String> object.
					try {
						//Now we get a single elements from each column.
						// +------------+
						// | Łodzianin  |
						// +------------+
						String temp;
						for(int j = 0; j < numberofRacesTableColumns; j++){
							//Now we need to put this into one of the lists
							// +------------+---------+-----------+-----+-----+-----+
							// | nazwa_rasy | base_hp | base_mana | STR | DEX | INT |
							// +------------+---------+-----------+-----+-----+-----+
							temp = tableRow.getString(raceTableColumnIDs[j]);
							racesList.get(j).add(temp);
						}
					} catch (JSONException e) {
						Log.d("GetDataFromDatabase", message5);
					}
				}
				
				publishProgress(progress4);
				for (int i = 0; i < classesTable.length(); i++) {
					JSONObject tableRow = classesTable.getJSONObject(i);
					// Storing each json item in List<String> object.
					try {
						String temp;
						for(int j = 0; j < numberofclassesTableColumns; j++){
							temp = tableRow.getString(classTableColumnIDs[j]);
							classesList.get(j).add(temp);
						}
					} catch (JSONException e) {
						Log.d("GetDataFromDatabase", message5);
					}
				}

				isGetSuccessful = true;

			} else {
				isGetSuccessful = false;
				Log.d("GetDataFromDatabase", message7);
				return message8;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		publishProgress(progress5);

		return message6;
	}

}