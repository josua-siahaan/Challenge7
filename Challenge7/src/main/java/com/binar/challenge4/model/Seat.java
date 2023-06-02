package com.binar.challenge4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    private String seatNumber;
    private Boolean isAvailable;
    private String studio;

    @OneToMany(mappedBy = "seats")
    @JsonIgnore
    private List<Invoice> invoice;
}
