package com.piisw.jpa.repositories;

import com.piisw.jpa.entities.Event;
import com.piisw.jpa.entities.RequestEvent;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    Page<Event> findByTimeBetweenAndAnalysisRequired(LocalDateTime start, LocalDateTime end, Boolean toBeAnalyzed, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Event where time < :givenDate")
    void deleteInBulkBeforeDate(@Param("givenDate") LocalDateTime givenDate);

    @Modifying
    @Transactional
    @Query("update Event e set e.analysisRequired = true where type(e) = :clazz and e.duration > :minDuration")
    void updateInBulkToBeAnalyzedByType(@Param("clazz") Class<? extends Event> clazz, @Param("minDuration") int minDuration);


    @Query("SELECT new com.piisw.jpa.repositories.ServerStatistic(e.server, COUNT(e)) FROM Event e GROUP BY e.server")
    List<ServerStatistic> countEventsByServer();

    @Query(value = """
            SELECT new com.piisw.jpa.repositories.FollowerEventInfoDTO(
                        c.content, e.time, e.analysisRequired, c.content, f.subscriptionDate)
                        FROM Event e
                        JOIN e.comments c
                        JOIN c.follower f
                        WHERE f.userId = :userId
            """)
    List<FollowerEventInfoDTO> findEventInfoByFollowerUserId(@Param("userId") String userId);
}
