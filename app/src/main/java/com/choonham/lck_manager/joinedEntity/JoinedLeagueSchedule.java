package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.RawQuery;
import androidx.room.Relation;
import com.choonham.lck_manager.LeagueSchedule;
import com.choonham.lck_manager.entity.LeagueScheduleEntity;
import com.choonham.lck_manager.entity.TeamEntity;

public class JoinedLeagueSchedule {
    @Embedded
    public LeagueScheduleEntity leagueScheduleEntity;

    @Relation(
            parentColumn = "team_code_a",
            entityColumn = "team_code"
    )
    public TeamEntity teamEntityA;

    @Relation(
            parentColumn = "team_code_b",
            entityColumn = "team_code"
    )
    public TeamEntity teamEntityB;
}
