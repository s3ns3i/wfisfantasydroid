package com.s3ns3i.degejm;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

public class TestActivity extends Activity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
			/**
			 * s3ns3i:
			 * There we can initialize fragments.
			 * Now I'm initializing connection fragment, because I need to collect
			 * data from the server first.
			 * When it will be done, I'll swap this fragment with character creating one.
			 */
					.add(R.id.container, new PlayerEquipmentFragment()).commit();
		}
        
        //Those are for the navigation drawer. It was generated automatically.
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        
    }

    //This whole code below was generated automatically.
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_menu, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
	
	@Override
	public void onBackPressed(){
//		ProgressDialog pDialog;
//		pDialog = new ProgressDialog(this);
//		pDialog.setMessage("Lolz");
//		pDialog.setIndeterminate(false);
//		pDialog.setCancelable(false);
//		pDialog.setProgress(0);
//		pDialog.show();
		
		//pDialog.dismiss();
		//finish();
		//No wszystko fajnie, tylko jak potem zamkn¹æ to okienko i wywo³aæ finish? mo¿na to zrobiæ w procesie?
		//da siê: http://stackoverflow.com/questions/8623823/finish-the-calling-activity-when-asynctask-completes
		return;
	}
}
