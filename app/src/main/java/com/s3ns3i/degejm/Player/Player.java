package com.s3ns3i.degejm.Player;

import com.google.android.gms.maps.model.LatLng;
import com.s3ns3i.degejm.Player.Equipment.Boots;
import com.s3ns3i.degejm.Player.Equipment.ChestArmor;
import com.s3ns3i.degejm.Player.Equipment.Gloves;
import com.s3ns3i.degejm.Player.Equipment.Helmet;
import com.s3ns3i.degejm.Player.Equipment.Offhand;
import com.s3ns3i.degejm.Player.Equipment.Ring;
import com.s3ns3i.degejm.Player.Equipment.Weapon;

import java.util.ArrayList;


public class Player{

	//Variables describing unique player
	private String playerID_;
	private String playerName_;
	private String characterName_;
	private CharacterRace characterRace_;
	private CharacterClass className_;
	private Integer healthPoints_;
	private Integer manaPoints_;
	private Integer manaPointsRegen_;
	private Integer strength_;
	private Integer inteligence_;
	private Integer agility_;
	
	private Integer magicDefense_;
	private Integer meeleDefense_;
	
	private ArrayList<Skill> skillBar = new ArrayList<Skill>();
	
	//Variables responsible for location services
	private LatLng playerPosition_;
	private Integer range_;
	
	//Variables representing player inventory
	private Weapon weapon_;
	private Offhand offhand_;
	
	private Helmet helmet_;
	private ChestArmor chestArmor_;
	private Gloves gloves_;
	private Boots boots_;
	private Ring ring1_;
	private Ring ring2_;
	

	
	//==============================================
	//C-tors
	public Player(){
		
	}
	
	public Player(String characterName
				 ,CharacterClass className
				 ,CharacterRace characterRace
				 ,Integer strength
				 ,Integer agility
				 ,Integer inteligence 
				 ){
		characterName_=characterName;
		characterRace_=characterRace;
		className_=className;
		strength_=strength;
		agility_=agility;
		inteligence_=inteligence;
		
	}
	//===================NON GETTER/SETTER METHODS=========================
	
	
	public void setCurrentHpAfterCombat(Integer magicDamage,Integer meeleDamage){
		if((meeleDamage-this.meeleDefense_)>=0)
			this.healthPoints_-=(meeleDamage-this.meeleDefense_);
		if((magicDamage-this.magicDefense_)>0)
			this.healthPoints_-=(magicDamage-this.magicDefense_);
	}
	/*
	public Skills onSkillClickListener(){
		
		
		
		
		return damage;	
	}
	
	*/
	
	//====================END OF NON GETTER/SETTER METHODS=================
	public String GetCharacterName(){
	return characterName_;
	}
	
	
	public CharacterClass getClassName(){
	return className_;
	}
	
	
	public Integer getstrength(){
		return strength_;
	}
	
	public Integer getAgility(){
		return agility_;		
	}
	
	public Integer getInteligence(){
		return inteligence_;	
	}
	
	
	/**
	 * @return the playerID_
	 */
	public String getPlayerID_() {
		return playerID_;
	}
	/**
	 * @return the playerName_
	 */
	public String getPlayerName_() {
		return playerName_;
	}
	/**
	 * @return the characterName_
	 */
	public String getCharacterName_() {
		return characterName_;
	}
	/**
	 * @return the characterRace_
	 */
	public CharacterRace getCharacterRace_() {
		return characterRace_;
	}
	/**
	 * @return the className_
	 */
	public CharacterClass getClassName_() {
		return className_;
	}
	/**
	 * @return the healthPoints_
	 */
	public Integer getHealthPoints_() {
		return healthPoints_;
	}
	/**
	 * @return the manaPoints_
	 */
	public Integer getManaPoints_() {
		return manaPoints_;
	}
	/**
	 * @return the manaPointsRegen_
	 */
	public Integer getManaPointsRegen_() {
		return manaPointsRegen_;
	}
	/**
	 * @return the strength_
	 */
	public Integer getStrength_() {
		return strength_;
	}
	/**
	 * @return the inteligence_
	 */
	public Integer getInteligence_() {
		return inteligence_;
	}
	/**
	 * @return the agility_
	 */
	public Integer getAgility_() {
		return agility_;
	}
	/**
	 * @return the magicDefense_
	 */
	public Integer getMagicDefense_() {
		return magicDefense_;
	}
	/**
	 * @return the meeleDefense_
	 */
	public Integer getMeeleDefense_() {
		return meeleDefense_;
	}
	/**
	 * @return the skillBar
	 */
	public ArrayList<Skill> getSkillBar() {
		return skillBar;
	}
	/**
	 * @return the playerPosition_
	 */
	public LatLng getPlayerPosition_() {
		return playerPosition_;
	}
	/**
	 * @return the range_
	 */
	public Integer getRange_() {
		return range_;
	}
	/**
	 * @return the weapon_
	 */
	public Weapon getWeapon_() {
		return weapon_;
	}
	/**
	 * @return the offhand_
	 */
	public Offhand getOffhand_() {
		return offhand_;
	}
	/**
	 * @return the helmet_
	 */
	public Helmet getHelmet_() {
		return helmet_;
	}
	/**
	 * @return the chestArmor_
	 */
	public ChestArmor getChestArmor_() {
		return chestArmor_;
	}
	/**
	 * @return the gloves_
	 */
	public Gloves getGloves_() {
		return gloves_;
	}
	/**
	 * @return the boots_
	 */
	public Boots getBoots_() {
		return boots_;
	}
	/**
	 * @return the ring1_
	 */
	public Ring getring1_() {
		return ring1_;
	}
	/**
	 * @return the ring1_
	 */
	public Ring getRing2_()	{
		return ring2_;
	}
	/**
	 * @param playerID_ the playerID_ to set
	 */
	public void setPlayerID_(String playerID_) {
		this.playerID_ = playerID_;
	}
	/**
	 * @param playerName_ the playerName_ to set
	 */
	public void setPlayerName_(String playerName_) {
		this.playerName_ = playerName_;
	}
	/**
	 * @param characterName_ the characterName_ to set
	 */
	public void setCharacterName_(String characterName_) {
		this.characterName_ = characterName_;
	}
	/**
	 * @param characterRace_ the characterRace_ to set
	 */
	public void setCharacterRace_(CharacterRace characterRace_) {
		this.characterRace_ = characterRace_;
	}
	/**
	 * @param className_ the className_ to set
	 */
	public void setClassName_(CharacterClass className_) {
		this.className_ = className_;
	}
	/**
	 * @param healthPoints_ the healthPoints_ to set
	 */
	public void setHealthPoints_(Integer healthPoints_) {
		this.healthPoints_ = healthPoints_;
	}
	/**
	 * @param manaPoints_ the manaPoints_ to set
	 */
	public void setManaPoints_(Integer manaPoints_) {
		this.manaPoints_ = manaPoints_;
	}
	/**
	 * @param manaPointsRegen_ the manaPointsRegen_ to set
	 */
	public void setManaPointsRegen_(Integer manaPointsRegen_) {
		this.manaPointsRegen_ = manaPointsRegen_;
	}
	/**
	 * @param strength_ the strength_ to set
	 */
	public void setStrength_(Integer strength_) {
		this.strength_ = strength_;
	}
	/**
	 * @param inteligence_ the inteligence_ to set
	 */
	public void setInteligence_(Integer inteligence_) {
		this.inteligence_ = inteligence_;
	}
	/**
	 * @param agility_ the agility_ to set
	 */
	public void setAgility_(Integer agility_) {
		this.agility_ = agility_;
	}
	/**
	 * @param magicDefense_ the magicDefense_ to set
	 */
	public void setMagicDefense_(Integer magicDefense_) {
		this.magicDefense_ = magicDefense_;
	}
	/**
	 * @param meeleDefense_ the meeleDefense_ to set
	 */
	public void setMeeleDefense_(Integer meeleDefense_) {
		this.meeleDefense_ = meeleDefense_;
	}
	/**
	 * @param skillBar the skillBar to set
	 */
	public void setSkillBar(ArrayList<Skill> skillBar) {
		this.skillBar = skillBar;
	}
	/**
	 * @param playerPosition_ the playerPosition_ to set
	 */
	public void setPlayerPosition_(LatLng playerPosition_) {
		this.playerPosition_ = playerPosition_;
	}
	/**
	 * @param range_ the range_ to set
	 */
	public void setRange_(Integer range_) {
		this.range_ = range_;
	}
	/**
	 * @param weapon_ the weapon_ to set
	 */
	public void setWeapon_(Weapon weapon_) {
		this.weapon_ = weapon_;
	}
	/**
	 * @param offhand_ the offhand_ to set
	 */
	public void setOffhand_(Offhand offhand_) {
		this.offhand_ = offhand_;
	}
	/**
	 * @param helmet_ the helmet_ to set
	 */
	public void setHelmet_(Helmet helmet_) {
		this.helmet_ = helmet_;
	}
	/**
	 * @param chestArmor_ the chestArmor_ to set
	 */
	public void setChestArmor_(ChestArmor chestArmor_) {
		this.chestArmor_ = chestArmor_;
	}
	/**
	 * @param gloves_ the gloves_ to set
	 */
	public void setGloves_(Gloves gloves_) {
		this.gloves_ = gloves_;
	}
	/**
	 * @param boots_ the boots_ to set
	 */
	public void setBoots_(Boots boots_) {
		this.boots_ = boots_;
	}
	/**
	 * @param ring1_ the ring1_ to set
	 */
	public void setring1_(Ring ring1_) {
		this.ring1_ = ring1_;
	}
	/**
	 * @param ring2_ the ring2_ to set
	 */
	public void setring2_(Ring ring2_) {
		this.ring2_ = ring2_;
	}
	
	
}

//=====================================


//=====================================
//public class 