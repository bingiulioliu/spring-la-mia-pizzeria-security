package org.lessons.java.spring_la_mia_pizzeria_crud.service;

import java.util.List;
import java.util.Optional;
import java.util.Locale.Category;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Offerta;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizzeria;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.OffertaRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service 
public class PizzaService {
    @Autowired 
    private PizzeriaRepository pizzeriaRepository;
    @Autowired 
    private IngredientRepository ingredientRepository;
    @Autowired 
    private OffertaRepository offertaRepository;

    public List<Pizzeria> findAll(){
        return pizzeriaRepository.findAll();
    }

    public Pizzeria getById(Integer id){
        Optional<Pizzeria> pizzaAttempt = pizzeriaRepository.findById(id);

        if (pizzaAttempt.isEmpty()){

        }

        return pizzaAttempt.get();
    }

    public Optional<Pizzeria> findById(Integer id){
        return pizzeriaRepository.findById(id);
    }

    public Pizzeria create (Pizzeria pizza){
        return pizzeriaRepository.save(pizza);
    }

    public Pizzeria update (Pizzeria pizza){
        return pizzeriaRepository.save(pizza);
    }

    public void delete (Pizzeria pizza){
        for (Offerta offertaToDelete : pizza.getOffer()){
            offertaRepository.delete(offertaToDelete);
        }
        pizzeriaRepository.delete(pizza);
    }

    public void deleteById (Integer id){
        Pizzeria pizza = getById(id);

        for (Offerta offertaToDelete : pizza.getOffer()){
            offertaRepository.delete(offertaToDelete);
        }
        pizzeriaRepository.delete(pizza);
    }
}
