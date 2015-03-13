package com.s3ns3i.degejm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
public class GetItemsDataFromTheServer extends AsyncTask<String, String, String> {
	// TODO Here I need to load items that players character is currently wearing.
	// ===============JSON related objects=================
	// Creating JSON Parser object
	private JSONParser jParser = new JSONParser();
	private static String url = "http://wfisfantasy.16mb.com/";
	private String[] tableNames = {"chest", "boots", "gloves", "helmet", "weapon", "ring", "offhand" }
	, tableColumnIDs = {"id", "name", "cost", "meele_defense", "magic_defense", "IMG", "meele_attack", "magic_attack", "two_hand"};
	private JSONArray[] itemsTable = new JSONArray[tableNames.length];
	private static final String TAG_SUCCESS = "success";

	// ===================Other objects====================
	// Progress Dialog
	
	private ProgressDialog pDialog;
	private Boolean isGetSuccessful;
	//private ListAdapter 
	//private ListAdapter itemsAdapter;
	//private ArrayList<ArrayList<ArrayList<String>>> itemsList;
	private ArrayList<ArrayList<Items>> itemsList;
	//private AlertDialog itemsListsDialogs;
	//private ArrayAdapter<String> itemsAdapter;
	//private View view;
    private Integer numberofItemTypes = Integer.valueOf(7);

	// ====================References======================
	private Context context;

	// =====================Messages=======================
	private final String[] message = { "Loading data from database."
			, "Getting data from the server..."
			, "Tables from the server: "
			, "The mapping doesn't exist or is not a JSONArray."
			, "No such mapping exists."
			, "Connected with the main server"
			, "Database is empty."
			, "Couldn't connect with the main server." }
	, progress = { "Attempting to connect with the main server..."
			, "Connected! Now making a tables..."
			, "Loading table with players items..."
			, "Getting layout elements references..."};
	//private Integer numberOfTasks= Integer.valueOf(progress.length);
	// ===================Constructor======================
	//GetItemsDataFromTheServer(ArrayList<ArrayList<ArrayList<String>>> itemsList, Context context, View view) {
	//GetItemsDataFromTheServer(ArrayList<ArrayList<Items>> itemsList, Context context, View view) {
	GetItemsDataFromTheServer(ArrayList<ArrayList<Items>> itemsList, Context context) {
		this.itemsList = itemsList;
		//this.itemsListsDialogs = itemsListsDialogs;
		this.context = context;
		//this.view = view;
	}

	/**
	 * Before starting background thread Show log message.
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Log.d("GetDataFromDatabase", message[0]);
		// First we need to open a progress dialog, so the main thread will stop
		// and wait for this
		// async task to end.
		pDialog = new ProgressDialog(context);
		pDialog.setMessage(progress[0]);
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.setProgress(0);
		pDialog.show();
	}

//	private Integer advanceProgress(Integer value, Integer numberOfTasks){
//		Double temp = (value.doubleValue() / numberOfTasks.doubleValue()) * 100.0;
//		return temp.intValue();
//	}
	/**
	 * getting All products from url
	 * */
	@Override
	protected String doInBackground(String... params) {
		// Getting tables from the server
		// 'paramID' - a list, that is used when you want to post something on server.
		List<NameValuePair> paramID = new ArrayList<NameValuePair>();
		JSONObject jsonItems;
		if(params.length == 1)
			jsonItems = jParser.makeHttpRequest(url + params[0], "GET", paramID);
		else{
			paramID.add(new BasicNameValuePair("id", params[1]));
		// getting JSON string from URL
		// At this point we get all tables that php could send us.
			jsonItems = jParser.makeHttpRequest(url + params[0], "POST", paramID);
		}

		try {
			// Checking for SUCCESS TAG
			publishProgress(progress[1]);
			int successItems = jsonItems.getInt(TAG_SUCCESS);
			if (successItems == 1) {
				//Pobierz itemy
				//P�tle tylko do jednego typu itemu.
				//konstruktory wymagaj� wszystkich stat�w, wi�c od razu b�dzie kr�tszy kod.
				//Ten poni�ej trzeba wyrzuci�.
				//Making an array of lists to store items in them.
				for(int i = 0; i < numberofItemTypes; i++){
					itemsList.add(new ArrayList<Items>());
				}
				JSONObject tableRow;
				
				//{"chest", "boots", "gloves", "helmet", "weapon", "ring", "offhand" }
				//Getting armors
				//[{"id":"1","name":"Szata Szelestu","cost":"1000","meele_defense":"10","magic_defense":"200","IMG":null},{"id":"3","name":"Szata Szelestu","cost":"1000","meele_defense":"10","magic_defense":"200","IMG":null}]
				itemsTable[0] = jsonItems.getJSONArray(tableNames[0]);
				//int lol = itemsTable[0].length();
				for(int i = 0; i < itemsTable[0].length(); i++){
					tableRow = itemsTable[0].getJSONObject(i);
					//  0       1      2           3                4            5          6               7              8
					//{"id", "name", "cost", "meele_defense", "magic_defense", "IMG", "meele_attack", "magic_attack", "two_hand"}
					//ChestArmor(Integer ID_, Integer meeleDefense_, Integer magicDefense_, Integer meeleAttack_, Integer magicAttack_, String imgURL_, String name_, Integer cost_)
					itemsList.get(0).add(new ChestArmor(
							tableRow.getInt(tableColumnIDs[0]),	//id
							tableRow.getInt(tableColumnIDs[3]),	//meeDef
							tableRow.getInt(tableColumnIDs[4]),	//magDef
							0,	//meeAtt
							0,	//magAtt
							tableRow.getString(tableColumnIDs[5]),	//imgURL
							tableRow.getString(tableColumnIDs[1]),	//name
							tableRow.getInt(tableColumnIDs[2])));	//cost
				}
				//Getting boots
				itemsTable[1] = jsonItems.getJSONArray(tableNames[1]);
				for(int i = 0; i < itemsTable[1].length(); i++){
					tableRow = itemsTable[1].getJSONObject(i);
					//  0       1      2           3                4            5          6               7              8
					//{"id", "name", "cost", "meele_defense", "magic_defense", "IMG", "meele_attack", "magic_attack", "two_hand"}
					//Boots(Integer ID_, Integer meeleDefense_, Integer magicDefense_, Integer meeleAttack_, Integer magicAttack_, String imgURL_, String name_, Integer cost_)
					itemsList.get(1).add(new Boots(
							tableRow.getInt(tableColumnIDs[0]),	//id
							tableRow.getInt(tableColumnIDs[3]),	//meeDef
							tableRow.getInt(tableColumnIDs[4]),	//magDef
							0,	//meeAtt
							0,	//magAtt
							tableRow.getString(tableColumnIDs[5]),	//imgURL
							tableRow.getString(tableColumnIDs[1]),	//name
							tableRow.getInt(tableColumnIDs[2])));	//cost
				}

				//Getting gloves
				itemsTable[2] = jsonItems.getJSONArray(tableNames[2]);
				for(int i = 0; i < itemsTable[2].length(); i++){
					tableRow = itemsTable[2].getJSONObject(i);
					//  0       1      2           3                4            5          6               7              8
					//{"id", "name", "cost", "meele_defense", "magic_defense", "IMG", "meele_attack", "magic_attack", "two_hand"}
					//Gloves(Integer ID_, Integer meeleDefense_, Integer magicDefense_, Integer meeleAttack_, Integer magicAttack_, String imgURL_, String name_, Integer cost_)
					itemsList.get(2).add(new Gloves(
							tableRow.getInt(tableColumnIDs[0]),	//id
							tableRow.getInt(tableColumnIDs[3]),	//meeDef
							tableRow.getInt(tableColumnIDs[4]),	//magDef
							0,	//meeAtt
							0,	//magAtt
							tableRow.getString(tableColumnIDs[5]),	//imgURL
							tableRow.getString(tableColumnIDs[1]),	//name
							tableRow.getInt(tableColumnIDs[2])));	//cost
				}

				//Getting helmets
				itemsTable[3] = jsonItems.getJSONArray(tableNames[3]);
				for(int i = 0; i < itemsTable[3].length(); i++){
					tableRow = itemsTable[3].getJSONObject(i);
					//  0       1      2           3                4            5          6               7              8
					//{"id", "name", "cost", "meele_defense", "magic_defense", "IMG", "meele_attack", "magic_attack", "two_hand"}
					//Helmet(Integer ID_, Integer meeleDefense_, Integer magicDefense_, Integer meeleAttack_, Integer magicAttack_, String imgURL_, String name_, Integer cost_)
					itemsList.get(3).add(new Helmet(
							tableRow.getInt(tableColumnIDs[0]),	//id
							tableRow.getInt(tableColumnIDs[3]),	//meeDef
							tableRow.getInt(tableColumnIDs[4]),	//magDef
							0,	//meeAtt
							0,	//magAtt
							tableRow.getString(tableColumnIDs[5]),	//imgURL
							tableRow.getString(tableColumnIDs[1]),	//name
							tableRow.getInt(tableColumnIDs[2])));	//cost
				}

				//Getting weapons
				itemsTable[4] = jsonItems.getJSONArray(tableNames[4]);
				for(int i = 0; i < itemsTable[4].length(); i++){
					tableRow = itemsTable[4].getJSONObject(i);
					//  0       1      2           3                4            5          6               7              8
					//{"id", "name", "cost", "meele_defense", "magic_defense", "IMG", "meele_attack", "magic_attack", "two_hand"}
					//Weapon(Integer ID_,  Integer meeleDefense_,  Integer magicDefense_,  Integer meeleAttack_,  Integer magicAttack_,  String imgURL_,  String name_,  Integer cost_, Boolean twoHand_)
					itemsList.get(4).add(new Weapon(
							tableRow.getInt(tableColumnIDs[0]),	//id
							tableRow.getInt(tableColumnIDs[3]),	//meeDef
							tableRow.getInt(tableColumnIDs[4]),	//magDef
							tableRow.getInt(tableColumnIDs[6]),	//meeAtt
							tableRow.getInt(tableColumnIDs[7]),	//magAtt
							tableRow.getString(tableColumnIDs[5]),	//imgURL
							tableRow.getString(tableColumnIDs[1]),	//name
							tableRow.getInt(tableColumnIDs[2]),	//cost
							Boolean.valueOf(tableRow.getString(tableColumnIDs[8]))));	//twohand
				}

				//Getting rings
				itemsTable[5] = jsonItems.getJSONArray(tableNames[5]);
				for(int i = 0; i < itemsTable[5].length(); i++){
					tableRow = itemsTable[5].getJSONObject(i);
					//  0       1      2           3                4            5          6               7              8
					//{"id", "name", "cost", "meele_defense", "magic_defense", "IMG", "meele_attack", "magic_attack", "two_hand"}
					//Ring(Integer ID_, Integer meeleDefense_, Integer magicDefense_, Integer meeleAttack_, Integer magicAttack_, String imgURL_, String name_, Integer cost_)
					itemsList.get(5).add(new Ring(
							tableRow.getInt(tableColumnIDs[0]),	//id
							tableRow.getInt(tableColumnIDs[3]),	//meeDef
							tableRow.getInt(tableColumnIDs[4]),	//magDef
							tableRow.getInt(tableColumnIDs[6]),	//meeAtt
							tableRow.getInt(tableColumnIDs[7]),	//magAtt
							tableRow.getString(tableColumnIDs[5]),	//imgURL
							tableRow.getString(tableColumnIDs[1]),	//name
							tableRow.getInt(tableColumnIDs[2])));	//cost
				}

				//Getting offhands
				itemsTable[6] = jsonItems.getJSONArray(tableNames[6]);
				for(int i = 0; i < itemsTable[6].length(); i++){
					tableRow = itemsTable[6].getJSONObject(i);
					//  0       1      2           3                4            5          6               7              8
					//{"id", "name", "cost", "meele_defense", "magic_defense", "IMG", "meele_attack", "magic_attack", "two_hand"}
					//Offhand(Integer ID_, Integer meeleDefense_, Integer magicDefense_, Integer meeleAttack_, Integer magicAttack_, String imgURL_, String name_, Integer cost_)
					itemsList.get(6).add(new Offhand(
							tableRow.getInt(tableColumnIDs[0]),	//id
							tableRow.getInt(tableColumnIDs[3]),	//meeDef
							tableRow.getInt(tableColumnIDs[4]),	//magDef
							0,	//meeAtt
							0,	//magAtt
							tableRow.getString(tableColumnIDs[5]),	//imgURL
							tableRow.getString(tableColumnIDs[1]),	//name
							tableRow.getInt(tableColumnIDs[2])));	//cost
				}
				

				//DO WYWALENIA
				//Initialize list according to number of items.
//				for(int i = 0; i < jsonItems.length(); i++){
//					itemsList.add(new ArrayList<ArrayList<String>>());
//					//Making suitable Lists.
//					//Some items have less attributes, so we need to constantly check.
//					JSONObject tableRow = itemsTable[i].getJSONObject(0);
//					for(int j = 0; j < tableRow.length(); j++){
//						itemsList.get(i).add(new ArrayList<String>());
//					}
//				}
				
				// Looping through all types of items (currently there's only 7 types of items)
//				for(int i = 0; i < tableNames.length; i++){
//					itemsList.add(new ArrayList<ArrayList<String>>());
//					try {
//						//Getting table with items of one type. (For example boots)
//						itemsTable[i] = jsonItems.getJSONArray(tableNames[i]);
//					} catch (JSONException e) {
//						Log.d("GetDataFromDatabase", message[3] + " " + tableNames[i]);
//					}
//	
//					// There we have to get items names and their stats.
//					// It looks like this:
//					// +----+-----------+-----------+------+----------------------------+-----------+----------+
//					// | id | meele_def | magic_def | cost | name                       | meele_att | two_hand |
//					// +----+-----------+-----------+------+----------------------------+-----------+----------+
//					// | 2  |       100 |        30 |   20 | Cichobiegi Betlejemskie I  |         0 |        0 |
//					// +----+-----------+-----------+------+----------------------------+-----------+----------+
//					// | 2  |        20 |        10 |   20 | Gintoki's Black Boots      |         0 |        0 |
//					// +----+-----------+-----------+------+----------------------------+-----------+----------+
//					// | 2  |         5 |         5 |    5 | Cichobiegi Betlejemskie II |         0 |        0 |
//					// +----+-----------+-----------+------+----------------------------+-----------+----------+
//					//Looping through all items of one type.
//					for (int j = 0; j < itemsTable[i].length(); j++) {
//						itemsList.get(i).add(new ArrayList<String>());
//						//This gets a single row from a table
//						// +----+-----------+-----------+------+---------------------------+-----------+----------+
//						// | 2  |       100 |        30 |   20 | Cichobiegi Betlejemskie I |         0 |        0 |
//						// +----+-----------+-----------+------+---------------------------+-----------+----------+
//						JSONObject tableRow = itemsTable[i].getJSONObject(j);
//						// Storing each json item in List<String> object.
//						try {
//							String temp;
//							//Now we get a single elements from each column.
//							// +----+
//							// | 2  |
//							// +----+
//							//Looping through all atributes of one item.
//							for(int k = 0; k < tableRow.length(); k++){
//								//Now we need to put this into one of the lists
//								// +----+-----------+-----------+------+-----------------------+-----------+----------+
//								// | id | meele_def | magic_def | cost | name                  | meele_att | two_hand |
//								// +----+-----------+-----------+------+-----------------------+-----------+----------+
//								temp = tableRow.getString(tableColumnIDs[k]);
//								itemsList.get(i).get(j).add(temp);
//							}
//						} catch (JSONException e) {
//							Log.d("GetDataFromDatabase", message[4]);
//						}
//
//						Log.e("itemsList", itemsList.toString());
//					}
	
					isGetSuccessful = true;
//				}
				// We get only names of the items.
				//itemsAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice, itemsList.get(0).get(1));
				
			} else {
				isGetSuccessful = false;
				Log.d("GetDataFromDatabase", message[6]);
				return message[7];
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// Populating adapters with data
//		itemsAdapter = new ArrayAdapter<String>(context,
//				android.R.layout.simple_spinner_dropdown_item, itemsList.get(0));

		return message[5];
	}

	protected void onProgressUpdate(String progress) {
		pDialog.setMessage(progress);
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	@Override
	protected void onPostExecute(String connectionStatus) {
		Log.d("GetDataFromDatabase", connectionStatus);
		// Put items through adapter to the dialogs.
		//Create Dialogs in PlayerEquipmentFragment and pass it there as an argument.
		//Fill it with data.
		
		pDialog.dismiss();
	}

	public Boolean isGetSuccessful() {
		return isGetSuccessful;
	}
	
	protected void preparePlayersItems(){
		
	}
	
	protected void prepareAllItems(){
		
	}

}