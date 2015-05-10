package com.s3ns3i.degejm.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.s3ns3i.degejm.FileManager;
import com.s3ns3i.degejm.JSONParser;
import com.s3ns3i.degejm.Player.Player;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Dobra, to działa tak:
 * Klasa dostaje trzy parametry	- url danego pliku php (bez adresu głównego, który widać niżej w zmiennej "url",
 * 								- listę z parametrami, które chcemy wysłać na serwer,
 * W konstruktorze inicjują obiekt JSONParser, listę parametrów i doklejam do głównego adresu ten przekazany jako argument.
 * Jak wystartuje wątek (new Sender().execute(); - nie tworzymy obiektu jawnie, bo nie trzeba), to najpierw wysyła do
 * loga info, że będzie komunikować się z serwerem. Potem jsonParser rozpoczyna komunikację.
 * Jeśli "playerJson.getInt(TAG_SUCCESS);" zwróci "1" to znaczy, że wszystko kul (wysłać dane na serwa),
 * w przeciwnym wypadku, wiadomo.
 * Na dodatek 
 * 
 * TODO Kurde, trzeba zrobić wyświetlanie toast'ów, bo użytkownik nie będzie wiedział czy się udało czy nie.
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
//	private ProgressDialog pDialog;
	/**
	 * Constructor for this class
	 * 
	 * @param phpURL
	 *            - we need to give here a php page address, where we have
	 *            posting code.
	 * @param params
	 *            - these are elements that we want to post to the server.
	 */
	public SendNewCharacterToTheServer(String phpURL, List<NameValuePair> params,
			Context context, Player player) {
		jsonParser = new JSONParser();
		this.params = params;
		this.context = context;
		this.player = player;
		registerURL = url + phpURL;
        armorURL = url + "get_armor.php";
	}

	/**
	 * Creating player
	 * */
	protected String doInBackground(String... args) {
		playerJson = jsonParser.makeHttpRequest(registerURL, "POST", params);
        itemsJson = jsonParser.makeHttpRequest(armorURL, "POST", params);

		// check for success tag
		try {
			int registerSuccess = playerJson.getInt(TAG_SUCCESS);
			int getEquipSuccess = itemsJson.getInt(TAG_SUCCESS);

			if (registerSuccess == 1 && getEquipSuccess == 1) {
				// successfully created player
				Log.d("PostDataToDatabase", "Post was successful.");
				Log.d("PostDataToDatabase", playerJson.toString());
				Log.d("PostDataToDatabase", itemsJson.toString());
				//===========================Write data to Player object.==============================
//                player.setCharacterName_(params.get(1).getValue());
				player.setCharacterName_(playerJson.getString("name"));
				/**	TODO
				 * Nie mog ustawić nazwy rasy ani nazwy klasy.
				 * Tylko te wartości są wysyłane na serwer, więc równie dobrze mogę od razu te wartości dodać
				 * na wstępie a potem dodać inne. Ale znowu:
				 * - CharacterRace to klasa pusta
				 * - Konstruktor klasy CharacterClass wymaga pełno zmiennych, których w danym momencie nie posiadam.
				 * Gdybym mógł utworzyć pusty obiekt to po kropce tylko wywołałbym setClassName i po krzyku.
				 */
				player.setStrength_(playerJson.getInt("str"));
				player.setAgility_(playerJson.getInt("agi"));
				player.setInteligence_(playerJson.getInt("int"));
				player.setHealthPoints_(playerJson.getInt("hp"));
				player.setManaPoints_(playerJson.getInt("mana"));
				// TODO Nie są wysyłane wszystkie dane. Brakuje ekwipunku i być może czegoś jeszcze.
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
}
