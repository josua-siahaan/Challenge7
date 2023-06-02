package com.binar.challenge4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filmCode;
    private String filmName;
    private Boolean isPremiered;

    @OneToMany(mappedBy = "film")
    @JsonIgnore
    private List<Schedule> schedule = new ArrayList<>();

    @OneToMany(mappedBy = "film")
    @JsonIgnore
    private List<Invoice> invoice;
}
