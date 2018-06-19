package com.javasampleapproach.couchbase.Gemma.controller;

import com.javasampleapproach.couchbase.Gemma.service.GemmaService;
import com.javasampleapproach.couchbase.Gemma.model.Gemma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/gemme")
public class GemmaController {

    @Autowired
    private GemmaService gemmaService;

    @GetMapping(value = "/all")
    private ResponseEntity getAllGemme() {
        try {
            List<Gemma> gemme = this.gemmaService.getAllGemme();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Gemme","--- OK --- Lista Gemme Trovata Con Successo").body(gemme);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/byPrice/{price}")
    private ResponseEntity getAllGemmeByPrice(@PathVariable Integer price) {
        try {
            List<Gemma> gemmeByValue = this.gemmaService.getAllGemmeByPrice(price);
            return ResponseEntity.status(HttpStatus.OK).header("Lista Gemme Per Prezzo", "--- OK --- Lista Gemme Per Prezzo Trovata Con Successo").body(gemmeByValue);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/{id}")
    private ResponseEntity getGemmaById(@PathVariable String id) {
        try {
            Gemma gemmaById = this.gemmaService.getGemmaById(id);
            return ResponseEntity.status(HttpStatus.FOUND).header("Ricerca Gemma", "--- OK --- Gemma Trovato Con Successo").body(gemmaById);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "/insertGemma")
    public ResponseEntity addGemma(@RequestBody Gemma g) {
        try {
            Gemma gemmaSalvata = gemmaService.addGemma(g);
            return ResponseEntity.status(HttpStatus.CREATED).header("Creazione Gemma", "--- OK --- Gemma Creata Con Successo").body(gemmaSalvata);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping(value = "/upDateGemmaById/{id}")
    private ResponseEntity updateGemma(@RequestBody Gemma nuovaGemma, @PathVariable String id) {
        try {
            Gemma gemmaAggiornata = gemmaService.updateGemma(nuovaGemma, id);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Gemma", "--- OK --- Gemma Aggiornata Con Successo").body(gemmaAggiornata);
        } catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping(value = "/deleteGemmaById/{id}")
    private ResponseEntity deleteGemmaById(@PathVariable String id) {
        try {
            gemmaService.deleteGemmaById(id);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Gemma", "--- OK --- Gemma Eliminata Con Successo").body("La Gemma con Id: " + id + " è stata Eliminata con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

}