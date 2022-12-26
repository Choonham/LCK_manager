package com.choonham.lck_manager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.dao.NewsAndIssueDAO;
import com.choonham.lck_manager.dao.TeamDAO;
import com.choonham.lck_manager.dao.UserDAO;
import com.choonham.lck_manager.entity.NewsAndIssueEntity;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.room.AppDatabase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private final ActivityTagEnum TAG = ActivityTagEnum.MAIN_ACTIVITY;

    ViewPager2 pager;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    GoogleSignInClient mGoogleSignInClient;

    SQLiteDatabase database;

    SharedPreferences prefs;

    SharedPreferences userPreferences;

    boolean isSeasonCodeLoaded = false;
    boolean isTeamCodeLoaded = false;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);

        userPreferences = Common.getPreferences(this);

        //getSeasonCode(this);
        db.userDAO().loadUserEntityById(1).observe(this, loadValue -> {
            SharedPreferences userPreferences = Common.getPreferences(this);

            SharedPreferences.Editor editor = userPreferences.edit();

            editor.putInt("user_season", loadValue.getSeasonCode());

            editor.commit();

            db.teamDAO().loadTeamDataByUserCode(loadValue.getApiUserCode()).observe(this, teamData -> {
                SharedPreferences userPreferences2 = Common.getPreferences(this);

                SharedPreferences.Editor editor2 = userPreferences2.edit();

                editor2.putInt("user_team_code", teamData.getApiTeamCode());

                editor2.commit();
            });

        });

        //loadTeamCodeByUserCode(this);

        //while(!isSeasonCodeLoaded || !isTeamCodeLoaded) {}

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        /*checkFirstRun(this);*/

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
        int id = item.getItemId();

        if(id == R.id.nav_sign_out) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

            mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    FirebaseAuth.getInstance().signOut();

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);

                    /*finishAffinity();*/
                }
            });
        } else if(id == R.id.nav_gallery) {

        } else if(id == R.id.nav_slideshow) {

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

   /* public void getSeasonCode(Context context) {
        UserDAO userDAO = db.userDAO();

        userDAO.loadUserEntityById(1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    //Log.d("호호 시즌", String.valueOf(loadValue.getSeasonCode()));
                    //Common.CURR_SEASON_CODE = loadValue.getSeasonCode();

                    SharedPreferences userPreferences = Common.getPreferences(context);

                    SharedPreferences.Editor editor = userPreferences.edit();

                    editor.putInt("user_season", loadValue.getSeasonCode());

                    editor.commit();

                    isSeasonCodeLoaded = true;
                })
                .doOnError(error -> {
                    Log.e("getSeasonCode error 2:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/

   /* public void loadTeamCodeByUserCode(Context context) {
        UserDAO userDAO = db.userDAO();

        userDAO.loadUserEntityById(1)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(userEntity -> {

                    TeamDAO teamDAO = db.teamDAO();

                    teamDAO.loadTeamDataByUserCode(userEntity.getApiUserCode())
                            .subscribeOn(Schedulers.io())
                            .doOnSuccess(loadValue -> {
                                //Log.d("호호 팀", String.valueOf(loadValue.getApiTeamCode()));
                                //Common.CURR_TEAM_CODE = loadValue.getApiTeamCode();

                                SharedPreferences userPreferences = Common.getPreferences(context);

                                SharedPreferences.Editor editor = userPreferences.edit();

                                editor.putInt("user_team_code", loadValue.getApiTeamCode());

                                editor.commit();

                                isTeamCodeLoaded = true;
                            })
                            .doOnError(error -> {
                                Log.e("loadMainRoster error 2:", error.getMessage());
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe();
                })
                .doOnError(error -> {
                    Log.e("loadMainRoster error 2:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/

}

