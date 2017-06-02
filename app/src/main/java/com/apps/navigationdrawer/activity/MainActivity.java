package com.apps.navigationdrawer.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.apps.navigationdrawer.R;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    Toolbar toolbar;
    FragmentDrawer fragmentDrawer;

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume Called  ");
        displayView(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"onCreate Called  ");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Enable the toolbar by calling setSupportActionBar() by passing the toolbar object.
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fragmentDrawer = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentDrawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout),toolbar);
        fragmentDrawer.setDrawerListener(this);

        displayView(0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id= item.getItemId();

        if (id == R.id.action_settings){
            return true;
        }

        if (id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {

        Log.i(TAG,"displayView position ? "+position);

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (position){
            case  0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case  1:
                fragment = new FriendsFragment();
                title = getString(R.string.title_friends);
                break;
            case  2:
                fragment = new MessageFragment();
                title = getString(R.string.title_messages);
                break;
            default:
                break;
        }

        if (fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body,fragment);
            fragmentTransaction.commit();


            //Set the toolbar title
            getSupportActionBar().setTitle(title);
        }

    }
}
