/**
 * Base Class Items.java
 */
package com.s3ns3i.degejm.Player;

/**
 * @author Bartek
 *
 */
public class Items {

	protected Integer ID_; 
	protected String  name_;
	protected Integer meeleDefense_;
	protected Integer magicDefense_;
	protected Integer meeleAttack_;
	protected Integer magicAttack_;
	protected Integer cost_;
	protected String imgURL_;
	protected Integer positionOnItemList_;
	
	
	//===============C-Tor================================
	
	public Items(){
		//Kurde no, muszę dorabiać konstruktory. //s3ns3i
		this.ID_ = 0;
		this.meeleDefense_ = 0;
		this.magicDefense_ = 0;
		this.meeleAttack_ = 0;
		this.magicAttack_ = 0;
		this.imgURL_="";
		this.cost_=0;
		this.name_="";
		this.positionOnItemList_=-1;
		
	}
	
	public Items(Integer ID_
			,  Integer meeleDefense_
			,  Integer magicDefense_
			,  Integer meeleAttack_
			,  Integer magicAttack_
			,  String imgURL_
			,  String name_
			,  Integer cost_) {
		super();
		this.ID_ = ID_;
		this.meeleDefense_ = meeleDefense_;
		this.magicDefense_ = magicDefense_;
		this.meeleAttack_ = meeleAttack_;
		this.magicAttack_ = magicAttack_;
		this.imgURL_=imgURL_;
		this.cost_=cost_;
		this.name_=name_;
		this.positionOnItemList_=-1;
	}

	//===================END OF C-Tor=======================
	//===================GETTERS============================

	public Integer getID_() {
		return ID_;
	}

	public Integer getMeeleDefense_() {
		return meeleDefense_;
	}

	public Integer getMagicDefense_() {
		return magicDefense_;
	}

	public Integer getMeeleAttack_() {
		return meeleAttack_;
	}

	public Integer getMagicAttack_() {
		return magicAttack_;
	}

	public Integer getCost_() {
		return cost_;
	}
	
	public String getImgURL_() {
		return imgURL_;
	}

	public String getName_() {
		return name_;
	}
	
	public Integer getPositionOnItemList_(){
		return positionOnItemList_;
	}
	//===================END OF GETTERS=====================

	//====================SETTERS===========================
	
	public void setID_(Integer ID_) {
		this.ID_ = ID_;
	}


	public void setMeeleDefense_(Integer meeleDefense_) {
		this.meeleDefense_ = meeleDefense_;
	}

	public void setMagicDefense_(Integer magicDefense_) {
		this.magicDefense_ = magicDefense_;
	}

	public void setMeeleAttack_(Integer meeleAttack_) {
		this.meeleAttack_ = meeleAttack_;
	}

	public void setMagicAttack_(Integer magicAttack_) {
		this.magicAttack_ = magicAttack_;
	}

	public void setCost_(Integer cost_) {
		this.cost_ = cost_;
	}
	
	public void setImgURL_(String imgURL_){
		this.imgURL_ = imgURL_;
	}
	
	public void setName_(String name_){
		this.name_ = name_;
	}
	
	public void setPositionOnItemList_(Integer positionOnItemList_){
		this.positionOnItemList_ = positionOnItemList_;
	}
//===================END OF SETTERS=====================
}
