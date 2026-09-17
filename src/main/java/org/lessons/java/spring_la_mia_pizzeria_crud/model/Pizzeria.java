package org.lessons.java.spring_la_mia_pizzeria_crud.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pizzas")
public class Pizzeria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size (min = 5, max = 50, message = "Scegliere una lunghezza tra 5 e 50 caratteri")
    @Column (nullable = false)
    @NotBlank
    private String name;

    @NotBlank(message = "Inserire una descrizione")
    private String description;

    @NotBlank(message = "Inserire un url")
    private String img;

    @NotNull(message = "Inserire un prezzo")
    @Min(value = 1, message = "Il prezzo non può essere negativo o pari a zero")
    private Double price;

    // Setto relazione 1:N
    // Aggiungo CascadeTyoe.REMOVE per non ciclare in caso di eliminazione
    @OneToMany (mappedBy = "pizza", cascade = CascadeType.REMOVE)
    @JsonIgnore 
    private List<Offerta> offerte;

    // Relazione N:N
    @ManyToMany 
    @JsonIgnore 
    @JoinTable (
        name = "ingredient_pizza",
        joinColumns = @JoinColumn(name = "pizza_id"),
        inverseJoinColumns = @JoinColumn (name = "ingredient_id")
    )
    
    private List<Ingredient> ingredients; 

    public List<Ingredient> getIngredients() {
        return ingredients;
    }


    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }


    public List<Offerta> getOffer(){
        return offerte;
    }


    public void setPizzas(List<Offerta> offerte){
        this.offerte = offerte;
    }

    // Getter e setter
    public Integer getId(){
        return this.id;
    }
    public void setId( Integer id ){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getImg(){
        return this.img;
    }
    public void setImg(String img){
        this.img = img;
    }

    public Double getPrice(){
        return this.price;
    }
    public void setPrice(Double price){
        this.price = price;
    }
}
