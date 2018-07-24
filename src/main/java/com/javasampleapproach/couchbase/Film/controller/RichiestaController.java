package com.javasampleapproach.couchbase.Film.controller;

import com.javasampleapproach.couchbase.Film.model.Film;
import com.javasampleapproach.couchbase.Film.model.Richiesta;
import com.javasampleapproach.couchbase.Film.service.RichiestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/richieste")
public class RichiestaController {

    @Autowired
    private RichiestaService richiestaService;

    @CrossOrigin
    @GetMapping(value = "/all")
    private ResponseEntity getAllRichieste() {
        try {
            List<Richiesta> richieste = this.richiestaService.getAllRichieste();
            return ResponseEntity.status(HttpStatus.OK).header("Lista Richieste", "--- OK --- Lista Richieste Trovata Con Successo").body(richieste);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "/insertRichiesta")
    public ResponseEntity addRichiesta(@RequestBody Richiesta r) {
        try {
            Richiesta richiestaDaSalvare = this.richiestaService.addRichiesta(r);
            return ResponseEntity.status(HttpStatus.CREATED).header("Creazione Richiesta", "--- OK --- Richiesta Creata Con Successo").body(getAllRichieste().getBody());
        } catch (Exception e) {
            throw e;
        }
    }
}
