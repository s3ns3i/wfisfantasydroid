package com.s3ns3i.degejm.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.s3ns3i.degejm.Player.Items;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by s3ns3i on 2015-04-19.
 */
public class LoadData extends AsyncTask {
    private ProgressDialog pDialog;
    private Context context;
    private ArrayList<ArrayList<String>> racesList;
    private ArrayList<ArrayList<String>> classesList;
    private ArrayList<ArrayList<Items>> itemsList;
    GetDefaultCharacterDataFromTheServer GDCDFTS;
    GetItemsDataFromTheServer GIDFTS;

    // =====================Messages=======================
    private final String message1 = "Downloading data from database.";

    public LoadData(Context context, ArrayList<ArrayList<String>> racesList
            , ArrayList<ArrayList<String>> classesList
            , ArrayList<ArrayList<Items>> itemsList){
        this.context = context;
        this.racesList = racesList;
        this.classesList = classesList;
        this.itemsList = itemsList;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        racesList = new ArrayList<ArrayList<String>>();
        classesList = new ArrayList<ArrayList<String>>();
        itemsList = new ArrayList<ArrayList<Items>>();
        GDCDFTS = new GetDefaultCharacterDataFromTheServer(racesList, classesList);
        GIDFTS = new GetItemsDataFromTheServer(itemsList);
        GDCDFTS.execute();
        while(GDCDFTS.getStatus() == Status.RUNNING){}
        pDialog.setProgress(50);
        GIDFTS.execute();
        while(GIDFTS.getStatus()== Status.RUNNING){}
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
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        pDialog.dismiss();
    }
}
