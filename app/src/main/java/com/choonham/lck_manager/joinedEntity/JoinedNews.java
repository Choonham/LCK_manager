package com.choonham.lck_manager.joinedEntity;

import androidx.room.Embedded;
import androidx.room.Relation;
import com.choonham.lck_manager.entity.NewsAndIssueEntity;
import com.choonham.lck_manager.entity.NewsEffectsEntity;

import java.util.List;


public class JoinedNews {
    @Embedded
    public NewsAndIssueEntity newsAndIssueEntity;

    @Relation(
            parentColumn = "news_code",
            entityColumn = "news_code"
    )
    public List<NewsEffectsEntity> newsEffectsEntities;
}
