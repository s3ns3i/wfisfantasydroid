package com.s3ns3i.degejm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Dobra, to dzia�a tak:
 * Klasa dostaje trzy parametry	- url danego pliku php (bez adresu g��wnego, kt�ry wida� ni�ej w zmiennej "url",
 * 								- list� z parametrami, kt�re chcemy wys�a� na serwer,
 * =================================================TO OLA� - NIE DZIA�A===================================================
 * 								- kontekst aktywno�ci, kt�ra wywo�a�a ten w�tek (aktywno�� musi wiedzie�, �e otworzy si�
 * 								  okienko �adowania, �eby nie robi� �adnych operacji w tym czasie).
 * =================================================TO OLA� - NIE DZIA�A===================================================
 * W konstruktorze inicjuj� obiekt JSONParser, list� parametr�w i doklejam do g��wnego adresu ten przekazany jako argument.
 * Jak wystartuje w�tek (new Sender().execute(); - nie tworzymy obiektu jawnie, bo nie trzeba), to najpierw wysy�a do
 * loga info, �e b�dzie komunikowa� si� z serwerem. Potem jsonParser rozpoczyna komunikacj�.
 * Je�li "playerJson.getInt(TAG_SUCCESS);" zwr�ci "1" to znaczy, �e wszystko kul (wys�a� dane na serwa),
 * w przeciwnym wypadku, wiadomo.
 * Na dodatek 
 * 
 * TODO Kurde, trzeba zrobi� wy�wietlanie toast'�w, bo u�ytkownik nie b�dzie wiedzia� czy si� uda�o czy nie.
 * @author s3ns3i
 *
 */

/**
 * Background Async Task to Create new player
 * */
public class SendNewCharacterToTheServer extends
		AsyncTask<String, String, String> {

	private JSONObject playerJson, itemsJson;
	private JSONParser jsonParser;
	private List<NameValuePair> params;
	private Boolean isPostSuccessful;
	private Context context;
	private Player player;

	// url to the server
	private static String url = "http://wfisfantasy.16mb.com/";
    private String registerURL;
    private String armorURL;

	// JSON Node name - this checks if posting to the server succeeded.
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_ID = "player_id";
	private static final String FILE_NAME = "DO_NOT_LOOK_HERE.dkb";

	// Progress Dialog
	private ProgressDialog pDialog;
	/**
	 * Constructor for this class
	 * 
	 * @param phpURL
	 *            - we need to give here a php page address, where we have
	 *            posting code.
	 * @param params
	 *            - these are elements that we want to post to the server.
	 */
	SendNewCharacterToTheServer(String phpURL, List<NameValuePair> params,
			Context context, Player player) {
		jsonParser = new JSONParser();
		this.params = params;
		isPostSuccessful = false;
		this.context = context;
		this.player = player;
		registerURL = url + phpURL;
        armorURL = url + "get_armor.php";
	}

	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Log.d("PostDataToDatabase", "Sending request to the server.");
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Sending data to the server...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}

	/**
	 * Creating player
	 * */
	protected String doInBackground(String... args) {
		playerJson = jsonParser.makeHttpRequest(registerURL, "POST", params);
        //itemsJson = jsonParser.makeHttpRequest(armorURL, "POST", params);

		// check for success tag
		try {
			int success = playerJson.getInt(TAG_SUCCESS);

			if (success == 1) {
				// successfully created player
				Log.d("PostDataToDatabase", "Post was successful.");
				isPostSuccessful = true;
				
				//===========================Write data to Player object.==============================
//                player.setCharacterName_(params.get(1).getValue());
				player.setCharacterName_(playerJson.getString("name"));
				/**	TODO
				 * Nie mog� ustawi� nazwy rasy ani nazwy klasy.
				 * Tylko te warto�ci s� wysy�ane na serwer, wi�c r�wnie dobrze mog� od razu te warto�ci doda�
				 * na wst�pie a potem doda� inne. Ale znowu:
				 * - CharacterRace to klasa pusta
				 * - Konstruktor klasy CharacterClass wymaga pe�no zmiennych, kt�rych w danym momencie nie posiadam.
				 * Gdybym m�g� utworzy� pusty obiekt to po kropce tylko wywo�a�bym setClassName i po krzyku.
				 */
				//player.setCharacterRace_(new CharacterRace().setRaceName_(playerJson.getString("race")));
				//player.setClassName_(new CharacterClass().setClassName_(playerJson.getString("class")));
//                CharacterRace CR = new CharacterRace();
//                CR.setRaceName_(params.get(2).getValue());
//                CR.setRaceID(Integer.valueOf(params.get(3).getValue()));
//                CharacterClass CC = new CharacterClass();
//                CC.setClassName_(params.get(4).getValue());
//                CC.setClassID_(Integer.valueOf(params.get(5).getValue()));
                //player.setCharacterRace_(new CharacterRace().setRaceName_(playerJson.getString("race")));
                //player.setClassName_(new CharacterClass().setClassName_(playerJson.getString("class")));
//                player.setStrength_(Integer.valueOf(params.get(6).getValue()));
//                player.setAgility_(Integer.valueOf(params.get(7).getValue()));
//                player.setInteligence_(Integer.valueOf(params.get(8).getValue()));
                //ChestArmor chestArmor = new ChestArmor();
				player.setStrength_(playerJson.getInt("str"));
				player.setAgility_(playerJson.getInt("agi"));
				player.setInteligence_(playerJson.getInt("int"));
				player.setHealthPoints_(playerJson.getInt("hp"));
				player.setManaPoints_(playerJson.getInt("mana"));
			} else {
				// failed to create player
				Log.d("PostDataToDatabase", "Post was unsuccessful.");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	// @Override
	protected void onPostExecute(String file_url) {
		// dismiss the dialog once done
		Log.d("PostDataToDatabase", "Done sending data to the server.");
		try {
			//FileOutputStream outputStream = new FileOutputStream(file);
			//Save given ID to the external storage.
			FileManager.writeFile(context.getFilesDir(), FILE_NAME, playerJson.getString(TAG_ID));
			String data = FileManager.readFile(context.getFilesDir(), FILE_NAME);
			pDialog.dismiss();
			//Show a toast telling user that his character was created.
			Toast.makeText(context, playerJson.getString(TAG_MESSAGE) + " : " + data, Toast.LENGTH_SHORT).show();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Boolean isPostSuccessful() {
		return isPostSuccessful;
	}
}
