package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.*;

public class JoinedMatchData {
    @Embedded
    public MatchDataEntity matchDataEntity;

    @Relation(
            parentColumn = "league_schedule_code",
            entityColumn = "league_schedule_code"
    )
    public LeagueScheduleEntity leagueScheduleEntity;

    @Relation(
            parentColumn = "team_code",
            entityColumn = "team_code"
    )
    public TeamEntity teamEntity;

    @Relation(
            parentColumn = "player_code",
            entityColumn = "player_code"
    )
    public PlayerEntity playerEntity;

    @Relation(
            parentColumn = "champion_code",
            entityColumn = "champion_code"
    )
    public ChampionEntity championEntity;
}
