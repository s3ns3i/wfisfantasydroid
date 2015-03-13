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
    private CharacterCreationWindow CCW;
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
        CCW = (CharacterCreationWindow) getActivity();
        nicknameTV = (TextView) rootView.findViewById(R.id.playersNicknameTextView);
        bundle = getArguments();
        player = CCW.getPlayer();
        nameValue = bundle.getString(CharacterCreationWindow.nameKey);
        nicknameTV.setText(nameValue);
        raceValue = bundle.getString(CharacterCreationWindow.raceKey);
        raceIDValue = bundle.getLong(CharacterCreationWindow.raceIDKey);
        classValue = bundle.getString(CharacterCreationWindow.classKey);
        classIDValue = bundle.getLong(CharacterCreationWindow.classIDKey);
        strValue = bundle.getString(CharacterCreationWindow.strKey);
        agiValue = bundle.getString(CharacterCreationWindow.agiKey);
        intValue = bundle.getString(CharacterCreationWindow.intKey);
        hpValue = bundle.getString(CharacterCreationWindow.hpKey);
        manaValue = bundle.getString(CharacterCreationWindow.manaKey);
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
			itemInfo = new ItemInfoFragment(playerItems[0], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.bootsButton:
			itemInfo = new ItemInfoFragment(playerItems[1], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.glovesButton:
			itemInfo = new ItemInfoFragment(playerItems[2], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.helmetButton:
			itemInfo = new ItemInfoFragment(playerItems[3], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.weaponButton:
			itemInfo = new ItemInfoFragment(playerItems[4], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.firstRingButton:
			itemInfo = new ItemInfoFragment(playerItems[5], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.secondRingButton:
			itemInfo = new ItemInfoFragment(playerItems[6], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.offhandButton:
			itemInfo = new ItemInfoFragment(playerItems[7], null, false);
			itemInfo.show(getFragmentManager(), "item_info");
			break;
		case R.id.createCharacterButton:
			// Send all data to the server.
			// TODO Maybe get the location and then send it too?
			//isLocationButton = true;
			//createCharacterButton.setClickable(false);
			//messageLabel.setText("Obtaining your location...");
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.singlePlayerKey, "0"));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.nameKey, nameValue));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.raceKey, raceValue));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.raceIDKey, Long.toString(raceIDValue)));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.classKey, classValue));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.classIDKey, Long.toString(classIDValue)));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.strKey, strValue));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.agiKey, agiValue));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.intKey, intValue));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.chestKey, Integer.toString(playerItems[0].getID_())));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.bootsKey, Integer.toString(playerItems[1].getID_())));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.glovesKey, Integer.toString(playerItems[2].getID_())));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.helmetKey, Integer.toString(playerItems[3].getID_())));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.weaponKey, Integer.toString(playerItems[4].getID_())));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.firstRingKey, Integer.toString(playerItems[5].getID_())));
			//playerParams.add(new BasicNameValuePair(CharacterCreationWindow.secondRingKey, Integer.toString(playerItems[6].getID_())));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.offhandKey, Integer.toString(playerItems[7].getID_())));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.hpKey, hpValue));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.manaKey, manaValue));
			playerParams.add(new BasicNameValuePair(CharacterCreationWindow.expKey, "0"));
			new SendNewCharacterToTheServer(registerURL, playerParams, getActivity(), player).execute();
			break;
		}
	}

	@Override
	public boolean onLongClick(View v) {
		Integer whichButton = v.getId();
		switch(whichButton){
		case R.id.armorButton:
			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(0), playerItems[0], armorButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.bootsButton:
			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(1), playerItems[1], bootsButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.glovesButton:
			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(2), playerItems[2], glovesButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.helmetButton:
			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(3), playerItems[3], helmetButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.weaponButton:
			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(4), playerItems[4], weaponButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.firstRingButton:
			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(5), playerItems[5], firstRingButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.secondRingButton:
			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(5), playerItems[6], secondRingButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		case R.id.offhandButton:
			allItemsInfo = new AllItemsDialogFragment(allItemsList.get(6), playerItems[7], offhandButton);
			allItemsInfo.show(getFragmentManager(), "all_item_info");
			break;
		}
		return true;
	}

}
