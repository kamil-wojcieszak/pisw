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

import java.time.LocalDateTime;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    Page<Event> findByTimeBetweenAndAnalysisRequired(LocalDateTime start, LocalDateTime end, Boolean toBeAnalyzed, Pageable pageable);

    @Modifying
    @Transactional
    @Query("delete from Event where time < :givenDate")
    void deleteInBulkBeforeDate(@Param("givenDate") LocalDateTime givenDate);

    @Modifying
    @Transactional
    @Query("UPDATE Event e SET e.analysisRequired = true WHERE TYPE(e) = :clazz AND e.duration > :minDuration")
    int updateInBulkToBeAnalyzedByType(@Param("clazz") Class<? extends Event> clazz, @Param("minDuration") int minDuration);

}
