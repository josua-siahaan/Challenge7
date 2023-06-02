package com.binar.challenge4.service;

import com.binar.challenge4.model.Schedule;
import com.binar.challenge4.model.Seat;
import com.binar.challenge4.repository.ScheduleRepository;
import com.binar.challenge4.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private DataSource dataSource;

    @Transactional(readOnly = true)
    public List<Seat> getAllSeat(){
        return seatRepository.findAll();

    }
    @Transactional
    public Seat addSeats(String seatNumber, boolean isAvailable, String studioName) throws Exception {
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);

        }
        catch (SQLException e){
            e.printStackTrace();
            connection.rollback();
        }
        Seat seats = seatRepository.findSeatsBySeatNumberAndStudio(seatNumber, studioName);
        if(seats != null){
            throw new Exception("Seats already exist");
        }
        Seat seat = new Seat();
        seat.setSeatNumber(seatNumber);
        seat.setIsAvailable(isAvailable);
        seat.setStudio(studioName);
        connection.commit();

        return seatRepository.save(seat);
    }
}
