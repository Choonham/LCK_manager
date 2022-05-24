package com.choonham.lck_manager;

import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.choonham.lck_manager.ui.slideshow.SlideshowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ViewPager2 pager;
    SlideshowFragment slideshowFragment;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(5);

        MyPagerAdapter adapter = new MyPagerAdapter(this);

        TransferWindow transferWindow = new TransferWindow();
        TeamRoster teamRoster = new TeamRoster();

        MainView mainView = new MainView();

        LeagueSchedule leagueSchedule = new LeagueSchedule();
        LeagueRanking leagueRanking = new LeagueRanking();

        adapter.addItem(transferWindow);
        adapter.addItem(teamRoster);
        adapter.addItem(mainView);
        adapter.addItem(leagueSchedule);
        adapter.addItem(leagueRanking);

        pager.setAdapter(adapter);

        pager.setCurrentItem(2);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setSelectedItemId(R.id.tab3);

        bottomNavigation.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab1:
                        pager.setCurrentItem(0);
                        return true;

                    case R.id.tab2:
                        pager.setCurrentItem(1);
                        return true;

                    case R.id.tab3:
                        pager.setCurrentItem(2);
                        return true;

                    case R.id.tab4:
                        pager.setCurrentItem(3);
                        return true;

                    case R.id.tab5:
                        pager.setCurrentItem(4);
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        return false;
    }
}