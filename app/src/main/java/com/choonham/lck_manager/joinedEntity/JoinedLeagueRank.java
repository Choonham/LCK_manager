package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.LeagueRanking;
import com.choonham.lck_manager.entity.LeagueRankEntity;
import com.choonham.lck_manager.entity.TeamEntity;

public class JoinedLeagueRank {
    @Embedded
    public LeagueRankEntity leagueRankEntity;

    @Relation(
            parentColumn = "team_code",
            entityColumn = "team_code"
    )
    public TeamEntity teamEntity;
}
