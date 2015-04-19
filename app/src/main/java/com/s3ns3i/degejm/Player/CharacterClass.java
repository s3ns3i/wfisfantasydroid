package com.s3ns3i.degejm.Player;


public class CharacterClass{

    private Integer classID_;
    private String className_;
    private Integer magicAttack_;
    private Integer meeleAttack_;
    private Integer magicDefense_;
    private Integer meeleDefense_;
    private Integer hpModifier_;
    private Integer manaModifier_;



//c-tor

    public Integer getClassID_() {
        return classID_;
    }



    public void setClassID_(Integer classID_) {
        this.classID_ = classID_;
    }



    public String getClassName_() {
        return className_;
    }



    public void setClassName_(String className_) {
        this.className_ = className_;
    }



    public Integer getMagicAttack_() {
        return magicAttack_;
    }



    public void setMagicAttack_(Integer magicAttack_) {
        this.magicAttack_ = magicAttack_;
    }



    public Integer getMeeleAttack_() {
        return meeleAttack_;
    }



    public void setMeeleAttack_(Integer meeleAttack_) {
        this.meeleAttack_ = meeleAttack_;
    }



    public Integer getMagicDefense_() {
        return magicDefense_;
    }



    public void setMagicDefense_(Integer magicDefense_) {
        this.magicDefense_ = magicDefense_;
    }



    public Integer getMeeleDefense_() {
        return meeleDefense_;
    }



    public void setMeeleDefense_(Integer meeleDefense_) {
        this.meeleDefense_ = meeleDefense_;
    }



    public Integer getHpModifier_() {
        return hpModifier_;
    }



    public void setHpModifier_(Integer hpModifier_) {
        this.hpModifier_ = hpModifier_;
    }



    public Integer getManaModifier_() {
        return manaModifier_;
    }



    public void setManaModifier_(Integer manaModifier_) {
        this.manaModifier_ = manaModifier_;
    }

    public CharacterClass() {

    }

    public CharacterClass(Integer classID
            ,String className
            ,Integer magicAttack
            ,Integer meeleAttack
            ,Integer magicDefense
            ,Integer meeleDefense
            ,Integer hpModifier
            ,Integer manaModifier){
        classID_=classID;
        magicAttack_=magicAttack;
        meeleAttack_=meeleAttack;
        className_=className;
        meeleAttack_=meeleAttack;
        magicAttack_=magicAttack;
        hpModifier_=hpModifier;
        manaModifier_=manaModifier;
    }


}