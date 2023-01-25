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

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private final ActivityTagEnum TAG = ActivityTagEnum.MAIN_ACTIVITY;

    ViewPager2 pager;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    GoogleSignInClient mGoogleSignInClient;

    SharedPreferences prefs;

    SharedPreferences userPreferences;

    AppDatabase db;

    ContentViewFragment contentViewFragment;

    BanPickFragment banPickFragment;

    InGameFragment inGameFragment;

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

        // ContentViewFragment 제어 코드 삽입
        contentViewFragment = ContentViewFragment.newInstance();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, contentViewFragment).commit();

        banPickFragment = BanPickFragment.newInstance();
        inGameFragment = InGameFragment.newInstance();

    }

    public void onFragmentChanged(int index, @Nullable Bundle data) {
        if(index == 0) {
            contentViewFragment.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, contentViewFragment).commit();
        } else if(index == 1) {
            banPickFragment.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, banPickFragment).commit();
        } else if(index == 2) {
            inGameFragment.setArguments(data);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, inGameFragment).commit();
        }
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

}

