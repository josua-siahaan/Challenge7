package com.binar.challenge4.repository;

import com.binar.challenge4.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllBySeatIdAndAndIsAvailable(Long seatId, Boolean isAvailable);
    Seat findSeatsBySeatNumberAndStudio(String seatNumber, String studio);
}
