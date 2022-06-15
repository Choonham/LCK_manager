package com.choonham.lck_manager.dao;

import androidx.room.*;
import com.choonham.lck_manager.entity.NewsAndIssueEntity;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NewsAndIssueDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable insertNews(NewsAndIssueEntity newsAndIssueEntity);

    @Update
    public Completable updateNews(NewsAndIssueEntity newsAndIssueEntity);

    @Delete
    public Completable deleteNews(NewsAndIssueEntity newsAndIssueEntity);

    @Query("SELECT * FROM news_and_issue WHERE news_code = :newsCode")
    public Single<NewsAndIssueEntity> loadNewsByCode(int newsCode);

}
