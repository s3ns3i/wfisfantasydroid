package com.s3ns3i.degejm.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.s3ns3i.degejm.JSONParser;
import com.s3ns3i.degejm.Player.Player;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s3ns3i on 2015-03-25.
 */
public class GetPlayer extends AsyncTask <String, Void, Void> {

    private Context context;
    private Player player;
    private ProgressDialog pDialog;
    private JSONParser jsonParser;
    private JSONObject json;
    private List<NameValuePair> params;
    private final String playerIDKey = "id";

    // url to the server
    private String url = "http://wfisfantasy.16mb.com/";

    private static final String TAG_SUCCESS = "success";

    public GetPlayer(/*String phpURL, */Context context, Player player){
//        this.context = context;
        this.player = player;
        jsonParser = new JSONParser();
        params = new ArrayList<NameValuePair>();
//        this.url += phpURL;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading player's data...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.setProgress(0);
        pDialog.show();
    }

    @Override
    protected Void doInBackground(String... armorURL) {
        params.add(new BasicNameValuePair(playerIDKey, player.getPlayerID_()));
        json = jsonParser.makeHttpRequest(url + armorURL[0], "POST", params);
        try {
            int success = json.getInt(TAG_SUCCESS);
            if(success == 1){
                Log.d("GetPlayer", json.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        pDialog.dismiss();
    }
}
