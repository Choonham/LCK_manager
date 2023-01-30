package com.choonham.lck_manager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.paging.Pager;
import androidx.viewpager2.widget.ViewPager2;
import com.choonham.lck_manager.common.Common;
import com.choonham.lck_manager.dao.*;
import com.choonham.lck_manager.entity.*;
import com.choonham.lck_manager.enums.ActivityTagEnum;
import com.choonham.lck_manager.joinedEntity.JoinedNews;
import com.choonham.lck_manager.joinedEntity.JoinedPlayer;
import com.choonham.lck_manager.room.AppDatabase;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class MainView extends Fragment {

    private final ActivityTagEnum TAG = ActivityTagEnum.MAIN_VIEW;

    ListView matchScheduleListView;

    List<MatchData> matchDataList;

    ImageButton matchStartButton;

    AppDatabase db;

    SharedPreferences userPreferences;

    boolean isMatchDataLoaded = false;

    Fragment parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_view, container, false);

        userPreferences = Common.getPreferences(getContext());

        db = AppDatabase.getInstance(getContext());
        matchDataList = new ArrayList<>();

        //getMatchData();

        //while(!isMatchDataLoaded) {}

        MatchScheduleAdapter matchScheduleAdapter = new MatchScheduleAdapter(matchDataList, getContext());
        matchScheduleListView = view.findViewById(R.id.match_schedule_list_view);

        matchScheduleListView.setAdapter(matchScheduleAdapter);

        db.leagueScheduleDAO().loadScheduleAgainstTeam(userPreferences.getInt("user_season", 1), userPreferences.getInt("user_team_code", 1))
                .observe(this, loadValue -> {
                    matchDataList.addAll(loadValue);

                    MatchScheduleAdapter matchScheduleAdapter2 = new MatchScheduleAdapter(matchDataList, getContext());

                    matchScheduleListView.setAdapter(matchScheduleAdapter2);
                });

        matchScheduleListView.smoothScrollToPosition(userPreferences.getInt("curr_match_index", 1));

        LinearLayout newsLayout = view.findViewById(R.id.news);
        newsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewsPopUpActivity.class);
                startActivity(intent);
            }
        });

        matchScheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                Intent intent = new Intent(getContext(), TeamInfoPopUpActivity.class);
                /*TextView teamName = selectedView.findViewById(R.id.match_schedule_team_name);
                TextView teamRank = selectedView.findViewById(R.id.match_schedule_rank);
                intent.putExtra("teamName", teamName.getText());
                intent.putExtra("teamRank", teamRank.getText());*/

                intent.putExtra("teamCode", matchDataList.get(i).getAgainst_team());
                intent.putExtra("teamName", matchDataList.get(i).getAgainst_team_name());
                intent.putExtra("teamRank", matchDataList.get(i).getTeam_rank());

                mGetContent.launch(intent);
                /*startActivity(intent);*/
            }
        });

        matchStartButton = view.findViewById(R.id.match_start_button);

        matchStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) parent.getActivity();
                mainActivity.onFragmentChanged(1, null);
            }
        });

        db = AppDatabase.getInstance(getContext());

        insertNewsData(db);
        insertEffectsData(db);


       /* // 로그인한 객체 정보(google)
        AccountManager am = AccountManager.get(getContext());
        Account[] accounts = am.getAccountsByType("com.google");

        for(Account account : accounts) {
            Log.d("account: ", account.name);
        }
        */


        /* 빈 공간 3번 탭하면 인게임 Activity로 넘어가도록 */
        view.setOnTouchListener(new View.OnTouchListener() {
            int clicked = 0;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    if(clicked == 3) {

                        Intent intent = new Intent(getContext(), MatchPlay.class);
                        startActivity(intent);

                        clicked = 0;
                    }
                    clicked = clicked + 1;
                }
                return false;
            }
        });

        return view;
    }

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                }
            }
    });


    private void insertNewsData(AppDatabase db){
        NewsAndIssueEntity newsAndIssueEntity = new NewsAndIssueEntity();
        newsAndIssueEntity.setNewsTitle("너구리: 킹켄 오른, 나보다 훨씬 잘해");
        newsAndIssueEntity.setNewsContent("22 LCK SUMMER 개막전: DRX 킹겐의 오른, 농심 선수들에게 벽을 느끼게 하며 승리!");
        newsAndIssueEntity.setEffectedPlayer(0);
        newsAndIssueEntity.setTeamCode(0);
        newsAndIssueEntity.setRemaining(3);

        Log.d("Insert data(news): ", newsAndIssueEntity.getNewsTitle());

        NewsAndIssueDAO newsAndIssueDAO = db.newsAndIssueDAO();
        newsAndIssueDAO.insertNews(newsAndIssueEntity)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(data -> {
                    Log.d("Insert data(news): ", data.toString());
                })
                .subscribe();


    }

    /*private Single<NewsAndIssueEntity> selectNewsDataByCode(AppDatabase db) {
        NewsAndIssueDAO newsAndIssueDAO = db.newsAndIssueDAO();
        return newsAndIssueDAO.loadNews();
    }*/

    private void insertEffectsData(AppDatabase db) {
        String[] effectContentList = {"경험치 획득량 변경", "스텟 조정: 안정성", "스텟 조정: 한타력"};
        int[] effectIndexList = {50, 10, 10};

        for(int i = 0; i < 3; i++) {
            NewsEffectsEntity newsEffectsEntity = new NewsEffectsEntity();

            newsEffectsEntity.setNewsCode(1);
            newsEffectsEntity.setEffect(i);
            newsEffectsEntity.setEffectContent(effectContentList[i]);
            newsEffectsEntity.setEffectedIndex(effectIndexList[i]);
            newsEffectsEntity.setEffectedStatus(i);

            db.newsAndIssueDAO().insertEffect(newsEffectsEntity).subscribeOn(Schedulers.io())
                    .doOnSuccess(data -> {
                        Log.d("Insert data(effects): ", data.toString());
                    })
                    .subscribe();
        }
    }

    public void setParentFragment(Fragment parent) {
        this.parent = parent;
    }

    /*private Single<List<JoinedNews>> selectNewsAndEffectByCode(AppDatabase db, int newsCode) {
        NewsAndIssueDAO newsAndIssueDAO = db.newsAndIssueDAO();
        return newsAndIssueDAO.loadNewsAndEffectByCode(newsCode);
    }*/

    /*public void getMatchData() {

        LeagueScheduleDAO leagueScheduleDAO = db.leagueScheduleDAO();
        leagueScheduleDAO.loadScheduleAgainstTeam(userPreferences.getInt("user_season", 1), userPreferences.getInt("user_team_code", 1))
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    for(MatchData temp : loadValue) {
                        matchDataList.add(temp);
                    }

                    isMatchDataLoaded = true;
                })
                .doOnError(error -> {
                    Log.e("getTeamList error 2:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/

    /*public void updateLeagueRankingList(int season) {
        LeagueRankingDAO leagueRankingDAO = db.leagueRankingDAO();

        Log.e("getLeagueRanking season: ", String.valueOf(season));

        leagueRankingDAO.loadLeagueRankingList(season)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(loadValue -> {
                    for(TeamRank teamEntity : loadValue) {
                        LeagueRankingEntity leagueRankingEntity = new LeagueRankingEntity();
                        leagueRankingEntity.setTeamCode(teamEntity.getTeam_code());
                        leagueRankingEntity.setRank(teamEntity.getRank());

                        leagueRankingDAO.insertLeagueRanking(leagueRankingEntity)
                                .subscribeOn(Schedulers.io())
                                .doOnSuccess(value -> {
                                    Log.d("updateLeagueRankingEntity",  value.toString());
                                })
                                .doOnError(error -> {
                                    Log.e("updateLeagueRankingList error 1:", error.getMessage());
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe();
                    }
                })
                .doOnError(error -> {
                    Log.e("updateLeagueRankingList error 2:", error.getMessage());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }*/

}
