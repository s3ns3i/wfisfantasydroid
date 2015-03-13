/**
 * 	CREATED:															Krzysztof Studnicki
 * */
package com.s3ns3i.degejm;

// TODO Trzeba zrobiæ odbieranie id przy tworzeniu postaci i zapisywanie go na dysku urz¹dzenia.

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CharacterCreationWindow extends Activity {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character_creation);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			/**
			 * s3ns3i:
			 * There we can initialize fragments.
			 * Now I'm initializing connection fragment, because I need to collect
			 * data from the server first.
			 * When it will be done, I'll swap this fragment with character creating one.
			 */
					.add(R.id.container, new CharacterCreationFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	public static class PlaceholderFragment extends Fragment implements
//    OnClickListener,
//    LocationListener,
//    OnItemSelectedListener {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number"
//        		, POOL_POINTS_ERROR = "You need to assign all points first!";
//		
//		private final String racesURL = "get_races.php"
//				, classesURL = "get_classes.php";
//		private final Integer numberOfDistributionalPoints = Integer.valueOf(20);
//	
//		//Activity reference
//		CharacterCreationWindow CCW;
//		
//		private ArrayList<Integer> statsArray;
//		private Integer pointPool;
//        //Location objects    	
//        private Double latitudeDouble
//        , longitudeDouble;
//    	
//        private LocationManager locationManager;
//        private TextView pointPoolTV;
//    	
//        private Boolean isLocationButton;
//
//    	//DATABASE TEST
//    	
//        private Button chooseInventoryButton
//        , baseHPDecrementButton
//        , baseHPIncrementButton
//        , baseManaDecrementButton
//        , baseManaIncrementButton
//        , strengthDecrementButton
//        , strengthIncrementButton
//        , agilityDecrementButton
//        , agilityIncrementButton
//        , intelligenceDecrementButton
//        , intelligenceIncrementButton
//        , defaultStatsButton;
//    	// Creating JSON Parser object
//        private ArrayList<ArrayList<String>> racesList
//        , classesList;
//        private GetDefaultCharacterDataFromTheServer GT;
//        
//		/**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//    	
//        public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(
//					R.layout.fragment_character_creation, container, false);
//			
//			//Initializations
//			
//			//=============================================================================================
//			//	DELETE AFTER ADDING THIS MODULE TO A MENU AND PASS PLAYER AS AN ARGUMENT INSTEAD
//			//=============================================================================================
//			/**	TODO
//			 * Nie mogê utworzyæ gracza bez wstawiania czegoœ do konstruktora.
//			 * Muszê utworzyæ gracza bez ¿adnych wstêpnych danych bo one s¹ dodawane dopiero póŸniej.
//			 */
//			//player = new Player();
//			//=============================================================================================
//			//	DELETE AFTER ADDING THIS MODULE TO A MENU AND PASS PLAYER AS AN ARGUMENT INSTEAD
//			//=============================================================================================
//			statsArray = new ArrayList<Integer>();
//			pointPool = Integer.valueOf(numberOfDistributionalPoints);
//			latitudeDouble = Double.valueOf(0.);
//        	longitudeDouble = Double.valueOf(0.);
//    		messageLabel = (TextView) rootView.findViewById(R.id.MessageLabel);
//    		baseHPTV = (TextView) rootView.findViewById(R.id.baseHPValueTextView);
//    		baseManaTV = (TextView) rootView.findViewById(R.id.baseManaValueTextView);
//    		strTV = (TextView) rootView.findViewById(R.id.strengthValueTextView);
//    		agiTV = (TextView) rootView.findViewById(R.id.agilityValueTextView);
//    		intTV = (TextView) rootView.findViewById(R.id.intelligenceValueTextView);
//    		pointPoolTV = (TextView) rootView.findViewById(R.id.pointPoolTextView);
//    		pointPoolTV.setText(Integer.toString(numberOfDistributionalPoints));
//    		racesSpinner = (Spinner) rootView.findViewById(R.id.raceSpinner);
//    		racesSpinner.setOnItemSelectedListener(this);
//    		classesSpinner = (Spinner) rootView.findViewById(R.id.classSpinner);
//    		classesSpinner.setOnItemSelectedListener(this);
//    		nicknameEditText = (EditText) rootView.findViewById(R.id.editText1);
//    		isLocationButton = false;
//        	locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
//    		try{
//    			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
//    		}
//    		catch(Exception e){
//    			messageLabel.setText("Couldn't send request to obtain location updates.");
//    		}
//    		chooseInventoryButton = (Button) rootView.findViewById(R.id.chooseInventoryButton);
//			
//			// A condition checking if a character was already made.
//    		try{
//    			//I don't need here players id. I only check if the file exist (if it exists,
//    			//that means a character was already made.
//    			//It's an university project!
//    			FileManager.readPlayersIDFromFile();
//    			messageLabel.setText("You've already created a character! For now you can't make more. Sry.");
//    			chooseInventoryButton.setClickable(false);
//    		} catch(FileNotFoundException e){
//    			chooseInventoryButton.setOnClickListener(this);
//	    		chooseInventoryButton.setClickable(true);
//    		} catch (IOException e) {
//				// TODO There we can check players id in the future.
//				e.printStackTrace();
//			}
//    		baseHPDecrementButton = (Button) rootView.findViewById(R.id.baseHPDecrementButton);
//    		baseHPDecrementButton.setOnClickListener(this);
//            baseHPIncrementButton = (Button) rootView.findViewById(R.id.baseHPIncrementButton);
//            baseHPIncrementButton.setOnClickListener(this);
//            baseManaDecrementButton = (Button) rootView.findViewById(R.id.baseManaDecrementButton);
//            baseManaDecrementButton.setOnClickListener(this);
//            baseManaIncrementButton = (Button) rootView.findViewById(R.id.baseManaIncrementButton);
//            baseManaIncrementButton.setOnClickListener(this);
//            strengthDecrementButton = (Button) rootView.findViewById(R.id.strDecrementButton);
//            strengthDecrementButton.setOnClickListener(this);
//            strengthIncrementButton = (Button) rootView.findViewById(R.id.strIncrementButton);
//            strengthIncrementButton.setOnClickListener(this);
//            agilityDecrementButton = (Button) rootView.findViewById(R.id.agiDecrementButton);
//            agilityDecrementButton.setOnClickListener(this);
//            agilityIncrementButton = (Button) rootView.findViewById(R.id.agiIncrementButton);
//            agilityIncrementButton.setOnClickListener(this);
//            intelligenceDecrementButton = (Button) rootView.findViewById(R.id.intDecrementButton);
//            intelligenceDecrementButton.setOnClickListener(this);
//            intelligenceIncrementButton = (Button) rootView.findViewById(R.id.intIncrementButton);
//            intelligenceIncrementButton.setOnClickListener(this);
//            defaultStatsButton = (Button) rootView.findViewById(R.id.defaultStatsButton);
//            defaultStatsButton.setOnClickListener(this);
//			// Building Parameters
//			playerParams = new ArrayList<NameValuePair>();
//    		racesList = new ArrayList<ArrayList<String>>();
//    		classesList = new ArrayList<ArrayList<String>>();
//    		GT = new GetDefaultCharacterDataFromTheServer(racesList, classesList, getActivity(), rootView, this);
//    		try{
//				GT.execute(racesURL, classesURL);
//    		}
//    		catch(Exception e){
//    			messageLabel.setText("Couldn't get data from database.");
//    		}
//    		
//            return rootView;
//		}
//		
//		public LatLng getPlayerLocation() {
//			return new LatLng(latitudeDouble, longitudeDouble);
//		}
//
//		@Override
//		public void onClick(View v) {
//			Integer whichButton = v.getId()
//					, processedBaseHP = Integer.parseInt(processStat(0))		//It only processes hp (0) and mana (1)
//					, processedBaseMana = Integer.parseInt(processStat(1));		//It only processes hp (0) and mana (1)
//			
//			switch(whichButton){
//			case R.id.chooseInventoryButton:
//				if(pointPool > 0){
//					Toast.makeText(getActivity(), POOL_POINTS_ERROR, Toast.LENGTH_SHORT).show();
//				}
//				else{
//					// TODO Open fragment with inventory.
//					//	or replace with current fragment.
//					//	of make activity that will launch with fragment.
//				}
//				break;
//			case R.id.baseHPDecrementButton:
//				if(pointPool == 20){
//					break;
//				}
//				
//				else if(processedBaseHP.equals(statsArray.get(0))){
//					break;
//				}
//				pointPool++;
//				setStatsArray(0, statsArray.get(0) - 1);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				baseHPTV.setText(getStatString(0));
//				break;
//				
//			case R.id.baseHPIncrementButton:
//				if(pointPool == 0){
//					break;
//				}
//				pointPool--;
//				setStatsArray(0, statsArray.get(0) + 1);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				baseHPTV.setText(getStatString(0));
//				break;
//				
//			case R.id.baseManaDecrementButton:
//				if(pointPool == 20){
//					break;
//				}
//				
//				else if(processedBaseMana.equals(statsArray.get(1))){
//					break;
//				}
//				pointPool++;
//				setStatsArray(1, statsArray.get(1) - 1);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				baseManaTV.setText(getStatString(1));
//				break;
//				
//			case R.id.baseManaIncrementButton:
//				if(pointPool == 0){
//					break;
//				}
//				pointPool--;
//				setStatsArray(1, statsArray.get(1) + 1);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				baseManaTV.setText(getStatString(1));
//				break;
//				
//			case R.id.strDecrementButton:
//				if(pointPool == 20){
//					break;
//				}
//				
//				else if(statsArray.get(2).equals(Integer.parseInt(
//								racesList.get(3).get((int) racesSpinner.getSelectedItemId())))){
//					break;
//				}
//				pointPool++;
//				setStatsArray(2, statsArray.get(2) - 1);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				strTV.setText(getStatString(2));
//				break;
//				
//			case R.id.strIncrementButton:
//				if(pointPool == 0){
//					break;
//				}
//				pointPool--;
//				setStatsArray(2, statsArray.get(2) + 1);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				strTV.setText(getStatString(2));
//				break;
//				
//			case R.id.agiDecrementButton:
//				if(pointPool == 20){
//					break;
//				}
//				
//				else if(statsArray.get(3).equals(Integer.parseInt(
//								racesList.get(4).get((int) racesSpinner.getSelectedItemId())))){
//					break;
//				}
//				pointPool++;
//				setStatsArray(3, statsArray.get(3) - 1);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				agiTV.setText(getStatString(3));
//				break;
//				
//			case R.id.agiIncrementButton:
//				if(pointPool == 0){
//					break;
//				}
//				pointPool--;
//				setStatsArray(3, statsArray.get(3) + 1);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				agiTV.setText(getStatString(3));
//				break;
//				
//			case R.id.intDecrementButton:
//				if(pointPool == 20){
//					break;
//				}
//				
//				else if(statsArray.get(4).equals(Integer.parseInt(
//								racesList.get(5).get((int) racesSpinner.getSelectedItemId())))){
//					break;
//				}
//				pointPool++;
//				setStatsArray(4, statsArray.get(4) - 1);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				intTV.setText(getStatString(4));
//				break;
//				
//			case R.id.intIncrementButton:
//				if(pointPool == 0){
//					break;
//				}
//				pointPool--;
//				setStatsArray(4, statsArray.get(4) + 1);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				intTV.setText(getStatString(4));
//				break;
//				
//			case R.id.defaultStatsButton:
//				pointPool = Integer.valueOf(20);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				setStatsArray(0, processedBaseHP);
//				setStatsArray(1, processedBaseMana);
//				setStatsArray(2, racesList.get(3).get((int) racesSpinner.getSelectedItemId()));
//				setStatsArray(3, racesList.get(4).get((int) racesSpinner.getSelectedItemId()));
//				setStatsArray(4, racesList.get(5).get((int) racesSpinner.getSelectedItemId()));
//				baseHPTV.setText(Integer.toString(processedBaseHP));
//				baseManaTV.setText(Integer.toString(processedBaseMana));
//				strTV.setText(racesList.get(3).get((int) racesSpinner.getSelectedItemId()));
//				agiTV.setText(racesList.get(4).get((int) racesSpinner.getSelectedItemId()));
//				intTV.setText(racesList.get(5).get((int) racesSpinner.getSelectedItemId()));
//				break;
//				
//			case R.id.backButton:
//				//Go back to the previous fragment.
//				break;
//			}
//		}
//
//		@Override
//		public void onLocationChanged(Location location) {
//			if(isLocationButton){
//				latitudeDouble = location.getLatitude();
//				longitudeDouble = location.getLongitude();
//				LatitudeTextView.setText(Double.toString(latitudeDouble));
//				LongitudeTextView.setText(Double.toString(longitudeDouble));
//				messageLabel.setText("Got your location.");
//				isLocationButton = false;
//				chooseInventoryButton.setClickable(true);
//			}
//		}
//
//		@Override
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onProviderEnabled(String provider) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onProviderDisabled(String provider) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
//				long id) {
//			//We get id of the clicked/tapped spinner.
//			Integer currentSpinner = arg0.getId();
//			String stat
//					, processedStat;
//			//We check what spinner it is.
//			switch(currentSpinner){
//			case R.id.raceSpinner:
//				pointPool = Integer.valueOf(20);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				//We set value from table with data.
//				processedStat = processStat(0);		//It only processes hp (0) and mana (1)
//				baseHPTV.setText(processedStat);
//				statsArray.set(0, Integer.parseInt(processedStat));
//				
//				processedStat = processStat(1);		//It only processes hp (0) and mana (1)
//				baseManaTV.setText(processedStat);
//				statsArray.set(1, Integer.parseInt(processedStat));
//				
//				stat = racesList.get(3).get((int) id);
//				strTV.setText(stat);
//				statsArray.set(2, Integer.parseInt(stat));
//				
//				stat = racesList.get(4).get((int) id);
//				agiTV.setText(stat);
//				statsArray.set(3, Integer.parseInt(stat));
//				
//				stat = racesList.get(5).get((int) id);
//				intTV.setText(stat);
//				statsArray.set(4, Integer.parseInt(stat));
//				break;
//				
//			case R.id.classSpinner:
//				pointPool = Integer.valueOf(20);
//				pointPoolTV.setText(Integer.toString(pointPool));
//				//We set value from table with data.
//				processedStat = processStat(0);		//It only processes hp (0) and mana (1)
//				baseHPTV.setText(processedStat);
//				statsArray.set(0, Integer.parseInt(processedStat));
//				
//				processedStat = processStat(1);		//It only processes hp (0) and mana (1)
//				baseManaTV.setText(processedStat);
//				statsArray.set(1, Integer.parseInt(processedStat));
//				
//				stat = racesList.get(3).get((int) racesSpinner.getSelectedItemId());
//				strTV.setText(stat);
//				statsArray.set(2, Integer.parseInt(stat));
//				
//				stat = racesList.get(4).get((int) racesSpinner.getSelectedItemId());
//				agiTV.setText(stat);
//				statsArray.set(3, Integer.parseInt(stat));
//				
//				stat = racesList.get(5).get((int) racesSpinner.getSelectedItemId());
//				intTV.setText(stat);
//				statsArray.set(4, Integer.parseInt(stat));
//				break;
//			}
//		}
//
//		@Override
//		public void onNothingSelected(AdapterView<?> arg0) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//		public String processStat(String stat, String modifier){
//			Integer iStat = Integer.parseInt(stat)
//			, iModifier = Integer.parseInt(modifier)
//			, processedStat = iStat + iModifier;
//			return Integer.toString(processedStat);
//		}
//		
//		public String processStat(Integer statNumber){
//			String stat = racesList.get(statNumber + 1).get((int) racesSpinner.getSelectedItemId())
//			,modifier = classesList.get(statNumber + 5).get((int) classesSpinner.getSelectedItemId())
//			, processedStat = processStat(stat, modifier);
//			return processedStat;
//		}
//		
//		public ArrayList<Integer> getStatsArray(){
//			return statsArray;
//		}
//		
//		public Integer getStatInteger(Integer index){
//			return statsArray.get(index);
//		}
//		
//		public String getStatString(Integer index){
//			return String.valueOf(statsArray.get(index));
//		}
//		
//		public void setStatsArray(Integer index, Integer value){
//			statsArray.set(index, value);
//		}
//		
//		public void setStatsArray(Integer index, String value){
//			statsArray.set(index, Integer.parseInt(value));
//		}
//		
//		public void addToStatsArray(Integer value){
//			statsArray.add(value);
//		}
//		
//		public void addToStatsArray(String value){
//			statsArray.add(Integer.parseInt(value));
//		}
//		
//	}
}
