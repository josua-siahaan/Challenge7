package com.binar.challenge4.repository;

import com.binar.challenge4.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule>findSchedulesByFilmFilmCode(Long filmCode);

    Schedule findScheduleByScheduleId(Long scheduleId);
    List<Schedule>findSchedulesByFilmFilmName(String filmName);
    List<Schedule> findSchedulesByFilmIsPremiered(Boolean isPremiered);
}
