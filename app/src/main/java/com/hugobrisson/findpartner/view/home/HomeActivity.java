package com.hugobrisson.findpartner.view.home;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.hugobrisson.findpartner.R;
import com.hugobrisson.findpartner.adapter.NavAdapter;
import com.hugobrisson.findpartner.manager.FragmentTransitionManager;
import com.hugobrisson.findpartner.model.NavItem;
import com.hugobrisson.findpartner.model.User;
import com.hugobrisson.findpartner.utils.DrawerListener;
import com.parse.ParseUser;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugo on 17/07/2015.
 */
@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {

    @ViewById(R.id.toolbar)
    Toolbar mToolbar;

    @ViewById(R.id.navigation)
    RecyclerView mRecyclerView;

    @ViewById(R.id.drawer)
    DrawerLayout mDrawerLayout;

    @Bean
    FragmentTransitionManager fragmentTransitionManager;

    private List<NavItem> mNavItems;

    private RecyclerView.LayoutManager mLayoutManager;

    @AfterViews
    void configure() {
        // Initializing Toolbar and setting it as the actionbar
        setSupportActionBar(mToolbar);

        mNavItems = getMenu();
        NavAdapter navAdapter = new NavAdapter(this, (User) ParseUser.getCurrentUser(), mNavItems, new DrawerListener() {
            @Override
            public void OnItemClick(int position) {
                Fragment fragment = null;
                switch (position) {
                    //Home
                    case 1:
                        break;
                    //Sports
                    case 2:
                        break;
                    //Events
                    case 3:
                        break;
                    //Partner
                    case 4:
                        break;
                    //Notification
                    case 5:
                        break;
                    //Setting
                    case 6:
                        break;
                }
                mDrawerLayout.closeDrawers();
               // fragmentTransitionManager.initActivity(getFragmentManager(), fragment);
            }

        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(navAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open_drawer, R.string.close_drawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
/*
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }

    /**
     * get all element for menu drawer.
     *
     * @return
     */
    private List<NavItem> getMenu() {
        List<NavItem> navItems = new ArrayList<>();
        Resources res = getResources();
        TypedArray icons = res.obtainTypedArray(R.array.nav_icon);
        TypedArray titles = res.obtainTypedArray(R.array.nav_title);

        for (int i = 0; i <= titles.length() - 1; i++) {
            NavItem navItem = new NavItem(icons.getDrawable(i), titles.getString(i));
            navItems.add(navItem);
        }

        // for the header
        NavItem navItem = new NavItem();
        navItems.add(navItem);

        icons.recycle();
        titles.recycle();

        return navItems;
    }

}