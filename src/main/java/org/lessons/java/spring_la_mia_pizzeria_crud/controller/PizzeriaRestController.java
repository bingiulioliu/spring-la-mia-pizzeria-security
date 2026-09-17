package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;
import java.util.Optional;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizzeria;
import org.lessons.java.spring_la_mia_pizzeria_crud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pizzas")
public class PizzeriaRestController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public List<Pizzeria> index() {
        List<Pizzeria> pizzas = pizzaService.findAll();
        return pizzas;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizzeria> show(@Valid @PathVariable Integer id) {
        Optional<Pizzeria> pizzaAttempt = pizzaService.findById(id);

        if (pizzaAttempt.isEmpty()) {
            return new ResponseEntity<Pizzeria>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Pizzeria>(pizzaAttempt.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Pizzeria> store(@Valid @RequestBody Pizzeria pizza) {
        return new ResponseEntity<Pizzeria>(pizzaService.create(pizza), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizzeria> update(@Valid @RequestBody Pizzeria pizza, @PathVariable Integer id) {

        if (pizzaService.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pizza.setId(id);
        return new ResponseEntity<Pizzeria>(pizzaService.update(pizza), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pizzeria> delete(@Valid @PathVariable Integer id) {
        if (pizzaService.findById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        pizzaService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
