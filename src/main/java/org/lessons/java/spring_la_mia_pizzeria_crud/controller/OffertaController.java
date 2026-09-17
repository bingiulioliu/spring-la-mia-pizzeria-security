package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Offerta;
import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizzeria;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.OffertaRepository;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzeriaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping ("/offerte")
public class OffertaController {
    
    //@Autowired 
    //private OffertaRepository offertaRepository;

    private final PizzeriaRepository pizzeriaRepository;
    private final OffertaRepository offertaRepository;
    public OffertaController(OffertaRepository offertaRepository, PizzeriaRepository pizzeriaRepository){
        this.offertaRepository = offertaRepository;
        this.pizzeriaRepository = pizzeriaRepository;
    }

    @GetMapping("/create/{pizzaId}")
    public String create(@PathVariable Integer pizzaId, Model model) {
        Pizzeria pizza = pizzeriaRepository.findById(pizzaId)
                .orElseThrow(() -> new IllegalArgumentException("Pizza non trovata con ID: " + pizzaId));

        Offerta offerta = new Offerta();
        offerta.setPizza(pizza);

        model.addAttribute("nuovaOfferta", offerta);
        model.addAttribute("edit", false);

        return "offerte/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute ("nuovaOfferta") Offerta nuovaOfferta,
        BindingResult bindingResult, Model model) {

        if (nuovaOfferta.getOfferStart() != null && nuovaOfferta.getOfferEnd() != null){
            if (!nuovaOfferta.getOfferEnd().isAfter(nuovaOfferta.getOfferStart())) {
            // Collega l'errore direttamente al campo offerEnd
            bindingResult.rejectValue("offerEnd", "error.offerEnd", "La data di fine deve essere successiva alla data di inizio");
            }
        }
        
        if(bindingResult.hasErrors()){
            model.addAttribute("edit", false);
            return "offerte/create-or-edit";
        }

        offertaRepository.save(nuovaOfferta);
        
        return "redirect:/pizzas/" + nuovaOfferta.getPizza().getId();
    }

    @GetMapping ("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){

        model.addAttribute("nuovaOfferta", offertaRepository.findById(id).get());
        model.addAttribute("edit", true);

        return "offerte/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
        @Valid @ModelAttribute ("nuovaOfferta") Offerta nuovaOfferta,
        BindingResult bindingResult, Model model) {

        nuovaOfferta.setId(id);

        if (nuovaOfferta.getOfferStart() != null && nuovaOfferta.getOfferEnd() != null){
            if (!nuovaOfferta.getOfferEnd().isAfter(nuovaOfferta.getOfferStart())) {
            // Collega l'errore direttamente al campo offerEnd
            bindingResult.rejectValue("offerEnd", "error.offerEnd", "La data di fine deve essere successiva alla data di inizio");
            }
        }
        
        if (bindingResult.hasErrors()){
            model.addAttribute("edit", true);
            return "offerte/create-or-edit";
        }
        
        offertaRepository.save(nuovaOfferta);
        return "redirect:/pizzas/" + nuovaOfferta.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Offerta offerta = offertaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Offerta non trovata con ID: " + id));

        offertaRepository.deleteById(id);
        
        return "redirect:/pizzas/" + offerta.getPizza().getId();
    }
    
    
}
