package com.binar.challenge4.service;

import com.binar.challenge4.model.Film;
import com.binar.challenge4.model.Invoice;
import com.binar.challenge4.model.Schedule;
import com.binar.challenge4.model.Seat;
import com.binar.challenge4.repository.FilmRepository;
import com.binar.challenge4.repository.InvoiceRepository;
import com.binar.challenge4.repository.ScheduleRepository;
import com.binar.challenge4.repository.SeatRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.*;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoice() {
        return invoiceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public JasperPrint generateInvoice(Long parameter) throws Exception{
        InputStream fileReport = new ClassPathResource("report/InvoiceReport.jasper").getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(fileReport);
        Map<String, Object> params = new HashMap<>();
        params.put("invoiceId", parameter);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, getConnection());
        return jasperPrint;
    }

    @Transactional
    public Invoice addDataForBooking(Long filmCode, Long scheduleId, Long seatId) throws Exception {
        Connection connection = dataSource.getConnection();
        try{
            connection.setAutoCommit(false);

        }
        catch (SQLException e){
            e.printStackTrace();
            connection.rollback();
        }
        Film film = filmRepository.findById(filmCode).orElseThrow(() -> new Exception("Film Code Tidak Ada"));
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new Exception("Schedule Tidak Ada"));
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new Exception("Seat Id Tidak Ada"));

        List<Invoice> invoices = invoiceRepository.findByFilmAndSchedule(film, schedule);
        for(Invoice i : invoices){
            if(i.getSeats().equals(seat)){
                throw new Exception("Seat has already been booked.");
            }
        }

        Invoice invoice = new Invoice();
        invoice.setFilm(film);
        invoice.setSchedule(schedule);
        invoice.setSeats(seat);
        connection.commit();

        return invoiceRepository.save(invoice);
    }
}
