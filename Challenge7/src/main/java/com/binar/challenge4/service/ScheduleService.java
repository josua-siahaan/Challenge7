package com.binar.challenge4.service;

import com.binar.challenge4.DTO.ScheduleDTO;
import com.binar.challenge4.model.Film;
import com.binar.challenge4.model.Schedule;
import com.binar.challenge4.repository.FilmRepository;
import com.binar.challenge4.repository.ScheduleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private FilmRepository filmRepository;

    @Transactional
    public Schedule addSchedule(ScheduleDTO schedule, Long filmCode) throws SQLException {
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);

        }
        catch (SQLException e){
            e.printStackTrace();
            connection.rollback();
        }
        ObjectMapper mapperObj = new ObjectMapper();
        mapperObj.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        DateFormat df = new SimpleDateFormat("HH:mm");
        mapperObj.setDateFormat(df);
        Film film = filmRepository.findById(filmCode).get();
        Schedule schedule1 = new Schedule();
        schedule1.setFilm(film);
        schedule1.setStartHour(schedule.getJamMulai());
        schedule1.setPremieredDate(schedule.getTanggalTayang());
        schedule1.setPrice(schedule.getHarga());
        schedule1.setEndHour(schedule.getJamSelesai());
        log.info("Add Data Schedule With Parameter Join Film Code Success");
        connection.commit();
        return scheduleRepository.save(schedule1);
    }

    @Transactional(readOnly = true)
    public List<Schedule> getScheduleByFilmCode(Long filmCode){
        log.info("Get Data Schedule By Film Id Success");
        return scheduleRepository.findSchedulesByFilmFilmCode(filmCode);
    }

    @Transactional(readOnly = true)
    public List<Schedule> getScheduleByFilmName(String filmName){
        log.info("Get Data Schedule By Film Name Success");
        return scheduleRepository.findSchedulesByFilmFilmName(filmName);
    }
    @Transactional(readOnly = true)
    public List<Schedule> getScheduleByFilmIsPremiered(Boolean isPremiered){
        log.info("Get Data Film By Is Premiered Success");
        return scheduleRepository.findSchedulesByFilmIsPremiered(isPremiered);
    }

    @Transactional(readOnly = true)
    public List<Schedule> getAllSchedule(){
        log.info("Get Data Film By Is Premiered Success");
        return scheduleRepository.findAll();
    }
}