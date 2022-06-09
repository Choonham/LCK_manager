package com.choonham.lck_manager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.choonham.lck_manager.entity.TempLeagueSchedule;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.*;

public class LeagueSchedule extends Fragment {
    private MaterialCalendarView calendarView;

    String[] teamList = {"T1", "DRX", "DK", "BRO", "Gen", "KDF", "NS", "LSB", "KT", "HLE", "KMH"};

    ArrayList<String[]> leagueScheduleList;

    TextView teamA;
    TextView teamB;
    String teamName;
    String teamRank;

    TextView teamARank;
    TextView teamBRank;

    ArrayList<TempLeagueSchedule> tempLeagueScheduleList = new ArrayList<>();
    ArrayList<String> matchDateList = new ArrayList<>();

    ListView leagueScheduleListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.league_schedule, container, false);

        leagueScheduleList = setLeagueSchedule(teamList);

        try{
            setLeagueScheduleMap();
        } catch (Exception e) {
            Log.e("error" , e.getMessage());
        }

        LeagueScheduleAdapter leagueScheduleAdapter = new LeagueScheduleAdapter(tempLeagueScheduleList, matchDateList, getContext());
        leagueScheduleListView = view.findViewById(R.id.league_schedule_list_view);
        leagueScheduleListView.setAdapter(leagueScheduleAdapter);

        leagueScheduleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int i, long l) {
                TextView teamA = view.findViewById(R.id.match_detail_team_a);
                TextView teamB = view.findViewById(R.id.match_detail_team_b);
                TextView matchInfo = view.findViewById(R.id.league_schedule_match_detail);

                TextView selectedTeamA = selectedView.findViewById(R.id.league_schedule_team_a);
                teamA.setText(selectedTeamA.getText());

                TextView selectedTeamB = selectedView.findViewById(R.id.league_schedule_team_b);
                teamB.setText(selectedTeamB.getText());

                TextView selectedMatch = selectedView.findViewById(R.id.league_schedule_match_num);
                matchInfo.setText(selectedMatch.getText());

            }
        });

        teamA = view.findViewById(R.id.match_detail_team_a);
        teamB = view.findViewById(R.id.match_detail_team_b);

        teamARank = view.findViewById(R.id.match_detail_team_a_rank);
        teamBRank = view.findViewById(R.id.match_detail_team_b_rank);
        teamA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamName = (String) teamA.getText();
                teamRank = (String) teamARank.getText();

                Intent intent = new Intent(getContext(), TeamInfoPopUpActivity.class);

                intent.putExtra("teamName", teamName);
                intent.putExtra("teanRank", teamRank);

                startActivity(intent);
            }
        });

        teamB.setOnClickListener(view1 -> {
            teamName = (String) teamB.getText();
            teamRank = (String) teamBRank.getText();

            Intent intent = new Intent(getContext(), TeamInfoPopUpActivity.class);

            intent.putExtra("teamName", teamName);
            intent.putExtra("teanRank", teamRank);

            startActivity(intent);
        });

        return view;
    }

    public void setLeagueScheduleMap() throws Exception{

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse("2022-01-12");
     /*   Date endDate = formatter.parse("2022-02-18");*/

        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        /*Calendar end = Calendar.getInstance();
        end.setTime(endDate);*/

        Date date = start.getTime();
        int matchNum = 1;
        int matchDay = 1;

        for(int i = 1; i <= 55;) {

            Calendar temp = Calendar.getInstance();
            temp.setTime(date);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd E요일");
            String strNowDate = simpleDateFormat.format(date);

            int day = temp.get(Calendar.DAY_OF_WEEK);

            if(day >= 4 || day == 1) {

                for(int j = 0; j <= 1; j++) {
                    if(leagueScheduleList.size() > 0) {
                        String[] tempMatch = leagueScheduleList.remove(0);
                        TempLeagueSchedule leagueSchedule = new TempLeagueSchedule();
                        leagueSchedule.setTeamA(tempMatch[0]);
                        leagueSchedule.setTeamB(tempMatch[1]);
                        leagueSchedule.setMatchNum(matchNum);
                        leagueSchedule.setDate(date);

                        tempLeagueScheduleList.add(leagueSchedule);
                        matchNum = matchNum + 1;
                    }
                }

                /*matchDateList.add(strNowDate);*/
                matchDateList.add(Integer.toString(matchDay));
                /*Log.d("dd", matchScheduleList.size() + "||" + matchDateList.size());*/
                i = i + 2;
                matchDay ++;

            }

            start.add(Calendar.DATE, 1);
            date = start.getTime();
        }

    }

    public ArrayList<String[]> setLeagueSchedule(String[] teamList) {
        String[] tempTeamList = teamList;
        int teamNum = tempTeamList.length;
        int[] teamIdArray = new int[teamNum];

        for(int i = 1; i <= teamNum; i++) {
            teamIdArray[i-1] = i;
        }

        ArrayList<Integer> groupA = new ArrayList<>();
        ArrayList<Integer> groupB = new ArrayList<>();

        for(int i = 0; i <= teamIdArray.length-1; i++) {
            groupA.add(teamIdArray[i]);
            groupB.add(teamIdArray[i]);
        }

        groupB.remove(0);
        groupB.add(0);

        ArrayList<String[]> leagueScheduleResult = new ArrayList<String[]>();

        while (groupB.contains(11)) {
            for (int j = 0; j < teamIdArray.length; j++) {
                /*if(!Objects.equals(groupA.get(j), groupB.get(j))) {*/
                    /*Log.d("DD", groupA.get(j)+ "||" + groupB.get(j));*/
                    int a = groupA.get(j) - 1;
                    int b = groupB.get(j) - 1;
                    if(b >= 0) {
                        String[] aMatch = {tempTeamList[a], tempTeamList[b]};
                        /*Log.d("dd", aMatch[0] + "||" +aMatch[1]);*/
                        leagueScheduleResult.add(aMatch);
                    }

                }
            /*}*/
            /*Collections.rotate(groupB, -1);*/
            groupB.remove(0);
            groupB.add(0);
        }

        return leagueScheduleResult;
    }

}
