package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.LeagueRankingEntity;
import com.choonham.lck_manager.entity.LeagueScheduleEntity;
import com.choonham.lck_manager.entity.TeamEntity;

public class JoinedLeagueRanking {
    @Embedded
    public LeagueRankingEntity leagueRankingEntity;

    @Relation(
            parentColumn = "team_code",
            entityColumn = "api_team_code"
    )
    public TeamEntity teamEntity;
}
