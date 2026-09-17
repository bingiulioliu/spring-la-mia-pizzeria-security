package org.lessons.java.spring_la_mia_pizzeria_crud.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity 
@Table (name = "offerte")
public class Offerta {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    // Pizze da cui dipende
    @NotNull (message = "La data di inizio offerta è obbligatoria")
    @FutureOrPresent (message = "La data non può essere passata")
    private LocalDate offerStart;

    @Future (message = "La data non può essere passata")
    private LocalDate offerEnd;

    @NotNull (message = "Titolo offerta obblicatorio")
    @Size(min=2, max=70, message = "Titolo deve essere compreso tra 2 e 70 caratteri")
    private String title;

    // Setto relazione N:1
    @ManyToOne 
    @JsonIgnore 
    @JoinColumn (name = "pizza_id", nullable = false)
    private Pizzeria pizza;


    public Pizzeria getPizza() {
        return pizza;
    }

    public void setPizza(Pizzeria pizza) {
        this.pizza = pizza;
    }

    // Getter e setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getOfferStart() {
        return offerStart;
    }

    public void setOfferStart(LocalDate offerStart) {
        this.offerStart = offerStart;
    }

    public LocalDate getOfferEnd() {
        return offerEnd;
    }

    public void setOfferEnd(LocalDate offerEnd) {
        this.offerEnd = offerEnd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
}
