package com.s3ns3i.degejm;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by s3ns3i on 2015-03-25.
 */
public class GetPlayer extends AsyncTask <Void, Void, Void> {

    Context context;
    Player player;
    ProgressDialog pDialog;
    JSONParser jsonParser;
    JSONObject json;
    private List<NameValuePair> params;

    // url to the server
    private static String url = "http://wfisfantasy.16mb.com/";

    private static final String TAG_SUCCESS = "success";

    public GetPlayer(String phpURL, Context context, Player player, String url){
        this.context = context;
        this.player = player;
        jsonParser = new JSONParser();
        url += phpURL;
    }

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

    @Override
    protected Void doInBackground(Void... voidParams) {
        json = jsonParser.makeHttpRequest(url, "POST", params);
        try {
            int success = json.getInt(TAG_SUCCESS);
            if(success == 1){

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
