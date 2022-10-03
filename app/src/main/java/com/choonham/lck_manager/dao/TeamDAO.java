package com.choonham.lck_manager.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.choonham.lck_manager.entity.TeamEntity;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

@Dao
public interface TeamDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> insertTeamData(TeamEntity teamEntity);

    @Query("SELECT * FROM team t WHERE t.team_code = :teamCode")
    Single<TeamEntity> loadTeamDataByCode(Long teamCode);

    @Query("SELECT * FROM team t WHERE t.user_code = :userCode")
    Single<TeamEntity> loadTeamDataByUserCode(int userCode);

    @Query("SELECT * FROM team t WHERE t.season_code = :seasonCode")
    Single<List<TeamEntity>> loadAllTeamBySeasonCode(int seasonCode);
}
