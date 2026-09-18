package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Ingredient;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizzeria;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller 
@RequestMapping ("/ingredients")
public class IngredientController {
    
    @Autowired 
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredients", ingredientRepository.findAll());
        return "ingredients/index";
    }
    
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("edit", false);
        return "ingredients/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute ("ingredient") Ingredient newIngredient,
        BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("edit", false);
            return "ingredients/create-or-edit";
        }
        
        ingredientRepository.save(newIngredient);
        return "redirect:/ingredients";
    }
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ingredient", ingredientRepository.findById(id).get());
        model.addAttribute("edit", true);
        return "ingredients/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@Valid @ModelAttribute ("ingredient") Ingredient newIngredient,
        BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "ingredients/create-or-edit";
        }
        
        ingredientRepository.save(newIngredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("ingredient", ingredientRepository.findById(id).get());
        return "ingredients/show";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        // Recupero l'ingrediente che voglio eliminare
        Ingredient ingredientToDelete = ingredientRepository.findById(id).get();
        
        // Scorro tutte le pizze associate all'ingrediente
        for (Pizzeria linkedPizza : ingredientToDelete.getPizzas()){
            // Da ogni pizza associata, rimuovo l'ingrediente
            linkedPizza.getIngredients().remove(ingredientToDelete);
        }
        
        ingredientRepository.delete(ingredientToDelete);
        return "redirect:/ingredients";
    }
    
}
