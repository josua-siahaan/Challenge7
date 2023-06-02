package com.binar.challenge4.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate premieredDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startHour;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endHour;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "film_code")
    @JsonIgnore
    private Film film;

    @OneToMany(mappedBy = "schedule")
    @JsonIgnore
    private List<Invoice> invoice;
}
