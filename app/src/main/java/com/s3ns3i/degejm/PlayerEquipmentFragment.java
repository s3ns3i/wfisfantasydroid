package com.s3ns3i.degejm;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class PlayerEquipmentFragment extends Fragment implements OnClickListener, OnLongClickListener{

    private static final String ARG_SECTION_NUMBER = "section_number";
	private final String registerURL = "register.php";
	private final String getAllItemsURL = "get_all_items.php";
    private final String getPlayersItemsURL = "get_armor.php"
    		, param1 = "";// FileManager.readPlayersIDFromFile();
    private final Integer numberOfItemTypes = Integer.valueOf(8);
    private Button helmetButton;
    private Button armorButton;
    private Button weaponButton;
    private Button offhandButton;
    private Button glovesButton;
    private Button bootsButton;
    private Button firstRingButton;
    private Button secondRingButton;
    private Button createCharacterButton;
    private MainDrawerActivity MDA;
    //private CharacterCreationWindow CCW;
    TextView nicknameTV;
    private Bundle bundle;
	private Player player;

    private String nameValue, raceValue, classValue, strValue, agiValue, intValue, hpValue, manaValue;
    private Long raceIDValue, classIDValue;

    private List<NameValuePair> playerParams;
    //private ArrayList<ArrayList<ArrayList<String>>> itemsList;
    private ArrayList<ArrayList<Items>> allItemsList;
    //private ArrayList<Items> itemsList;
    private Items[] playerItems;
    private ItemInfoFragment itemInfo;
    private AllItemsDialogFragment allItemsInfo;
    //private Items item;
    //Depending on what slot will we tap, it will be filled with corresponding list.
    //private AlertDialog itemsListDialog;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlayerEquipmentFragment newInstance(int sectionNumber) {
        PlayerEquipmentFragment fragment = new PlayerEquipmentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlayerEquipmentFragment() {

    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View rootView = inflater.inflate(R.layout.fragment_player_equipment, container, false);

        //All objects we need in this window
	    helmetButton = (Button) rootView.findViewById(R.id.helmetButton);
	    helmetButton.setOnClickListener(this);
	    helmetButton.setOnLongClickListener(this);
	    armorButton = (Button) rootView.findViewById(R.id.armorButton);
	    armorButton.setOnClickListener(this);
	    armorButton.setOnLongClickListener(this);
	    weaponButton = (Button) rootView.findViewById(R.id.weaponButton);
	    weaponButton.setOnClickListener(this);
	    weaponButton.setOnLongClickListener(this);
	    offhandButton = (Button) rootView.findViewById(R.id.offhandButton);
	    offhandButton.setOnClickListener(this);
	    offhandButton.setOnLongClickListener(this);
	    glovesButton = (Button) rootView.findViewById(R.id.glovesButton);
	    glovesButton.setOnClickListener(this);
	    glovesButton.setOnLongClickListener(this);
	    bootsButton = (Button) rootView.findViewById(R.id.bootsButton);
	    bootsButton.setOnClickListener(this);
	    bootsButton.setOnLongClickListener(this);
	    firstRingButton = (Button) rootView.findViewById(R.id.firstRingButton);
	    firstRingButton.setOnClickListener(this);
	    firstRingButton.setOnLongClickListener(this);
	    secondRingButton = (Button) rootView.findViewById(R.id.secondRingButton);
	    secondRingButton.setOnClickListener(this);
	    secondRingButton.setOnLongClickListener(this);
        createCharacterButton = (Button) rootView.findViewById(R.id.createCharacterButton);
        createCharacterButton.setOnClickListener(this);

        MDA = (MainDrawerActivity) getActivity();
        //CCW = (CharacterCreationWindow) getActivity();
        nicknameTV = (TextView) rootView.findViewById(R.id.playersNicknameTextView);
        bundle = getArguments();
        player = MDA.getPlayer();
        //player = CCW.getPlayer();
        nameValue = bundle.getString(MainDrawerActivity.nameKey);
        nicknameTV.setText(nameValue);
        raceValue = bundle.getString(MainDrawerActivity.raceKey);
        raceIDValue = bundle.getLong(MainDrawerActivity.raceIDKey);
        classValue = bundle.getString(MainDrawerActivity.classKey);
        classIDValue = bundle.getLong(MainDrawerActivity.classIDKey);
        strValue = bundle.getString(MainDrawerActivity.strKey);
        agiValue = bundle.getString(MainDrawerActivity.agiKey);
        intValue = bundle.getString(MainDrawerActivity.intKey);
        hpValue = bundle.getString(MainDrawerActivity.hpKey);
        manaValue = bundle.getString(MainDrawerActivity.manaKey);
        playerParams = new ArrayList<NameValuePair>();
        allItemsList = new ArrayList<ArrayList<Items>>();
        playerItems = new Items[numberOfItemTypes];
        for(int i = 0; i < numberOfItemTypes; i++){
        	playerItems[i] = new Items();
        }
        GetItemsDataFromTheServer GIDFTS = new GetItemsDataFromTheServer(allItemsList, getActivity());
        try{
        	if(param1.isEmpty())
        		GIDFTS.execute(getAllItemsURL);
        	else
        		GIDFTS.execute(getPlayersItemsURL, param1);
        } catch(IllegalStateException e){
        	Log.e("PlayerEquipmentFragment", "Something went wrong while connecting with the server");
        }
        
		return rootView;
	}

	@Override
	public void onClick(View v) {
		Integer whichButton = v.getId();
		
		switch(whichButton){
		case R.id.armorButton:
			itemInfo = new ItemInfoFragment();
            itemInfo.setArguments(playerItems[0], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.bootsButton:
			itemInfo = new ItemInfoFragment();
            itemInfo.setArguments(playerItems[1], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.glovesButton:
			itemInfo = new ItemInfoFragment();
            itemInfo.setArguments(playerItems[2], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.helmetButton:
			itemInfo = new ItemInfoFragment();
            itemInfo.setArguments(playerItems[3], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.weaponButton:
			itemInfo = new ItemInfoFragment();
            itemInfo.setArguments(playerItems[4], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.firstRingButton:
			itemInfo = new ItemInfoFragment();
            itemInfo.setArguments(playerItems[5], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.secondRingButton:
			itemInfo = new ItemInfoFragment();
            itemInfo.setArguments(playerItems[6], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.offhandButton:
			itemInfo = new ItemInfoFragment();
            itemInfo.setArguments(playerItems[7], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.createCharacterButton:
			// Send all data to the server.
			// TODO Maybe get the location and then send it too?
			//isLocationButton = true;
			//createCharacterButton.setClickable(false);
			//messageLabel.setText("Obtaining your location...");
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.singlePlayerKey, "0"));//0
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.nameKey, nameValue));//1
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.raceKey, raceValue));//2
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.raceIDKey, Long.toString(raceIDValue)));//3
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.classKey, classValue));//4
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.classIDKey, Long.toString(classIDValue)));//5
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.strKey, strValue));//6
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.agiKey, agiValue));//7
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.intKey, intValue));//8
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.chestKey, Integer.toString(playerItems[0].getID_())));//9
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.bootsKey, Integer.toString(playerItems[1].getID_())));//10
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.glovesKey, Integer.toString(playerItems[2].getID_())));//11
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.helmetKey, Integer.toString(playerItems[3].getID_())));//12
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.weaponKey, Integer.toString(playerItems[4].getID_())));//13
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.firstRingKey, Integer.toString(playerItems[5].getID_())));//14
			//playerParams.add(new BasicNameValuePair(CharacterCreationWindow.secondRingKey, Integer.toString(playerItems[6].getID_())));
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.offhandKey, Integer.toString(playerItems[7].getID_())));//15
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.hpKey, hpValue));//16
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.manaKey, manaValue));//17
			playerParams.add(new BasicNameValuePair(MainDrawerActivity.expKey, "0"));//18
			new SendNewCharacterToTheServer(registerURL, playerParams, getActivity(), player).execute();//19
			break;
		}
	}

	@Override
	public boolean onLongClick(View v) {
		Integer whichButton = v.getId();
		switch(whichButton){
		case R.id.armorButton:
//			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(0), playerItems[0], armorButton);
            allItemsInfo = new AllItemsDialogFragment();
            allItemsInfo.setArguments(allItemsList.get(0), playerItems[0], armorButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.bootsButton:
//			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(1), playerItems[1], bootsButton);
            allItemsInfo = new AllItemsDialogFragment();
            allItemsInfo.setArguments(allItemsList.get(1), playerItems[1], bootsButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.glovesButton:
//			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(2), playerItems[2], glovesButton);
            allItemsInfo = new AllItemsDialogFragment();
            allItemsInfo.setArguments(allItemsList.get(2), playerItems[2], glovesButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.helmetButton:
//			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(3), playerItems[3], helmetButton);
            allItemsInfo = new AllItemsDialogFragment();
            allItemsInfo.setArguments(allItemsList.get(3), playerItems[3], helmetButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.weaponButton:
//			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(4), playerItems[4], weaponButton);
            allItemsInfo = new AllItemsDialogFragment();
            allItemsInfo.setArguments(allItemsList.get(4), playerItems[4], weaponButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.firstRingButton:
//			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(5), playerItems[5], firstRingButton);
            allItemsInfo = new AllItemsDialogFragment();
            allItemsInfo.setArguments(allItemsList.get(5), playerItems[5], firstRingButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.secondRingButton:
//			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(5), playerItems[6], secondRingButton);
            allItemsInfo = new AllItemsDialogFragment();
            allItemsInfo.setArguments(allItemsList.get(5), playerItems[6], secondRingButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.offhandButton:
//			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(6), playerItems[7], offhandButton);
            allItemsInfo = new AllItemsDialogFragment();
            allItemsInfo.setArguments(allItemsList.get(6), playerItems[7], offhandButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		}
		return true;
	}

}
