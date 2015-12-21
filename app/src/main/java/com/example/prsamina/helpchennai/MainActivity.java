package com.example.prsamina.helpchennai;
//Certificate fingerprint (SHA1):

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.prsamina.helpchennai.adaptor.NavDrawerListAdapter;
import com.example.prsamina.helpchennai.model.NavDrawerItems;
import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;

import static com.example.prsamina.helpchennai.R.string.app_name;

/**
 * Created by prsamina on 12/19/2015.
 */
public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItems> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItems>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItems(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItems(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItems(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItems(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Pages
        navDrawerItems.add(new NavDrawerItems(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                getActionBar().setHomeButtonEnabled(true);
            }
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, app_name, app_name) {
            public void onDrawerClosed(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    getActionBar().setTitle(mTitle);
                }
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
             //displayView(0);
        }

        ////Adding the Listener to the list


        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());}

    void displayView(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0: {
                fragment = new MapFragment();
                break;
            }
            case 4: fragment= new Climate();
            default:
                break;
        }
        if (fragment != null) {
            //MapFragment mapFragment =new MapFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.map, fragment).commit();
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            Log.e("MyActivity", "Error in creating Fragment");
        }
    }


    private class SlideMenuClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }
}





