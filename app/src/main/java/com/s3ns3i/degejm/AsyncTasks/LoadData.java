package com.s3ns3i.degejm.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.s3ns3i.degejm.JSONParser;
import com.s3ns3i.degejm.Player.Equipment.Boots;
import com.s3ns3i.degejm.Player.Equipment.ChestArmor;
import com.s3ns3i.degejm.Player.Equipment.Gloves;
import com.s3ns3i.degejm.Player.Equipment.Helmet;
import com.s3ns3i.degejm.Player.Equipment.Offhand;
import com.s3ns3i.degejm.Player.Equipment.Ring;
import com.s3ns3i.degejm.Player.Equipment.Weapon;
import com.s3ns3i.degejm.Player.Items;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s3ns3i on 2015-05-16.
 */
public class LoadData extends AsyncTask<String, Void, Void> {
    private ProgressDialog pDialog;
    private Context context;
    private ArrayList<ArrayList<String>> racesList;
    private ArrayList<ArrayList<String>> classesList;
    private ArrayList<ArrayList<Items>> itemsList;

    // ===============JSON related objects=================
    // Creating JSON Parser object.
    private JSONParser jParser = new JSONParser();
    //Link to the server.
    private static String url = "http://wfisfantasy.16mb.com/";
    //Arrays that will hold downloaded races and classes.
    private JSONArray racesTable = null, classesTable = null;
    //Tag checking if download from the server succeeded.
    private static final String TAG_SUCCESS = "success";
    private String[] tablesNames = {"race", "classes", "chest", "boots", "gloves", "helmet", "weapon", "ring", "offhand" }
            , tableColumnIDs = {"id", "name", "cost", "meele_defense", "magic_defense", "IMG", "meele_attack", "magic_attack", "two_hand"};
    private JSONArray[] itemsTable = new JSONArray[tablesNames.length - 2]; // Minus "race" and "classes" tables.

    // ===================Other objects====================
    //Columns names.
    private String [] raceTableColumnIDs = {"nazwa_rasy", "base_hp", "base_mana", "STR", "DEX", "INT"}
            , classTableColumnIDs = {"class_name", "magic_attack", "meele_attack", "magic_defense", "meele_defense", "hp_modifier", "mana_modifier"};
    //
    private Boolean isGetSuccessful;
    private Integer numberofRacesTableColumns = 6;
    private Integer numberofclassesTableColumns = 7;
    private Integer numberofItemTypes = 7;

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


    public LoadData(Context context, ArrayList<ArrayList<String>> racesList
            , ArrayList<ArrayList<String>> classesList
            , ArrayList<ArrayList<Items>> itemsList){
        this.context = context;
        this.racesList = racesList;
        this.classesList = classesList;
        this.itemsList = itemsList;
    }

    /**
     *
     * @param params - first param should be an url to the races php, second the url to the classes php, third an url to the items php and the fourth an users ID.
     * @return
     */
    @Override
    protected Void doInBackground(String... params) {
        downloadCharacterData(params[0], params[1]);
//        GDCDFTS = new GetDefaultCharacterDataFromTheServer(racesList, classesList);
//        GIDFTS = new GetItemsDataFromTheServer(itemsList);
//        GDCDFTS.executeOnExecutor(THREAD_POOL_EXECUTOR, urls[0], urls[1]);
        pDialog.setProgress(50);
        // Four, because - read description of this method.
        if(params.length < 4){
            downloadItems(params[2], null);
        }
        else {
            downloadItems(params[2], params[3]);
        }
//        GIDFTS.executeOnExecutor(THREAD_POOL_EXECUTOR, urls[2]);
        pDialog.setProgress(100);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("LoadData", message1);
        // First we need to open a progress dialog, so the main thread will stop
        // and wait for this
        // async task to end.
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(message1);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.setProgress(0);
        pDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        pDialog.dismiss();
    }

    /**
     * Method that downloads all data necessary to create a character (without items).
     * @param racesURL - relative url to the php site that returns JSON string of races.
     * @param classesURL - relative url to the php site that returns JSON string of classes.
     */
    protected void downloadCharacterData(String racesURL, String classesURL){
        // Getting table/s from the server
        // I named this list 'dummyList', 'cause I can't figure out how it's
        // used in JSONParser.
        List<NameValuePair> dummyList = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        // At this point we get all tables that php could send us.
//        publishProgress(progress1);
        JSONObject jsonRaces = jParser.makeHttpRequest(url + racesURL, "GET", dummyList);
        JSONObject jsonClasses = jParser.makeHttpRequest(url + classesURL, "GET", dummyList);

        // Check your log cat for JSON reponse
        Log.d("GetDataFromDatabase", message3 + jsonRaces.toString());
        Log.d("GetDataFromDatabase", message3 + jsonClasses.toString());

        try {
            // Checking for SUCCESS TAG
//            publishProgress(progress2);
            int successRaces = jsonRaces.getInt(TAG_SUCCESS);
            int successClasses = jsonClasses.getInt(TAG_SUCCESS);
            if (successRaces == 1 && successClasses == 1) {

                //Making a list of races to store races in them.
                for(int i = 0; i < numberofRacesTableColumns; i++){
                    racesList.add(new ArrayList<String>());
                }
                //Making a list of classes to store classes in them.
                for(int i = 0; i < numberofclassesTableColumns; i++){
                    classesList.add(new ArrayList<String>());
                }
                // Getting desired array (stated in 'tableName' variable passed
                // as an argument).
                // Now we'll have only this table that we want. In my case it'll
                // be class table and race table. //s3ns3i
                try {
                    racesTable = jsonRaces.getJSONArray(tablesNames[0]);
                    classesTable = jsonClasses.getJSONArray(tablesNames[1]);
                } catch (JSONException e) {
                    Log.d("GetDataFromDatabase", message4);
                }

                // Had to make two loops with exact code, because tables may
                // have different sizes.
//                publishProgress(progress3);
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

//                publishProgress(progress4);
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
                return;
//                return message8;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        publishProgress(progress5);

//        return message6;
    }

    /**
     * Method that has two functions:
     * - if userID is null, then it downloads all items available on the server,
     * - if userID is passed, it downloads only items that users character possess.
     * @param phpURL - relative url to the php site returning JSON string of items.
     * @param userID - users ID.
     */
    protected void downloadItems(String phpURL, String userID){
        // Getting tables from the server
        // 'paramID' - a list, that is used when you want to post something on server.
        List<NameValuePair> paramID = new ArrayList<NameValuePair>();
        JSONObject jsonItems;
        if(userID == null)
            jsonItems = jParser.makeHttpRequest(url + phpURL, "GET", paramID);
        else{
            paramID.add(new BasicNameValuePair("id", userID));
            // getting JSON string from URL
            // At this point we get all tables that php could send us.
            jsonItems = jParser.makeHttpRequest(url + phpURL, "POST", paramID);
        }

        try {
            // Checking for SUCCESS TAG
//            publishProgress(progress[1]);
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
                itemsTable[0] = jsonItems.getJSONArray(tablesNames[2]);
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
                itemsTable[1] = jsonItems.getJSONArray(tablesNames[3]);
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
                itemsTable[2] = jsonItems.getJSONArray(tablesNames[4]);
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
                itemsTable[3] = jsonItems.getJSONArray(tablesNames[5]);
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
                itemsTable[4] = jsonItems.getJSONArray(tablesNames[6]);
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
                itemsTable[5] = jsonItems.getJSONArray(tablesNames[7]);
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
                itemsTable[6] = jsonItems.getJSONArray(tablesNames[8]);
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
                Log.d("GetDataFromDatabase", message5);
//                return message[7];
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        return message[5];
    }
}
