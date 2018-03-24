package com.example.sulta.tplan.view.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.sulta.tplan.R;
import com.example.sulta.tplan.model.Trip;
import com.example.sulta.tplan.presenter.adapters.HomePagerAdapter;
import com.example.sulta.tplan.view.activities.interfaces.IHomeActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements IHomeActivity {

    Toolbar homeToolBar;
    TabLayout homeTabs;
    ViewPager homeViewPager;
    private final int[] imageResId = {
            R.drawable.ic_action_name,
            R.drawable.ic_done_all_black_24dp,
            R.drawable.ic_settings_black_24dp };

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeToolBar = (Toolbar) findViewById(R.id.tpToolBar);
        setSupportActionBar(homeToolBar);

        homeTabs = (TabLayout) findViewById(R.id.homeTabs);
        //  add taps to taplayout ;
        homeTabs.addTab(homeTabs.newTab().setText("UpComingTrips"));
        homeTabs.addTab(homeTabs.newTab().setText("HistoryTrips"));
        homeTabs.addTab(homeTabs.newTab().setText("Settings"));
      for (int i = 0; i < imageResId.length; i++) {
         int id =imageResId[i];
          homeTabs.getTabAt(i).setIcon(imageResId[i]);
         // getTabAt(i).setIcon(imageResId[i]);
      }
        // make gravity  fill tap layout
        homeTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        homeViewPager = (ViewPager) findViewById(R.id.homeViewPager);

        final HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager(), homeTabs.getTabCount());

        homeViewPager.setAdapter(adapter);
        homeViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(homeTabs));
        // listner when select tap  change viewoager (fragments)

        homeTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //on select tap  change view page item to another fragment
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                homeViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
////sulta editing

// make refrence for synchdata and give home refrence

    @Override
    public ArrayList<Trip> getAllTrips() {
        //return all trips here
        return null;
    }



    @Override
    public void setAllTrips(ArrayList<Trip> trips) {
    //set your trips with new one and reload view
    }


    ///sulta editing
}

