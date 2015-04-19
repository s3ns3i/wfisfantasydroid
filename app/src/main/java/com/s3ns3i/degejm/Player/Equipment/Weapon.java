package com.s3ns3i.degejm.Player.Equipment;

import com.s3ns3i.degejm.Player.Items;

public class Weapon extends Items {

		
		
		public Weapon(Integer ID_
				,  Integer meeleDefense_
				,  Integer magicDefense_
				,  Integer meeleAttack_
				,  Integer magicAttack_
				,  String imgURL_
				,  String name_
				,  Integer cost_
				, Boolean twoHand_
				) {
			super(ID_, meeleDefense_, magicDefense_, meeleAttack_, magicAttack_, imgURL_, name_, cost_);
			this.twoHand_=twoHand_;
		}

		public Boolean getTwoHand_() {
			return twoHand_;
		}

		public void setTwoHand_(Boolean twoHand_) {
			this.twoHand_ = twoHand_;
		}

		private Boolean twoHand_;
		
		
		
	}
	//=====================================	