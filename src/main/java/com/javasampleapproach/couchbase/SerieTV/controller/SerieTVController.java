package com.javasampleapproach.couchbase.SerieTV.controller;

import com.javasampleapproach.couchbase.SerieTV.model.SerieTV;
import com.javasampleapproach.couchbase.SerieTV.service.SerieTVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/serieTV")
public class SerieTVController {

    @Autowired
    private SerieTVService serieTVService;

    @CrossOrigin
    @GetMapping(value = "all")
    private ResponseEntity getAllSerieTVs() {
        try {
            List<SerieTV> serieTVs = this.serieTVService.getAllSerieTVs();
            return ResponseEntity.status(HttpStatus.OK).header("Lista SerieTV", "--- OK --- Lista SerieTV Trovata Con Successo").body(serieTVs);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(value = "/formato/{formato}")
    private ResponseEntity getSerieTVsByFormatoQuery(@PathVariable String formato) {
        try {
            List<SerieTV> serieTVsByFormatoQuery = this.serieTVService.getSerieTVsByFormatoQuery(formato);
            return ResponseEntity.status(HttpStatus.OK).header("Lista serieTV per Formato", "--- OK --- Lista serieTV per Formato Trovata Con Successo").body(serieTVsByFormatoQuery);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "/insertSerieTV")
    public ResponseEntity addSerieTV(@RequestBody SerieTV stv) {
        try {
            SerieTV serieTVSalvato = serieTVService.addSerieTV(stv);
            return ResponseEntity.status(HttpStatus.CREATED).header("Creazione Film", "--- OK --- SerieTV Creata Con Successo").body(getAllSerieTVs().getBody());
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(value = "/insertLocalListSerieTV/{formato}")
    public ResponseEntity addSerieTVsList(@PathVariable String formato) {
        try {
            List<SerieTV> listaSerieTV = serieTVService.addListaSerieTVs(formato);
            return ResponseEntity.status(HttpStatus.CREATED).header("Creazione Lista SerieTV", "--- OK --- Lista SerieTV Creata Con Successo").body(listaSerieTV);
        } catch (Exception e) {
            throw e;
        }
    }
}
