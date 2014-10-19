package me.gethelloworld.android.youhadmeathelloworld;

import android.app.ActionBar;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.gimbal.proximity.ProximityError;
import com.gimbal.proximity.ProximityFactory;
import com.gimbal.proximity.ProximityListener;
import com.gimbal.proximity.Visit;
import com.gimbal.proximity.VisitListener;
import com.gimbal.proximity.VisitManager;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import me.gethelloworld.android.youhadmeathelloworld.adapters.MainViewPagerAdapter;
import me.gethelloworld.android.youhadmeathelloworld.controller.MainFragmentsStore;
import me.gethelloworld.android.youhadmeathelloworld.listeners.OnPageToFragmentListener;

import com.gimbal.proximity.Proximity;
import com.gimbal.proximity.ProximityListener;

import java.util.Date;


public class MainActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, RootFragmentInteractionListener, ProximityListener, VisitListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    private ViewPager mPager;
    private MainViewPagerAdapter mPagerAdapter;
    public Date lastRecordedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Proximity Service Stuff
        //only run when sdk is 4.4.3 or higher

        int sdk = Build.VERSION.SDK_INT;
        int target = Build.VERSION_CODES.JELLY_BEAN;

        if ( sdk >= target ) {
            Proximity.initialize(this,
                    "15b9185423d8d4d2bb6a1a9bfac6e7f83c200c0a7ec92eacd17c223f6408e816",
                    "80d4f0eaabee200e58121eda35150e75e4bfe9a05c9a289e51c1f40a0a06f75c");
            Proximity.startService(this);
        }



        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.mainActivity_pager);
        mPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //TODO: Not abs.
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d("MainPager", "onPageScrolled()");
                //TODO: Use this call to slide a indicator on the action bar for the tabs.
                //Or add the tabs view to the AB
            }

            @Override
            public void onPageSelected(int position) { //TODO: Get this to call on the first load.
                setActionBarTitle(MainFragmentsStore.getFragmentNameFromLocation(position));
                invalidateOptionsMenu();
                if (mPagerAdapter.getItem(position) instanceof OnPageToFragmentListener) {
                    ((OnPageToFragmentListener) mPagerAdapter.getItem(position)).onPageToFragment();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mPager.setAdapter(mPagerAdapter);
        setActionBarTitle(MainFragmentsStore.getFragmentNameFromLocation(0));//hack
    }

    private void setActionBarTitle(String title) {
        mTitle = title;
        getActionBar().setTitle(title);
    }


    @Override
    public void onNavigationDrawerItemSelected(NavigationDrawerListItem item) {
        // update the main content by replacing fragments

        startActivity(new Intent(this, item.getActivityClazz()));
        finish();
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
            getMenuInflater().inflate(R.menu.main, menu);

            int currentPosition = mPager.getCurrentItem();

            // Set an icon in the ActionBar
            menu.findItem(R.id.root_swipe).setIcon(
                    new IconDrawable(this, Iconify.IconValue.fa_group)
                            .colorRes((currentPosition == 0) ? android.R.color.white : android.R.color.darker_gray)
                            .actionBarSize());
            // Set an icon in the ActionBar
            menu.findItem(R.id.root_matches).setIcon(
                    new IconDrawable(this, Iconify.IconValue.fa_comments)
                            .colorRes((currentPosition == 1) ? android.R.color.white : android.R.color.darker_gray)
                            .actionBarSize());
            // Set an icon in the ActionBar
            menu.findItem(R.id.root_moments).setIcon(
                    new IconDrawable(this, Iconify.IconValue.fa_picture_o)
                            .colorRes((currentPosition == 2) ? android.R.color.white : android.R.color.darker_gray)
                            .actionBarSize());

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

        switch (id) {
            case R.id.root_swipe:
                mPager.setCurrentItem(0);
                break;
            case R.id.root_matches:
                mPager.setCurrentItem(1);
                break;
            case R.id.root_moments:
                mPager.setCurrentItem(2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.close();
        } else if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void serviceStarted() {
        // this will be invoked if the service has successfully started
        // bluetooth scanning will be started at this point
        Log.d("Proximity", "Proximity Service successfully started!");

        VisitManager visitManager = ProximityFactory.getInstance().createVisitManager();
        visitManager.setVisitListener(this);
        visitManager.start();
    }

    @Override
    public void startServiceFailed(int i, String s) {
        // this will be called if the service has failed to start
        String logMsg = String.format("Proximity Service failed with error code %d, message: %s!", i, s);
        Log.d("Proximity", logMsg);
        //check for the error Code for Bluetooth status check
        if (i == ProximityError.PROXIMITY_BLUETOOTH_IS_OFF.getCode()) {
            //turn on the bluetooth and once the bluetooth is ON call startService again.
        }
    }

    @Override
    public void didArrive(Visit visit) {

    }

    @Override
    public void receivedSighting(Visit visit, Date date, Integer integer) {
       Log.d("tags", "Received Sighting Date: " + date);
    }

    @Override
    public void didDepart(Visit visit) {

    }
}
