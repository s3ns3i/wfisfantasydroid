package com.s3ns3i.degejm.Player;

public class Skill {
	
	protected Integer ID_;
	protected String name_;
	protected Integer damage_;
	protected Double magicMultiplier_;
	protected Double meeleMultiplier_;
	protected String skillURL_;
	
	


	/**
	 * @param iD_
	 * @param name_
	 * @param damage_
	 * @param magicMultiplier_
	 * @param meeleMultiplier_
	 * @param skillURL_
	 */
	public Skill(Integer iD_, String name_, Integer damage_,
			Double magicMultiplier_, Double meeleMultiplier_, String skillURL_) {
		
		ID_ = iD_;
		this.name_ = name_;
		this.damage_ = damage_;
		this.magicMultiplier_ = magicMultiplier_;
		this.meeleMultiplier_ = meeleMultiplier_;
		this.skillURL_ = skillURL_;
	}


	/**
	 * @return the iD_
	 */
	public Integer getID_() {
		return ID_;
	}


	/**
	 * @param iD_ the iD_ to set
	 */
	public void setID_(Integer iD_) {
		ID_ = iD_;
	}


	/**
	 * @return the name_
	 */
	public String getName_() {
		return name_;
	}


	/**
	 * @param name_ the name_ to set
	 */
	public void setName_(String name_) {
		this.name_ = name_;
	}


	/**
	 * @return the damage_
	 */
	public Integer getDamage_() {
		return damage_;
	}


	/**
	 * @param damage_ the damage_ to set
	 */
	public void setDamage_(Integer damage_) {
		this.damage_ = damage_;
	}


	/**
	 * @return the magicMultiplier_
	 */
	public Double getMagicMultiplier_() {
		return magicMultiplier_;
	}


	/**
	 * @param magicMultiplier_ the magicMultiplier_ to set
	 */
	public void setMagicMultiplier_(Double magicMultiplier_) {
		this.magicMultiplier_ = magicMultiplier_;
	}


	/**
	 * @return the meeleMultiplier_
	 */
	public Double getMeeleMultiplier_() {
		return meeleMultiplier_;
	}


	/**
	 * @param meeleMultiplier_ the meeleMultiplier_ to set
	 */
	public void setMeeleMultiplier_(Double meeleMultiplier_) {
		this.meeleMultiplier_ = meeleMultiplier_;
	}


	/**
	 * @return the skillURL_
	 */
	public String getSkillURL_() {
		return skillURL_;
	}


	/**
	 * @param skillURL_ the skillURL_ to set
	 */
	public void setSkillURL_(String skillURL_) {
		this.skillURL_ = skillURL_;
	}
	

}
