package com.s3ns3i.degejm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.s3ns3i.degejm.AsyncTasks.GetItemsDataFromTheServer;
import com.s3ns3i.degejm.AsyncTasks.GetPlayer;
import com.s3ns3i.degejm.AsyncTasks.LoadData;
import com.s3ns3i.degejm.AsyncTasks.LoadData_OLD;
import com.s3ns3i.degejm.Fragments.CharacterCreationFragment;
import com.s3ns3i.degejm.Fragments.NavigationDrawerFragment;
import com.s3ns3i.degejm.Fragments.PlayerEquipmentFragment;
import com.s3ns3i.degejm.Player.Items;
import com.s3ns3i.degejm.Player.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class MainDrawerActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    //Objects from Character Creation Window
    private final String registerURL = "register.php"
            , racesURL = "get_races.php"
            , classesURL = "get_classes.php"
            , itemsURL = "get_all_items.php"
            , currentEquipmentURL = "get_armor.php";
    public final static String singlePlayerKey = "single_player", nameKey = "name", raceKey = "race", raceIDKey = "race_id"
            , classKey = "class", classIDKey = "class_id", chestKey = "chest", bootsKey = "boots", glovesKey = "gloves"
            , helmetKey = "helm", weaponKey = "weapon", firstRingKey = "ring", secondRingKey = "ring2", offhandKey = "offhand"
            , strKey = "str", agiKey = "agi", intKey = "int", hpKey = "hp", manaKey = "mana", expKey = "exp", bundleKey = "bundle";
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    //Objects necessary to open character creation window and equipment.
    ArrayList<ArrayList<String>> racesList;
    ArrayList<ArrayList<String>> classesList;
    ArrayList<ArrayList<Items>> itemsList;
//    List<NameValuePair> params;
//    GetItemsDataFromTheServer GIDFTS;
//    LoadData_OLD loadDataOLD;
    LoadData loadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileWeb = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(!(wifi.isConnected() || mobileWeb.isConnected())) {
//            NoWiFi noWiFi = NoWiFi.newInstance(NoWiFi.WAIT);
            NoWiFi noWiFi = NoWiFi.newInstance();
            noWiFi.show(getFragmentManager(), "noWiFiError");
            return;
        }

        player = new Player();
//        loadDataOLD = new LoadData_OLD(getApplicationContext());
//        loadDataOLD.execute();
        //Workaround. Pod żadnym pozorem nie stosować tego w kodzie wynikowym.
//        player.setPlayerID_("31");
//        GetPlayer getPlayer = new GetPlayer("get_armor.php", getApplicationContext(), player);
//        getPlayer.execute();
        try {
            player.setPlayerID_(FileManager.readPlayersIDFromFile());
            GetPlayer getPlayer = new GetPlayer(this, player);
            getPlayer.execute(currentEquipmentURL);


//            getPlayer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "get_armor.php");
//            GIDFTS = new GetItemsDataFromTheServer(itemsList);
            // We already have an account, so we only get player info
            // and items to equip.
        } catch(FileNotFoundException e){
            //Workaround. Pod żadnym pozorem nie stosować tego w kodzie wynikowym.
//            player.setPlayerID_("31");
        } catch (IOException e) {
            //If there was exception, it means, we don't have a player yet.
            // TODO Something with creating player or not.

            e.printStackTrace();
        }

        racesList = new ArrayList<ArrayList<String>>();
        classesList = new ArrayList<ArrayList<String>>();
        itemsList = new ArrayList<ArrayList<Items>>();
        loadData = new LoadData(this, racesList, classesList, itemsList);
        loadData.execute(racesURL, classesURL, itemsURL);


//        loadDataOLD = new LoadData_OLD(this, racesList, classesList, itemsList);
//        loadDataOLD.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, racesURL, classesURL, itemsURL);
//        loadDataOLD.execute(racesURL, classesURL, itemsURL);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentManager fragmentManager = getFragmentManager();

        switch(position) {
            case 0:
                fragmentManager.beginTransaction()
                    .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                    .commit();
                break;
            case 1:
                if(player.getPlayerID_() != null){
                    Toast.makeText(this, "You already have a character!", Toast.LENGTH_LONG);
                    break;
                }
                Bundle args = new Bundle();
                args.putInt("racesListSize", racesList.size());
                for(int i = 0; i < racesList.size(); i++)
                    args.putStringArrayList("racesListIndex" + i, racesList.get(i));
                args.putInt("classesListSize", classesList.size());
                for(int j = 0; j < classesList.size(); j++)
                    args.putStringArrayList("classesListIndex" + j, classesList.get(j));
                args.putInt("itemsListSize", itemsList.size());
                for(int l = 0; l < itemsList.size(); l++){
                    args.putInt("numberOf" + itemsList.get(l).getClass().getSimpleName() + l, itemsList.get(l).size());
                    for(int m = 0; m < itemsList.get(l).size(); m++) {
                        try {
                            args.putInt("getMeeleDefense" + l + m, itemsList.get(l).get(m).getMeeleDefense_());
                            args.putInt("getMagicDefense" + l + m, itemsList.get(l).get(m).getMagicDefense_());
                            args.putInt("getMeeleAttack" + l + m, itemsList.get(l).get(m).getMeeleAttack_());
                            args.putInt("getMagicAttack" + l + m, itemsList.get(l).get(m).getMagicAttack_());
                            args.putInt("itemCost" + l + m, itemsList.get(l).get(m).getCost_());
                            args.putString("getImgURL" + l + m, itemsList.get(l).get(m).getImgURL_());
                            args.putString("getName" + l + m, itemsList.get(l).get(m).getName_());
                            args.putInt("getPositionOnItemList" + l + m, itemsList.get(l).get(m).getPositionOnItemList_());
                        } catch(Exception e){
                            Log.d("bundle in Activity:\n", e.toString());
                            e.printStackTrace();
                        }
                    }
                }

                fragmentManager.beginTransaction()
                        // To the arguments of the static method newInstance I've added Bundle argument.
                        // Check the method and you will see what purpose it has.
                        .replace(R.id.container, CharacterCreationFragment.newInstance(position + 1, args))
                        .commit();
                break;
            case 2:
                break;
            case 3:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, PlayerEquipmentFragment.newInstance(position + 1))
                        .commit();
                break;
        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_menu, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainDrawerActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }
}
