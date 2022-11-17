package com.choonham.lck_manager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.choonham.lck_manager.entity.NewsAndIssueEntity;
import com.choonham.lck_manager.entity.NewsEffectsEntity;
import com.choonham.lck_manager.joinedEntity.JoinedNews;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

@Dao
public interface NewsAndIssueDAO {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        Maybe<Long> insertNews(NewsAndIssueEntity newsAndIssueEntity);

        @Update
        Completable updateNews(NewsAndIssueEntity newsAndIssueEntity);

        @Delete
        Completable deleteNews(NewsAndIssueEntity newsAndIssueEntity);

        @Query("SELECT * FROM news_and_issue")
        LiveData<NewsAndIssueEntity> loadNews();

        @Query("SELECT " +
                "t1.news_code, " +
                "t1.news_content, " +
                "t1.news_title, " +
                "t1.effected_player, " +
                "t1.remaining, " +
                "t2.effect_code, " +
                "t2.effect, " +
                "t2.effect_content, " +
                "t2.effected_index, " +
                "t2.effected_status " +
        "FROM news_and_issue t1 " +
        "INNER JOIN news_effects_entity t2 " +
        "ON t2.news_code = t1.news_code " +
        "WHERE t1.news_code = :newsCode"
        )
        LiveData<List<JoinedNews>> loadNewsAndEffectByCode(int newsCode);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        Maybe<Long> insertEffect(NewsEffectsEntity newsEffectsEntity);
}
