package com.s3ns3i.degejm;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;

//=============================================
public class AllItemsDialogFragment extends DialogFragment{
	
	private ArrayList<Items> items;
	//This item will be passed to the parent fragment.
	private Items selectedItem;
	//Adapter that puts data into alert dialog.
	//private ArrayAdapter<String> itemsAdapter;
	//List of items names.
	//private ArrayList<String> itemsNames;
	private CharSequence[] itemsCS;
	private Integer positionOnItemList_;
	private Button pressedButton;
	
	/**
	 * 
	 * @param items - ArrayList of item that will be shown 
	 * @param selectedItem - returns clicked item to this reference.
	 */
//	public AllItemsDialogFragment(ArrayList<Items> items, Items selectedItem, Button pressedButton){
//		this.items = items;
//		this.selectedItem = selectedItem;
//		this.pressedButton = pressedButton;
//		//itemsNames = new ArrayList<String>();
//		itemsCS = new CharSequence[items.size() + 1];
//		positionOnItemList_ = Integer.valueOf(0);
//		//itemsNames.add("Unequip item");
//		itemsCS[0] = "Unequip item";
//		for(int i = 1; i < items.size() + 1; i++){
//			//itemsNames.add(this.items.get(i - 1).getName_());
//			itemsCS[i] = this.items.get(i - 1).getName_();
//		}
//	}

    public AllItemsDialogFragment(){
        //itemsNames = new ArrayList<String>();
    }

    public void setArguments(ArrayList<Items> items, Items selectedItem, Button pressedButton){
        this.items = items;
        this.selectedItem = selectedItem;
        this.pressedButton = pressedButton;
        itemsCS = new CharSequence[items.size() + 1];
        positionOnItemList_ = Integer.valueOf(0);
        //itemsNames.add("Unequip item");
        itemsCS[0] = "Unequip item";
        for(int i = 1; i < items.size() + 1; i++){
            //itemsNames.add(this.items.get(i - 1).getName_());
            itemsCS[i] = this.items.get(i - 1).getName_();
        }
    }
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		//We need to create a Dialog with all items of one type listed, so
		//we can choose one to wear.
		builder.setSingleChoiceItems(itemsCS, selectedItem.getPositionOnItemList_(), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				positionOnItemList_ = which;
				Log.d("AllItemsDialogFragment", "Clicked item!");
			}
		});
		
//		builder.setSingleChoiceItems(itemsAdapter, selectedItem.getPositionOnItemList_(), new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		builder.setNegativeButton(R.string.backString, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.d("AllItemsDialogFragment", "Clicked back button!");
				dialog.dismiss();
			}
		});
		
		builder.setNeutralButton(R.string.compare_items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.d("AllItemsDialogFragment", "Clicked compare button!");
			}
		});
		
		builder.setPositiveButton(R.string.equip_item, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.d("AllItemsDialogFragment", "Clicked equip button!\nwhich = " + which + "\ncheckedItem = " + positionOnItemList_);
				if(positionOnItemList_ == 0){
					selectedItem.setCost_(0);
					selectedItem.setID_(0);
					selectedItem.setName_("");
					pressedButton.setBackgroundResource(getActivity().getResources().getIdentifier(selectedItem.getImgURL_() + "empty_slot", "drawable", getActivity().getPackageName()));
					selectedItem.setImgURL_("");
					selectedItem.setMagicAttack_(0);
					selectedItem.setMagicDefense_(0);
					selectedItem.setMeeleAttack_(0);
					selectedItem.setMeeleDefense_(0);
					selectedItem.setPositionOnItemList_(-1);
					//pressedButton.setBackgroundResource(getActivity().getResources().getIdentifier(items.get(positionOnItemList_).getImgURL_() + "empty_slot", "drawable", getActivity().getPackageName()));
				}
				else{
					try{
						selectedItem.setCost_(items.get(positionOnItemList_ - 1).getCost_());
						selectedItem.setID_(items.get(positionOnItemList_ - 1).getID_());
						selectedItem.setName_(items.get(positionOnItemList_ - 1).getName_());
						selectedItem.setImgURL_(items.get(positionOnItemList_ - 1).getImgURL_());
						selectedItem.setMagicAttack_(items.get(positionOnItemList_ - 1).getMagicAttack_());
						selectedItem.setMagicDefense_(items.get(positionOnItemList_ - 1).getMagicDefense_());
						selectedItem.setMeeleAttack_(items.get(positionOnItemList_ - 1).getMeeleAttack_());
						selectedItem.setMeeleDefense_(items.get(positionOnItemList_ - 1).getMeeleDefense_());
						selectedItem.setPositionOnItemList_(positionOnItemList_);
						pressedButton.setBackgroundResource(getActivity().getResources().getIdentifier(items.get(positionOnItemList_ - 1).getImgURL_() + items.get(positionOnItemList_ - 1).getID_(), "drawable", getActivity().getPackageName()));
					} catch (Exception e){
						selectedItem.setCost_(items.get(positionOnItemList_ - 1).getCost_());
						selectedItem.setID_(items.get(positionOnItemList_ - 1).getID_());
						selectedItem.setName_(items.get(positionOnItemList_ - 1).getName_());
						selectedItem.setImgURL_(items.get(positionOnItemList_ - 1).getImgURL_());
						selectedItem.setMagicAttack_(items.get(positionOnItemList_ - 1).getMagicAttack_());
						selectedItem.setMagicDefense_(items.get(positionOnItemList_ - 1).getMagicDefense_());
						selectedItem.setPositionOnItemList_(positionOnItemList_);
						pressedButton.setBackgroundResource(getActivity().getResources().getIdentifier(items.get(positionOnItemList_ - 1).getImgURL_() + items.get(positionOnItemList_ - 1).getID_(), "drawable", getActivity().getPackageName()));
					}
				}
			}
		});
		
		return builder.create();
	}
}