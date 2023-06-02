package com.binar.challenge4.repository;

import com.binar.challenge4.model.Film;
import com.binar.challenge4.model.Invoice;
import com.binar.challenge4.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByFilmAndSchedule(Film film, Schedule schedule);
}
