package com.s3ns3i.degejm.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.s3ns3i.degejm.Player.Equipment.Boots;
import com.s3ns3i.degejm.Player.Equipment.ChestArmor;
import com.s3ns3i.degejm.Player.Equipment.Gloves;
import com.s3ns3i.degejm.Player.Equipment.Helmet;
import com.s3ns3i.degejm.Player.Items;
import com.s3ns3i.degejm.JSONParser;
import com.s3ns3i.degejm.Player.Equipment.Offhand;
import com.s3ns3i.degejm.Player.Equipment.Ring;
import com.s3ns3i.degejm.Player.Equipment.Weapon;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Jednak wszystko będzie w jednym module. Tzn, każdy musi zrobić
 * wczytywanie danych z serwera na swój sposób (jeśli potrzebuje) Tutaj trzeba
 * zrobić:
 * - wczytanie twóch tablic do spinnerów,
 * 	-- klasy
 * 	-- rasy
 * - wczytanie danych do trzech zmiennych i wyświetlić je (początkowe staty)
 * 	--siła
 * 	-- zręczność
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
	private ArrayList<ArrayList<Items>> itemsList;
    private Integer numberofItemTypes = 7;

	// ====================References======================
//	private Context context;

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
	// ===================Constructor======================
	public GetItemsDataFromTheServer(ArrayList<ArrayList<Items>> itemsList) {
		this.itemsList = itemsList;
	}
	/**
	 * getting All products from url
	 * */
	@Override
	protected String doInBackground(String... params) {
		// Getting tables from the server
		// 'paramID' - a list, that is used when you want to post something on server.
		List<NameValuePair> paramID = new ArrayList<NameValuePair>();
		JSONObject jsonItems;
//		if(params.length == 1)
			// getting JSON string from URL
			// At this point we get all tables that php could send us.
			jsonItems = jParser.makeHttpRequest(url + params[0], "GET", paramID);
//		else{
//			paramID.add(new BasicNameValuePair("id", params[1]));
//			jsonItems = jParser.makeHttpRequest(url + params[0], "POST", paramID);
//		}

		try {
			// Checking for SUCCESS TAG
			publishProgress(progress[1]);
			int successItems = jsonItems.getInt(TAG_SUCCESS);
			if (successItems == 1) {
				//Pobierz itemy
				//Pętle tylko do jednego typu itemu.
				//konstruktory wymagają wszystkich statów, więc od razu będzie krótszy kod.
				//Ten poniżej trzeba wyrzucić.
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
			} else {
				Log.d("GetDataFromDatabase", message[6]);
				return message[7];
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return message[5];
	}
}
