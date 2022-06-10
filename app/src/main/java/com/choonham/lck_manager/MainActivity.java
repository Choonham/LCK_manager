package com.choonham.lck_manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;
import com.choonham.lck_manager.dao.TestDAO;
import com.choonham.lck_manager.entity.LeagueRankEntity;
import com.choonham.lck_manager.room.AppDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ViewPager2 pager;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    SQLiteDatabase database;

    SharedPreferences prefs;

    AppDatabase db;

    LeagueRankEntity leagueRankEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        /*checkFirstRun(this);*/
        db = AppDatabase.getInstance(this);


        /*leagueRankEntity = new LeagueRankEntity();
        leagueRankEntity.setRank(12);
        leagueRankEntity.setLose(8);
        leagueRankEntity.setWin(5);
        leagueRankEntity.setWd(-3);
        leagueRankEntity.setTeamCode(3);

        testDataInsertAndSelect(db, leagueRankEntity);*/

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.navi_icon);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigation.setSelectedItemId(R.id.tab1);
                        break;

                    case 1:
                        bottomNavigation.setSelectedItemId(R.id.tab2);
                        break;

                    case 2:
                        bottomNavigation.setSelectedItemId(R.id.tab3);
                        break;

                    case 3:
                        bottomNavigation.setSelectedItemId(R.id.tab4);
                        break;

                    case 4:
                        bottomNavigation.setSelectedItemId(R.id.tab5);
                        break;
                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        return false;
    }

    private void createDatabase(String name) {
        database = openOrCreateDatabase(name, MODE_PRIVATE, null);
    }

    private void createTables(Context contect) {

        if(database == null) {
            return;
        }
        Log.d("데이터베이스 생성 시작!", database.getPath());
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_champion));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_player));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_player_champion_played_data));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_sub_roster));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_team));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_user));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_user_record));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_champion_counter));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_league_schedule));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_main_roster));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_match_data));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_league_rank));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_pog_point));
        database.execSQL(contect.getResources().getString(R.string.sql_create_table_transfer_window));
        Log.d("데이터베이스 생성 끝!", database.getPath());
    }

    public void checkFirstRun(Context context){
        boolean isFirstRun = prefs.getBoolean("isFirstRun",true);
        if(isFirstRun)    {
            createDatabase("lck_manager");
            createTables(context);
        }
    }

    private void testDataInsertAndSelect(AppDatabase db, LeagueRankEntity leagueRankEntity) {
        TestDAO testDAO = db.textDao();
        testDAO.insertLeagueRankEntity(leagueRankEntity);
        List<LeagueRankEntity> temp = testDAO.loadAllLeagueRankEntity();

        Log.d("test:", Integer.toString(temp.get(0).getRank()));
    }
}

