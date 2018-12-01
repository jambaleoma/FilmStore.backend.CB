package com.javasampleapproach.couchbase.Stagione.controller;

import com.javasampleapproach.couchbase.Stagione.model.Stagione;
import com.javasampleapproach.couchbase.Stagione.service.StagioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/stagioni")
public class StagioneController {

    @Autowired
    private StagioneService stagioneService;

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllStagioni() {
        try {
            List<Stagione> stagioni = this.stagioneService.getAllStagioni();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Stagioni","--- OK --- Lista Stagioni Trovata Con Successo").body(stagioni);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/bySerieId/{serieId}")
    private ResponseEntity getAllStagioniByIdSerie(@PathVariable String serieId) {
        try {
            List<Stagione> stagioniByIdSerie = this.stagioneService.getAllStagioniByIdSerie(serieId);
            return ResponseEntity.status(HttpStatus.OK).header("Lista Stagioni Per Serie ID", "--- OK --- Lista Stagioni Per Serie ID Trovata Con Successo").body(stagioniByIdSerie);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    private ResponseEntity getStagioneById(@PathVariable String id) {
        try {
            Stagione stagioneById = this.stagioneService.getStagioneById(id);
            return ResponseEntity.status(HttpStatus.OK).header("Ricerca Stagione", "--- OK --- Stagione Trovata Con Successo").body(stagioneById);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertStagione")
    public ResponseEntity addStagione(@RequestBody Stagione s) {
        try {
            stagioneService.addStagione(s);
            return ResponseEntity.status(HttpStatus.CREATED).header("Creazione Stagione", "--- OK --- Stagione Creata Con Successo").body(getAllStagioniByIdSerie(s.getSerie_id()).getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/upDateStagioneById/{id}")
    private ResponseEntity updateStagione(@RequestBody Stagione nuovaStagione, @PathVariable String id) {
        try {
            stagioneService.updateStagione(nuovaStagione, id);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Stagione", "--- OK --- Stagione Aggiornata Con Successo").body(getAllStagioniByIdSerie(nuovaStagione.getSerie_id()).getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteStagioneById/{id}")
    private ResponseEntity deleteStagioneById(@PathVariable String id) {
        try {
            stagioneService.deleteStagioneById(id);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Stagione", "--- OK --- Stagione Eliminata Con Successo").body("La Stagione con Id: " + id + " è stata Eliminata con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteStagioniBySerieId/{id}")
    private ResponseEntity deleteStagioniBySerieId(@PathVariable String id) {
        try {
            stagioneService.deleteStagioniBySerieId(id);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Stagioni", "--- OK --- Stagioni Eliminate Con Successo").body("Le Stagioni della Serie con Id: " + id + " sono state Eliminate con Successo");
        } catch (Exception e) {
            throw e;
        }
    }
}
