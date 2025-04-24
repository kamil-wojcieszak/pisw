package com.piisw.jpa.repositories;

import com.piisw.jpa.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

    Page<Event> findByTimeBetweenAndAnalysisRequired(LocalDateTime start, LocalDateTime end, Boolean toBeAnalyzed, Pageable pageable);
}
