package com.s3ns3i.degejm.Fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.s3ns3i.degejm.AsyncTasks.GetDefaultCharacterDataFromTheServer;
import com.s3ns3i.degejm.FileManager;
import com.s3ns3i.degejm.MainDrawerActivity;
import com.s3ns3i.degejm.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class CharacterCreationFragment extends Fragment implements
OnClickListener,
OnItemSelectedListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number"
    		, POOL_POINTS_ERROR = "You need to assign all points first!";

	private final String racesURL = "get_races.php"
			, classesURL = "get_classes.php";
	private final Integer numberOfDistributionalPoints = 20;

	private ArrayList<Integer> statsArray;
	private Integer pointPool;
    private TextView messageLabel
    , baseHPTV
    , baseManaTV
    , strTV
    , agiTV
    , intTV
    , pointPoolTV;
    private Spinner racesSpinner
	, classesSpinner;
    private EditText nicknameEditText;

	//DATABASE TEST
	
    private Button chooseInventoryButton
    , baseHPDecrementButton
    , baseHPIncrementButton
    , baseManaDecrementButton
    , baseManaIncrementButton
    , strengthDecrementButton
    , strengthIncrementButton
    , agilityDecrementButton
    , agilityIncrementButton
    , intelligenceDecrementButton
    , intelligenceIncrementButton
    , defaultStatsButton;
	// Creating JSON Parser object
    private ArrayList<ArrayList<String>> racesList
    , classesList;
    private GetDefaultCharacterDataFromTheServer GT;
    
	/**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CharacterCreationFragment newInstance(int sectionNumber) {
        CharacterCreationFragment fragment = new CharacterCreationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
	
    public CharacterCreationFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_character_creation, container, false);
		
		//Initializations
		statsArray = new ArrayList<Integer>();
		pointPool = numberOfDistributionalPoints;
		messageLabel = (TextView) rootView.findViewById(R.id.MessageLabel);
		baseHPTV = (TextView) rootView.findViewById(R.id.baseHPValueTextView);
		baseManaTV = (TextView) rootView.findViewById(R.id.baseManaValueTextView);
		strTV = (TextView) rootView.findViewById(R.id.strengthValueTextView);
		agiTV = (TextView) rootView.findViewById(R.id.agilityValueTextView);
		intTV = (TextView) rootView.findViewById(R.id.intelligenceValueTextView);
		pointPoolTV = (TextView) rootView.findViewById(R.id.pointPoolTextView);
		pointPoolTV.setText(Integer.toString(numberOfDistributionalPoints));
		racesSpinner = (Spinner) rootView.findViewById(R.id.raceSpinner);
		racesSpinner.setOnItemSelectedListener(this);
		classesSpinner = (Spinner) rootView.findViewById(R.id.classSpinner);
		classesSpinner.setOnItemSelectedListener(this);
		nicknameEditText = (EditText) rootView.findViewById(R.id.editText1);
		chooseInventoryButton = (Button) rootView.findViewById(R.id.chooseInventoryButton);
		
		// A condition checking if a character was already made.
		try{
			//I don't need here players id. I only check if the file exist (if it exists,
			//that means a character was already made.
			//It's an university project!
			FileManager.readPlayersIDFromFile();
			messageLabel.setText("You've already created a character! For now you can't make more. Sry.");
			chooseInventoryButton.setClickable(false);
		} catch(FileNotFoundException e){
			chooseInventoryButton.setOnClickListener(this);
    		chooseInventoryButton.setClickable(true);
		} catch (IOException e) {
			// TODO There we can check players id in the future.
			e.printStackTrace();
		}
		baseHPDecrementButton = (Button) rootView.findViewById(R.id.baseHPDecrementButton);
		baseHPDecrementButton.setOnClickListener(this);
        baseHPIncrementButton = (Button) rootView.findViewById(R.id.baseHPIncrementButton);
        baseHPIncrementButton.setOnClickListener(this);
        baseManaDecrementButton = (Button) rootView.findViewById(R.id.baseManaDecrementButton);
        baseManaDecrementButton.setOnClickListener(this);
        baseManaIncrementButton = (Button) rootView.findViewById(R.id.baseManaIncrementButton);
        baseManaIncrementButton.setOnClickListener(this);
        strengthDecrementButton = (Button) rootView.findViewById(R.id.strDecrementButton);
        strengthDecrementButton.setOnClickListener(this);
        strengthIncrementButton = (Button) rootView.findViewById(R.id.strIncrementButton);
        strengthIncrementButton.setOnClickListener(this);
        agilityDecrementButton = (Button) rootView.findViewById(R.id.agiDecrementButton);
        agilityDecrementButton.setOnClickListener(this);
        agilityIncrementButton = (Button) rootView.findViewById(R.id.agiIncrementButton);
        agilityIncrementButton.setOnClickListener(this);
        intelligenceDecrementButton = (Button) rootView.findViewById(R.id.intDecrementButton);
        intelligenceDecrementButton.setOnClickListener(this);
        intelligenceIncrementButton = (Button) rootView.findViewById(R.id.intIncrementButton);
        intelligenceIncrementButton.setOnClickListener(this);
        defaultStatsButton = (Button) rootView.findViewById(R.id.defaultStatsButton);
        defaultStatsButton.setOnClickListener(this);
		// Building Parameters
		//playerParams = new ArrayList<NameValuePair>();
		racesList = new ArrayList<ArrayList<String>>();
		classesList = new ArrayList<ArrayList<String>>();
		GT = new GetDefaultCharacterDataFromTheServer(racesList, classesList, getActivity(), rootView, this);
		try{
			GT.execute(racesURL, classesURL);
		}
		catch(IllegalStateException e){
			messageLabel.setText("Couldn't get data from database.");
		}
		
        return rootView;
	}

	@Override
	public void onClick(View v) {
		Integer whichButton = v.getId()
				, processedBaseHP = Integer.parseInt(processStat(0))		//It only processes hp (0) and mana (1)
				, processedBaseMana = Integer.parseInt(processStat(1));		//It only processes hp (0) and mana (1)
		
		switch(whichButton){
		case R.id.chooseInventoryButton:
			if(pointPool > 0){
				Toast.makeText(getActivity(), POOL_POINTS_ERROR, Toast.LENGTH_SHORT).show();
			}
			else{
				// TODO Open fragment with inventory.
				//	or replace with current fragment.
				//	of make activity that will launch with fragment.
				Bundle bundle = new Bundle();
				bundle.putString(MainDrawerActivity.nameKey, nicknameEditText.getText().toString());
				bundle.putString(MainDrawerActivity.raceKey, racesSpinner.getSelectedItem().toString());
				bundle.putLong(MainDrawerActivity.raceIDKey, racesSpinner.getSelectedItemId());
				bundle.putString(MainDrawerActivity.classKey, classesSpinner.getSelectedItem().toString());
				bundle.putLong(MainDrawerActivity.classIDKey, classesSpinner.getSelectedItemId());
				bundle.putString(MainDrawerActivity.strKey, strTV.getText().toString());
				bundle.putString(MainDrawerActivity.agiKey, agiTV.getText().toString());
				bundle.putString(MainDrawerActivity.intKey, intTV.getText().toString());
				bundle.putString(MainDrawerActivity.hpKey, baseHPTV.getText().toString());
				bundle.putString(MainDrawerActivity.manaKey, baseManaTV.getText().toString());
//				Intent intent = new Intent();
//				intent.putExtra(MainDrawerActivity.bundleKey, bundle);
				PlayerEquipmentFragment PEF = new PlayerEquipmentFragment();
				PEF.setArguments(bundle);
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.replace(R.id.container, PEF, getTag());
				ft.addToBackStack(null);
				ft.commit();
			}
			break;
		case R.id.baseHPDecrementButton:
			if(pointPool.equals(numberOfDistributionalPoints)){
				break;
			}
			
			else if(processedBaseHP.equals(statsArray.get(0))){
				break;
			}
			pointPool++;
			setStatsArray(0, statsArray.get(0) - 1);
			pointPoolTV.setText(Integer.toString(pointPool));
			baseHPTV.setText(getStatString(0));
			break;
			
		case R.id.baseHPIncrementButton:
			if(pointPool == 0){
				break;
			}
			pointPool--;
			setStatsArray(0, statsArray.get(0) + 1);
			pointPoolTV.setText(Integer.toString(pointPool));
			baseHPTV.setText(getStatString(0));
			break;
			
		case R.id.baseManaDecrementButton:
			if(pointPool.equals(numberOfDistributionalPoints)){
				break;
			}
			
			else if(processedBaseMana.equals(statsArray.get(1))){
				break;
			}
			pointPool++;
			setStatsArray(1, statsArray.get(1) - 1);
			pointPoolTV.setText(Integer.toString(pointPool));
			baseManaTV.setText(getStatString(1));
			break;
			
		case R.id.baseManaIncrementButton:
			if(pointPool == 0){
				break;
			}
			pointPool--;
			setStatsArray(1, statsArray.get(1) + 1);
			pointPoolTV.setText(Integer.toString(pointPool));
			baseManaTV.setText(getStatString(1));
			break;
			
		case R.id.strDecrementButton:
			if(pointPool.equals(numberOfDistributionalPoints)){
				break;
			}
			
			else if(statsArray.get(2).equals(Integer.parseInt(
							racesList.get(3).get((int) racesSpinner.getSelectedItemId())))){
				break;
			}
			pointPool++;
			setStatsArray(2, statsArray.get(2) - 1);
			pointPoolTV.setText(Integer.toString(pointPool));
			strTV.setText(getStatString(2));
			break;
			
		case R.id.strIncrementButton:
			if(pointPool == 0){
				break;
			}
			pointPool--;
			setStatsArray(2, statsArray.get(2) + 1);
			pointPoolTV.setText(Integer.toString(pointPool));
			strTV.setText(getStatString(2));
			break;
			
		case R.id.agiDecrementButton:
			if(pointPool.equals(numberOfDistributionalPoints)){
				break;
			}
			
			else if(statsArray.get(3).equals(Integer.parseInt(
							racesList.get(4).get((int) racesSpinner.getSelectedItemId())))){
				break;
			}
			pointPool++;
			setStatsArray(3, statsArray.get(3) - 1);
			pointPoolTV.setText(Integer.toString(pointPool));
			agiTV.setText(getStatString(3));
			break;
			
		case R.id.agiIncrementButton:
			if(pointPool == 0){
				break;
			}
			pointPool--;
			setStatsArray(3, statsArray.get(3) + 1);
			pointPoolTV.setText(Integer.toString(pointPool));
			agiTV.setText(getStatString(3));
			break;
			
		case R.id.intDecrementButton:
			if(pointPool.equals(numberOfDistributionalPoints)){
				break;
			}
			
			else if(statsArray.get(4).equals(Integer.parseInt(
							racesList.get(5).get((int) racesSpinner.getSelectedItemId())))){
				break;
			}
			pointPool++;
			setStatsArray(4, statsArray.get(4) - 1);
			pointPoolTV.setText(Integer.toString(pointPool));
			intTV.setText(getStatString(4));
			break;
			
		case R.id.intIncrementButton:
			if(pointPool == 0){
				break;
			}
			pointPool--;
			setStatsArray(4, statsArray.get(4) + 1);
			pointPoolTV.setText(Integer.toString(pointPool));
			intTV.setText(getStatString(4));
			break;
			
		case R.id.defaultStatsButton:
			pointPool = numberOfDistributionalPoints;
			pointPoolTV.setText(Integer.toString(pointPool));
			setStatsArray(0, processedBaseHP);
			setStatsArray(1, processedBaseMana);
			setStatsArray(2, racesList.get(3).get((int) racesSpinner.getSelectedItemId()));
			setStatsArray(3, racesList.get(4).get((int) racesSpinner.getSelectedItemId()));
			setStatsArray(4, racesList.get(5).get((int) racesSpinner.getSelectedItemId()));
			baseHPTV.setText(Integer.toString(processedBaseHP));
			baseManaTV.setText(Integer.toString(processedBaseMana));
			strTV.setText(racesList.get(3).get((int) racesSpinner.getSelectedItemId()));
			agiTV.setText(racesList.get(4).get((int) racesSpinner.getSelectedItemId()));
			intTV.setText(racesList.get(5).get((int) racesSpinner.getSelectedItemId()));
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long id) {
		//We get id of the clicked/tapped spinner.
		Integer currentSpinner = arg0.getId();
		String stat
				, processedStat;
		//We check what spinner it is.
		switch(currentSpinner){
		case R.id.raceSpinner:
			pointPool = numberOfDistributionalPoints;
			pointPoolTV.setText(Integer.toString(pointPool));
			//We set value from table with data.
			processedStat = processStat(0);		//It only processes hp (0) and mana (1)
			baseHPTV.setText(processedStat);
			statsArray.set(0, Integer.parseInt(processedStat));
			
			processedStat = processStat(1);		//It only processes hp (0) and mana (1)
			baseManaTV.setText(processedStat);
			statsArray.set(1, Integer.parseInt(processedStat));
			
			stat = racesList.get(3).get((int) id);
			strTV.setText(stat);
			statsArray.set(2, Integer.parseInt(stat));
			
			stat = racesList.get(4).get((int) id);
			agiTV.setText(stat);
			statsArray.set(3, Integer.parseInt(stat));
			
			stat = racesList.get(5).get((int) id);
			intTV.setText(stat);
			statsArray.set(4, Integer.parseInt(stat));
			break;
			
		case R.id.classSpinner:
			pointPool = numberOfDistributionalPoints;
			pointPoolTV.setText(Integer.toString(pointPool));
			//We set value from table with data.
			processedStat = processStat(0);		//It only processes hp (0) and mana (1)
			baseHPTV.setText(processedStat);
			statsArray.set(0, Integer.parseInt(processedStat));
			
			processedStat = processStat(1);		//It only processes hp (0) and mana (1)
			baseManaTV.setText(processedStat);
			statsArray.set(1, Integer.parseInt(processedStat));
			
			stat = racesList.get(3).get((int) racesSpinner.getSelectedItemId());
			strTV.setText(stat);
			statsArray.set(2, Integer.parseInt(stat));
			
			stat = racesList.get(4).get((int) racesSpinner.getSelectedItemId());
			agiTV.setText(stat);
			statsArray.set(3, Integer.parseInt(stat));
			
			stat = racesList.get(5).get((int) racesSpinner.getSelectedItemId());
			intTV.setText(stat);
			statsArray.set(4, Integer.parseInt(stat));
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
	
	public String processStat(String stat, String modifier){
		Integer iStat = Integer.parseInt(stat)
		, iModifier = Integer.parseInt(modifier)
		, processedStat = iStat + iModifier;
		return Integer.toString(processedStat);
	}
	
	public String processStat(Integer statNumber){
		String stat = racesList.get(statNumber + 1).get((int) racesSpinner.getSelectedItemId())
		,modifier = classesList.get(statNumber + 5).get((int) classesSpinner.getSelectedItemId());
		return processStat(stat, modifier);
	}
	
	public ArrayList<Integer> getStatsArray(){
		return statsArray;
	}
	
	public Integer getStatInteger(Integer index){
		return statsArray.get(index);
	}
	
	public String getStatString(Integer index){
		return String.valueOf(statsArray.get(index));
	}
	
	public void setStatsArray(Integer index, Integer value){
		statsArray.set(index, value);
	}
	
	public void setStatsArray(Integer index, String value){
		statsArray.set(index, Integer.parseInt(value));
	}
	
	public void addToStatsArray(Integer value){
		statsArray.add(value);
	}
	
	public void addToStatsArray(String value){
		statsArray.add(Integer.parseInt(value));
	}
	
}
