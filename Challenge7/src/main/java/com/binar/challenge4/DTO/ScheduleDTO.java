package com.binar.challenge4.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleDTO {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalTayang;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime jamMulai;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime jamSelesai;
    private BigDecimal harga;
}
