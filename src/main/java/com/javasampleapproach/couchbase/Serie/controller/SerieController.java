package com.javasampleapproach.couchbase.Serie.controller;

import com.javasampleapproach.couchbase.Serie.service.SerieService;
import com.javasampleapproach.couchbase.Serie.model.Serie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/serie")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllSerie() {
        try {
            List<Serie> serie = this.serieService.getAllSerie();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Serie","--- OK --- Lista Serie Trovata Con Successo").body(serie);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/byName/{name}")
    private ResponseEntity getAllSerieByName(@PathVariable String name) {
        try {
            List<Serie> serieByValue = this.serieService.getAllSerieByName(name);
            return ResponseEntity.status(HttpStatus.OK).header("Lista Serie Per Nome", "--- OK --- Lista Serie Per Nome Trovata Con Successo").body(serieByValue);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    private ResponseEntity getSerieById(@PathVariable String id) {
        try {
            Serie serieById = this.serieService.getSerieById(id);
            return ResponseEntity.status(HttpStatus.FOUND).header("Ricerca Serie", "--- OK --- Serie Trovato Con Successo").body(serieById);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertSerie")
    public ResponseEntity addSerie(@RequestBody Serie g) {
        try {
            Serie serieSalvata = serieService.addSerie(g);
            return ResponseEntity.status(HttpStatus.CREATED).header("Creazione Serie", "--- OK --- Serie Creata Con Successo").body(serieSalvata);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PutMapping(value = "/upDateSerieById/{id}")
    private ResponseEntity updateSerie(@RequestBody Serie nuovaSerie, @PathVariable String id) {
        try {
            Serie serieAggiornata = serieService.updateSerie(nuovaSerie, id);
            return ResponseEntity.status(HttpStatus.OK).header("Aggiornamento Serie", "--- OK --- Serie Aggiornata Con Successo").body(serieAggiornata);
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @DeleteMapping(value = "/deleteSerieById/{id}")
    private ResponseEntity deleteSerieById(@PathVariable String id) {
        try {
            serieService.deleteSerieById(id);
            return ResponseEntity.status(HttpStatus.OK).header("Eliminazione Serie", "--- OK --- Serie Eliminata Con Successo").body("La Serie con Id: " + id + " è stata Eliminata con Successo");
        } catch (Exception e) {
            throw e;
        }
    }

    @CrossOrigin
    @PostMapping(value = "/insertLocalListSerie/{formato}")
    public ResponseEntity addSerieByFormato(@PathVariable String formato) {
        try {
            List<Serie> listaSerie = serieService.addListaSerieByFormato(formato);
            return ResponseEntity.status(HttpStatus.CREATED).header("Creazione Lista Serie", "--- OK --- Lista Serie Creata Con Successo").body(listaSerie);
        } catch (Exception e) {
            throw e;
        }
    }

}