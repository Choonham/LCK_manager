package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.LeagueSeasonTeamEntity;
import com.choonham.lck_manager.entity.SeasonEntity;
import com.choonham.lck_manager.entity.TeamEntity;

public class JoinedLeagueSeasonTeam {
    @Embedded
    public LeagueSeasonTeamEntity leagueSeasonTeamEntity;

    @Relation(
            parentColumn = "team_code",
            entityColumn = "team_code"
    )
    public TeamEntity teamEntity;

    @Relation(
            parentColumn = "season_code",
            entityColumn = "season_code"
    )
    public SeasonEntity seasonEntity;
}
