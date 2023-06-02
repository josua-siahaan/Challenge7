package com.binar.challenge4.controller;

import com.binar.challenge4.DTO.ScheduleDTO;
import com.binar.challenge4.model.Schedule;
import com.binar.challenge4.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/addSchedule")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Schedule> addSchedule(@RequestBody ScheduleDTO schedule, @RequestParam Long filmCode) throws SQLException {
        Schedule schedule1 = scheduleService.addSchedule(schedule, filmCode);
        return ResponseEntity.ok(schedule1);
    }

    @GetMapping("/getScheduleByFilmCode")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Schedule>> getScheduleByFilmCode(@RequestParam(name = "filmCode") Long filmCode){
        List<Schedule> allSchedule = scheduleService.getScheduleByFilmCode(filmCode);
        return ResponseEntity.ok(allSchedule);
    }

    @GetMapping("/getScheduleByFilmName")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Schedule>> getScheduleByFilmCode(@RequestParam(name = "filmName") String filmName){
        List<Schedule> Schedule = scheduleService.getScheduleByFilmName(filmName);
        return ResponseEntity.ok(Schedule);
    }

    @GetMapping("/getScheduleByFilmIsPremiered")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<Schedule>> getScheduleByFilmIsPremiered(@RequestParam(name = "isPremiered") Boolean isPremiered){
        List<Schedule> schedule = scheduleService.getScheduleByFilmIsPremiered(isPremiered);
        return ResponseEntity.ok(schedule);
    }

    @GetMapping("/getAllSchedule")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<Schedule> getAllSchedule(){
        return scheduleService.getAllSchedule();
    }
}
