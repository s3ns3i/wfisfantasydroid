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
 * TODO Jednak wszystko b�dzie w jednym module. Tzn, ka�dy musi zrobi�
 * wczytywanie danych z serwera na sw�j spos�b (je�li potrzebuje) Tutaj trzeba
 * zrobi�:
 * - wczytanie tw�ch tablic do spinner�w,
 * 	-- klasy
 * 	-- rasy
 * - wczytanie danych do trzech zmiennych i wy�wietli� je (pocz�tkowe staty)
 * 	--si�a
 * 	-- zr�czno��
 * 	-- inteligencja
 * 
 * @author s3ns3i
 * 
 */
public class GetDefaultCharacterDataFromTheServer extends
		AsyncTask<String, String, String> {

	// ===============JSON related objects=================
	// Creating JSON Parser object
	private JSONParser jParser = new JSONParser();
	private static String url = "http://wfisfantasy.16mb.com/";
	private JSONArray racesTable = null, classesTable = null;
	private static final String TAG_SUCCESS = "success";

	// ===================Other objects====================
	// Progress Dialog
	private String []tableNames = {"race", "classes"}
			, raceTableColumnIDs = {"nazwa_rasy", "base_hp", "base_mana", "STR", "DEX", "INT"}
			, classTableColumnIDs = {"class_name", "magic_attack", "meele_attack", "magic_defense", "meele_defense", "hp_modifier", "mana_modifier"};
	
	private ProgressDialog pDialog;
	private Boolean isGetSuccessful;
	private ArrayAdapter<String> racesAdapter, classesAdapter;
	private ArrayList<ArrayList<String>> racesList, classesList;
	private View view;
	private CharacterCreationFragment characterCreation;
    private Integer numberofRacesTableColumns = Integer.valueOf(6);
    private Integer numberofclassesTableColumns = Integer.valueOf(7);

	// ====================References======================
	private Context context;
	private Spinner racesSpinner, classesSpinner;
	private TextView baseHPTV, baseManaTV, strTV, agiTV, intTV;

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
	public GetDefaultCharacterDataFromTheServer(ArrayList<ArrayList<String>> racesList, ArrayList<ArrayList<String>> classesList, Context context, View view, CharacterCreationFragment characterCreation) {
		this.racesList = racesList;
		this.classesList = classesList;
		this.context = context;
		this.view = view;
		this.characterCreation = characterCreation;
		
		for(int i = 0; i < numberofRacesTableColumns; i++){
			this.racesList.add(new ArrayList<String>());
		}
		
		for(int j = 0; j < numberofclassesTableColumns; j++){
			this.classesList.add(new ArrayList<String>());
		}
	}

	/**
	 * Before starting background thread Show log message.
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Log.d("GetDataFromDatabase", message1);
		// First we need to open a progress dialog, so the main thread will stop
		// and wait for this
		// async task to end.
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(message2);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.setProgress(0);
		pDialog.show();
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
		// Populating adapters with data
		racesAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_dropdown_item, racesList.get(0));
		classesAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_dropdown_item, classesList.get(0));
		// Now loading those tables to spinners
		racesSpinner = (Spinner) view.findViewById(R.id.raceSpinner);
		classesSpinner = (Spinner) view.findViewById(R.id.classSpinner);
		baseHPTV = (TextView) view.findViewById(R.id.baseHPValueTextView);
		baseManaTV = (TextView) view.findViewById(R.id.baseManaValueTextView);
		strTV = (TextView) view.findViewById(R.id.strengthValueTextView);
		agiTV = (TextView) view.findViewById(R.id.agilityValueTextView);
		intTV = (TextView) view.findViewById(R.id.intelligenceValueTextView);

		return message6;
	}

	protected void onProgressUpdate(String progress) {
		pDialog.setMessage(progress);
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	protected void onPostExecute(String connectionStatus) {
		Log.d("GetDataFromDatabase", connectionStatus);
		if(isGetSuccessful){
		//Setting adapters to spinners.
		racesSpinner.setAdapter(racesAdapter);
		classesSpinner.setAdapter(classesAdapter);
		//Set basic stats
		characterCreation.addToStatsArray(
				characterCreation.processStat(
						racesList.get(1).get(0), classesList.get(5).get(0)
						));
		baseHPTV.setText(characterCreation.getStatString(0));
		characterCreation.addToStatsArray(
				characterCreation.processStat(
						racesList.get(2).get(0), classesList.get(6).get(0)
				));
		baseManaTV.setText(characterCreation.getStatString(1));
		characterCreation.addToStatsArray(racesList.get(3).get(0));
		characterCreation.addToStatsArray(racesList.get(4).get(0));
		characterCreation.addToStatsArray(racesList.get(5).get(0));
		strTV.setText(racesList.get(3).get(0));
		agiTV.setText(racesList.get(4).get(0));
		intTV.setText(racesList.get(5).get(0));
		}
		else{
			Log.d("GetDataFromDatabase", message7);
		}
		pDialog.dismiss();
	}

	public Boolean isGetSuccessful() {
		return isGetSuccessful;
	}

}