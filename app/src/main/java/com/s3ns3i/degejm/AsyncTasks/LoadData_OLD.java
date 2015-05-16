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
public class LoadData_OLD extends AsyncTask <String, Void, Void>{
    private ProgressDialog pDialog;
    private Context context;
    private ArrayList<ArrayList<String>> racesList;
    private ArrayList<ArrayList<String>> classesList;
    private ArrayList<ArrayList<Items>> itemsList;
    GetDefaultCharacterDataFromTheServer GDCDFTS;
    GetItemsDataFromTheServer GIDFTS;

    // =====================Messages=======================
    private final String message1 = "Downloading data from database.";

    public LoadData_OLD(Context context, ArrayList<ArrayList<String>> racesList
            , ArrayList<ArrayList<String>> classesList
            , ArrayList<ArrayList<Items>> itemsList){
        this.context = context;
        this.racesList = racesList;
        this.classesList = classesList;
        this.itemsList = itemsList;
    }
    @Override
    protected Void doInBackground(String... urls) {
        racesList = new ArrayList<ArrayList<String>>();
        classesList = new ArrayList<ArrayList<String>>();
        itemsList = new ArrayList<ArrayList<Items>>();
        GDCDFTS = new GetDefaultCharacterDataFromTheServer(racesList, classesList);
        GIDFTS = new GetItemsDataFromTheServer(itemsList);
        GDCDFTS.executeOnExecutor(THREAD_POOL_EXECUTOR, urls[0], urls[1]);
//        GDCDFTS.execute(urls[0], urls[1]);
//        while(GDCDFTS.getStatus() == Status.RUNNING || GDCDFTS.getStatus() == Status.PENDING){
//            try {
//                wait(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        pDialog.setProgress(50);
        GIDFTS.executeOnExecutor(THREAD_POOL_EXECUTOR, urls[2]);
//        GIDFTS.execute(urls[2]);
//        while(GIDFTS.getStatus() == Status.RUNNING || GIDFTS.getStatus() == Status.PENDING){
//            try {
//                wait(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        pDialog.setProgress(100);
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("LoadData_OLD", message1);
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
}
