package com.choonham.lck_manager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import com.choonham.lck_manager.dao.NewsAndIssueDAO;
import com.choonham.lck_manager.entity.NewsAndIssueEntity;
import com.choonham.lck_manager.entity.NewsEffectsEntity;
import com.choonham.lck_manager.joinedEntity.JoinedNews;
import com.choonham.lck_manager.room.AppDatabase;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.List;

public class MainView extends Fragment {

    ListView matchScheduleListView;

    String[] matchScheduleTeamList = {"T1", "DRX", "Gen.G", "KT", "DWG"};
    int[] matchScheduleResultList = {1, 0, 2, 2, 2};
    int[] matchScheduleRankList = {1, 4, 2, 6, 2};

    ImageButton matchStartButton;

    AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_view, container, false);

        MatchScheduleAdapter matchScheduleAdapter = new MatchScheduleAdapter(matchScheduleTeamList, matchScheduleResultList, matchScheduleRankList, getContext());
        matchScheduleListView = view.findViewById(R.id.match_schedule_list_view);

        matchScheduleListView.setAdapter(matchScheduleAdapter);

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
                TextView teamName = selectedView.findViewById(R.id.match_schedule_team_name);
                TextView teamRank = selectedView.findViewById(R.id.match_schedule_rank);
                intent.putExtra("teamName", teamName.getText());
                intent.putExtra("teamRank", teamRank.getText());

                mGetContent.launch(intent);
                /*startActivity(intent);*/
            }
        });

        matchStartButton = view.findViewById(R.id.match_start_button);

        matchStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Single<NewsAndIssueEntity> tempData = selectNewsDataByCode(db, 0);
                tempData
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(data -> {
                            Log.d("SELECT", data.getNewsContent());
                        })
                        .subscribe();

                Single<List<JoinedNews>> selectedData = selectNewsAndEffectByCode(db, 0);
                selectedData
                        .subscribeOn(Schedulers.io())
                        .doOnSuccess(data -> {
                            int i = 0;
                            for(JoinedNews e : data) {
                                Log.d("JOINED DATA(newsCode): ", Integer.toString(e.newsAndIssueEntity.getNewsCode()));
                                Log.d("JOINED DATA(effectCode): ", Integer.toString(e.newsEffectsEntities.get(i).getEffectCode()));
                                i++;
                            }
                        })
                        .subscribe();
            }
        });

        db = AppDatabase.getInstance(getContext());
        /*
        insertNewsData(db);
        insertEffectsData(db);
        */

       /* // 로그인한 객체 정보(google)
        AccountManager am = AccountManager.get(getContext());
        Account[] accounts = am.getAccountsByType("com.google");

        for(Account account : accounts) {
            Log.d("account: ", account.name);
        }
        */
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

        NewsAndIssueDAO newsAndIssueDAO = db.newsAndIssueDAO();
        newsAndIssueDAO.insertNews(newsAndIssueEntity)
                .subscribeOn(Schedulers.io())
                .doOnSuccess(data -> {
                    Log.d("Insert data(news): ", data.toString());
                })
                .subscribe();
    }

    private Single<NewsAndIssueEntity> selectNewsDataByCode(AppDatabase db, int newsCode) {
        NewsAndIssueDAO newsAndIssueDAO = db.newsAndIssueDAO();
        return newsAndIssueDAO.loadNewsByCode(0);
    }

    private void insertEffectsData(AppDatabase db) {
        String[] effectContentList = {"경험치 획득량 변경", "스텟 조정: 안정성", "스텟 조정: 한타력"};
        int[] effectIndexList = {50, 10, 10};

        for(int i = 0; i < 3; i++) {
            NewsEffectsEntity newsEffectsEntity = new NewsEffectsEntity();

            newsEffectsEntity.setNewsCode(0);
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

    private Single<List<JoinedNews>> selectNewsAndEffectByCode(AppDatabase db, int newsCode) {
        NewsAndIssueDAO newsAndIssueDAO = db.newsAndIssueDAO();
        return newsAndIssueDAO.loadNewsAndEffectByCode(newsCode);
    }

}
