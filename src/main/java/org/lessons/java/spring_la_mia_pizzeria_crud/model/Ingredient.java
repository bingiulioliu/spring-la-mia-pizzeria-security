package org.lessons.java.spring_la_mia_pizzeria_crud.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity 
@Table (name = "ingredients")
public class Ingredient {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size (min = 5, max = 50, message = "Scegliere una lunghezza tra 5 e 50 caratteri")
    @Column (nullable = false)
    @NotBlank
    private String name;

    @Lob 
    private String description;

    @ManyToMany (mappedBy = "ingredients")
    @JsonIgnore 
    private List<Pizzeria> pizzas;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Pizzeria> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizzeria> pizzas) {
        this.pizzas = pizzas;
    }

    
}
