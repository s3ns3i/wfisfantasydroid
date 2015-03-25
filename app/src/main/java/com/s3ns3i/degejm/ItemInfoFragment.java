package com.s3ns3i.degejm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class ItemInfoFragment extends DialogFragment {
	
	private Boolean isCompare = false;
	private Items item
	, itemToCompare;
	/**
	 * 
	 * @param items - ArrayList of item that will be shown 
	 * @param isCompare - leave null, if you create dialog to show stats of an item that player is already wearing.
	 */
	public ItemInfoFragment(){
	}

    public void setArguments(Items item, Items itemToCompare, Boolean isCompare){
        this.item = item;
        this.itemToCompare = itemToCompare;
        this.isCompare = isCompare;
    }
	
	public void isCompare(){
		this.isCompare = isCompare;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		// There I need to input info about current item that will be readable for a player.
		// If we are making a dialog to compare two items (in list that we can choose new item to put on.
		if(itemToCompare == null){
			if(item.getID_() == 0){
				builder.setMessage("Slot is empty. Hold to choose an item.");
			}
			// If we want to only see ours items stats.
			else{
				String itemInfo = "Name: " + item.getName_() + "\n";
				itemInfo += "Meele defense: " + item.getMeeleDefense_() + "\n";
				itemInfo += "Magic defense: " + item.getMagicDefense_() + "\n";
				if(item.getMeeleAttack_() != 0)
					itemInfo += "Meele attack: " + item.getMeeleAttack_() + "\n";
				if(item.getMagicAttack_() != 0)
					itemInfo += "Magic attack: " + item.getMagicAttack_() + "\n";
				itemInfo += "Price: " + item.getCost_() + "\n";
				builder.setMessage(itemInfo);
			}
		}
		else{
			//It means we don't have yet any item
			if(item.getID_() == 0){
				//We only show description of the item (not in inventory, because slot is empty).
				String itemInfo = "Name: " + itemToCompare.getName_() + "\n";
				itemInfo += "Meele defense: " + itemToCompare.getMeeleDefense_() + "\n";
				itemInfo += "Magic defense: " + itemToCompare.getMagicDefense_() + "\n";
				if(itemToCompare.getMeeleAttack_() != 0)
					itemInfo += "Meele attack: " + itemToCompare.getMeeleAttack_() + "\n";
				if(itemToCompare.getMagicAttack_() != 0)
					itemInfo += "Magic attack: " + itemToCompare.getMagicAttack_() + "\n";
				itemInfo += "Price: " + itemToCompare.getCost_() + "\n";
				builder.setMessage(itemInfo);
			}
			else{
				//We need to store two cells of text in one Dialog.
				builder.setMessage("lol, two items. Coming soon!");
			}
		}
		return builder.create();
	}
}